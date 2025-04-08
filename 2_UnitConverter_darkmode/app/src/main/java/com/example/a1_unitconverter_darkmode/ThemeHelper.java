package com.example.a1_unitconverter_darkmode;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeHelper {
    public static boolean isDarkMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("dark_mode", false);
    }
}