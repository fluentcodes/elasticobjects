package org.fluentcodes.projects.elasticobjects.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Get endpoint for example web site.
 * Will create a TemplateResourceCall json object loading the template ContentPage.
 */

@RestController
public class WebEoGet {
    private static final Logger LOG = LogManager.getLogger(WebEoGet.class);
    private static final String SELECTED_ITEM = "selectedItem";
    private static final String CONTENT_DIRECTORY = "contentDirectory";
    public static final String CONFIG_TYPE = "configType";
    public static final String CONFIG_FILTER = "configFilter";
    public static final String CONFIG_SELECTED = "configSelected";

    @Value("${elasticobjects.scope:QS}")
    String scope;

    ConfigMaps cache;

    @Autowired
    public WebEoGet(ConfigMaps cache) {
        this.cache = cache;
    }

    /**
     * A root page with content loaded from input/web/docs
     *
     * @param contentName the page name loaded from source path file
     * @return a processed template page
     */
    @GetMapping(value = "/{contentName:.+}.html")
    @ResponseBody
    public String createRootPage(@PathVariable String contentName) {
        return createPage("docs", contentName + ".html");
    }

    /**
     * A blog page with content loaded from input/web/blog.
     * Some thoughts about elastic objects project.
     * originally used for version description and not shown actually.
     *
     * @param contentName the pagename loaded from source path file
     * @return a processed template page
     */

    @GetMapping(value = "/blog/{contentName:.+}")
    @ResponseBody
    public String createBlogPage(@PathVariable String contentName) {
        return createPage("blog", contentName);
    }

    /**
     * An eo page with content loaded from input/web/eo.
     * These pages primarily describe the usage within an java application.
     *
     * @param contentName the pagename loaded from source path file
     * @return a processed template page
     */
    @GetMapping(value = "/eo/{contentName:.+}")
    @ResponseBody
    public String createEoPage(@PathVariable String contentName) {
        return createPage("eo", contentName);
    }

    /**
     * An example page with content loaded from input/web/examples/directory.
     * These pages primarily describe the usage within an java application.
     *
     * @param contentName the page name loaded from source path file
     * @param directory   the sub directory
     * @return a processed template page
     */

    @GetMapping(value = "/examples/{directory}/{contentName:.+}")
    @ResponseBody
    public String createExamplesPageWithDirectory(@PathVariable String directory, @PathVariable String contentName) {
        return createPage("examples", directory + "/" + contentName);
    }

    /**
     * An example page with content loaded from input/web/examples.
     * These pages primarily describe the usage within an java application.
     *
     * @param contentName the page name loaded from source path file
     * @return a processed template page
     */
    @GetMapping(value = "/examples/{contentName:.+}")
    @ResponseBody
    public String createExamplesPage(@PathVariable String contentName) {
        return createPage("examples", contentName);
    }

    /**
     * A faq page with content loaded from input/web/faq.
     * These pages primarily describe question and answers pattern.
     *
     * @param contentName the page name loaded from source path file
     * @return a processed template page
     */
    @GetMapping(value = "/faq/{contentName:.+}")
    @ResponseBody
    public String createFaqPage(@PathVariable String contentName) {
        return createPage("faq", contentName);
    }

    /**
     * An faq page with content loaded from input/web/faq.
     * These pages primarily describe question and answers pattern.
     *
     * @param contentName the page name loaded from source path file
     * @return a processed template page
     */
    @GetMapping(value = "/howto/{contentName:.+}")
    @ResponseBody
    public String createHowToPage(@PathVariable String contentName) {
        return createPage("howto", contentName);
    }

    private String createPage(final String contentDirectory, final String selectedItem) {
        EoRoot eo = EoRoot.of(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem, SELECTED_ITEM);
        eo.set(contentDirectory, CONTENT_DIRECTORY);
        eo.addCall(new TemplateResourceCall("ContentPage.html"));

        LOG.info("Request for {}/{}", contentDirectory, selectedItem);
        try {
            eo.execute();
        } catch (Exception e) {
            LOG.error(e);
            return e.getMessage();
        }
        if (eo.hasErrors()) {
            LOG.error(eo.getLog());
            return eo.getLog();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @GetMapping(value = "/config/{configType}/{configSelected:.+}")
    @ResponseBody
    public String createConfigPage(@PathVariable String configType, @PathVariable String configSelected, @RequestParam(required = false, defaultValue = ".*") String configFilter) {
        EoRoot eo = EoRoot.of(cache);

        eo.set(configType + " - " + configSelected, SELECTED_ITEM);
        eo.set(configFilter, CONFIG_FILTER);
        eo.set(configType, CONFIG_TYPE);
        eo.set("config", CONTENT_DIRECTORY);
        eo.set(configSelected, CONFIG_SELECTED);

        LOG.info("Request for {}/{}", configType, configSelected);
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.setRoles(Arrays.asList(WebEo.getRoles()));

        try {
            eo.execute();
            if (eo.hasErrors()) {
                LOG.error(eo.getLog());
                return "Errors occured: " + eo.getLog();
            }
        } catch (Exception e) {
            LOG.error(e);
            return "Exception thrown: " + e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @GetMapping(value = "/configs/{selectedItem:.+}.html")
    @ResponseBody
    public String createConfigStartPage(@PathVariable String selectedItem) {
        EoRoot eo = EoRoot.of(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem + ".html", SELECTED_ITEM);
        eo.set(".*", "configFilter");
        eo.set(selectedItem, "configType");

        LOG.info("Request for config {}.", selectedItem);
        eo.addCall(new TemplateResourceCall("ConfigsStartPage.html"));
        try {
            eo.execute();
            if (eo.hasErrors()) {
                LOG.error(eo.getLog());
                return "Errors occured: " + eo.getLog();
            }
        } catch (Exception e) {
            LOG.error(e);
            return "Exception thrown: " + e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }
}
