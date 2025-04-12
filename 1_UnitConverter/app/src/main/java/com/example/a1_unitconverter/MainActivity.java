package com.example.a1_unitconverter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputValue;
    private Spinner unitSpinnerFrom, unitSpinnerTo;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        unitSpinnerFrom = findViewById(R.id.unitSpinnerFrom);
        unitSpinnerTo = findViewById(R.id.unitSpinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Define unit options
        String[] units = {"Meters", "Kilometers", "Miles", "Yards"};

        // Setup adapter for Spinners with better styling
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, 
            R.layout.spinner_item,
            units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinnerFrom.setAdapter(adapter);
        unitSpinnerTo.setAdapter(adapter);

        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void convertUnits() {
        String fromUnit = unitSpinnerFrom.getSelectedItem().toString();
        String toUnit = unitSpinnerTo.getSelectedItem().toString();
        String input = inputValue.getText().toString();

        if (input.isEmpty()) {
            resultText.setText("Please enter a value");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            double result = performConversion(value, fromUnit, toUnit);
            resultText.setText(String.format("%.2f %s", result, toUnit));
        } catch (NumberFormatException e) {
            resultText.setText("Invalid number format");
        }
    }

    private double performConversion(double value, String fromUnit, String toUnit) {
        double meters;

        // Convert input value to meters
        switch (fromUnit) {
            case "Kilometers":
                meters = value * 1000;
                break;
            case "Miles":
                meters = value * 1609.34;
                break;
            case "Yards":
                meters = value * 0.9144;
                break;
            default: // Meters
                meters = value;
        }

        // Convert from meters to the target unit
        switch (toUnit) {
            case "Kilometers":
                return meters / 1000;
            case "Miles":
                return meters / 1609.34;
            case "Yards":
                return meters / 0.9144;
            default:
                return meters; // Return meters if no conversion is needed
        }
    }
}
