package com.example.lw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView models;
    Spinner spinner;
    carExpert carExpert;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carExpert = new carExpert(this);
    }

    public void onClickChoose(View view){
        models = findViewById(R.id.textView2);
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.imageView);

        String car_model = String.valueOf(spinner.getSelectedItem());
        String brandsStr = carExpert.getModel(car_model);

        carExpert.getModel_image(car_model, imageView);

        models.setText(brandsStr);

    }

}