package org.fluentcodes.projects.elasticobjects.io;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.io.IOMappingObject;
import org.fluentcodes.tools.io.IORuntimeException;

public class IOEo<T> extends IOMappingObject<T> {
    private final ConfigMaps configMaps;
    private JSONSerializationType type = JSONSerializationType.STANDARD;

    public IOEo(final ConfigMaps configMaps, final Class<?>... classes) {
        super(classes);
        this.configMaps = configMaps;
    }

    public IOEo(final String fileName, final ConfigMaps configMaps, final Class<?>... classes) {
        super(fileName, classes);
        this.configMaps = configMaps;
    }

    public JSONSerializationType getType() {
        return type;
    }

    public void setType(JSONSerializationType type) {
        this.type = type;
    }

    @Override
    public String asString(T object) {
        if (object == null) {
            throw new IORuntimeException("Null object for serialization!");
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            if (object instanceof IEOObject) {
                return new EOToJSON()
                        .setSerializationType(type)
                        .toJson((IEOScalar) object);
            }
            EoRoot eo = EoRoot.ofValue(configMaps, object);
            if (type != null) {
                return new EOToJSON()
                        .setSerializationType(type)
                        .toJson(eo);
            }
            return new EOToJSON()
                    .setSerializationType(JSONSerializationType.STANDARD)
                    .toJson(eo);
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public T asObject(final String asString) {
        try {
            return (T) asEo(asString).get();
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    public EoRoot asEo(final String asString) {
        try {
            return EoRoot.ofClass(configMaps, asString, getMappingClasses());
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }
}
