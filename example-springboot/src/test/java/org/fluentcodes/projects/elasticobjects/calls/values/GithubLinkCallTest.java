package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.08.20.
 */
public class GithubLinkCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return GithubLinkCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }


    @Test
    public void configKey_CallImpl__execute__$() {
        GithubLinkCall call = new GithubLinkCall("CallImpl");
        EoRoot eo = ObjectProvider.createEo();
        String result = call.execute(eo);
    }

    @Test
    public void FileConfig_configKey_AnObjectCsv__execute__$() {
        GithubLinkCall call = new GithubLinkCall("FileConfig", "AnObject.csv");
        EoRoot eo = ObjectProvider.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(result).isEqualTo("\n" +
                "<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/input/data/AnObject.csv\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>AnObject.csv</a></nobreak>");
    }

    @Test
    public void call_NONE_SPIwebHeaderHtml___execute__expected() {
        GithubLinkCall call = new GithubLinkCall("NONE", "SPI|web|Header.html");
        EoRoot eo = ObjectProvider.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(result).isEqualTo("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak>");
    }

    @Test
    public void eo_NONE_SPIwebHeaderHtml___execute__expected() {
        GithubLinkCall call = new GithubLinkCall("NONE", "SPI|web|Header.html");
        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.execute();
        String result = (String) eo.get(PathElement.TEMPLATE);
        Assertions.assertThat(result).isEqualTo("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak>");
    }

    @Test
    public void template_NONE_SPIwebHeaderHtml___execute__expected() {
        TemplateCall call = new TemplateCall("START #{GithubLinkCall->NONE, SPI|web|Header.html}. END");
        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.execute();
        String result = (String) eo.get(PathElement.TEMPLATE);
        Assertions.assertThat(result).isEqualTo("START\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak> END");
    }


}
