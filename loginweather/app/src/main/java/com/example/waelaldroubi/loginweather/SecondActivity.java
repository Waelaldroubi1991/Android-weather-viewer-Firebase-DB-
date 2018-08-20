package com.example.waelaldroubi.loginweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    TextView t1_temp, t2_city, t3_description, t4_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        t1_temp = (TextView)findViewById(R.id.textView);
        t2_city = (TextView)findViewById(R.id.textView3);
        t3_description = (TextView)findViewById(R.id.textView4);
        t4_date = (TextView)findViewById(R.id.textView2);

        find_weather();

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });

    }

    public void find_weather(){

        String url = "http://samples.openweathermap.org/data/2.5/weather?id=2172797&appid=37300524a80c8d0d5d07309ea45accfd";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    //to import live date and time
                    //calendar calendar = calendar.getInstance();
                   //SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-DD");
                    //String formatted_date = sdf.format(calendar.getTime());

                    //t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi =  (temp_int - 32 ) /1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jor);

    }

}


