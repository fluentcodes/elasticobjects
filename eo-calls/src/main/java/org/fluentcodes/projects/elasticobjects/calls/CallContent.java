package org.fluentcodes.projects.elasticobjects.calls;

public interface CallContent {
    String CONTENT = "content";

    String getContent();

    CallContent setContent(String content);

    default boolean hasContent() {
        return getContent() != null && !getContent().isEmpty();
    }
}
