package com.rnavarro.forofinal.settings;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;

import com.rnavarro.forofinal.R;

public class SettingsPreferences extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.layout_settings,rootKey);
    }

}
