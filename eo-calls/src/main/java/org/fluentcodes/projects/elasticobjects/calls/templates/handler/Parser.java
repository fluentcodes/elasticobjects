/**
 * <p>
 * Some static utility classes....
 * <p>
 * http://www.jdbee.org<br/>
 * date 10.3.2012<br/>
 *
 * @author Werner Diwischek
 * @version 0.1
 */
package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.calls.templates.handler.HandlerAbstract.DEFAULT_SEPARATOR;

/**
 * Central parser using {@link TemplateMarker}.
 */
public class Parser {
    private static final Pattern NEW_LINE_REMOVE = Pattern.compile("\\\\\n$", Pattern.DOTALL);
    private final TemplateMarker templateMarker;
    private final String template;
    private Matcher match;
    private int end;

    /**
     * Constructor with the template as input. Will use default {@link TemplateMarker}.CURLY value.
     *
     * @param template the template to be parsed.
     */
    public Parser(final String template) {
        this(TemplateMarker.CURLY, template);
    }

    /**
     * Constructor with the template as input.
     *
     * @param templateMarker the values which will be parsed
     * @param template       the template to be parsed.
     */
    public Parser(final TemplateMarker templateMarker, final String template) {
        this.template = template;
        this.templateMarker = templateMarker;
    }

    /**
     * Will start a parsing in a static context.
     *
     * @param template template
     * @param eo       eo
     * @return
     */
    public static String replacePathValues(final String template, final EOInterfaceScalar eo) {
        if (template == null || template.isEmpty()) {
            return template;
        }
        if (TemplateMarker.CURLY.hasStartSequence(template)) {
            return new Parser(template).parse(eo);
        }
        if (TemplateMarker.SQUARE.hasStartSequence(template)) {
            return new Parser(TemplateMarker.SQUARE, template).parse(eo);
        }
        return template;
    }

    public static String replaceEnv(final String key, final String defaultValue) {
        if (System.getenv(key) != null) {
            return System.getenv(key);
        }
        return defaultValue == null || defaultValue.isEmpty() ?
                "Could not find env variable with key '" + key + "'" :
                defaultValue;
    }

    public String getTemplate() {
        return template;
    }

    public boolean hasTemplate() {
        return template != null && !template.isEmpty();
    }

    public int getEnd() {
        return end;
    }

    public String parse() {
        return parse(null);
    }


    /**
     * Main parsing method. Will look for elements defined by {@link TemplateMarker}.
     *
     * @param eo the elastic object which will be used.
     * @return the concatenated result of call results.
     */

    public String parse(final EOInterfaceScalar eo) {
        if (!templateMarker.hasStartSequence(template)) {
            if (template == null) {
                return "";
            }
            return Parser.replacePathValues(template, eo);
        }
        this.match = templateMarker.getVarPattern().matcher(template);
        StringBuilder result = new StringBuilder();
        String parseString = template;
        end = 0;
        while (match.find()) {
            int start = match.start();
            String before = chomp(template.substring(end, start));
            result.append(before);
            end = match.end();
            String callIndicator = match.group(1);
            String callSequence = match.group(2);
            String finish = match.group(3);
            Indicators indicator = Indicators.find(callIndicator);
            handle(eo, callSequence, indicator, finish, result);
        }
        result.append(parseString.substring(end, parseString.length()));
        return Parser.replacePathValues(result.toString(), eo);
    }

    void handle(EOInterfaceScalar eo, String callSequence, Indicators callIndicator, String finish, StringBuilder result) {
        String defaultValue = getDefault(callSequence);
        callSequence = callSequence.replaceAll("\\" + DEFAULT_SEPARATOR + ".*", "");
        String content = templateMarker.isContinueSequence(finish) ? findContent() : null;
        try {
            switch (callIndicator) {
                case VALUE:
                    result.append(EoValueHandler.call(eo, callSequence));
                    break;
                case ENV:
                    result.append(replaceEnv(callSequence, defaultValue));
                    break;
                case SYSTEM:
                    result.append(SystemValueHandler.call(callSequence, defaultValue));
                    break;
                case VALUES:
                    result.append(
                            new ParamsHandler()
                                    .setTemplateMarker(templateMarker)
                                    .setEo(eo)
                                    .setCallDirective(callSequence)
                                    .setDefaultValue(defaultValue)
                                    .setContent(content)
                                    .call()
                    );
                    break;
                case JSON:
                    result.append(
                            new JsonHandler()
                                    .setTemplateMarker(templateMarker)
                                    .setEo(eo)
                                    .setCallDirective(callSequence)
                                    .setDefaultValue(defaultValue)
                                    .setContent(content)
                                    .call()
                    );
                    break;
                case ATTRIBUTES:
                    result.append(
                            new AttributeHandler()
                                    .setTemplateMarker(templateMarker)
                                    .setEo(eo)
                                    .setCallDirective(callSequence)
                                    .setDefaultValue(defaultValue)
                                    .setContent(content)
                                    .call()
                    );
                    break;
                default:
                    throw new EoInternalException("Callindication should be @, #,. or & but is '" + callIndicator + "'!");
            }
        } catch (EoException e) {
            if (eo != null) {
                eo.info(e.getMessage());
            }
            if (defaultValue != null) {
                result.append(defaultValue);
            } else {
                result.append("!!" + e.getMessage() + "!!");
            }
        }
    }


    private String getDefault(String pathOrKey) {
        if (!pathOrKey.contains("|>")) {
            return null;
        }
        return pathOrKey.replaceAll(".*\\|>", "");
    }

    private String chomp(final String prepend) {
        if (NEW_LINE_REMOVE.matcher(prepend).find()) {
            return prepend.substring(0, prepend.length() - 2);
        } else if (prepend.endsWith("\n")) {
            return prepend.substring(0, prepend.length() - 1);
        }
        return prepend;
    }

    private String findContent() {
        StringBuilder content = new StringBuilder();
        int hierarchy = 1;
        while (match.find()) {
            int start = match.start();
            content.append(template.substring(end, start));
            end = match.end();
            String foundEnd = match.group(3);
            String foundAll = match.group();
            if (templateMarker.isCloseSequence(foundAll)) {
                hierarchy--;
                if (hierarchy == 0) {
                    return chomp(content.toString());  // replace backslash+newline or newline from content due to closing tag
                }
            } else if (templateMarker.isContinueSequence(foundEnd)) {
                hierarchy++;
            }
            content.append(foundAll);
        }
        throw new EoException("Could not find closing tag for parseString");
    }

    @Override
    public String toString() {
        return template;
    }
}
