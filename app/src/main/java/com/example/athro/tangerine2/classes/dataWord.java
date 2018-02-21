package com.example.athro.tangerine2.classes;

/**
 * Created by athro on 10/22/2016.
 */

public class dataWord {
    int id;
    String word;
    int rank;

    public dataWord(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public dataWord(int id, String word, int rank) {
        this.id = id;
        this.word = word;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
