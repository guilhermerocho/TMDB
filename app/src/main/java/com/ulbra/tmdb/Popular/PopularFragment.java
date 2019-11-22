package com.ulbra.tmdb.Popular;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ulbra.tmdb.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.ulbra.tmdb.MovieAdapter;
import com.ulbra.tmdb.MovieDetailActivity;
import com.ulbra.tmdb.MovieService;
import com.ulbra.tmdb.R;
import java.util.ArrayList;

public class PopularFragment extends Fragment {
    private PopularViewModel popularViewModel;
    ArrayList<Movie> popular = new ArrayList<Movie>();

    ListView listView;
    MovieAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        popularViewModel =
                ViewModelProviders.of(this).get(PopularViewModel.class);
        View root = inflater.inflate(R.layout.popular_fragment, container, false);

        listView = root.findViewById(R.id.list);
        adapter = new MovieAdapter(popular,getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie)((MovieAdapter)adapterView.getAdapter()).getItem(i);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
            }
        });
        return root;
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
        public ArrayList<Movie> list = new ArrayList<Movie>();
        @Override
        protected Integer doInBackground(String... strings) {

            MovieService service = new MovieService(getActivity().getApplicationContext());
            list = service.getPopular();
            Log.d("MainActivity","API Popular received");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            popular = list;
            if(popular == null || popular.size() == 0) {
                Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_LONG).show();
                adapter.setItens(new ArrayList<Movie>());
            } else {
                adapter.setItens(popular);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        MyAsyncTask task = new MyAsyncTask();
        task.execute("");
    }
}
