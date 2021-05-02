package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.BuilderParams;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.Model;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public interface GenerateAbstractInterface extends Call {
    /*=>{}.*/
    /*=>{javaStaticNames}|*/
    String FILE_ENDING = "fileEnding";
    String ALL = ".*";
    void mergeParams(BuilderParams params);

    default void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<module>[,<moduleScope>][,<condition>][,<keepCall>]' with max length 4 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setModule(array[0]);
        }
        if (array.length>1) {
            setModuleScope( array[1]);
        }
        if (array.length>2) {
            setFileEnding( array[2]);
        }
    }

    default void defaultValues() {
        defaultModule();
        defaultModuleScope();
        defaultProjectDirectory();
        defaultOverwrite();
        defaultFileEnding();
    }

    String getSourceFileConfigKey();
    GenerateAbstractInterface setSourceFileConfigKey(String sourceFileConfig);
    default boolean hasSourceFileConfigKey() {
        return getSourceFileConfigKey() != null && !getSourceFileConfigKey().isEmpty();
    }

    default boolean isModelBean(final EO eoModel) {
        if (!(eoModel.get() instanceof Model)) {
            return false;
        }
        return true;
    }

    default ModelBean getModelBean (final EO eoModel) {
        /*if (eoModel.isEmpty()) {
            throw new EoException("Problem eoModel is empty '" + eoModel.getModelClass().getSimpleName() + "'");
        }*/
        /*if (eoModel.getModelClass() != ModelBean.class) {
            throw new EoException("Problem eoModel is not instance of modelBean '" + eoModel.getModelClass().getSimpleName() + "'");
        }*/
        return (ModelBean) eoModel.get();
    }

    default boolean filter(ModelBean modelBean) {
        if ("basic".equals(modelBean.getModule())) {
            return false;
        }
        if (hasModule() && ! modelBean.getModule().matches(getModule())) {
            return false;
        }
        if (hasModuleScope() && !modelBean.getModuleScope().matches(getModuleScope())) {
            return false;
        }
        return true;
    }

    String getTargetFileConfigKey();
    GenerateAbstractInterface setTargetFileConfigKey(String targetFileConfig);
    default boolean hasTargetFileConfigKey() {
        return getTargetFileConfigKey() != null && !getTargetFileConfigKey().isEmpty();
    }

    String getModule();
    GenerateAbstractInterface setModule(String moduleScope);
    default boolean hasModule () {
        return getModule()!= null && !getModule().isEmpty();
    }
    default GenerateAbstractInterface mergeModule(Object module) {
        if (module == null) {
            return this;
        }
        if (hasModule()) {
            return this;
        }
        setModule(ScalarConverter.toString(module));
        return this;
    }
    default void defaultModule() {
        if (!hasModule()) {
            setModule(ALL);
        }
    }

    String getModuleScope();
    GenerateAbstractInterface setModuleScope(String moduleScope);
    default boolean hasModuleScope () {
        return getModuleScope()!= null && !getModuleScope().isEmpty();
    }
    default GenerateAbstractInterface mergeModuleScope(Object value) {
        if (value == null) {
            return this;
        }
        if (hasModuleScope()) {
            return this;
        }
        setModuleScope(ScalarConverter.toString(value));
        return this;
    }
    default void defaultModuleScope() {
        if (!hasModuleScope()) {
            setModuleScope(ALL);
        }
    }

    String getFileEnding();
    GenerateAbstractInterface setFileEnding(String moduleScope);
    default boolean hasFileEnding () {
        return getFileEnding()!= null && !getFileEnding().isEmpty();
    }
    default GenerateAbstractInterface mergeFileEnding(Object value) {
        if (value == null) {
            return this;
        }
        if (hasFileEnding()) {
            return this;
        }
        setFileEnding(ScalarConverter.toString(getFileEnding()));
        return this;
    }

    default void defaultFileEnding() {
        if (!hasFileEnding()) {
            setFileEnding(ALL);
        }
    }

    String getProjectDirectory();
    GenerateAbstractInterface setProjectDirectory(String projectDirectory);
    default boolean hasProjectDirectory() {
        return getProjectDirectory()!=null && !getProjectDirectory().isEmpty();
    }
    default GenerateAbstractInterface mergeProjectDirectory(Object value) {
        if (value == null) {
            return this;
        }
        if (hasProjectDirectory()) {
            return this;
        }
        setProjectDirectory(getProjectDirectory());
        return this;
    }

    default void defaultProjectDirectory() {
        if (!hasProjectDirectory()) {
            setProjectDirectory("..");
        }
    }

    default void defaultOverwrite() {
        if (getOverwrite() == null) {
            setOverwrite(false);
        }
    }



}
