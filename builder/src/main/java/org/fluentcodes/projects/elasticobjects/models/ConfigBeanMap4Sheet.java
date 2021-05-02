package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigBeanMap4Sheet<T extends ConfigConfigInterface> extends ConfigBeanMap<T> {
    protected ConfigBeanMap4Sheet(final Scope scope, final Class<? extends ConfigBean> beanClass, final Class<? extends ConfigConfig> configClass)  {
        super(scope, beanClass, configClass);
    }
    protected ConfigBeanMap4Sheet(final Scope scope,final Class<? extends ConfigConfig> configClass)  {
        super(scope, configClass);
    }

    @Override
    void initMapByJson() {

    }
    public void readSheet(final String sheetKey) {
        StringBuilder feedback = new StringBuilder();
        final EO readEo = EoRoot.of(new ConfigMaps(Scope.TEST));
        XlsxReadCall modelCall = new XlsxReadCall(sheetKey + ":" + getDefaultConfigClass().getSimpleName());
        modelCall.setTargetPath("/=>[naturalId].");
        feedback.append(modelCall.execute(readEo));
        for (String key: readEo.keys()) {
            addBean(key, createBean(key, (Map) readEo.get(key)));
        }
    }

}
