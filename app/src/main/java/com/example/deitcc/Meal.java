package com.example.deitcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class Meal extends AppCompatActivity {

    EditText text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

       text = findViewById(R.id.editTextText);
       button = findViewById(R.id.button2);

       Intent intent = new Intent(this,Summary.class);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String food = text.getText().toString();
               String appId = "7b52a0b6";
               String appKey = "55365555103f9d6a26282e331189c9a6";
               String foodItem = food;

               String encodedFoodItem = Uri.encode(foodItem);

               String apiUrl = "https://api.edamam.com/api/nutrition-data?app_id=" + appId
                       + "&app_key=" + appKey
                       + "&ingr=" + encodedFoodItem;

               VolleyNutritionRequest.getInstance(Meal.this).fetchNutritionData(apiUrl,
                       new VolleyNutritionRequest.VolleyCallback() {
                   @Override
                   public void onSuccess(JSONObject response) {

                       try {
                           double fatGrams = response.getJSONObject("totalNutrients").getJSONObject("FAT").getDouble("quantity");
                           double proteinGrams = response.getJSONObject("totalNutrients").getJSONObject("PROCNT").getDouble("quantity");
                           double carbsGrams = response.getJSONObject("totalNutrients").getJSONObject("CHOCDF").getDouble("quantity");
                           double ttlCal = response.getJSONObject("totalNutrients").getJSONObject(
                                   "ENERC_KCAL").getDouble("quantity");

                           // Calculate calories from fat, protein, and carbohydrates
                           double caloriesFromFat = fatGrams*9;
                           double caloriesFromProtein = proteinGrams * 4;
                           double caloriesFromCarbs = carbsGrams * 4;

                           Bundle b = new Bundle();
                           Bundle c = new Bundle();
                           Bundle d = new Bundle();
                           Bundle tc = new Bundle();
                           b.putDouble("Calories From Fat", (caloriesFromFat));
                           c.putDouble("Calories From Protein", (caloriesFromProtein));
                           d.putDouble("Calories From Carbs", (caloriesFromCarbs));
                           tc.putDouble("Total Calories", (ttlCal));
                           intent.putExtras(b);
                           intent.putExtras(c);
                           intent.putExtras(d);
                           intent.putExtras(tc);

                           Log.d("Calories From Fat", String.valueOf(caloriesFromFat));
                           Log.d("Calories From Protein", String.valueOf(caloriesFromProtein));
                           Log.d("Calories From Carbs", String.valueOf(caloriesFromCarbs));

                           startActivity(intent);


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onError(String errorMessage) {
                       // Handle API errors here
                       Log.e("API Error", errorMessage);
                   }
               });
           }
       });

    }
}