package org.fluentcodes.projects.elasticobjects.models;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface Config extends Model {
    void resolve() ;
    String getKey();
    String getModule();
    String getSubModule();
    List<Scope> getScope();
    EOConfigsCache getConfigsCache();
    Expose getExpose();
    void setExpose(Expose expose);
    String getConfigModelKey();
    boolean hasExpose();
    boolean hasScope(final Scope scope);


}
