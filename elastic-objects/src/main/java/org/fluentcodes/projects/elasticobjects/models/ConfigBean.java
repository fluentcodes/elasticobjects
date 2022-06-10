package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*.{javaHeader}|*/

/**
 * The basic bean class for the configuration classes.
 *
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 10:57:55 CET 2021
 */
public class ConfigBean {
    public static final String F_PROPERTIES = "properties";
    public static final String F_MODULE = "module";
    public static final String F_CONFIG_MODEL_KEY = "configModelKey";
    public static final String F_EXPOSE = "expose";
    public static final String F_MODULE_SCOPE = "moduleScope";
    public static final String F_SCOPE = "scope";
    public static final String F_CREATION_DATE = "creationDate";
    public static final String F_AUTHOR = "author";
    public static final String F_DESCRIPTION = "description";
    public static final String F_ID = "id";
    public static final String F_NATURAL_ID = "naturalId";
    private static final String CONFIG_MODEL_KEY = "configModelKey";
    public static final String SCOPE_FROM_STRING_EXCEPTION = "Could not set scope from string with value '";
    /*.{javaInstanceVars}|*/
    /* The model of the configuration to determine type. */
    private String configModelKey;
    /* expose */
    private Expose expose;
    /* Defines a target module where generating occurs.  */
    private String module;
    /* Defines scope of the configuration within module, eg 'test' or 'main' . */
    private String moduleScope;
    /* A scope for the cache value. */
    private List<Scope> scope;
    /* The author of the class. */
    private String author;
    /* Used to define the creation of an item. */
    private Date creationDate;
    /* A description of the model used by every model extending BaseClassImpl.  */
    private String description;
    /* The numeric id of an instance of a class. */
    private Long id;
    /* The natural key in @Base */
    private String naturalId;
    /*.{}.*/
    private Map<String, Object> properties;

    public ConfigBean() {
        super();
        properties = new TreeMap<>();
    }

    public ConfigBean(final String key) {
        properties = new TreeMap<>();
        this.naturalId = key;
    }

    public ConfigBean(final Map<String, Object> configMap) {
        this.properties = new TreeMap<>();
        setConfigModelKey(
                toString(configMap.get(CONFIG_MODEL_KEY)));
        setModule(
                toString(configMap.get(F_MODULE)));
        setModuleScope(
                toString(configMap.get(F_MODULE_SCOPE)));
        setExpose(
                new ShapeTypeSerializerEnum<Expose>().asObject(Expose.class, configMap.get(F_EXPOSE)));
        setScope(
                toScopes(configMap.get(F_SCOPE)));
        setId(
                toLong(configMap.get(F_ID)));
        setNaturalId(
                toString(configMap.get(F_NATURAL_ID)));
        setDescription(
                toString(configMap.get(F_DESCRIPTION)));
        setCreationDate(
                toDate(configMap.get(F_CREATION_DATE)));
        setAuthor(
                toString(configMap.get(F_AUTHOR)));
    }

    public ConfigBean(final Config config) {
        setConfigModelKey(config.getConfigModelKey());
        setExpose(config.getExpose());
        setModule(config.getModule());
        setModuleScope(config.getModuleScope());
        setExpose(config.getExpose());
        setNaturalId(config.getNaturalId());
        this.author = config.getAuthor();
        this.creationDate = config.getCreationDate();
        this.description = config.getDescription();
        this.id = config.getId();
        properties = new HashMap<>();
    }

    private static List<Scope> toScopes(final Object scopes) {
        List<Scope> scopeList = new ArrayList<>();
        if (scopes == null) {
            return scopeList;
        }
        if (scopes instanceof List) {
            return (List<Scope>) scopes;
        }
        String[] scopeArray = ((String)scopes).split(",");
        for (String scope: scopeArray) {
            scopeList.add(new ShapeTypeSerializerEnum<Scope>().asObject(Scope.class, scope));
        }
        return scopeList;
    }



