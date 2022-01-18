package org.fluentcodes.projects.elasticobjects.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class WebEo {
    private final static Logger LOG = LogManager.getLogger(WebEo.class);
    @Autowired
    private ConfigMaps configsCache;

    private static final LogLevel getLevel(final String logLevelAsString) {
        if (logLevelAsString == null || logLevelAsString.isEmpty()) {
            return LogLevel.WARN;
        }
        try {
            return LogLevel.valueOf(logLevelAsString);
        } catch (Exception e) {
            return LogLevel.WARN;
        }
    }

    protected static final String[] getRoles() {
        /**HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("roles") != null) {
            return (String[]) session.getAttribute("roles");
        }*/
        String[] roles = new String[]{"guest"};
        //session.setAttribute("roles", roles);
        return roles;
    }
    // https://stackoverflow.com/questions/36520314/accessing-httpsession-in-a-controlleradvice-in-a-springboot-application
    // https://stackoverflow.com/questions/49670209/can-spring-map-post-parameters-by-a-way-other-than-requestbody

    @RequestMapping(value = "/eo", method = RequestMethod.POST)
    public String eoPost(@RequestBody String body) {
        return eoPostForm(body, LogLevel.WARN.name());
    }


    @RequestMapping(value = "/eo-form", method = RequestMethod.POST)
    public String eoPostForm(
            @RequestParam(value = "eo", required = true) final String eoAsString,
            @RequestParam(value = "logLevel", required = false, defaultValue = "WARN") final String logLevelAsString
    ) {
        if (eoAsString == null) {
            return "No 'value' is set!";
        }
        if (eoAsString.isEmpty()) {
            return "'value' is empty!";
        }

        LOG.info("Post request " + eoAsString);

        final String[] roles = getRoles();
        final LogLevel logLevel = getLevel(logLevelAsString);

        try {
            final EoRoot eo = EoRoot.of(this.configsCache);
            eo.setLogLevel(logLevel);
            eo.map(eoAsString);

            eo.setRoles(Arrays.asList(roles));
            try {
                eo.execute();
                if (!eo.getLogList().isEmpty()) {
                    LOG.error(eo.getLog());
                    return "Error occured: " + eo.getLog();
                }
            }
            catch (Exception e) {
                LOG.error(e);
                return "Exception occured: " + e.getMessage();
            }
            eo.debug("'/eo-form' is executed ");
            if (((EoChild)eo).hasEo("asTemplate")) {
                return (String)eo.get(PathElement.TEMPLATE);
            }
            final String result = new EOToJSON()
                    .setSpacer("  ")
                    .toJson(eo);
            return result;
        } catch (Exception e) {
            return "Unexpected Exception occured. Please contact Elastic Objects support! " + e.getMessage();
        }
    }

    @RequestMapping(value = "/eo-template", method = RequestMethod.POST)
    public String eoPostTemplate(
            @RequestParam(value = "template", required = false) final String template,
            @RequestParam(value = "logLevel", required = false, defaultValue = "WARN") final String logLevelAsString
    ) {
        if (template == null) {
            return "No 'template' data provided at all!";
        }
        if (template.isEmpty()) {
            return "'template' data is empty!";
        }

        final String[] roles = getRoles();
        final LogLevel logLevel = getLevel(logLevelAsString);

        try {
            final EoRoot eo = EoRoot.of(this.configsCache);
            eo.setLogLevel(logLevel);

            eo.setRoles(Arrays.asList(roles));
            try {
                eo.execute();
                if (!eo.getLogList().isEmpty()) {
                    LOG.error(eo.getLog());
                    return "Error occured: " + eo.getLog();
                }
            }
            catch (Exception e) {
                LOG.error(e);
                return "Exception occured: " + e.getMessage();
            }
            TemplateCall call = new TemplateCall();
            call.setContent(template.replaceAll("&gt;", ">"));
            String result = call.execute(eo);
            eo.debug("'/eo-template' is executed ");
            return result;
        } catch (Exception e) {
            return "Unexpected Exception occured. Please contact Elastic Objects support! " + e.getMessage();
        }
    }
}
