package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/
/**
 * Create a link to a config page and if its FileConfig or ModelConfig to the github source.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 15:40:45 CET 2020
 */
public class ConfigLinkCall extends GithubLinkCall  {
/*.{}.*/

    /*.{javaStaticNames}|*/
/*.{}.*/

    /*.{javaInstanceVars}|*/
/*.{}.*/
    public ConfigLinkCall() {
        super();
    }
    public ConfigLinkCall(final String fileConfigKey) {
        this("ModelConfig", fileConfigKey);
    }
    public ConfigLinkCall(final String configType, final String fileConfigKey) {
        super(configType, fileConfigKey);
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        if (!hasConfigKey()) {
            throw new EoException("No key is set. Could not find value");
        }
        super.setNoLabel();
        StringBuilder builder = new StringBuilder(super.execute(eo));
        builder.append("\n<a href=\"/config/");
        builder.append(getConfigType());
        builder.append("/");
        builder.append(getConfigKey());
        builder.append("\">&equiv;");
        builder.append(getConfigKey());
        builder.append("</a>\n");
        return builder.toString();
    }
    /*.{javaAccessors}|*/
/*.{}.*/
}
