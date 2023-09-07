package com.example.deitcc;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class VolleyNutritionRequest {

    private static VolleyNutritionRequest instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyNutritionRequest(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyNutritionRequest getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyNutritionRequest(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void fetchNutritionData(String apiUrl, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }
        );

        getRequestQueue().add(jsonObjectRequest);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject response);
        void onError(String errorMessage);
    }
}
