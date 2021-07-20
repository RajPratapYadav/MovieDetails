package com.example.moviedetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailPage extends AppCompatActivity {
TextView name1;
TextView summery;
ImageView pic,cImageView,cImageView1;
String generNames="",language="",productions="",pCountries="";
TextView genersTextView,cNameTextView,pCountryView,pNameView,rates,
        languageTextView,popularityTextView,budgetTextView,ratings,revenueTextView,
        runtimeTextView,statusTextView,taglineTextView,releaseDate,homePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        //Finding Id
        cImageView1=findViewById(R.id.cImageView1);
        popularityTextView=findViewById(R.id.popularity);
        budgetTextView=findViewById(R.id.budgetTextView);
        homePage=findViewById(R.id.homePage);
        releaseDate=findViewById(R.id.releaseDate);
        name1=findViewById(R.id.name1);
        summery=findViewById(R.id.summery);
        cNameTextView=findViewById(R.id.cNameTextView);
        pCountryView=findViewById(R.id.pCountryView);
        cImageView=findViewById(R.id.cImageView);
        pNameView=findViewById(R.id.pNameView);
        pic=findViewById(R.id.pic);
        languageTextView=findViewById(R.id.languageTextView);
        genersTextView=findViewById(R.id.geners);
        rates=findViewById(R.id.rates);
        ratings=findViewById(R.id.ratings);
        statusTextView=findViewById(R.id.status);
        taglineTextView=findViewById(R.id.tagLine);
        revenueTextView=findViewById(R.id.revanue);
        runtimeTextView=findViewById(R.id.runTime);


        //Getting Id of Movie Through Intent
        long path=getIntent().getLongExtra("id",0);



        //Making Url Using Id
        String url="https://api.themoviedb.org/3/movie/"+path+"?api_key=55957fcf3ba81b137f8fc01ac5a31fb5";



        //Json Parsing
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    //Getting Data Via JSON
                    JSONObject movie=new JSONObject(response);
                    double budget=movie.getLong("budget"),
                     revenue=movie.getLong("revenue");
                    long        runtime=movie.getInt("runtime"),
                            vote_count=movie.getLong("vote_count");
                    double vote_average=movie.getDouble("vote_average"),
                            popularity=movie.getDouble("popularity");
                    String homepage=movie.getString("homepage"),
                            original_title=movie.getString("title"),
                            overview=movie.getString("overview"),
                            release_date=movie.getString("release_date"),
                            status=movie.getString("status"),
                            tagline=movie.getString("tagline"),
                    imgPath=movie.getString("backdrop_path");
                    JSONArray companies=movie.getJSONArray("production_companies");

                    for(int i=0;i<companies.length();i++){
                        JSONObject names=companies.getJSONObject(i);
                        String name=names.getString("name");
                        productions=productions+"\n"+(i+1)+"."+name;
                    }
                    String cName = "NA", cPath = movie.getString("poster_path"), cpath1 = companies.getJSONObject(0).getString("logo_path");

                    try {
                        JSONObject collection = movie.getJSONObject("belongs_to_collection");
                        if (collection != null) {
                            cName = collection.getString("name");
                            cPath = collection.getString("poster_path");
                            cpath1 = collection.getString("backdrop_path");
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


                    JSONArray geners=movie.getJSONArray("genres");
                    for(int i=0;i<geners.length();i++){
                        JSONObject names=geners.getJSONObject(i);
                        String name=names.getString("name");
                       generNames=generNames+" "+name;
                    }

                    JSONArray languages=movie.getJSONArray("spoken_languages");
                    for(int i=0;i<languages.length();i++){
                        JSONObject names=languages.getJSONObject(i);
                        String name=names.getString("english_name");
                        language=language+" "+name;
                    }

                    JSONArray countries=movie.getJSONArray("production_countries");
                    for(int i=0;i<countries.length();i++){
                        JSONObject names=countries.getJSONObject(i);
                        String name=names.getString("name");
                        pCountries=pCountries+"\n"+(i+1)+"."+name;
                    }


                    //Setting Data to Views

                    genersTextView.setText("Geners : "+generNames);
                    ratings.setText("Ratings : "+vote_average+"");
                    rates.setText("Votes : "+vote_count+"");
                    name1.setText(original_title);
                    summery.setText(overview);
                    popularityTextView.setText("Popularity : "+popularity);
                    budgetTextView.setText("Budget : "+((float)budget/1000000)+" Million");
                    statusTextView.setText("Status : "+status);
                    revenueTextView.setText("Revenue : "+ ((float) (revenue / 1000000))+" Million");
                    taglineTextView.setText("TagLine : "+tagline);
                    runtimeTextView.setText("Run Time : "+runtime+" Minutes");
                    releaseDate.setText(""+release_date);
                    homePage.setText("Go To Official Site");
                    homePage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homePage.setBackgroundColor(Color.BLUE);
                            Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                            startActivity(i);
                        }
                    });
                    Picasso.with(DetailPage.this).load("https://image.tmdb.org/t/p/w780"+imgPath+"").into(pic);
                    Picasso.with(DetailPage.this).load("https://image.tmdb.org/t/p/w780"+cPath+"").into(cImageView);
                    Picasso.with(DetailPage.this).load("https://image.tmdb.org/t/p/w780"+cpath1+"").into(cImageView1);
                    languageTextView.setText("Languages : "+language);
                    cNameTextView.setText("Collection Name : "+cName);
                    pNameView.setText("Production Names : "+productions);
                    pCountryView.setText("Production Countries : "+pCountries);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}