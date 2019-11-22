package com.ulbra.tmdb;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movie> itens;
    private Activity activity;


    public MovieAdapter(ArrayList<Movie> itens, Activity act) {
        this.itens = itens;
        this.activity = act;
    }

    public void setItens(ArrayList<Movie> itens) {
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int i) {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return itens.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String BASE_IMG="https://image.tmdb.org/t/p/w500//";
        View v = activity.getLayoutInflater().inflate(R.layout.movie_detail,viewGroup,false);
        final Movie m = itens.get(i);
        ((TextView)v.findViewById(R.id.title)).setText(m.getTitle());
        ((TextView)v.findViewById(R.id.popularity)).setText(activity.getResources().getText(R.string.popularity) + ": " + m.getPopularity());
        ImageView imageView = v.findViewById(R.id.imageView);
        /*Picasso.get()
                .load(BASE_IMG + m.getThumb())
                .placeholder(R.drawable.image_placeholder)
                . into(imageView);

         */
//        Picasso.get().load("https://image.tmdb.org/t/p/w185//2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg").into(imageView);
        Picasso.get().load(BASE_IMG + m.getThumb()).into(imageView);
        return v;
    }
}
