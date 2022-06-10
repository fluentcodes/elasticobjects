package org.fluentcodes.projects.elasticobjects.calls.templates;

public enum KeepCalls {
    HTML("<!--", "-->"),
    JAVA("/*", "*/\n"),
    NONE("", "");
    private String startComment;
    private String endComment;

    KeepCalls(String startComment, String endComment) {
        this.startComment = startComment;
        this.endComment = endComment;
    }

    public String createStartDirective(final String directive) {
        return directive + endComment;
    }

    public String getStartComment() {
        return startComment;
    }

    public String getEndComment() {
        return endComment;
    }
}
