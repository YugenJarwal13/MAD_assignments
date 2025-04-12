package com.example.a1_unitconverter_darkmode;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText inputValue;
    private Spinner unitSpinnerFrom;
    private Spinner unitSpinnerTo;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply selected theme before setting content view
        if (ThemeHelper.isDarkMode(this)) {
            setTheme(R.style.Theme_UnitConverter_Dark);
        } else {
            setTheme(R.style.Theme_UnitConverter_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputValue = findViewById(R.id.inputValue);
        unitSpinnerFrom = findViewById(R.id.unitSpinnerFrom);
        unitSpinnerTo = findViewById(R.id.unitSpinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Setup spinners with custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        unitSpinnerFrom.setAdapter(adapter);
        unitSpinnerTo.setAdapter(adapter);

        // Set default selections
        unitSpinnerFrom.setSelection(0);
        unitSpinnerTo.setSelection(1);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        try {
            double input = Double.parseDouble(inputValue.getText().toString());
            String fromUnit = unitSpinnerFrom.getSelectedItem().toString();
            String toUnit = unitSpinnerTo.getSelectedItem().toString();
            
            double result = 0;
            
            // Convert to meters first (base unit)
            double inMeters = 0;
            switch (fromUnit) {
                case "Meters":
                    inMeters = input;
                    break;
                case "Kilometers":
                    inMeters = input * 1000;
                    break;
                case "Centimeters":
                    inMeters = input / 100;
                    break;
                case "Millimeters":
                    inMeters = input / 1000;
                    break;
                case "Inches":
                    inMeters = input * 0.0254;
                    break;
                case "Feet":
                    inMeters = input * 0.3048;
                    break;
                case "Yards":
                    inMeters = input * 0.9144;
                    break;
                case "Miles":
                    inMeters = input * 1609.34;
                    break;
            }
            
            // Convert from meters to target unit
            switch (toUnit) {
                case "Meters":
                    result = inMeters;
                    break;
                case "Kilometers":
                    result = inMeters / 1000;
                    break;
                case "Centimeters":
                    result = inMeters * 100;
                    break;
                case "Millimeters":
                    result = inMeters * 1000;
                    break;
                case "Inches":
                    result = inMeters / 0.0254;
                    break;
                case "Feet":
                    result = inMeters / 0.3048;
                    break;
                case "Yards":
                    result = inMeters / 0.9144;
                    break;
                case "Miles":
                    result = inMeters / 1609.34;
                    break;
            }
            
            resultText.setText(String.format("Result: %.2f %s", result, toUnit));
        } catch (NumberFormatException e) {
            resultText.setText("Please enter a valid number");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
