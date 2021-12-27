package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ConfigBean extends BaseBean implements ConfigInterface {
    public static final String F_PROPERTIES = "properties";
    /*.{}.*/
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
    /*.{}.*/
    private Map<String, Object> properties;

    public ConfigBean() {
        super();
        properties = new TreeMap<>();
    }

    public ConfigBean(final String key) {
        super(key);
        properties = new TreeMap<>();
    }

    public ConfigBean(final Map<String, Object> configMap) {
        super(configMap);
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

    public ConfigBean(final ConfigConfig config) {
        super(config);
        setConfigModelKey(config.getClass().getSimpleName());
        setExpose(config.getExpose());
        setModule(config.getModule());
        setModuleScope(config.getModuleScope());
        setExpose(config.getExpose());
        properties = new HashMap<>();
    }

    public void merge(ConfigBean bean) {
        super.merge(bean);
        mergeConfigModelKey(bean.getConfigModelKey());
        mergeExpose(bean.getExpose());
        mergeModule(bean.getModule());
        mergeModuleScope(bean.getModuleScope());
        mergeScope(bean.getScope());
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public boolean hasProperties() {
        return this.properties !=null && !this.properties.isEmpty();
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

    public Expose getExpose() {
        return this.expose;
    }

    public ConfigBean setExpose(final Expose expose) {
        this.expose = expose;
        return this;
    }

    public String getModule() {
        return this.module;
    }

    public ConfigBean setModule(final String module) {
        this.module = module;
        return this;
    }

    public String getModuleScope() {
        return this.moduleScope;
    }

    public ConfigBean setModuleScope(final String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }

    public List<Scope> getScope() {
        return this.scope;
    }

    public ConfigBean setScope(final List<Scope> scope) {
        this.scope = scope;
        return this;
    }

    /**
     * Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    void mergeConfigModelKey(Object value) {
        if (value == null) {
            return;
        }
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(new ShapeTypeSerializerString().asObject(value));
    }

    Class<?> deriveConfigClass(final String configModelKey) {
        try {
            return Class.forName(
                    this.getClass().getPackage().toString().replace("package ", "")
                            + "." + configModelKey);
        } catch (Exception e) {
            throw new EoException("Could not find configuration class: " + e.getMessage());
        }
    }

    Class<?> deriveConfigClass() {
        return deriveConfigClass(getConfigModelKey());
    }

    ConfigInterface createConfig(final ConfigMaps configMaps) {
        return createConfig(deriveConfigClass(), configMaps);
    }

    ConfigInterface createConfig(final Class<?> configClass, final ConfigMaps configMaps) {
        try {
            Constructor configurationConstructor = configClass.getConstructor(ConfigBean.class, ConfigMaps.class);
            try {
                return (ConfigInterface) configurationConstructor.newInstance(this, configMaps);
            }
            catch (EoException|EoInternalException e) {
                throw e;
            }
            catch (Exception e) {
                throw new EoInternalException("Problem with create new instance for config constructor with bean class for '" + getNaturalId() + "'/'" + configClass.getSimpleName() + "' in ModelConfig", e);
            }
        } catch (NoSuchMethodException e) {
            throw new EoInternalException("Problem find constructor for '" + getNaturalId() + "'  '" + configClass.getSimpleName() + "' with ConfigBean", e);
        }
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

    private void mergeModuleScope(Object value) {
        if (value == null) {
            return;
        }
        if (hasModuleScope()) {
            return;
        }
        setModuleScope(new ShapeTypeSerializerString().asObject(value));
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

    /*.{}.*/

}
