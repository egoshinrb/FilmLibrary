package com.example.filmlibrary.model;

public enum Genre {
    FANTASY("Фантастика"),
    SCIENCE_FICTION("Научная фантастика"),
    DRAMA("Драма"),
    NOVEL("Роман");

    private final String genreTextDisplay;

    Genre(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
