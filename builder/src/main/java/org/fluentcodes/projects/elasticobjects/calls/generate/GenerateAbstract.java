package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.BuilderParams;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Date;

/*=>{javaHeader}|*/

/**
 * Abstract super class for generating code.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:29:32 CET 2020
 */
public abstract class GenerateAbstract extends CallImpl implements GenerateAbstractInterface {
    public static final String MODULE = "module";
    public static final String MODULE_SCOPE = "moduleScope";
    public static final String PROJECT_DIRECTORY = "projectDirectory";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
    private String module;
    private String moduleScope;
    private String fileEnding;
    private String projectDirectory;
    private String targetFileConfigKey;
    private String sourceFileConfigKey;
/*=>{}.*/

    public GenerateAbstract() {
        super();
    }

    public void mergeParams(BuilderParams builderParams) {
        if (builderParams.hasModule()) setModule(builderParams.getModule());
        if (builderParams.hasModuleScope()) setModuleScope(builderParams.getModuleScope());
        if (builderParams.hasFileEnding()) setFileEnding(builderParams.getFileEnding());
        if (builderParams.hasProjectDirectory()) setProjectDirectory(builderParams.getProjectDirectory());
        if (builderParams.hasTargetFileConfigKey()) setTargetFileConfigKey(builderParams.getTargetFileConfigKey());
        if (builderParams.hasSourceFileConfigKey()) setSourceFileConfigKey(builderParams.getSourceFileConfigKey());
    }

    @Override
    protected boolean init(final EO eo) {
        if (!hasTargetFileConfigKey()) {
            throw new EoException("targetFileConfigKey must be set but is empty");
        }
        if (!hasProjectDirectory()) {
            throw new EoException("projectDirectory must be set but is empty");
        }
        if (!hasFileEnding()) {
            fileEnding = "";
        }
        setModule(ParserSqareBracket.replacePathValues(module,eo));
        setModuleScope(ParserSqareBracket.replacePathValues(moduleScope, eo));
        setProjectDirectory(ParserSqareBracket.replacePathValues(projectDirectory, eo));
        defaultModule();
        defaultModuleScope();
        eo.set(module, Path.DELIMITER, MODULE);
        eo.set(moduleScope, Path.DELIMITER, MODULE_SCOPE);
        eo.set(projectDirectory, Path.DELIMITER, PROJECT_DIRECTORY);
        eo.set(new Date().toString(), Path.DELIMITER, "date");
        eo.set(fileEnding, Path.DELIMITER, FILE_ENDING);
        return true;
    }

/*=>{javaAccessors}|*/

    public String getTargetFileConfigKey() {
        return targetFileConfigKey;
    }
    public GenerateAbstract setTargetFileConfigKey(String targetFileConfigKey) {
        this.targetFileConfigKey = targetFileConfigKey;
        return this;
    }

    public String getSourceFileConfigKey() {
        return sourceFileConfigKey;
    }

    public GenerateAbstract setSourceFileConfigKey(String sourceFileConfigKey) {
        this.sourceFileConfigKey = sourceFileConfigKey;
        return this;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public GenerateAbstract setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
        return this;
    }

    /**
    It's used to specify the file ending in different context. 
    */



    public GenerateAbstract setFileEnding(String fileEnding) {
        this.fileEnding = fileEnding;
        return this;
    }
    
    public String getFileEnding () {
       return this.fileEnding;
    }


    /**
    Defines a target module where generating occurs. 
    */

    public GenerateAbstract setModule(String module) {
        this.module = module;
        return this;
    }

    public void defaultModule() {
        if (hasModule()) {
            return;
        }
        this.module = ".*";
    }

    public void defaultModuleScope() {
        if (hasModuleScope()) {
            return;
        }
        this.moduleScope = ".*";
    }

    public String getModule () {
       return this.module;
    }

    /**
     Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    public GenerateAbstract setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }
    public String getModuleScope () {
       return this.moduleScope;
    }

    /*=>{}.*/
}
