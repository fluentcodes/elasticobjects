package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/

/**
 * Executes a template content.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 11:33:49 CET 2020
 */
public class TemplateCall extends CallImpl implements CallContent, SimpleCommand {
    /*.{javaStaticNames}|*/
    public static final String CONTENT = "content";
    /*.{}.*/
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);
    /*.{}.*/
    /*.{javaInstanceVars}|*/
    private String content;
    /*.{}.*/

    public TemplateCall() {
        super.setTargetPath(TARGET_AS_STRING);
    }

    public TemplateCall(final String content) {
        this();
        this.content = content;
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 3) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'.");
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null || array.length == 0) {
            throw new EoException("Set by empty input values");
        }
        if (array.length > 0) {
            if (array[0].replaceAll("\\s", "").isEmpty()) {
                setSourcePath(PathElement.SAME);
            } else {
                setSourcePath(array[0]);
            }
        }
        if (array.length > 1) {
            setCondition(array[1]);
        }
    }

    public boolean isContentActive() {
        return hasContent() && TemplateMarker.CURLY.hasStartSequence(content);
    }

    /**
     *
     */
    public String execute(EOInterfaceScalar eo) {
        if (!hasContent()) {
            throw new EoException("No content for template?!");
        }
        if (!init(eo)) {
            return "";
        }
        return new Parser(content).parse(eo);
    }

    public TemplateCall setTargetPath(String x) {
        super.setTargetPath(x);
        return this;
    }
    /*.{javaAccessors}|*/

    public String getContent() {
        return this.content;
    }

    /**
     * A content for different calls. In a template context the content of the markup.
     */

    public TemplateCall setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }
    /*.{}.*/
}
