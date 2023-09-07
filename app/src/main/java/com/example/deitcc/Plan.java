package com.example.deitcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deitcc.data.MyDBHandler;
import com.example.deitcc.model.Goal;

import java.util.List;

public class Plan extends AppCompatActivity {

    Button button;
    EditText cal;
    EditText cabs;
    EditText prtn;
    EditText f;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        button = findViewById(R.id.button);

        cal = findViewById(R.id.editTextNumber);
        cabs = findViewById(R.id.editTextNumber2);
        prtn = findViewById(R.id.editTextNumber3);
        f = findViewById(R.id.editTextNumber4);

        textView2 = findViewById(R.id.textView23);

        Intent intent = new Intent(this , EditGoal.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double calories = Double.parseDouble(cal.getText().toString());
                Double carbohydrates = Double.parseDouble(cabs.getText().toString());
                Double protein = Double.parseDouble(prtn.getText().toString());
                Double fat = Double.parseDouble(f.getText().toString());
                if((carbohydrates+protein+fat) == 100) {
                    MyDBHandler db = new MyDBHandler(Plan.this);
                    intent.putExtra("calories", calories);
                    intent.putExtra("carbohydrates", carbohydrates);
                    intent.putExtra("protein", protein);
                    intent.putExtra("fat", fat);

                    Goal goal = new Goal();
                    goal.setCalories(Double.toString(calories));
                    goal.setCarbohydrates(Double.toString(((calories * carbohydrates) / 100) / 4));
                    goal.setProtein(Double.toString(((calories * protein) / 100) / 4));
                    goal.setFat(Double.toString(((calories * fat) / 100) / 9));
                    db.addGOAL(goal);

                    List<Goal> allContacts = db.getAllContacts();
                    for(Goal goal1: allContacts){
                        Log.d("dbkapil","Calories: "+goal1.getCalories()+"\n"
                                +"Carbohydrates: "+goal1.getCarbohydrates()+"\n"
                                +"Protein: "+goal1.getProtein()+"\n"
                                +"Fat: "+goal1.getFat()+"\n");
                    }

                    startActivity(intent);
                }
                else
                    textView2.setTextSize(16);
            }
        });
    }
}