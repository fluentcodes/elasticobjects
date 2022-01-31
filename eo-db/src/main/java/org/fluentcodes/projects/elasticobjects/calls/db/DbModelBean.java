package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;


/**
 * Created by Werner on 05.01.2022.
 */
public class DbModelBean extends PermissionBean implements DbModelInterface {
    private String modelKey;

    public DbModelBean() {}

    public DbModelBean(final DbModelConfig config) {
        super(config);
        this.modelKey = config.getModelKey();
    }
    @Override
    public String getConfigModelKey() {
        return hasConfigModelKey()?
                super.getConfigModelKey():
                DbModelConfig.class.getSimpleName();
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }
}
