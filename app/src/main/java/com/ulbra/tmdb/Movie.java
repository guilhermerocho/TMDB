package com.ulbra.tmdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Movie implements Serializable {

    int id;
    String title;
    String overview;
    String thumb;
    double popularity;

    public Movie(){

    }

    public Movie(int id, String title, String overview, String thumb, double popularity) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.thumb = thumb;
        this.popularity = popularity;
    }

    @NonNull
    @Override
    public String toString() {
        return this.id + " " + this.title;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Movie && ((Movie)obj).id == this.id;
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id;}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setThumb(double pop) {
        this.popularity = pop;
    }
}