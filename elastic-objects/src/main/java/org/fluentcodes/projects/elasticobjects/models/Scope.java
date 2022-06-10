package org.fluentcodes.projects.elasticobjects.models;

import java.util.List;
import java.util.Optional;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public enum Scope {
    DEV(), TEST(), QS(), INT(), STAGE(), PROD(), ALL(), CREATE();

    Scope() {

    }

    public boolean shouldLoaded(List<Scope> scope) {
        if (scope == null) {
            return true;
        }
        if (this == ALL) {
            return true;
        }
        if (scope.size() == 0) {
            return true;
        }
        if (scope.contains(ALL)) {
            return true;
        }
        return scope.contains(this);
    }

    public boolean filter(ConfigBean bean) {
        if (!bean.hasScope()) {
            return true;
        }
        List<Scope> scopeList = bean.getScope();
        if (scopeList.contains(Scope.ALL)) {
            return true;
        }
        return scopeList.contains(this);
    }

}
