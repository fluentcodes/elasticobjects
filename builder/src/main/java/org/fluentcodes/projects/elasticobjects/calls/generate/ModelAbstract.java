package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/*=>{javaHeader}|*/
/**
 * Abstract super class for generating code.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:29:32 CET 2020
 */
public abstract class ModelAbstract extends GenerateAbstract implements ModelAbstractInterface {
    public static final String MODEL_BEANS_JSON = "ModelBeans.json";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
    private String packagePath;
/*=>{}.*/

    public ModelAbstract() {
        super();
    }

    @Override
    protected boolean init(final EO eo) {
        ModelBeanMap4SheetCall.read(getSourceFileConfigKey(), eo);
        super.init(eo);
        return true;
    }

    protected String create(final EO eoModel, FileConfig targetFileConfig) {
        if (targetFileConfig == null)
            throw new EoException("Null Target file");
        if (!targetFileConfig.hasTemplate())
            throw new EoException("Target file without template for '" + targetFileConfig.getNaturalId() + "'");
        if (!eoModel.getConfigsCache().hasFile(targetFileConfig.getTemplate()))
            throw new EoException("Target file '" + targetFileConfig.getNaturalId() + "' with template '" + targetFileConfig.getTemplate() + "': Template entry not found");

        TemplateResourceStoreCall templateCall = new TemplateResourceStoreCall(targetFileConfig.getTemplate(), targetFileConfig.getNaturalId());
        return templateCall.execute(eoModel);
    }


/*=>{javaAccessors}|*/

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
    /*=>{}.*/
}