    public static Boolean toBoolean(final Object value) {
        return new ShapeTypeSerializerBoolean().asObject(value);
    }

    public static Integer toInteger(final Object value) {
        return new ShapeTypeSerializerInteger().asObject(value);
    }

    public static String toString(final Object value) {
        return new ShapeTypeSerializerString().asObject(value);
    }

    public static Long toLong(final Object value) {
        return new ShapeTypeSerializerLong().asObject(value);
    }

    public static Date toDate(final Object value) {
        return new ShapeTypeSerializerDate().asObject(value);
    }

    /*.{javaAccessors}|*/
    public String getAuthor() {
        return this.author;
    }

    public ConfigBean setAuthor(final String author) {
        this.author = author;
        return this;
    }

    public boolean hasAuthor() {
        return getAuthor() != null && !getAuthor().isEmpty();
    }

    private void mergeAuthor(final Object value) {
        if (value == null) return;
        if (hasAuthor()) return;
        setAuthor(new ShapeTypeSerializerString().asObject(value));
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public ConfigBean setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public boolean hasCreationDate() {
        return getCreationDate() != null;
    }

    private void mergeCreationDate(final Object value) {
        if (value == null) return;
        if (hasCreationDate()) return;
        setCreationDate(new ShapeTypeSerializerDate().asObject(value));
    }

    public String getDescription() {
        return this.description;
    }

    public ConfigBean setDescription(final String description) {
        this.description = description;
        return this;
    }

    public boolean hasDescription() {
        return getDescription() != null && !getDescription().isEmpty();
    }

    private void mergeDescription(final Object value) {
        if (value == null) return;
        if (hasDescription()) return;
        setDescription(new ShapeTypeSerializerString().asObject(value));
    }

    public Long getId() {
        return this.id;
    }

    public ConfigBean setId(final Long id) {
        this.id = id;
        return this;
    }

    public boolean hasId() {
        return getId() != null;
    }

    private void mergeId(final Object value) {
        if (value == null) return;
        if (hasId()) return;
        setId(new ShapeTypeSerializerLong().asObject(value));
    }

    public String getNaturalId() {
        return this.naturalId;
    }

    public ConfigBean setNaturalId(final String naturalId) {
        this.naturalId = naturalId;
        return this;
    }


    public boolean hasNaturalId() {
        return getNaturalId() != null && !getNaturalId().isEmpty();
    }

    private void mergeNaturalId(final Object value) {
        if (value == null) return;
        if (hasNaturalId()) return;
        setNaturalId(new ShapeTypeSerializerString().asObject(value));
    }

    /*.{javaAccessors}|*/
    public String getConfigModelKey() {
        return this.configModelKey;
    }

    public boolean hasConfigModelKey() {
        return configModelKey != null && !configModelKey.isEmpty();
    }

    public ConfigBean setConfigModelKey(final String configModelKey) {
        this.configModelKey = configModelKey;
        return this;
    }

    /**
     * Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    private void mergeConfigModelKey(Object value) {
        if (value == null) {
            return;
        }
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(new ShapeTypeSerializerString().asObject(value));
    }

    public Expose getExpose() {
        return this.expose;
    }

    public ConfigBean setExpose(final Expose expose) {
        this.expose = expose;
        return this;
    }

    public boolean hasExpose() {
        return expose != null;
    }

    private void mergeExpose(Object value) {
        if (value == null) {
            return;
        }
        if (hasExpose()) {
            return;
        }
        if (value instanceof Expose) {
            setExpose((Expose) value);
            return;
        }
        if (value instanceof String) {
            setExpose(Expose.valueOf((String) value));
            return;
        }
        throw new EoException("Could not set expose from class '" + value.getClass() + "' and value '" + value + "'");
    }

    public String getModule() {
        return this.module;
    }

    public ConfigBean setModule(final String module) {
        this.module = module;
        return this;
    }

    public boolean hasModule() {
        return module != null && !module.isEmpty();
    }

    private void mergeModule(Object value) {
        if (value == null) {
            return;
        }
        if (hasModule()) {
            return;
        }
        setModule(new ShapeTypeSerializerString().asObject(value));
    }


    public String getModuleScope() {
        return this.moduleScope;
    }

    public ConfigBean setModuleScope(final String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }

    public boolean hasModuleScope() {
        return moduleScope != null && !moduleScope.isEmpty();
    }

    private void mergeModuleScope(Object value) {
        if (value == null) {
            return;
        }
        if (hasModuleScope()) {
            return;
        }
        setModuleScope(new ShapeTypeSerializerString().asObject(value));
    }

    public List<Scope> getScope() {
        return this.scope;
    }

    public ConfigBean setScope(final List<Scope> scope) {
        this.scope = scope;
        return this;
    }

    public boolean hasScope() {
        return scope != null && !scope.isEmpty();
    }


    private void mergeScope(Object value) {
        if (value == null) {
            return;
        }
        if (hasScope()) {
            return;
        }
        if (value instanceof String) {
            if (!((String) value).isEmpty()) {
                String[] values = ((String) value).split(",");
                setScope(createScopeList(Arrays.asList(values)));
                return;
            }
        } else if (value instanceof List) {
            setScope(createScopeList((List) value));
            return;
        }
        throw new EoException("Could not set moduleScope from class '" + value.getClass() + " and " + value + "'");
    }

    private List<Scope> createScopeList(List<Object> values) {
        List<Scope> scopeList = new ArrayList<>();
        for (Object value : values) {
            if (value instanceof String) {
                try {
                    scopeList.add(Scope.valueOf((String) value));
                } catch (Exception e) {
                    throw new EoException(SCOPE_FROM_STRING_EXCEPTION + value + "'");
                }
            } else if (value instanceof Scope) {
                scopeList.add((Scope) value);
            } else {
                throw new EoException("SCOPE_FROM_STRING_EXCEPTION '" + value.getClass() + "' and value '" + value + "'");
            }
        }
        return scopeList;
    }

    Class<?> deriveConfigClass(final String configModelKey) {
        try {
            return Class.forName(
                    this.getClass().getPackage().toString().replace("package ", "")
                            + "." + configModelKey);
        } catch (Exception e) {
            throw new EoException("Could not find configuration class with a Class.forName: " + e.getMessage());
        }
    }

    Class<?> deriveConfigClass() {
        return deriveConfigClass(getConfigModelKey());
    }

    public Config createConfig(final ConfigMaps configMaps) {
        return createConfig(deriveConfigClass(), configMaps);
    }

    Config createConfig(final Class<?> configClass, final ConfigMaps configMaps) {
        try {
            Constructor configurationConstructor = configClass.getConstructor(ConfigBean.class, ConfigMaps.class);
            try {
                return (Config) configurationConstructor.newInstance(this, configMaps);
            }
            catch (EoException|EoInternalException e) {
                throw e;
            }
            catch (Exception e) {
                throw new EoInternalException(e.getClass().getSimpleName() + ": Problem creating '" + configClass.getSimpleName() + "' configuration with bean '" + getNaturalId() + "'", e);
            }
        } catch (NoSuchMethodException e) {
            throw new EoInternalException("No constructor found for  '" + configClass.getSimpleName() + "' creating configuration with bean for '" + getNaturalId() + "'", e);
        }
    }

    public void merge(ConfigBean bean) {
        this.mergeAuthor(bean.getAuthor());
        this.mergeConfigModelKey(bean.getConfigModelKey());
        this.mergeCreationDate(bean.getCreationDate());
        this.mergeDescription(bean.getDescription());
        this.mergeExpose(bean.getExpose());
        this.mergeId(bean.getId());
        this.mergeModule(bean.getModule());
        this.mergeModuleScope(bean.getModuleScope());
        this.mergeNaturalId(bean.getNaturalId());
        this.mergeScope(bean.getScope());
    }

    @Override
    public String toString() {
        return hasNaturalId() ? getNaturalId() : "";
    }

}
