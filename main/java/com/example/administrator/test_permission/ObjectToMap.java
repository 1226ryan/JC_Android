package com.example.administrator.test_permission;

import java.util.Map;

public class ObjectToMap {
    /**
     * gradle 추가 :
     implementation 'com.fasterxml.jackson.core:jackson-core:2.6.3'
     implementation 'com.fasterxml.jackson.core:jackson-annotations:2.6.3'
     implementation 'com.fasterxml.jackson.core:jackson-databind:2.6.3'
     */

    void os() {
        ObjectMapper oMapper = new ObjectMapper();

        // object -> Map
        Map<String, Object> map = oMapper.convertValue(rows.get(i).get(keyName), Map.class);
        if(SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO)) {
            fundName = String.valueOf(map.get(Defines.KO));
        } else {
            fundName = String.valueOf(map.get(Defines.EN));
        }
    }
}
