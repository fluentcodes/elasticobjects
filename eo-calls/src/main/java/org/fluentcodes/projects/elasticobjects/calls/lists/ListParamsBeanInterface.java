package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.Path.DELIMITER;

/**
 * Created by Werner on 13.9.2020.
 */
public interface ListParamsBeanInterface {
    String LIST_PARAMS = "listParams";

    ListParamsBean getListParams();

    String getTargetPath();
    default String mapEo(final EOInterfaceScalar eo, final List<Map<String, Object>> filteredResult) {
        return mapEo(eo, filteredResult, Map.class);
    }
    default String mapEo(final EOInterfaceScalar eo, final List<Map<String, Object>> filteredResult, Class<?> mapClass) {
        if (filteredResult.isEmpty()) {
            return "";
        }
        String targetPath = getTargetPath();
        boolean isMapped = TemplateMarker.SQUARE.hasStartSequence(targetPath);
        for (int i = 0; i < filteredResult.size(); i++) {
            Object row = filteredResult.get(i);
            String target = targetPath + DELIMITER + Integer.toString(i);
            if (isMapped) {
                target = Parser.replacePathValues(targetPath, EoRoot.ofValue(eo.getConfigMaps(), row));
            }
            if (mapClass == Map.class) {
                eo.set(row, target);
                return "";
            }
            Models models = new Models(eo.getConfigMaps(), mapClass);
            EoChild targetEo = (EoChild) eo.set(models.create(), target);
            targetEo.map(row);
        }

        if (targetPath != null && targetPath.equals(Call.TARGET_AS_STRING)) {
            return "TODO asString";
        }
        return "";
    }
}
