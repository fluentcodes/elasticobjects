package org.fluentcodes.projects.elasticobjects.io;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.io.IOClasspathObjectList;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Read files from the class path as with an object mapping for each file.
 */
public class IOClasspathEOList<T> extends IOClasspathObjectList<T> {
    private final ConfigMaps configMaps;

    public IOClasspathEOList(
            final ConfigMaps configMaps,
            final String fileName, Class<?>... modelClasses) {
        super(fileName, modelClasses);
        this.configMaps = configMaps;
    }

    public IOClasspathEOList(
            final ConfigMaps configMaps,
            final String fileName,
                             Charset encoding,
                             Class<?>... modelClasses) {
        super(fileName, encoding, modelClasses);
        this.configMaps = configMaps;
    }

    @Override
    public List<T> asObject(final List<String> in) {
        List<T> objectList = new ArrayList<>();
        for (String entry : in) {
            objectList.add(new IOEo<T>(configMaps, getMappingClasses()).asObject(entry));
        }
        return objectList;
    }

    @Override
    public List<T> asObject(final String in) {
        List<T> objectList = new ArrayList<>();
        objectList.add(new IOEo<T>(configMaps, getMappingClasses()).asObject(in));
        return objectList;
    }

    @Override
    public String asString(final List<T> in) {
        StringBuilder listString = new StringBuilder("[");
        for (int i = 0; i < in.size(); i++) {
            listString.append(new IOEo<T>(configMaps, getMappingClasses()).asString(in.get(i)));
            if (i < in.size() - 1) {
                listString.append(",");
            }
        }
        listString.append("]");
        return listString.toString();
    }
}

