package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Created by Werner on 7.2.2015
 */
public enum PermissionType {
    CREATE(0, "createRoles"),
    DELETE(1, "deleteRoles"),
    EXECUTE(2, "executeRoles"),
    WRITE(3, "writeRoles"),
    READ(4, "readRoles"),
    NOTHING(5, "nothingRoles");

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
