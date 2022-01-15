package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class FileCallTest {

    @Test
    public void givenPublicTxt_whenExecuteCall_thenContentIsRead() {
        EoRoot eo = ObjectProvider.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = (String) new FileReadCall("public.txt").execute(eo);
        Assertions.assertThat(content).isEqualTo("Everyone can see this content!");
    }


    @Test
    public void givenPublicTxtOnXYZ_whenExecuteCall_thenXYZHasContent() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set(new FileReadCall("public.txt"), "xyz");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("xyz")).isEqualTo("Everyone can see this content!");
    }
}
