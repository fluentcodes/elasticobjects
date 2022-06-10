package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import java.util.HashMap;
import java.util.Map;

public class SystemValueHandler {
    private static final String HOME = "HOME";
    private static final String HOMEPATH = "HOMEPATH";
    private static final String TMP = "TMP";
    private static final String TMPDIR = "TMPDIR";
    private static final Map<String, String> SYSTEM = populateSystem();

    private SystemValueHandler() {
    }

    static String call(String key, String defaultValue) {
        if (SYSTEM.containsKey(key)) {
            return getSystemValue(key);
        }
        return defaultValue == null || defaultValue.isEmpty() ?
                "Could not find system variable with key '" + key + "'" :
                defaultValue;
    }

    private static final Map<String, String> populateSystem() {
        Map<String, String> result = new HashMap<>();
        if (System.getenv(TMPDIR) != null) {
            result.put(TMP, System.getenv(TMPDIR));
        } else if (System.getenv(TMP) != null) {
            result.put("TMP", System.getenv(TMP));
        }
        if (System.getenv(HOMEPATH) != null) {
            result.put(HOME, System.getenv(HOMEPATH));
        }
        return result;
    }

    public static final String getSystemValue(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        if (SYSTEM.get(key) != null) {
            return SYSTEM.get(key);
        }
        if (key.equals("TMP")) {
            return SYSTEM.get("TEMP");
        }
        if (key.equals("TEMP")) {
            return SYSTEM.get("TMP");
        }
        return null;
    }

}
