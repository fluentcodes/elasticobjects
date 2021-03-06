package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/*=>{javaHeader}|*/
/**
 * Abstract super class for generating code.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:29:32 CET 2020
 */
public abstract class GenerateModelAbstract extends GenerateAbstract implements GenerateModelProperties{
    public static final String MODEL_BEANS_JSON = "ModelBeans.json";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
    private String packagePath;
/*=>{}.*/

    public GenerateModelAbstract() {
        super();
    }

    public static EO READ(Scope scope) {
        EO eo = EoRoot.of(new EOConfigsCache(scope));
        new FileReadCall(MODEL_BEANS_JSON)
                .setTargetPath(".")
                .execute(eo);
        return eo;
    }

    public static boolean READ(final EO eo) {
        new FileReadCall(MODEL_BEANS_JSON)
                .setTargetPath(".")
                .execute(eo);
        return true;
    }

    @Override
    protected boolean init(final EO eo) {
        ModelBeansReadCall.read(getSourceFileConfigKey(), eo);
        super.init(eo);
        return true;
    }

    protected String create(final EO eoModel, final String sourceFileConfigKey, final String targetFileConfigKey) {
        TemplateResourceStoreCall templateCall = new TemplateResourceStoreCall(sourceFileConfigKey, targetFileConfigKey);
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
