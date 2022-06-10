package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Werner on 2.10.2020.
 */
public class DirectoryConfig extends FileConfig {
    private Map<String, String> cachedContent;

    public DirectoryConfig(ConfigBean configBean, final ConfigMaps configMaps) {
        this((FileBean)configBean, configMaps);
    }
    public DirectoryConfig(FileBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        if (getCached()) {
            cachedContent = new HashMap<>();
        }
    }

    public boolean hasCachedContent(final String fileName) {
        return cachedContent !=null && cachedContent.containsKey(fileName);
    }

    public String getCachedContent(final String fileName) {
        if (!getCached()) {
            return null;
        }
        return cachedContent.get(fileName);
    }

    public void setCachedContent( final String fileName, final String content) {
        if (!getCached()) {
            return;
        }
        this.cachedContent.put(fileName, content);
    }
}
