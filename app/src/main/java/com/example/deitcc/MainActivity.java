package com.example.deitcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deitcc.data.MyDBHandler;
import com.example.deitcc.data.MyDBHandler2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    ProgressBar process;
    ProgressBar process2;
    ProgressBar process3;

    TextView textview, p , f ,c , cal , p1 , f1, c1 , text;
    Button button ;
    ImageView img1 , img2 , img3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageButton);
        button = findViewById(R.id.button5);

        process = findViewById(R.id.progressBar);
        process2 = findViewById(R.id.progressBar2);
        process3 = findViewById(R.id.progressBar3);

        p = findViewById(R.id.textView8);
        f = findViewById(R.id.textView9);
        c = findViewById(R.id.textView10);
        cal = findViewById(R.id.textView2);
        p1 = findViewById(R.id.textView38);
        f1 = findViewById(R.id.textView39);
        c1 = findViewById(R.id.textView40);

        Intent intent2 = new Intent(this, EditGoal.class);
        Intent intent3 = new Intent(this, Meal.class);

        textview = findViewById(R.id.textView8);
        text = findViewById(R.id.textView14);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent3);
            }
        });


        MyDBHandler2 dbHandler = new MyDBHandler2(this);

        String columnName = "carbohydrates";
        String columnName2 = "protein";
        String columnName3 = "fat";
        String columnName8 = "calories";
        Double sum_C = dbHandler.getSumOfColumn(columnName);
        Double sum_P = dbHandler.getSumOfColumn(columnName2);
        Double sum_F = dbHandler.getSumOfColumn(columnName3);
        Double sum_Cal = dbHandler.getSumOfColumn(columnName8);
        c.setText(Double.toString(sum_C));
        p.setText(Double.toString(sum_P));
        f.setText(Double.toString(sum_F));

        MyDBHandler db = new MyDBHandler(this);

        String columnName4 = "carbohydrates";
        String columnName5 = "protein";
        String columnName6 = "fat";
        String columnName7 = "calories";
        Double sum_C1 = db.getSumOfColumn(columnName4);
        Double sum_P1 = db.getSumOfColumn(columnName5);
        Double sum_F1 = db.getSumOfColumn(columnName6);
        Double sum_Cal1 = db.getSumOfColumn(columnName7);
        cal.setText(Double.toString(sum_Cal1-sum_Cal));
        c1.setText(Double.toString(sum_C1));
        p1.setText(Double.toString(sum_P1));
        f1.setText(Double.toString(sum_F1));

        int prog1 =
                (int) ((Double.parseDouble(p.getText().toString()) / Double.parseDouble(p1.getText().toString()))*100) ;
        int prog2 =
                (int) ((Double.parseDouble(f.getText().toString()) / Double.parseDouble(f1.getText().toString()))*100);
        int prog3 =
                (int) ((Double.parseDouble(c.getText().toString()) / Double.parseDouble(c1.getText().toString()))*100);
        process.setProgress(prog1);
        process2.setProgress(prog2);
        process3.setProgress(prog3);

        Log.d("prg",prog1+" " + prog2+" "+prog3);
        Log.d("Sum", "Sum of " + columnName + ": " + sum_Cal1);
        Log.d("Sum", "Sum of " + columnName + ": " + sum_P);
        Log.d("Sum", "Sum of " + columnName + ": " + sum_F);

        dbHandler.close();

        img1 = findViewById(R.id.imageView);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlToOpen = "https://www.edamam.com/results/recipes/?search=salad";


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlToOpen));

                startActivity(intent);
            }
        });

        img2 = findViewById(R.id.imageView4);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlToOpen = "https://www.linkedin.com/in/kapil-jhariya/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToOpen));

                startActivity(intent);
            }
        });

        img3 = findViewById(R.id.imageView3);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap screenshot = captureScreenshot(findViewById(android.R.id.content));
                shareScreenshot(screenshot);
            }
        });


        if(Double.parseDouble(cal.getText().toString())<0){
            text.setText("YOUR GOAL IS COMPLETED");;
        }

    }
    public Bitmap captureScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return screenshot;
    }

    public void shareScreenshot(Bitmap screenshot) {
        String screenshotPath = MediaStore.Images.Media.insertImage(
                getContentResolver(), screenshot, "screenshot", null);
        Uri screenshotUri = Uri.parse(screenshotPath);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my progress");

        startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
    }


}