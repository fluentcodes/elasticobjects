package org.fluentcodes.projects.elasticobjects.xpect;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.io.IOEo;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.tools.xpect.XpectAbstract;

public abstract class XpectEo extends XpectAbstract {
    public XpectEo(ConfigMaps configMaps, Class annotationClass, Class<?>... classes) {
        super(new IOEo(configMaps, classes), "json", annotationClass);
    }

    public JSONSerializationType getSerializationType() {
        return ((IOEo)getIo()).getType();
    }

    public void setSerializationType(JSONSerializationType serializationType) {
        ((IOEo)getIo()).setType(serializationType);
    }
}
