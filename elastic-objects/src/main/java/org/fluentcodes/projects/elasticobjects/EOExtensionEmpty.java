package org.fluentcodes.projects.elasticobjects;

/**
 * Created by Werner on 19.03.2017.
 */
public class EOExtensionEmpty implements EOExtension {

    public Object doBeforeMap(EO adapter, Object objectToMap)  {
        return adapter.getModel().create();
    }

    public void doAfterMap(EO adapter) {

    }
}
