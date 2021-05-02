package org.fluentcodes.projects.elasticobjects.models;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface ConfigBeanMapInterface<T extends ConfigBeanInterface> {
    T find(final String key) ;
    Set<String> getKeys();
    boolean isEmpty();
    boolean hasKey(String configKey);
}
