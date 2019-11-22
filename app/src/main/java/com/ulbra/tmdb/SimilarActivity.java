package com.ulbra.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class SimilarActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Movie> similars;
    MovieAdapter adapter;
    MovieService service;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);
        listView = findViewById(R.id.list);
        similars = new ArrayList<>();
        service = new MovieService(getApplicationContext());
        adapter = new MovieAdapter(similars,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie)((MovieAdapter)adapterView.getAdapter()).getItem(i);
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
            }
        });
    }


    public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {

        public ArrayList<Movie> list = new ArrayList<Movie>();

        @Override
        protected Integer doInBackground(String... strings) {

            MovieService service = new MovieService(getApplicationContext());
            list = service.getSimilars(movie);


            Log.d("SimilarsActivity()","API Similars received");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            similars = list;
            if(similars == null || similars.size() == 0) {
                Toast.makeText(getApplicationContext(),"No internet connection.",Toast.LENGTH_LONG).show();
            } else {
                adapter.setItens(similars);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        movie = (Movie)getIntent().getExtras().getSerializable("movie");
        setTitle(getResources().getString(R.string.similars) + ": " + movie.getTitle());
        MyAsyncTask task = new MyAsyncTask();
        task.execute("");
    }
}