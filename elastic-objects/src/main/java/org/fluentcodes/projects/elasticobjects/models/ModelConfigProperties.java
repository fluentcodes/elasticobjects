package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigProperties implements ModelPropertiesInterface {
    private static final Logger LOG = LogManager.getLogger(ModelConfigProperties.class);
    private final Boolean abstractValue;
    private final Boolean create;
    private final Boolean dbAnnotated;
    private final String defaultImplementation;
    private final Boolean finalValue;
    private final String idKey;
    private final String naturalKeys;
    private final String table;

    public ModelConfigProperties(final ModelBeanProperties bean) {
        this.defaultImplementation = bean.getDefaultImplementation();
        this.abstractValue = bean.getAbstract();
        this.finalValue = bean.getFinal();
        this.dbAnnotated = bean.getDbAnnotated();
        this.create = bean.getCreate();
        this.table = bean.getTable();
        this.idKey = bean.getIdKey();
        this.naturalKeys = bean.getNaturalKeys();
    }

    @Override
    public String getDefaultImplementation() {
        return defaultImplementation;
    }

    @Override
    public Boolean getAbstract() {
        return abstractValue;
    }

    @Override
    public Boolean getFinal() {
        return finalValue;
    }

    @Override
    public Boolean getDbAnnotated() {
        return this.dbAnnotated;
    }

    @Override
    public Boolean getCreate() {
        return this.create;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getIdKey() {
        return idKey;
    }

    @Override
    public String getNaturalKeys() {
        return naturalKeys;
    }

    public ModelBeanProperties createBean() {
        return new ModelBeanProperties(this);
    }
}
