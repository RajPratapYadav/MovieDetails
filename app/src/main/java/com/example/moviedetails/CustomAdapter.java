package com.example.moviedetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Viewclass> {
    ArrayList<Modal> arrayList;
    Context context;


    public CustomAdapter(Context mainActivity, ArrayList<Modal> ar) {
        this.arrayList=ar;
        this.context=mainActivity;
    }


    @Override
    public @NotNull Viewclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v= LayoutInflater.from(context).inflate(R.layout.adapter,null);

        return new Viewclass(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.Viewclass holder, int position) {
holder.name.setText(arrayList.get(position).title);
holder.rating.setText(arrayList.get(position).vote);
holder.date.setText(arrayList.get(position).releaseDate);
Picasso.with(context).load("https://image.tmdb.org/t/p/w780"+arrayList.get(position).path).into(holder.poster);
holder.poster.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(context,DetailPage.class);
        i.putExtra("id",arrayList.get(position).id);
        context.startActivity(i);
    }
});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Viewclass extends RecyclerView.ViewHolder {
                    ImageView poster;
            TextView name,date,rating;
            public Viewclass(@NonNull View itemView) {
                super(itemView);
                poster=itemView.findViewById(R.id.poster);
                name=itemView.findViewById(R.id.name);
                date=itemView.findViewById(R.id.date);
                rating=itemView.findViewById(R.id.rating);

    }
}
}
