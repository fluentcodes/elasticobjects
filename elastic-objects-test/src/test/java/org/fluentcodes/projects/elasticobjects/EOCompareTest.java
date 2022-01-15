package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * New compare method tests
 * Created by Werner on 6.6.2018.
 */
public class EOCompareTest {

    @Test
    public void EO_first_test_EO_first_test__compare__equals()  {
        EoRoot eo1 = ObjectProvider.createEo();
        eo1.set(EoTestStatic.S_STRING, "first");

        EoRoot eo2 = ObjectProvider.createEo();
        eo2.set(EoTestStatic.S_STRING,"first");

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value__compare__equals()  {
        final Map map = new HashMap();
        map.put(AnObject.F_MY_STRING, "value");
        final EoRoot eo1 = ObjectProvider.createEo(map);

        final EoRoot eo2 = ObjectProvider.createEo(new AnObject().setMyString("value"));

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value2__compare__notquals()  {
        final Map map = new HashMap();
        map.put(AnObject.F_MY_STRING, "value");
        final EoRoot eoMap = ObjectProvider.createEo(map);

        final EoRoot eoAnObject = ObjectProvider.createEo(new AnObject().setMyString("value2"));

        String diff = eoMap.compare(eoAnObject);
        Assertions.assertThat(diff).isNotEmpty();
    }

    @Test
    public void Map_key0_test_Map_key1_test__compare__notEquals()  {
        EoRoot eo1 = ObjectProviderDev.createEo();
        eo1.set(EoTestStatic.S_STRING, EoTestStatic.S_KEY0);

        EoRoot eo2 = ObjectProviderDev.createEo();
        eo2.set(EoTestStatic.S_STRING, EoTestStatic.S_KEY1);

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isNotEmpty()
                .contains("null <> ")
                .contains("<> null");
    }

}
