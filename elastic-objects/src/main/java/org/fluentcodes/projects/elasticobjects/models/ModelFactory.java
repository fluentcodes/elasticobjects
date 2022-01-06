package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public abstract class ModelFactory extends ConfigFactory<ModelBean, ModelConfig> {

    protected ModelFactory(final ConfigMaps configMaps) {
        super(configMaps, ModelBean.class, ModelConfig.class);
    }

    /**
     * Create a config map from a bean map.
     *
     * @return the config map
     */
    @Override
    public Map<String, ModelConfig> createConfigMap() {
        Map<String, ModelConfig> configMap = new TreeMap<>();
        Map<String, ModelBean> beanMap = createBeanMap();
        for (Map.Entry<String, ModelBean> entry : beanMap.entrySet()) {
            try {
                Optional<String> filterScope = getScope().filter(entry.getKey());
                if (!filterScope.isPresent()) {
                    continue;
                }
                final String key = filterScope.get();
                ModelBean bean = entry.getValue();
                bean.resolveSuper(beanMap, true);
                ShapeTypes shapeType = bean.getShapeType();
                if (bean.hasConfigModelKey()) {
                    configMap.put(key,
                            (ModelConfig)bean.createConfig(bean.deriveConfigClass(),
                                    getConfigMaps()));
                    continue;
                }
                ModelConfig modelConfig = null;
                if (shapeType == ShapeTypes.SCALAR) {
                    modelConfig = (ModelConfig) bean.createConfig(bean.deriveConfigClass(ModelConfigScalar.class.getSimpleName()),
                                    getConfigMaps());
                } else if (shapeType == ShapeTypes.SCALAR) {
                    modelConfig = (ModelConfig) bean.createConfig(bean.deriveConfigClass(ModelConfigScalar.class.getSimpleName()),
                                    getConfigMaps());
                } else if (shapeType == ShapeTypes.MAP) {
                    modelConfig = (ModelConfig) bean.createConfig(bean.deriveConfigClass(ModelConfigMap.class.getSimpleName()),
                                    getConfigMaps());
                } else if (shapeType == ShapeTypes.LIST) {
                    modelConfig = (ModelConfig) bean.createConfig(bean.deriveConfigClass(ModelConfigList.class.getSimpleName()),
                                    getConfigMaps());
                } else {
                    modelConfig = (ModelConfig) bean.createConfig(
                                    bean.deriveConfigClass(ModelConfigObject.class.getSimpleName()),
                                    getConfigMaps());
                }
                configMap.put(key, modelConfig);

            } catch (EoException| EoInternalException e) {
                throw e;
            } catch (Exception e) {
                throw new EoException(e);
            }
        }
        for (Map.Entry<String, ModelConfig> entry : configMap.entrySet()) {
            try {
                ((ModelConfig) entry.getValue()).resolve(configMap);
            } catch (EoException e) {
                throw e;
            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
        return configMap;
    }

}
