package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/

/**
 * Replaces white space with underscore.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 11:53:03 CET 2020
 */
public class StringReplaceWhiteSpaceCall extends CallImpl implements SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/
    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        try {
            // https://alvinalexander.com/blog/post/java/remove-non-alphanumeric-characters-java-string/
            return ((String) eo.get()).replaceAll("[^a-zA-Z0-9]", "_");
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}
