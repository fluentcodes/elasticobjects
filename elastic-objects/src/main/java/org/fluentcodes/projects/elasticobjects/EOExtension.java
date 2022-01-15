package org.fluentcodes.projects.elasticobjects;

/**
 * Created by Werner on 19.03.2017.
 */
public interface EOExtension {
    Object doBeforeMap(EO adapter, Object object) ;

    void doAfterMap(EO adapter) ;
}
