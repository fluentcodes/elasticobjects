package org.fluentcodes.projects.elasticobjects.calls;

import java.util.ArrayList;
import java.util.List;

/**
 * A bean container for permissions.
 *
 * @author Werner Diwischek
 * @creationDate Fri Jan 07 00:00:00 CET 2022
 * @modificationDate Fri Jan 07 00:00:00 CET 2022
 */
public class PermissionsForBean {

    public static final String SUPER_ADMIN = "superadmin";
    private List<String> createRoles;
    private List<String> deleteRoles;
    private List<String> executeRoles;
    private List<String> nothingRoles;
    private List<String> readRoles;
    private List<String> writeRoles;

    public PermissionsForBean() {
    }

    public PermissionsForBean(final PermissionsForConfig config) {
        this.createRoles = new ArrayList<>(config.getCreateRoles());
        this.deleteRoles = new ArrayList<>(config.getDeleteRoles());
        this.executeRoles = new ArrayList<>(config.getExecuteRoles());
        this.nothingRoles = new ArrayList<>(config.getNothingRoles());
        this.readRoles = new ArrayList<>(config.getReadRoles());
        this.writeRoles = new ArrayList<>(config.getWriteRoles());
    }

    public void setCreateRoles(List<String> createRoles) {
        this.createRoles = createRoles;
    }

    public List<String> getCreateRoles() {
        return createRoles != null ? createRoles : new ArrayList<>();
    }

    public List<String> getDeleteRoles() {
        return deleteRoles != null ? deleteRoles : new ArrayList<>();
    }

    public void setDeleteRoles(List<String> deleteRoles) {
        this.deleteRoles = deleteRoles;
    }

    public List<String> getExecuteRoles() {
        return executeRoles != null ? executeRoles : new ArrayList<>();
    }


    public void setExecuteRoles(List<String> executeRoles) {
        this.executeRoles = executeRoles;
    }

    public List<String> getNothingRoles() {
        return nothingRoles != null ? nothingRoles : new ArrayList<>();
    }


    public void setNothingRoles(List<String> nothingRoles) {
        this.nothingRoles = nothingRoles;
    }

    public List<String> getReadRoles() {
        return readRoles != null ? readRoles : new ArrayList<>();
    }

    public void setReadRoles(List<String> readRoles) {
        this.readRoles = readRoles;
    }

    public List<String> getWriteRoles() {
        return writeRoles != null ? writeRoles : new ArrayList<>();
    }

    public void setWriteRoles(List<String> writeRoles) {
        this.writeRoles = writeRoles;
    }
}
