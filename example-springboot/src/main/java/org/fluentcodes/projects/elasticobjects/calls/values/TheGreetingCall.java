package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/
/**
 * An example for a simple call with a field.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 15:07:40 CET 2020
 */
public class TheGreetingCall extends CallImpl implements SimpleCommand {
/*.{}.*/

    /*.{javaStaticNames}|*/
   public static final String GREETING = "greeting";
/*.{}.*/

    /*.{javaInstanceVars}|*/
   private  String greeting;
/*.{}.*/
    public TheGreetingCall() {
        super();
    }
    public TheGreetingCall(final String greeting) {
        super();
        this.greeting = greeting;
    }
    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        if (eo.get() == null) {
            throw new EoException("Value " + eo.getPathAsString() + " is null ");
        }
        if (greeting == null) {
            this.greeting = "Hi";
        }

        String name = "Stranger";
        if ((eo.get() instanceof String)) {
            name = (String)eo.get();
        }
        String value = this.greeting + " " + name + "!";
        if (hasTargetPath()) {
            eo.set(value, getTargetPath());
        }
        return (value);
    }

    /*.{javaAccessors}|*/
    /**
    The field value for TheGreetingCall example. 
    */

    public TheGreetingCall setGreeting(String greeting) {
        this.greeting = greeting;
        return this;
    }
    
    public String getGreeting () {
       return this.greeting;
    }
    
    public boolean hasGreeting () {
        return greeting!= null && !greeting.isEmpty();
    }
/*.{}.*/
}
