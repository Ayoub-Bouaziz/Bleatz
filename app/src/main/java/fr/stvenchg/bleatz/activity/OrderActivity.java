package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import fr.stvenchg.bleatz.R;

public class OrderActivity extends AppCompatActivity {

    private int idOrder;
    private TextView orderIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        idOrder = intent.getIntExtra("order_id", 0);

        orderIdTextView = findViewById(R.id.order_id);
        orderIdTextView.setText("Commande " + String.valueOf(idOrder));



    }



}