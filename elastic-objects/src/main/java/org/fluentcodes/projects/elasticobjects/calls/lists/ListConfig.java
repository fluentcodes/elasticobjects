package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 30.10.2016.
 */
public abstract class ListConfig extends ConfigResourcesImpl implements ListConfigInterface {
    private final ListParams listParams;
    private final ListMapper listMapper;
    private boolean resolved = false;

    public ListConfig(final EOConfigsCache configsCache, final Builder builder) {
        super(configsCache, builder);
        this.listParams = builder.listParams;
        this.listMapper = builder.listMapper;
    }

    public ListParams getListParams() {
        return listParams;
    }

    public ListMapper getListMapper() {
        return listMapper;
    }

    public Integer getRowHead() {
        return listParams.getRowHead();
    }

    public boolean hasRowHead() {
        return listParams.hasRowHead();
    }

    public boolean hasRowStart() {
        return getRowStart() != null && getRowStart()  > -1;
    }

    public Integer getRowStart() {
        return listParams.getRowStart();
    }
    public boolean hasRowEnd() {
        return getRowEnd() != null && getRowEnd()  > -1;
    }
    public Integer getRowEnd() {
        return listParams.getRowEnd();
    }

    public Integer getLength() {
        return listParams.getLength();
    }

    public Or getOr() {
        return listParams.getFilter();
    }

    public boolean hasColKeys() {
        return this.listMapper.hasColKeys();
    }

    public List<String> getColKeys() {
        return this.listMapper.getColKeys();
    }

    public void setColKeys(Object object) {
        this.listMapper.setColKeys(object);
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        if (hasColKeys()) {
            return;
        }
        //List<String> header = ((ListIOInterface) createIO()).readHead(listParams.getRowHead());
        //setColKeys(header);
    }

    public static class Builder extends ConfigResourcesImpl.Builder {
        private ListParams listParams;
        private ListMapper listMapper;

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            super.prepare(configsCache, values);
            this.listParams = new ListParams((Map) values.get("listParams"));
            this.listMapper = new ListMapper((Map) values.get("listMapper"));
        }
    }
}