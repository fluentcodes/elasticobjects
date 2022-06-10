package org.fluentcodes.projects.elasticobjects.io;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.io.IOClasspathObjectList;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read files from the class path as with an object mapping for each file.
 */
public class IOClasspathEOFlatMap<T> {
    private final ConfigMaps configMaps;
    private final IOClasspathObjectList<Map<String, T>> ioList;

    public IOClasspathEOFlatMap(final ConfigMaps configMaps, final String fileName, Class<?> modelClass) {
        this(configMaps, fileName, StandardCharsets.UTF_8, modelClass);
    }

    public IOClasspathEOFlatMap(final ConfigMaps configMaps, final String fileName, Charset encoding, Class<?> modelClass) {
        Class<?>[] listClass = new Class[]{Map.class, modelClass};
        ioList = new IOClasspathEOList<>(configMaps, fileName, encoding, listClass);
        this.configMaps = configMaps;
    }

    public Map<String, T> read() {
        Map<String, T> flat = new HashMap<>();
        List<Map<String, T>> list = ioList.read();
        for (Map<String, T> cpEntry : list) {
            flat.putAll(cpEntry);
        }
        return flat;
    }
}

