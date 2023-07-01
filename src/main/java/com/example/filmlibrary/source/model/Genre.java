package com.example.filmlibrary.source.model;

public enum Genre {
    FANTASY("Фантастика"), // 0
    DRAMA("Драма"), // 1
    NOVEL("Роман"), // 2
    MELODRAMA("Мелодрама"), // 3
    ADVENTURES("Приключения"), // 4
    COMEDY("Комедия"), // 5
    SERIAL("Сериал"); // 6


    private final String genreTextDisplay;

    Genre(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
