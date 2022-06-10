package org.fluentcodes.projects.elasticobjects.io;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.io.IOClasspathObjectList;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Read files from the class path as with an object mapping for each file.
 */
public class IOClasspathEOFlatList<T> {
    private final ConfigMaps configMaps;
    private final IOClasspathObjectList<List<T>> ioList;
    public IOClasspathEOFlatList( final ConfigMaps configMaps, final String fileName, Class<?> modelClass ) {
        this(configMaps, fileName, StandardCharsets.UTF_8, modelClass);
    }

    public IOClasspathEOFlatList(final ConfigMaps configMaps, final String fileName, Charset encoding, Class<?> modelClass ) {
        Class<?>[] listClass = new Class[]{List.class, modelClass};
        ioList = new IOClasspathEOList<>(configMaps, fileName, encoding, listClass);
        this.configMaps = configMaps;
    }

    public List<T> read() {
        List<T> flat = new ArrayList<>();
        List<List<T>> list = ioList.read();
        for (List<T> cpEntry: list) {
            flat.addAll(cpEntry);
        }
        return flat;
    }
}

