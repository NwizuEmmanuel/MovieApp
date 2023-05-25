package com.example.moviephlix.model;

import java.util.Objects;

public final class TableModel {
    int id;
    String title;
    String director;
    int year;
    int runtime;
    String filePath;

    public TableModel(int id, String title, String director, int year, int runtime, String filePath) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.runtime = runtime;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getFilePath() {
        return filePath;
    }
}
