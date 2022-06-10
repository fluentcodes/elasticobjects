package org.fluentcodes.projects.elasticobjects.testitems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectProviderArray {
  public static final List<List<String>> ARRAY_SIMPLE = createArraySimple();

  private static List<List<String>> createArraySimple () {
    List<List<String>> array = new ArrayList<>();
    array.add(Arrays.asList("val11", "val12"));
    array.add(Arrays.asList("val21", "val22"));
    return array;
  }
}
