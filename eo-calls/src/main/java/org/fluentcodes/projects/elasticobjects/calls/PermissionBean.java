package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*.{javaHeader}|*/

/**
 * A bean container class for Field values
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 13:07:13 CET 2021
 */
public class PermissionBean extends ConfigBean {
    /*.{}.*/
    private List<String> create;
    private List<String> delete;
    private List<String> execute;
    private List<String> nothing;
    private List<String> read;
    private List<String> write;

    public PermissionBean(final Map<String, Object> map) {
        super(map);
        setCreate(map.get(PermissionType.CREATE.getFieldKey()));
        setDelete(map.get(PermissionType.DELETE.getFieldKey()));
        setExecute(map.get(PermissionType.EXECUTE.getFieldKey()));
        setNothing(map.get(PermissionType.NOTHING.getFieldKey()));
        setRead(map.get(PermissionType.READ.getFieldKey()));
        setWrite(map.get(PermissionType.WRITE.getFieldKey()));
    }

    public PermissionBean(PermissionConfig config) {
        super(config);
        setCreate(config.getCreate());
        setDelete(config.getDelete());
        setExecute(config.getExecute());
        setNothing(config.getNothing());
        setRead(config.getRead());
        setWrite(config.getWrite());
    }

    public PermissionBean() {
        super();
    }

    /*.{javaAccessors}|*/

    /*.{}.*/

    public List<String> getCreate() {
        if (create == null) {
            return this.create = new ArrayList<>();
        }
        return create;
    }

    public void setCreate(List<String> createList) {
        this.create = createList;
    }

    private void setCreate(Object roleKeys) {
        if (roleKeys == null) {
            this.create = new ArrayList<>();
            return;
        }
        if (roleKeys instanceof List) {
            this.create = (List) roleKeys;
            return;
        }
        throw new EoException("Could not map value for " + roleKeys.getClass().getSimpleName());
    }

    public List<String> getDelete() {
        if (delete == null) {
            return this.delete = new ArrayList<>();
        }
        return delete;
    }

    public void setDelete(List<String> deleteList) {
        this.delete = deleteList;
    }

    private void setDelete(Object deleteList) {
        if (deleteList == null) {
            this.delete = new ArrayList<>();
            return;
        }
        if (deleteList instanceof List) {
            this.delete = (List) deleteList;
            return;
        }
        throw new EoException("Could not map value for " + deleteList.getClass().getSimpleName());
    }

    public List<String> getExecute() {
        if (execute == null) {
            return this.execute = new ArrayList<>();
        }
        return execute;
    }

    public void setExecute(List<String> executeList) {
        this.execute = executeList;
    }

    private void setExecute(Object roleKeys) {
        if (roleKeys == null) {
            this.execute = new ArrayList<>();
            return;
        }
        if (roleKeys instanceof List) {
            this.execute = (List) roleKeys;
            return;
        }
        throw new EoException("Could not map value for " + roleKeys.getClass().getSimpleName());
    }

    public List<String> getNothing() {
        if (nothing == null) {
            return this.nothing = new ArrayList<>();
        }
        return nothing;
    }

    public void setNothing(List<String> roleKeys) {
        this.nothing = roleKeys;
    }

    private void setNothing(Object roleKeys) {
        if (roleKeys == null) {
            this.nothing = new ArrayList<>();
            return;
        }
        if (roleKeys instanceof List) {
            this.nothing = (List) roleKeys;
            return;
        }
        throw new EoException("Could not map value for " + roleKeys.getClass().getSimpleName());
    }


    public List<String> getRead() {
        if (read == null) {
            return this.read = new ArrayList<>();
        }
        return read;
    }

    public void setRead(List<String> roleKeys) {
        this.read = roleKeys;
    }

    private void setRead(Object roleKeys) {
        if (roleKeys == null) {
            this.read = new ArrayList<>();
            return;
        }
        if (roleKeys instanceof List) {
            this.read = (List) roleKeys;
            return;
        }
        throw new EoException("Could not map value for " + roleKeys.getClass().getSimpleName());
    }

    public List<String> getWrite() {
        if (write == null) {
            return this.write= new ArrayList<>();
        }
        return write;
    }

    public void setWrite(List<String> roleKeys) {
        this.write = roleKeys;
    }

    private void setWrite(Object roleKeys) {
        if (roleKeys == null) {
            this.write = new ArrayList<>();
            return;
        }
        if (roleKeys instanceof List) {
            this.write = (List) roleKeys;
            return;
        }
        throw new EoException("Could not map value for " + roleKeys.getClass().getSimpleName());
    }

}
