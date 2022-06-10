package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import java.util.regex.Pattern;

/**
 * Defines some patterns which will be used by the {@link Parser}.
 */
public enum TemplateMarker {
    CURLY("{", "}"),
    SQUARE("[", "]");
    private final String start;
    private final String stop;
    private final Pattern startPattern;
    private final String endSequence;
    private final String closeSequence;
    private final String continueSequence;
    private final Pattern varPattern;

    TemplateMarker(String start, String stop) {
        this.start = start;
        this.stop = stop;
        this.startPattern = Pattern.compile("([" + Indicators.PATTERN + "])\\" + start, Pattern.DOTALL);
        this.varPattern = Pattern.compile("[\t ]*([" + Indicators.PATTERN + "])\\" + start + "(.*?)(\\" + stop + "[\\.\\|])", Pattern.DOTALL);
        this.endSequence = stop + ".";
        this.continueSequence = stop + "|";
        this.closeSequence = "." + start + stop + ".";
    }

    String getStart() {
        return start;
    }

    String getStop() {
        return stop;
    }

    String getCloseSequence() {
        return closeSequence;
    }

    boolean isCloseSequence(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        return content.endsWith(closeSequence);
    }

    boolean isContinueSequence(String content) {
        return continueSequence.equals(content);
    }

    Pattern getVarPattern() {
        return varPattern;
    }

    /**
     * Check if to string contains a start sequence.
     *
     * @param toParse
     * @return
     */
    public boolean hasStartSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && startPattern.matcher(toParse).find();
    }

    boolean isEndSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && toParse.equals(endSequence);
    }

    String getContinueSequence() {
        return continueSequence;
    }
}
