package com.example.deitcc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.deitcc.data.MyDBHandler;
import com.example.deitcc.model.Goal;

import java.util.List;

public class EditGoal extends AppCompatActivity {

    Button button , done;
    TextView cal;
    TextView cab;
    TextView p;
    TextView f;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        button = findViewById(R.id.imageButton4);
        done = findViewById(R.id.button3);

        Intent intent = new Intent(this, Plan.class);
        Intent intent2 = new Intent(this, MainActivity.class);

        MyDBHandler dbHandler = new MyDBHandler(EditGoal.this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "goal_table");

        Double c = 0.0;
        Double cb = 0.0;
        Double pt = 0.0;
        Double ft = 0.0;
        if (count > 0) {
            List<Goal> allContacts = dbHandler.getAllContacts();
            Goal goal = allContacts.get(allContacts.size() - 1);
            c = Double.parseDouble(goal.getCalories());
            cb = Double.parseDouble(goal.getCarbohydrates());
            pt = Double.parseDouble(goal.getProtein());
            ft = Double.parseDouble(goal.getFat());

        }
        db.close();


        Intent intent1 = getIntent();
        Double calories = intent1.getDoubleExtra("calories", c);
        Double cabs_p = intent1.getDoubleExtra("carbohydrates", cb);
        Double prtn_p = intent1.getDoubleExtra("protein", pt);
        Double fat_p = intent1.getDoubleExtra("fat", ft);

        Double cabs_g = ((calories * cabs_p) / 100) / 4;
        Double prtn_g = ((calories * prtn_p) / 100) / 4;
        Double fat_g = ((calories * fat_p) / 100) / 9;

        cal = findViewById(R.id.textView30);
        cab = findViewById(R.id.textView31);
        p = findViewById(R.id.textView32);
        f = findViewById(R.id.textView33);

        cal.setText(Double.toString(calories));
        cab.setText(Double.toString(cabs_g) + "g");
        p.setText(Double.toString(prtn_g) + "g");
        f.setText(Double.toString(fat_g) + "g");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
    }
}