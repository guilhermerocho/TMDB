package com.ulbra.tmdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieService {
    private String API_KEY = "8cb907e706c0269c6d209755cd3794b5";
    private String API_URL = "https://api.themoviedb.org/3";
    private String METHOD_GET_POPULAR = "/movie/popular";
    private String METHOD_GET_TOPRATED = "/movie/top_rated";
    private String METHOD_GET_DETAIL = "/movie/";
    private String METHOD_GET_SIMILAR = "/movie/";
    private SQLiteDatabase sqldb;
    private DB db;

    public MovieService(Context context) {
        db = new DB(context);
    }

    private String call(String method) throws Exception {
        try {
            String url = API_URL + method + "?api_key=" + API_KEY;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Log.d("MovieService()->call: ",json);
            return json;
        } catch(Exception e) {
            throw e;
        }
    }

    public ArrayList<Movie> getToprated() {
        Log.d("getToprated()","executing ...");
        ArrayList<Movie> lista = new ArrayList<>();
        try {
            String json_string = this.call(this.METHOD_GET_TOPRATED);
            Log.d("getToprated()",json_string);
            JSONObject json = new JSONObject(json_string);
            JSONArray results = json.getJSONArray("results");
            int count = results.length();
            for(int i = 0; i < count; i++) {
                JSONObject obj = results.getJSONObject(i);
                Movie m = new Movie(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("overview"),
                        obj.getString("poster_path"),
                        obj.getDouble("popularity")
                );
                lista.add(m);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Movie> getPopular() {
        Log.d("getPopular()","executing ...");
        ArrayList<Movie> lista = new ArrayList<>();
        try {
            String json_string = this.call(this.METHOD_GET_POPULAR);
            Log.d("getPopular()",json_string);
            JSONObject json = new JSONObject(json_string);
            JSONArray results = json.getJSONArray("results");
            int count = results.length();
            for(int i = 0; i < count; i++) {
                JSONObject obj = results.getJSONObject(i);
                Movie m = new Movie(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("overview"),
                        obj.getString("poster_path"),
                        obj.getDouble("popularity")
                );
                lista.add(m);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Movie> getSimilars(Movie movie) {
        Log.d("getSimilars()","executing ...");
        ArrayList<Movie> lista = new ArrayList<>();
        try {
            String method = this.METHOD_GET_SIMILAR + movie.getId()+ "/similar";
            Log.d("getSimilars()","method: " + method);
            String json_string = this.call(method);
            Log.d("getSimilare()",json_string);
            JSONObject json = new JSONObject(json_string);
            JSONArray results = json.getJSONArray("results");
            int count = results.length();
            for(int i = 0; i < count; i++) {
                JSONObject obj = results.getJSONObject(i);
                Movie m = new Movie(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("overview"),
                        obj.getString("poster_path"),
                        obj.getDouble("popularity")
                );
                lista.add(m);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return lista;
    }




    public boolean isFavorite(Movie movie) {
        sqldb = db.getReadableDatabase();
        Cursor cursor;
        String[] fields =  {DB.ID};
        cursor = sqldb.query(db.TABLE, fields, "code = " + movie.getId(), null, null, null, null, null);

        return cursor.moveToFirst();

    }

    public void addFavorite(Movie movie) {
        sqldb = db.getWritableDatabase();
        ContentValues valores;
        long result;
        valores = new ContentValues();
        valores.put(DB.CODE, movie.id);
        valores.put(DB.TITLE, movie.title);
        valores.put(DB.DESC, movie.overview);
        valores.put(DB.POP, movie.popularity);
        valores.put(DB.POSTER, movie.thumb);
        result = sqldb.insert(DB.TABLE, null, valores);
        sqldb.close();
    }

    public void deleteFavorite(Movie movie) {
        sqldb = db.getWritableDatabase();
        sqldb.delete(DB.TABLE,"code = " + movie.getId(),null);
        sqldb.close();
    }

    public ArrayList<Movie>  getFavorites() {
        ArrayList<Movie> list = new ArrayList<>();
        Cursor cursor;
        String[] fields =  {DB.ID, DB.CODE ,DB.TITLE, DB.DESC, DB.POP, DB.POSTER};
        sqldb = db.getReadableDatabase();
        cursor = sqldb.query(db.TABLE, fields, null, null, null, null, null, null);
        boolean hasItens = cursor.moveToFirst();
        if(cursor!=null && hasItens){
            do {
                list.add(
                    new Movie(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(5),
                        cursor.getDouble(4)
                    )
                );
            }while(cursor.moveToNext());
        }
        sqldb.close();

        return list;
    }

}