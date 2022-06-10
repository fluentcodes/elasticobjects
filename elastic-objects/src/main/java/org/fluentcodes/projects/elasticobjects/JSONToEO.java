package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.PathElement.V_MODEL;

/**
 * A JSONToEO  extracts characters and tokens from a JSON serialized String.
 * Core source from JSON.org.
 *
 * @author Werner Diwischek
 * @since 10.6.2016
 */
public class JSONToEO {
    //http://stackoverflow.com/questions/3651725/match-multiline-text-using-regular-expression
    public static final Pattern JSON_PATTERN = Pattern.compile("^[\\{\\[]");
    public static final Pattern JSON_MAP_PATTERN = Pattern.compile("^\\{");
    public static final Pattern JSON_LIST_PATTERN = Pattern.compile("^\\[");
    private static final Logger LOG = LogManager.getLogger(JSONToEO.class);
    public static final String COMMENT = "_comment";
    private ConfigMaps provider;
    private long character;
    private long index;
    private boolean eof;
    private long line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;
    private boolean parseCalls = false;
    /**
     * Construct a JSONToEO from a getSerialized.
     *
     * @param s A source getSerialized.
     */
    private String json;


    /**
     * Construct a JSONToEO from a Reader.
     *
     * @param reader A reader.
     */
    private JSONToEO(Reader reader) {
        this.reader = reader.markSupported()
                ? reader
                : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

    public JSONToEO(String json, ConfigMaps provider) {
        this(new StringReader(json));
        this.json = json;
        this.provider = provider;
    }

    public JSONToEO(String json) {
        this(new StringReader(json));
        this.json = json;
    }

    /**
     * Back up one character. This provides a sort of lookahead capability,
     * so that you can test for a digit or letter before attempting to set
     * the next number or identifier.
     */
    private void back()  {
        if (this.usePrevious || this.index <= 0) {
            throw new EoException("Stepping back two steps is not supported");
        }
        this.index -= 1;
        this.character -= 1;
        this.usePrevious = true;
        this.eof = false;
    }

    private boolean end() {
        return this.eof && !this.usePrevious;
    }

    private boolean isEof()  {
        try {
            return reader.read() < 0;
        } catch (IOException e) {
            throw new EoException(e);
        }
    }

    /**
     * Get the next character in the source getSerialized.
     *
     * @return The next character, or 0 if past the end of the source getSerialized.
     */
    private char next()  {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                throw new EoException(exception);
            }

            if (c <= 0) { // End of stream
                this.eof = true;
                c = 0;
            }
        }
        this.index += 1;
        if (this.previous == '\r') {
            this.line += 1;
            this.character = c == '\n' ? 0 : 1;
        } else if (c == '\n') {
            this.line += 1;
            this.character = 0;
        } else {
            this.character += 1;
        }
        this.previous = (char) c;
        return this.previous;
    }

    /**
     * Get the next n characters.
     *
     * @param n The number of characters to take.
     * @return A getSerialized of n characters.
     * @ Substring bounds error if there are not
     *                   n characters remaining in the source getSerialized.
     */
    private String next(int n)  {
        if (n == 0) {
            return "";
        }

        char[] chars = new char[n];
        int pos = 0;

        while (pos < n) {
            chars[pos] = this.next();
            if (this.end()) {
                throw new EoException("Substring bounds error");
            }
            pos += 1;
        }
        return new String(chars);
    }

    /**
     * Get the next char in the getSerialized, skipping whitespace.
     *
     * @return A character, or 0 if there are no more characters.
     * @
     */
    private char nextClean()  {
        for (; ; ) {
            char c = this.next();
            if (c == 0 || c > ' ') {
                return c;
            }
        }
    }

    /**
     * Return the characters up to the next close quote character.
     * Backslash processing is done. The formal JSON format does not
     * allow strings in standard quotes, but an implementation is allowed to
     * accept them.
     *
     * @param quote The quoting character, either
     *              <code>"</code>&nbsp;<small>(double quote)</small> or
     *              <code>'</code>&nbsp;<small>(standard quote)</small>.
     * @return A String.
     * @ Unterminated getSerialized.
     */
    private final String nextString(final char quote, final String specifier)  {
        char c;
        final StringBuffer sb = new StringBuffer();
        for (; ; ) {
            c = this.next();
            switch (c) {
                case 0:
                case '\n':
                    //sb.append('\n');
                    //break;
                case '\r':
                    //sb.append('\r');
                    //break;
                    throw new EoException("Unterminated string cause of an escaped carriage return in a character: '" + specifier + "'.");
                case '\\':
                    c = this.next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(this.next(4), 16));
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                        case '/':
                            sb.append(c);
                            break;
                        default:
                            throw new EoException("Illegal escape.'" + c + "':" + sb.toString() + "(" + specifier + ")");
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }

    private String debug() {
        int position = new Long(index).intValue();
        int positionNext = position + 1;
        int max = position + 50;
        int min = position - 50;
        if (min<0) {
            min = 0;
        }
        if (max>json.length()) {
            max = json.length();
        }
        if (position>max) {
            position = max;
        }
        if (positionNext>max) {
            positionNext = max;
        }
        return (Thread.currentThread().getStackTrace()[2].getMethodName()
                + ": "
                + index + ": "
                + json.substring(min, position) +
                "=="
                + json.substring(position, position) +
                "=="
                + json.substring(positionNext, max));
    }

    public EO mapObject(EO eoCurrent)  {
        if (eoCurrent == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }
        boolean startFlag = true;
        for (; ; ) {
            //Check for closing tag or a key
            char c = this.nextClean();
            if (c == '}') { // empty
                return eoCurrent;
            }
            if (c == ',') {
                final String key = this.nextKey();
                if (this.nextClean() != ':') {
                    new EoException("Expected ':' in the map after the key '" + key + "' but see '" + c + "'': " + this.debug());
                }
                createChild(eoCurrent, key);
            } else if (startFlag) {
                back();
                final String key = this.nextKey();
                if (this.nextClean() != ':') {
                    new EoException("Expected ':' in the map after the key '" + key + "' but see '" + c + "'': " + this.debug());
                }
                createChild(eoCurrent, key);
                startFlag = false;
            } else {
                throw new EoException("Expected colon not found but '" + c + "': " + this.debug());
            }
        }
    }


    private EO mapList(final EO currentEO)  {
        if (currentEO == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }
        int counter = 0;
        for (; ; ) {
            final char next = this.nextClean();
            if (next == ']') {
                return currentEO;
            }
            // closing current builder
            if (next == ',') {
                this.createChild(currentEO, new Integer(counter).toString());
                counter++;
            } else if (counter == 0) {
                back();
                this.createChild(currentEO, new Integer(counter).toString());
                counter++;
            } else {
                throw new EoException("Expected colon is not set but '" + next + "' on line " + this.line + ": " + this.debug());
            }
        }
    }

    public EOInterfaceScalar createChild(EO parentAdapter)  {
        EOInterfaceScalar eo = createChild(parentAdapter, null);
        if (isEof()) {
            return eo;
        }
        if (parseCalls && eo instanceof EoRoot) {
            return eo;
        }
        final char c = nextClean();
        if (c == 0) {
            return eo;
        }
        throw new EoException("Not at the end of the json file!");
    }

    /**
     * Creates the childbuilder for the fieldName depending on the value.
     * It will createHost
     * <ul>
     * <li>maps Map or Object starting with '{'</li>
     * <li>maps List starting with '['</li>
     * <li>Scalar else. </li>
     * </ul>
     *
     * @param eoParent the parent builder where the child will be added
     * @param rawFieldName  The fieldname of the child.
     * @return
     * @
     */
    private EOInterfaceScalar createChild(EO eoParent, final String rawFieldName)  {

        if (eoParent == null) {
            throw new EoException("parent eo is null ...!");
        }

        char c = this.nextClean();
        PathElement pathElement = null;
        switch (c) {
            case '"':
            case '\'':  // String value
                if (rawFieldName == null || rawFieldName.isEmpty()) {
                    throw new EoException(this.getClass().getSimpleName() + " createChildForMap: Value with no name" + debug());
                }
                String value = this.nextString(c, rawFieldName);
                if (rawFieldName.equals(V_MODEL)) {
                    ((EoChild)eoParent).setModels(value);
                    ((EoChild)eoParent).createChild(new PathElement(rawFieldName), value);
                    return eoParent;
                }
                ((EoChild)eoParent).createChild(new PathElement(rawFieldName), value);
                return eoParent;

            case '{':  //
                if (rawFieldName!=null) {// Object value
                    EO child = (EO)((EoChild)eoParent).createChild(new PathElement(rawFieldName, Map.class), null);
                    mapObject(child);
                    return eoParent;
                }
                else {
                    eoParent = mapObject(eoParent); // start parsing
                    return eoParent;
                }
            case '[':
                if (rawFieldName != null) {// List value
                    PathElement pathFromKey = new PathElement(rawFieldName, List.class);
                    EOInterfaceScalar child = ((EoChild)eoParent).createChild(pathFromKey);
                    mapList((EO) child);
                    return child;
                }
                else {
                    mapList(eoParent); // start parsing
                    return eoParent;
                }
        }
        /*
         * Handle unquoted text. This could be the rows true, false, or
         * null, or it can be a number. An implementation (such as this one)
         * is allowed to also accept non-standard forms.
         *
         * Accumulate characters until we reach the end of the text or a
         * formatting character.
         */

        StringBuffer sb = new StringBuffer();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = this.next();
        }
        this.back();

        String value = sb.toString().trim();
        if ("null".equals(value) || value== null || value.isEmpty()) {
            return eoParent;
        }
        if (rawFieldName == null || rawFieldName.isEmpty()) {
            throw new EoException("Null rawFieldName" + debug());
        }
        if (rawFieldName.matches("\\(.*\\).*")) {
            pathElement = new PathElement(rawFieldName);
            return ((EoChild)eoParent).createChild(pathElement, value);
        }
        else {
            Object valueObject = fromJson(value);
            pathElement = new PathElement(rawFieldName);
            return ((EoChild)eoParent).createChild(pathElement, valueObject);
        }
    }

    private static Object fromJson(final String value) {
        if ("true".equals(value)) {
            return true;
        }
        if ("false".equals(value)) {
            return false;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (Exception e) {
            try{
                return Float.parseFloat(value);
            }
            catch (Exception e1) {
                throw new EoException("Could not transform non quoted value '" + value + "'.");
            }
        }
    }


    /**
     * Get the next fileName. The fileName can be a Boolean, Double, Integer,
     * JSONArray, JSONObject, Long, or String, or the JSONObject.NULL standard.
     *
     * @return An standard.
     * @ If syntax error.
     */
    private String nextKey()  {
        char c = this.nextClean();
        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c, " finding key");
        }
        return "";
    }

    /**
     * Make a Exception to signal a syntax error.
     *
     * @param message The error message.
     * @return A Exception standard, suitable for throwing
     */
    private Exception syntaxError(String message) {
        return new EoException(message + this.toString());
    }

    /**
     * Make a printable getSerialized of this JSONToEO.
     *
     * @return " at {index} [character {character} line {line}]"
     */
    public String toString() {
        return " [character " + this.index + " line " +
                this.line + "]";
    }
}
