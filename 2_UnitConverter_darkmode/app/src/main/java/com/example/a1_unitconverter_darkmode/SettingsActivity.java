package com.example.a1_unitconverter_darkmode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply selected theme before setting content view
        if (ThemeHelper.isDarkMode(this)) {
            setTheme(R.style.Theme_UnitConverter_Dark);
        } else {
            setTheme(R.style.Theme_UnitConverter_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeSwitch = findViewById(R.id.themeSwitch);
        themeSwitch.setChecked(ThemeHelper.isDarkMode(this));

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences prefs = getSharedPreferences("theme_prefs", MODE_PRIVATE);
                prefs.edit().putBoolean("dark_mode", isChecked).apply();
                
                // Restart both activities to apply theme change
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}
