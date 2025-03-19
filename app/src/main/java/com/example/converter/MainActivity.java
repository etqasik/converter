package com.example.converter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText amountInput;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private Button convertButton;
    private TextView resultTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amountInput);
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Настройка Spinner для выбора валют
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        // Обработка нажатия на кнопку конвертации
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void convertCurrency() {
        // Получаем введенную сумму
        String amountString = amountInput.getText().toString();
        if (amountString.isEmpty()) {
            resultTextView.setText("Введите сумму");
            return;
        }

        double amount = Double.parseDouble(amountString);

        // Получаем выбранные валюты
        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();

        // Курсы валют (примерные значения)
        double rubToUsd = 0.013;
        double rubToEur = 0.011;
        double usdToRub = 76.0;
        double usdToEur = 0.85;
        double eurToRub = 89.0;
        double eurToUsd = 1.18;

        // Конвертация
        double result = 0;
        if (fromCurrency.equals("Рубли") && toCurrency.equals("Доллары")) {
            result = amount * rubToUsd;
        } else if (fromCurrency.equals("Рубли") && toCurrency.equals("Евро")) {
            result = amount * rubToEur;
        } else if (fromCurrency.equals("Доллары") && toCurrency.equals("Рубли")) {
            result = amount * usdToRub;
        } else if (fromCurrency.equals("Доллары") && toCurrency.equals("Евро")) {
            result = amount * usdToEur;
        } else if (fromCurrency.equals("Евро") && toCurrency.equals("Рубли")) {
            result = amount * eurToRub;
        } else if (fromCurrency.equals("Евро") && toCurrency.equals("Доллары")) {
            result = amount * eurToUsd;
        } else if (fromCurrency.equals(toCurrency)) {
            result = amount;
        }

        // Вывод результата
        resultTextView.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency));
    }

}