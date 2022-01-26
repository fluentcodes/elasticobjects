package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by werner.diwischek on 13.01.18.
 */
public class EOToJSON {
    public static final String REPEATED = ".repeated";
    private PathPattern pathPattern;
    private JSONSerializationType serializationType;
    private boolean checkObjectReplication = false;
    private List<EO> objectRegistry;
    private String spacer = "  ";

    public EOToJSON() {

    }

    public EOToJSON(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
    }

    public boolean isCheckObjectReplication() {
        return checkObjectReplication;
    }

    public EOToJSON setCheckObjectReplication(boolean checkObjectReplication) {
        if (this.checkObjectReplication == true) {
            return this;
        }
        this.checkObjectReplication = checkObjectReplication;
        return this;
    }

    public EOToJSON setSpacer(String spacer) {
        this.spacer = spacer;
        return this;
    }

    public PathPattern getPathPattern() {
        return pathPattern;
    }

    public EOToJSON setPathPattern(PathPattern pathPattern) {
        this.pathPattern = pathPattern;
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public EOToJSON setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    private boolean isStandard() {
        return this.serializationType == JSONSerializationType.STANDARD;
    }

    public String toJson(final ConfigMaps cache, final Object object) {
        if (isStandard()) {
            EoRoot mapEo = EoRoot.ofClass(cache, Map.class);
            mapEo.setSerializationType(JSONSerializationType.STANDARD);
            return toJson(mapEo.map(object));
        }
        return toJson(EoRoot.ofValue(cache, object));
    }

    public String toJson(final EOInterfaceScalar eo) {
        if (eo.isScalar()) {
            return eo.get().toString();
        }
        if (serializationType == null) {
            this.setSerializationType(eo.getSerializationType());
        }
        StringWriter stringWriter = new StringWriter();
        this.addContainerStart(stringWriter, eo);
        toJson(stringWriter, eo, spacer);
        addContainerEnd(stringWriter, eo, "");
        return stringWriter.toString();
    }

    private void toJson(final StringWriter stringWriter, final EOInterfaceScalar eoParent, final String indentSpace) {
        if (eoParent.isEmpty() && serializationType != JSONSerializationType.EO) {
            return;
        }
        Set<String> fieldKeySet = ((EoChild) eoParent).keysEo();
        if (fieldKeySet.isEmpty()) {
            return;
        }
        boolean first = true;

        for (String fieldName : fieldKeySet) {
            if (PathElement.isParentNotSet(fieldName) && serializationType == JSONSerializationType.STANDARD) {
                continue;
            }
            if (eoParent.isJsonIgnore(fieldName)) {
                continue;
            }
            EoChildScalar eoChild = (EoChildScalar) eoParent.getEo(fieldName);
            if (eoChild.isEmpty()) {
                continue;
            }
            if (!first) {
                stringWriter.append(",");
            }
            first = false;
            if (!spacer.isEmpty()) {
                stringWriter.append("\n");
            }
            stringWriter.append(indentSpace);

            if (!(eoChild instanceof EoChild)) {
                eoChild.writeAsString(stringWriter, serializationType);
                continue;
            }
            eoChild.writeName(stringWriter, serializationType);
            addContainerStart(stringWriter, eoChild);
            toJson(stringWriter, eoChild, indentSpace + spacer);
            addContainerEnd(stringWriter, eoChild, indentSpace);
        }
    }

    private final void addContainerStart(final StringWriter stringWriter, final EOInterfaceScalar eo) {
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append("{");
            return;
        }
        if (eo.isList()) {
            stringWriter.append("[");
        } else {
            stringWriter.append("{");
        }
    }

    private void addContainerEnd(final StringWriter stringWriter, final EOInterfaceScalar eo, final String indentSpace) {
        if (!spacer.isEmpty()) {
            stringWriter.append("\n");
        }
        stringWriter.append(indentSpace);
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append("}");
            return;
        }
        if (eo.isList()) {
            stringWriter.append("]");
        } else {
            stringWriter.append("}");
        }
    }

    private void addRepeated(final StringWriter stringWriter, final EO repeated, final String indentSpace) {
        stringWriter.append(indentSpace);
        stringWriter.append("{\"");
        stringWriter.append(REPEATED);
        stringWriter.append("\": \"");
        stringWriter.append(repeated.getPathAsString());
        stringWriter.append("\"}");
        if (!spacer.isEmpty()) {
            stringWriter.append("\n");
        }
    }

    private final EO checkObjectReplication(final EO eo) {
        if (!this.checkObjectReplication) {
            return null;
        }
        if (objectRegistry == null) {
            objectRegistry = new ArrayList<EO>();
        }
        if (eo.get() == null) {
            return null;
        }
        Object object = eo.get();
        for (EO registered : objectRegistry) {
            if (registered.get() == object) {
                return registered;
            }
        }
        objectRegistry.add(eo);
        return null;
    }

}
