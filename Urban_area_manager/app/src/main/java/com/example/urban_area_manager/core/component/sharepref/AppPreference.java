package com.example.urban_area_manager.core.component.sharepref;

import android.content.SharedPreferences;

public interface AppPreference {

    SharedPreferences getSharedPreference();

    void saveString(String key, String value);
    String getString(String key, String defaultValue);

    void saveInt(String key, int value);
    int getInt(String key, int defaultValue);

    void saveBoolean(String key, Boolean value);
    Boolean getBoolean(String key, Boolean defaultValue);

    void saveFloat(String key, Float value);
    Float getFloat(String key, Float defaultValue);
}
