package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/

/**
 * For setting upper value to EO.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Nov 10 14:38:44 CET 2020
 */
public class StringLowerFirstCharCall extends CallImpl implements SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/

    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        try {
            return lowerFirstCharacter((String) eo.get());
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    /**
     * Returns the getSerialized with an lower first character
     *
     * @param item A getSerialized item
     * @return Capitalized item
     */
    public static String lowerFirstCharacter(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        return item.substring(0, 1).toLowerCase() + item.substring(1);
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}
