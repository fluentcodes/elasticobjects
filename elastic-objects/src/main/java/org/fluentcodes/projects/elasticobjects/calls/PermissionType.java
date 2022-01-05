package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Created by Werner on 7.2.2015
 */
public enum PermissionType {
    CREATE(2, "create"),
    DELETE(1, "delete"),
    EXECUTE(0, "execute"),
    WRITE(3, "write"),
    READ(4, "read"),
    NOTHING(5, "nothing"),;

    private int value;
    private String fieldKey;

    PermissionType(final int level, final String fieldKey) {
        this.value = level;
        this.fieldKey = fieldKey;
    }

    public int value() {
        return this.value;
    }

    public final String getFieldKey() {
        return fieldKey;
    }
}
