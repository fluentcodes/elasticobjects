package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FINAL;

public class ModelBeanProperties implements ModelPropertiesInterface {
    public static final String F_ABSTRACT = "abstract";
    public static final String F_CREATE = "create";
    public static final String F_DB_ANNOTATED = "dbAnnotated";
    public static final String F_DEFAULT_IMPLEMENTATION = "defaultImplementation";
    public static final String F_ID_KEY = "idKey";
    public static final String F_NATURAL_KEYS = "naturalKeys";
    public static final String F_TABLE = "table";

    private Boolean abstractValue;
    private Boolean create;
    private Boolean dbAnnotated;
    private String defaultImplementation;
    private Boolean finalValue;
    private String idKey;
    private String naturalKeys;
    private String table;

    public ModelBeanProperties() {
    }

    public ModelBeanProperties(final ModelConfigProperties config) {
        setAbstract(config.getAbstract());
        setCreate(config.getCreate());
        setDbAnnotated(config.getDbAnnotated());
        setDefaultImplementation(config.getDefaultImplementation());
        setFinal(config.getFinal());
        setIdKey(config.getIdKey());
        setNaturalKeys(config.getNaturalKeys());
    }

    public ModelBeanProperties(final Map<String, Object> properties) {
        setAbstract(
                ConfigBean.toBoolean(properties.get(F_ABSTRACT)));
        setCreate(
                ConfigBean.toBoolean(properties.get(F_CREATE)));
        setDbAnnotated(
                ConfigBean.toBoolean(properties.get(F_DB_ANNOTATED)));
        setDefaultImplementation(
                ConfigBean.toString(properties.get(F_DEFAULT_IMPLEMENTATION)));
        setFinal(
                ConfigBean.toBoolean(properties.get(F_FINAL)));
        setIdKey(
                ConfigBean.toString(properties.get(F_ID_KEY)));
        setNaturalKeys(
                ConfigBean.toString(properties.get(F_NATURAL_KEYS)));
        setTable(
                ConfigBean.toString(properties.get(F_TABLE)));
    }

    public void setDefault() {
        mergeAbstract(false);
        mergeCreate(false);
        mergeDbAnnotated(false);
        mergeFinal(false);
    }

    public Boolean getAbstract() {
        return abstractValue;
    }

    public ModelBeanProperties setAbstract(Boolean abstractValue) {
        this.abstractValue = abstractValue;
        return this;
    }

    private void mergeAbstract(Boolean value) {
        if (hasAbstract()) {
            return;
        }
        setAbstract(value);
    }

    @Override
    public Boolean getCreate() {
        return create;
    }

    public ModelBeanProperties setCreate(Boolean value) {
        this.create = value;
        return this;
    }

    private void mergeCreate(Boolean value) {
        if (hasCreate()) {
            return;
        }
        setCreate(value);
    }

    @Override
    public Boolean getDbAnnotated() {
        return dbAnnotated;
    }

    public ModelBeanProperties setDbAnnotated(Boolean value) {
        this.dbAnnotated = value;
        return this;
    }

    private void mergeDbAnnotated(Boolean value) {
        if (hasDbAnnotated()) {
            return;
        }
        setDbAnnotated(value);
    }

    @Override
    public String getDefaultImplementation() {
        return defaultImplementation;
    }

    public void setDefaultImplementation(String value) {
        this.defaultImplementation = value;
    }


    @Override
    public Boolean getFinal() {
        return finalValue;
    }

    public void setFinal(Boolean value) {
        this.finalValue = value;
    }

    private void mergeFinal(Boolean value) {
        if (hasFinal()) {
            return;
        }
        setFinal(value);
    }

    @Override
    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String value) {
        this.idKey = value;
    }

    public void setNaturalKeys(String value) {
        this.naturalKeys = value;
    }

    @Override
    public String getNaturalKeys() {
        return naturalKeys;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String value) {
        this.table = value;
    }
}
