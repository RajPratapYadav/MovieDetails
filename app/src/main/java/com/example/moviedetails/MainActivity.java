package com.example.moviedetails;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
RecyclerView movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList=findViewById(R.id.movieList);
        getDetails();
    }

    public void getDetails(){
ArrayList<Modal> arrayList=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=undefined";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject movieDetails = new JSONObject(response);
                    JSONArray details = movieDetails.getJSONArray("results");
                    for (int i = 0; i < details.length(); i++) {
                        JSONObject result = details.getJSONObject(i);
                        long id = result.getLong("id");
                        String title = result.getString("title"),
                                path = result.getString("poster_path"),
                                vote = result.getString("vote_average"),
                                releaseDate = result.getString("release_date") + "";
                        arrayList.add(new Modal(id, title, path, vote, releaseDate));
                    }

                    movieList.setLayoutManager(new GridLayoutManager(MainActivity.this,2, RecyclerView.VERTICAL,false));
                    movieList.setAdapter(new CustomAdapter(MainActivity.this,arrayList));
                    Log.e("ss",arrayList.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error + "");
            }
        });



        requestQueue.add(stringRequest);

    }
}