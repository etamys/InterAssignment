package com.example.internassignment;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NetworkUtils  {
   private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
   private static final String CountryAPI = "https://restcountries.eu/rest/v2/name";

   public static URL buildUrl() {
      Uri builtUri = Uri.parse(CountryAPI).buildUpon()
              .appendPath(MainActivity.userCountry.getText().toString())
              .build();
      URL url = null;
      try {
         url = new URL(builtUri.toString());
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
      Log.v(LOG_TAG,"this is "+url);
      return url;
   }

   public static String makeHttpRequest(URL url) throws IOException {

      String response ="";
      if (url == null) {
         return response;
      }
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
       httpURLConnection.setRequestMethod("GET");
      InputStream inputStream = httpURLConnection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line= "";
      while (line!=null){
          line = bufferedReader.readLine();
          response = response+line;
      }

//      Log.v(LOG_TAG,response);
       return  response;
   }





}
