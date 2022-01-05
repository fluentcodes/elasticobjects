package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;

import java.util.List;

/**
 * Created by Werner on 13.9.2020.
 */
public interface ListParamsBeanInterface {
    String LIST_PARAMS = "listParams";

    ListParamsBean getListParams();

    String getTargetPath();

    default String mapEo(final IEOScalar eo, final List filteredResult) {
        if (filteredResult.isEmpty()) {
            return "";
        }
        String targetPath = getTargetPath();
        boolean isMapped = TemplateMarker.SQUARE.hasStartSequence(targetPath);
        if (!isMapped) {
            eo.set(filteredResult, targetPath);
        } else {
            for (int i = 0; i < filteredResult.size(); i++) {
                Object row = filteredResult.get(i);
                if (isMapped) {
                    String target = Parser.replacePathValues(targetPath, EoRoot.ofValue(eo.getConfigMaps(), row));
                    eo.set(row, target);
                }
            }
        }
        if (targetPath != null && targetPath.equals(Call.TARGET_AS_STRING)) {
            return "TODO asString";
        }
        return "";
    }
}
