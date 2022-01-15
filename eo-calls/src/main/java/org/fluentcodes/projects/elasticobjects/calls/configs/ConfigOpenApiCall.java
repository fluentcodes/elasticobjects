package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Expose;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.JsonTypes;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*.{javaHeader}|*/

/**
 * Creates an open api schema from model configuration.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 05:43:36 CET 2020
 */
public class ConfigOpenApiCall extends CallImpl implements SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String CONFIG_FILTER = "configFilter";
    public static final String EXPOSE = "expose";
    public static final String MODULE = "module";
    public static final String MODULE_SCOPE = "moduleScope";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String configFilter;
    private Expose expose;
    private String module;
    private String moduleScope;
    /*.{}.*/
    private SortOrder sortOrder = SortOrder.ASC;

    private Set<String> created;
    private Set<String> toCreate;

    public ConfigOpenApiCall() {
        super();
    }


    public ConfigOpenApiCall(final String configFilter) {
        super();
        this.configFilter = configFilter;
    }

    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 4) {
            throw new EoException("Short form should have form '<configType>[,<naturalId>][,<expose>][,<targetPath>]' with max length 3 but has size " + array.length + ": '" + values + "'.");
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null || array.length == 0) {
            throw new EoException("Set by empty input values");
        }
        if (array.length > 0) {
            setConfigFilter(array[0]);
        }
        if (array.length > 1) {
            setTargetPath(array[1]);
        }
        if (array.length > 2) {
            setExpose(Expose.valueOf(array[2]));
        }

    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if (!hasExpose()) {
            expose = Expose.NONE;
        }
        created = new HashSet<>();
        toCreate = new HashSet<>();
        ConfigKeysCall keysCall = new ConfigKeysCall(ModelConfig.class, configFilter)
                .setExpose(expose)
                .setSortOrder(sortOrder);
        List<String> keys = (List<String>) keysCall.execute(eo);
        EoRoot schemeRoot = EoRoot.of(eo.getConfigMaps());
        EOInterfaceScalar components = schemeRoot.createChild("components");
        EOInterfaceScalar schemas = ((EoChild)components).createChild("schemas");

        for (String key : keys) {
            if (created.contains(key)) {
                continue;
            }
            ModelConfig configEntry = eo.getConfigMaps().findModel(key);
            try {
                if (hasFilterModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getModule()))) {
                    continue;
                }
                if (hasFilterSubModule()) { // && (configEntry.getConfigsCache() == null || !configEntry.getConfigsCache().equals(this.getModuleScope()))) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            create(schemas, configEntry);
        }
        return super.createReturnType(eo, schemeRoot.get());
    }

    private void create(EOInterfaceScalar schemasEo, ModelConfig modelConfig) {
        EOInterfaceScalar entry = ((EoChild)schemasEo).createChild(modelConfig.getModelKey());
        created.add(modelConfig.getModelKey());
        entry.set("object", "type");
        entry.set(modelConfig.getDescription(), "description");
        EOInterfaceScalar properties = ((EoChild)entry).createChild("properties");
        for (String fieldKey : modelConfig.getFieldKeys()) {
            FieldConfig fieldConfig = (FieldConfig) modelConfig.getField(fieldKey);
            EOInterfaceScalar field = ((EoChild)properties).createChild(fieldConfig.getFieldKey());
            Models fieldModels = fieldConfig.getModels();
            if (fieldConfig.hasDescription()) {
                field.set(fieldConfig.getDescription(), "description");
            }
            if (fieldModels.isEnum()) {
                field.set("string", "type");
                Class enumClass = fieldModels.getModelClass();
                List values = Arrays.asList(enumClass.getEnumConstants());
                field.set(values, "(List)enum");

            } else if (fieldModels.isScalar()) {
                field.set(JsonTypes.getType(fieldModels.getModelClass()), "type");
                if (JsonTypes.hasFormat(fieldModels.getModelClass())) {
                    field.set(JsonTypes.getFormat(fieldModels.getModelClass()), "format");
                }
            } else if (fieldModels.isObject()) {
                field.set("#/components/schemas/" + fieldModels.getModelClass().getSimpleName(), "$ref");
                if (!created.contains(fieldModels.getModelClass().getSimpleName())) {
                    toCreate.add(fieldModels.getModelClass().getSimpleName());
                }
            } else if (fieldModels.isList()) {
                EOInterfaceScalar array = field.set("array", "$ref");
                if (fieldModels.hasChildModel()) {
                    ModelConfig childModel = fieldModels.getChildModel();
                    ((EoChild)array).createChild("items");
                    if (childModel.isScalar()) {
                        array.set(JsonTypes.getType(childModel.getModelClass()), "type");
                    } else if (childModel.isObject()) {
                        array.set("#/components/schemas/" + childModel.getModelClass().getSimpleName(), "$ref");
                        if (!created.contains(childModel.getModelClass().getSimpleName())) {
                            toCreate.add(childModel.getModelClass().getSimpleName());
                        }
                    } else {
                        throw new EoException("Problem");
                    }
                }
            } else {
                field.set(fieldModels.toString(), "type");
            }
        }
        created.add(modelConfig.getModelKey());
        toCreate.remove(modelConfig.getModelKey());
        if (toCreate.isEmpty()) {
            return;
        }
        for (String modelKey : toCreate) {
            ModelConfig createConfig = schemasEo.getConfigMaps().findModel(modelKey);
            create(schemasEo, createConfig);
        }
    }

    /*.{javaAccessors}|*/

    /**
     * Key for filter configuration
     */

    public ConfigOpenApiCall setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
        return this;
    }

    public String getConfigFilter() {
        return this.configFilter;
    }

    public boolean hasConfigFilter() {
        return configFilter != null && !configFilter.isEmpty();
    }

    /**
     * expose
     */

    public ConfigOpenApiCall setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }

    public Expose getExpose() {
        return this.expose;
    }

    public boolean hasExpose() {
        return expose != null;
    }

    /**
     * Filter for modules
     */

    public ConfigOpenApiCall setModule(String module) {
        this.module = module;
        return this;
    }

    public String getModule() {
        return this.module;
    }

    public boolean hasFilterModule() {
        return module != null && !module.isEmpty();
    }

    public ConfigOpenApiCall setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }

    public String getModuleScope() {
        return this.moduleScope;
    }

    public boolean hasFilterSubModule() {
        return moduleScope != null && !moduleScope.isEmpty();
    }
    /*.{}.*/

}
