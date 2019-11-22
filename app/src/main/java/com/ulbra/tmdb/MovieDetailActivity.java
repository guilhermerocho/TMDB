package com.ulbra.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;
    Button btnFavorites;
    Button btnSimilars;
    ImageView image;
    MovieService movieService;
    TextView title;
    TextView code;
    TextView pop;
    TextView desc;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        title = findViewById(R.id.title);
        code = findViewById(R.id.code);
        pop = findViewById(R.id.popularity);
        desc = findViewById(R.id.description);
        image = findViewById(R.id.image);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnSimilars = findViewById(R.id.btnSimilars);
        movieService = new MovieService(getApplicationContext());

        movie = (Movie)getIntent().getExtras().getSerializable("movie");
        if(movie == null) {
            movie = new Movie();
            Log.d("MovieDetailActivity()","null object");
        } else {
            Log.d("MovieDetailActivity()","object: " + movie);
        }

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite) {
                    movieService.deleteFavorite(movie);
                    btnFavorites.setText(R.string.del_favorites);
                } else {
                    movieService.addFavorite(movie);
                    btnFavorites.setText(R.string.add_favorites);
                }
            }
        });

        btnSimilars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SimilarActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitle(getResources().getString(R.string.movie) + ": " + movie.getTitle());

        String BASE_IMG="https://image.tmdb.org/t/p/w500//";
        title.setText(movie.getTitle());
        code.setText(String.valueOf(movie.getId()));
        pop.setText(getResources().getText(R.string.popularity)  + ": " + movie.getPopularity());
        desc.setText(movie.getOverview());
        Picasso.get().load(BASE_IMG + movie.getThumb()).into(image);
        isFavorite = movieService.isFavorite(movie);

        if(isFavorite) {
            btnFavorites.setText(R.string.del_favorites);
        } else {
            btnFavorites.setText(R.string.add_favorites);
        }
    }
}
