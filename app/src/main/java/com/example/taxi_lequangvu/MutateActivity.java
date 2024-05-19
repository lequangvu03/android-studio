package com.example.taxi_lequangvu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MutateActivity extends AppCompatActivity {
    private EditText plateNumberInput, distanceInput, priceInput, discountPercentInput;
    private Button buttonSubmit, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate);
        plateNumberInput = findViewById(R.id.plate_number_input);
        distanceInput = findViewById(R.id.distance_input);
        priceInput = findViewById(R.id.price_input);
        discountPercentInput = findViewById(R.id.discount_input);
        buttonSubmit = findViewById(R.id.button_submit);
        buttonBack = findViewById(R.id.button_cancel);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null){
            String plateNumber = b.getString("plateNumber");
            Double distance = b.getDouble("distance");
            int price = b.getInt("price");
            int discount = b.getInt("discount");

            plateNumberInput.setText(plateNumber);
            distanceInput.setText(distance.toString());
            priceInput.setText(price+"");
            discountPercentInput.setText(discount+"");
        }


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("plateNumber", plateNumberInput.getText().toString());
                b.putDouble("distance", Double.parseDouble(String.valueOf(distanceInput.getText())));
                b.putInt("price", Integer.parseInt(String.valueOf(priceInput.getText())));
                b.putInt("discount", Integer.parseInt(String.valueOf(discountPercentInput.getText())));
                intent.putExtras(b);
                setResult(200, intent);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MutateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}