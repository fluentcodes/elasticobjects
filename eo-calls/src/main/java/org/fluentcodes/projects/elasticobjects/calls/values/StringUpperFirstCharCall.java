package org.fluentcodes.projects.elasticobjects.calls.values;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/

/**
 * For setting upper value to EO.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 12:03:32 CET 2020
 */
public class StringUpperFirstCharCall extends CallImpl implements SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/

    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        try {
            return upper((String) eo.get());
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    /**
     * Returns the getSerialized with an upper first character
     *
     * @param item A getSerialized item
     * @return Capitalized item
     */
    public static String upper(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        return item.substring(0, 1).toUpperCase() + item.substring(1);
    }

    public static String upperWord(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        String[] words = item.split("\\s+");
        StringBuilder concat = new StringBuilder();
        for (String word: words) {
            concat.append(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ");
        }
        return concat.toString().replaceAll(" $", "");
    }

    static Pattern WORD_PATTERN = Pattern.compile("([^ -]+)([ -]*)");
    public static String upperWords(String items) {
        StringBuilder upper = new StringBuilder();
        Matcher matcher = WORD_PATTERN.matcher(items);
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            start = matcher.start();
            upper.append(items.substring(end,start));
            end = matcher.end();
            upper.append(upperWord(matcher.group(1)) + matcher.group(2));
        }
        upper.append(items.substring(end,items.length()));
        return upper.toString();
    }

    public static String setter(String item) {
        return "set" + upper(item);
    }

    public static String getter(String item) {
        return "get" + upper(item);
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}
