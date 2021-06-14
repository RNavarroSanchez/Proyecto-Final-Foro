package com.rnavarro.forofinal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rnavarro.forofinal.settings.SettingsPreferences;

public class  SettingsActivity extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, new SettingsPreferences())
                .commit();
    }
}
