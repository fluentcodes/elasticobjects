package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCallTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testExampleListDouble() {
        String json = "{\"(List,Double)items\":{\"0\":1,\"1\":2}}";
        Assertions.assertThat(json).isNotEmpty();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        XpectStringJunit4.assertStatic(body);
    }

    @Test
    public void testSinusValueCall() {
        String json = "{\n" +
                "  \"(List,Double)source\": {\n" +
                "    \"0\": 1,\n" +
                "    \"1\": 2,\n" +
                "    \"2\": 3\n" +
                "  },\n" +
                "  \"(LogLevel)_logLevel\": \"WARN\",\n" +
                "  \"(List)_calls\": {\n" +
                "    \"(SinusValueCall)0\": {\n" +
                "      \"sourcePath\": \"/source/*\",\n" +
                "      \"targetPath\": \"/target\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Assertions.assertThat(json).isNotEmpty();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
    }

    @Test
    public void eo_TemplateDirResourceCall_examples_CallImplHtml_none__post__noLog() {
        String json = "{\"(TemplateDirResourceCall).\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }";
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
        EoRoot eo = ObjectProvider.createEo(body);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void eo_TemplateDirResourceCall_examples_CallImplHtml__post__hasLog() {
        String json = "{\"(TemplateDirResourceCall).\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\"\n" +
                "                }\n" +
                "            }";
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        Assertions.assertThat(body)
                .isNotEmpty()
                .contains("ERROR -");
    }

    @Test
    public void eo_ConfigKeysCall_configKey_ModelConfig__post__keysNotEmpty() {
        ;
        ;
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, ConfigKeysCallTest.DATA, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
        EoRoot eo = ObjectProvider.createEo(body);
        Assertions.assertThat((List) eo.get("keys")).isNotEmpty();
    }

    @Test
    public void examples_DbCallHtml__get__keysNotEmpty() {
        String url = "http://localhost:" + port + "/examples/DbCall.html";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
    }

    @Test
    public void examples_DbModelReadCallHtml__get__keysNotEmpty() {
        String url = "http://localhost:" + port + "/examples/DbModelReadCall.html";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
    }

    @Test
    public void examples_CallImplHtml__get__keysNotEmpty() {
        String url = "http://localhost:" + port + "/examples/CallImpl.html";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
    }

    @Test
    public void givenGetContent_whenImpressumHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Impressum.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        XpectStringJunit4.assertStatic(parsedContent);
    }

    @Ignore("mvn does not work")
    @Test
    public void givenGetContent_whenHomeHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Home.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        XpectStringJunit4.assertStatic(parsedContent);
    }

    @Ignore("mvn does not work")
    @Test
    public void givenGetExamplesStart_whenEo_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/examples/ExamplesStart.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        XpectStringJunit4.assertStatic(parsedContent);
    }


}
