package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigsCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.Expose;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * For getting a list of keys for a specific configuration type, config filter and expose type.
 */
public class ConfigKeysCall extends CallImpl implements ConfigsCommand {

    public static final String F_CONFIG_FILTER = "configFilter";
    public static final String F_CONFIG_TYPE = "configType";
    public static final String F_EXPOSE = "expose";
    public static final String F_SORT_ORDER = "sortOrder";

    private String configFilter;
    private String configType;
    private Expose expose;
    private SortOrder sortOrder;
    private Class<? extends Config> configClass;

    public ConfigKeysCall() {
        super();
        expose = Expose.NONE;
        sortOrder = SortOrder.ASC;

    }

    public ConfigKeysCall(final Class<? extends Config> configClass) {
        super();
        this.configClass = configClass;
        this.configType = configClass.getSimpleName();
        expose = Expose.NONE;
        sortOrder = SortOrder.ASC;
    }

    public ConfigKeysCall(final String configType) {
        super();
        this.configType = configType;
        expose = Expose.NONE;
        sortOrder = SortOrder.ASC;
    }

    public ConfigKeysCall(final Class<? extends Config> configClass, final String configFilter) {
        this(configClass);
        this.configFilter = configFilter;
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if (!hasConfigFilter()) {
            configFilter = ".*";
        }
        super.check(eo);
        if (configType == null && configClass == null) {
            throw new EoException("Problem no config type defined.");
        }
        if (TemplateMarker.SQUARE.hasStartSequence(configType)) {
            configType = new Parser(TemplateMarker.SQUARE, configType).parse(eo);
        }
        if (TemplateMarker.SQUARE.hasStartSequence(configFilter)) {
            configFilter = new Parser(TemplateMarker.SQUARE, configFilter).parse(eo);
        }
        if (configClass == null) {
            ModelConfig configTypeConfig = eo.getConfigMaps().findModel(configType);
            configClass = (Class<? extends Config>) configTypeConfig.getModelClass();
        }
        Set<String> keys = eo.getConfigMaps().getConfigKeys(configClass, expose);
        try {
            return super.createReturnType(eo, keys
                    .stream()
                    .filter(x -> x.matches(configFilter))
                    .sorted(sortOrder.getComparator())
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new EoException(e);
        }
    }

    /*.{javaAccessors}|*/

    /**
     * Key for filter configuration
     */

    public ConfigKeysCall setConfigFilter(String configFilter) {
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
     * Key for configuration type like ModelConfig, FileConfig, FieldConfig, HostConfig, DbSqlConfig.
     */

    public ConfigKeysCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getConfigType() {
        return this.configType;
    }

    public boolean hasConfigType() {
        return configType != null && !configType.isEmpty();
    }

    /**
     * expose
     */

    public ConfigKeysCall setExpose(Expose expose) {
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
     * A field with a SortOrder enum
     */

    public ConfigKeysCall setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    public boolean hasSortOrder() {
        return sortOrder != null;
    }
}
