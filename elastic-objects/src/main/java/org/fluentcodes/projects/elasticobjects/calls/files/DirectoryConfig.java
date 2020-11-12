package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Werner on 2.10.2020.
 */
public class DirectoryConfig extends FileConfig {
    private Map<String, String> cachedContent;

    public DirectoryConfig(final EOConfigsCache provider, Map map) {
        super(provider, map);
        if (isCached()) {
            cachedContent = new HashMap<>();
        }
    }

    public boolean hasCachedContent(final String fileName) {
        return cachedContent !=null && cachedContent.containsKey(fileName);
    }

    public String getCachedContent(final String fileName) {
        if (!isCached()) {
            return null;
        }
        return cachedContent.get(fileName);
    }

    public void setCachedContent( final String fileName, final String content) {
        if (!isCached()) {
            return;
        }
        this.cachedContent.put(fileName, content);
    }

    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @
     */

}
