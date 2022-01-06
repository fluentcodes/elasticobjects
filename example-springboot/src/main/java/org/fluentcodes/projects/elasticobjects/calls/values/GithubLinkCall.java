package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/*.{javaHeader}|*/
/**
 * Create a link, if its FileConfig or ModelConfig, to the github source.
 *
 * @author Werner Diwischek
 * @creationDate Thu Oct 01 00:00:00 CEST 2020
 * @modificationDate Tue Nov 10 15:38:29 CET 2020
 */
public class GithubLinkCall extends CallImpl  {
/*.{}.*/
    private static final String GITHUB_PIC = " <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>";

    /*.{javaStaticNames}|*/
   public static final String CONFIG_KEY = "configKey";
   public static final String CONFIG_TYPE = "configType";
/*.{}.*/

    /*.{javaInstanceVars}|*/
   private  String configKey;
   private  String configType;
/*.{}.*/

    private Config config;
    private boolean configured = true;
    private boolean noLabel = false;
    private Boolean noGithub = false;

    public GithubLinkCall() {
        super();
    }
    public GithubLinkCall(final String configKey) {
        this("ModelConfig", configKey);
    }
    public GithubLinkCall(final String configType, final String configKey) {
        super();
        setTargetPath(TARGET_AS_STRING);
        this.configKey = configKey;
        this.configType = configType;
    }

    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>0) {
            if (array[0].replaceAll("\\s","").isEmpty()) {
                configType = ModelConfig.class.getSimpleName();
            }
            else {
                configType = array[0];
            }
        }
        if (array.length>1) {
            configKey = array[1];
        }
        if (array.length>2) {
            throw new EoException("Short form should have form '<configType>,<configKey>' with length 2 but has size " + array.length + ": '" + values + "'." );
        }
    }

    @Override
    public String execute(final IEOScalar eo) {
        super.check(eo);
        if (!hasConfigKey()) {
            throw new EoException("No key is set. Could not find value");
        }
        this.configKey = Parser.replacePathValues(this.configKey, eo);
        Class configClass = null;
        if (!hasConfigType() || configType.equals("ModelConfig")) {
            configClass = ModelConfig.class;
            configType= configClass.getSimpleName();
        }
        else if (configType.equals("FileConfig")) {
            configClass = FileConfig.class;
        }
        else if (configType.equals("FieldConfig")||configType.equals("HostConfig")||configType.equals("DbSqlConfig")) {
            // just a link within
        }
        else if (configType.equals("NONE")) {
            configured = false;
        }
        else {
            throw new EoException("Not a valid configType: '" + configType + "'.");
        }
        StringBuilder builder = new StringBuilder();
        if (configClass == null && configured) {
            return "";
        }

        builder.append("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/");

        if (!configured) {
            String[] pathAndKey = configKey.split("\\|");
            if (pathAndKey.length!=3) {
                throw new EoException("A non configured github link call should has a delimiter '|' like 'filePath|fileName' but is '" + configKey + "'");
            }
            builder.append(Modules.findDirectory(pathAndKey[0]));
            builder.append(pathAndKey[1].replaceAll("\\.", "/"));
            builder.append("/");
            builder.append(pathAndKey[2]);
            builder.append("\">");
            builder.append(GITHUB_PIC);
            builder.append(pathAndKey[2]);
            builder.append("</a></nobreak>");
            return builder.toString();
        }
        this.config = (Config) eo.getConfigMaps().find(configClass, configKey);
        builder.append(config.getModule());
        if (configClass.equals(ModelConfig.class)) {
            builder.append("/src/");
            builder.append(config.getModuleScope());
            builder.append("/java/");
            builder.append(((ModelConfig) config).getPackagePath().replaceAll("\\.", "/"));
            builder.append("/");
            builder.append(((ModelConfig) config).getModelKey());
            builder.append(".java");
        } else if (configClass.equals(FileConfig.class)) {
            String filePath = ((FileConfig) config).getFilePath();
            if (filePath.startsWith("web") || filePath.startsWith("data")) {
                builder.append("/input/");
                builder.append(((FileConfig) config).getFilePath());
                builder.append("/");
                builder.append(((FileConfig) config).getFileName());
            }
            else {
                builder.append("/src/");
                builder.append(config.getModuleScope());
                builder.append("/resources/");
                builder.append(((FileConfig) config).getFilePath());
                builder.append("/");
                builder.append(((FileConfig) config).getFileName());
            }
        }
        builder.append("\">");
        builder.append(GITHUB_PIC);
        if (!noLabel) {
            builder.append(config.getNaturalId());
        }
        builder.append("</a></nobreak>");
        return builder.toString();
    }
    public Boolean isNoGithub() {
        return noGithub;
    }
    public Boolean getNoGithub() {
        return noGithub;
    }
    public void setNoGithub(Boolean noGithub) {
        this.noGithub = noGithub;
    }
    public void setNoGithub(boolean noGithub) {
        this.noGithub = noGithub;
    }

    protected Config getConfig() {
        return config;
    }
    protected void setNoLabel() {
        noLabel = true;
    }

    protected boolean isConfigured() {
        return configured;
    }

    /*.{javaAccessors}|*/
    /**
    Key for configuration  {@link ConfigInterface}
    */

    public GithubLinkCall setConfigKey(String configKey) {
        this.configKey = configKey;
        return this;
    }
    
    public String getConfigKey () {
       return this.configKey;
    }
    
    public boolean hasConfigKey () {
        return configKey!= null && !configKey.isEmpty();
    }
    /**
    Key for configuration type like ModelConfig, FileConfig, FieldConfig, HostConfig, DbSqlConfig.
    */

    public GithubLinkCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }
    
    public String getConfigType () {
       return this.configType;
    }
    
    public boolean hasConfigType () {
        return configType!= null && !configType.isEmpty();
    }
/*.{}.*/
}
