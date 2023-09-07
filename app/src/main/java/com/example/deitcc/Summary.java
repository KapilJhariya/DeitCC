package com.example.deitcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deitcc.data.MyDBHandler2;
import com.example.deitcc.model.Today;

import java.util.List;

public class Summary extends AppCompatActivity {
    TextView text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        text = findViewById(R.id.textView37);
        Intent intent = getIntent();
        Double g1 = intent.getDoubleExtra("Calories From Fat", 0);
        Double g2 = intent.getDoubleExtra("Calories From Protein", 0);
        Double g3 = intent.getDoubleExtra("Calories From Carbs", 0);
        Double g4 = intent.getDoubleExtra("Total Calories", 0);
        text.setText( "Total Calories : "+g4+"\n\n"+"Calories From Fat : "+ g1 +"\n\n"+"Calories " +
                "From Protein : "+ g2 +"\n\n"+ "Calories From Carbs : "+ g3);

        button = findViewById(R.id.button4);

        MyDBHandler2 db = new MyDBHandler2(Summary.this);
        Intent intent3 = new Intent(this,MainActivity.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Today today = new Today();
                today.setCalories(String.valueOf(g4));
                today.setCarbohydrates(Double.toString((Double)g3/4));
                today.setProtein(Double.toString((Double)g2/4));
                today.setFat(Double.toString((Double)g1/9));
                db.addToday(today);

                List<Today> allContacts = db.getAllContacts();
                for(Today goal1: allContacts){
                    Log.d("dbkapil","Calories: "+goal1.getCalories()+"\n"
                            +"Carbohydrates: "+goal1.getCarbohydrates()+"\n"
                            +"Protein: "+goal1.getProtein()+"\n"
                            +"Fat: "+goal1.getFat()+"\n"
                            +"Date: "+goal1.getDate()+"\n");
                }
                startActivity(intent3);
            }
        });


    }
}