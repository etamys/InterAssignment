package com.example.internassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public static EditText userCountry;
    public static TextView name;
    public static TextView capital;
    public static TextView region;
    public static TextView subRegion;
    public static TextView population;
    public static TextView languages;
    public static TextView borders;
    public String country;
    private Button loadBtn;
    public static ImageView flagView;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userCountry = findViewById(R.id.countryName);
        name = findViewById(R.id.name);
        capital = findViewById(R.id.capital);
        region = findViewById(R.id.region);
        subRegion = findViewById(R.id.subRegion);
        population = findViewById(R.id.population);
        languages = findViewById(R.id.languages);
        borders = findViewById(R.id.borders);
        country = userCountry.getText().toString();
        loadBtn = findViewById(R.id.loadbtn);
        flagView = findViewById(R.id.flag);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new fetchdata().execute();
            }
        });


    }

    public class fetchdata extends AsyncTask<Void, Void, Void> {
        URL url;
        String name = "";
        String capital = "";
        String region = "";
        String subregion = "";
        String flag="";
        String population = "";
        String border = "border";
        String languages = "";
        String jsonResponse = "";

        @Override
        protected Void doInBackground(Void... urls) {
            url = NetworkUtils.buildUrl();
            try {
                jsonResponse = NetworkUtils.makeHttpRequest(url);
                JSONArray countryObject = new JSONArray(jsonResponse);
                for (int i = 0; i < countryObject.length(); i++) {
                    JSONObject countryData = countryObject.getJSONObject(i);
                    String str = countryData.getString("name");
                    if (str.equals(MainActivity.userCountry.getText().toString())) {
                        name = (String) countryData.get("name");
                        capital = (String) countryData.get("capital");
                        region = countryData.getString("region");
                        subregion = countryData.getString("subregion");
                        flag = countryData.getString("flag");
                        population = countryData.getString("population");
                        JSONArray languageArray = countryData.getJSONArray("languages");
                        int k = 0;
                        while (i < languageArray.length()) {
                            JSONObject langObj = languageArray.getJSONObject(k);
                            languages = langObj.getString("name");
                            k++;
                        }
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String str = "Name: " + name;
            MainActivity.name.setText(str);
            MainActivity.capital.setText(capital);
            MainActivity.region.setText(region);
            MainActivity.subRegion.setText(subregion);
            MainActivity.population.setText(population);
            MainActivity.languages.setText(languages);
            Uri uri = Uri.parse(flag).buildUpon().build();
            Log.v(LOG_TAG,"This is "+flag);
        }
    }

}

