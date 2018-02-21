package com.example.athro.tangerine2.classes;

/**
 * Created by athro on 10/29/2016.
 */

public class textdata {
    String question;
    int id;
    int answer;
    String answert;
    int nwords;

    public textdata(String answert, int id, String question) {
        this.answert = answert;
        this.id = id;

        this.question = question;
    }
    public textdata(String answert, int id, String question, int nwords) {
        this.answert = answert;
        this.id = id;
        this.nwords = nwords;
        this.question = question;
    }

    public int getNwords() {
        return nwords;
    }

    public void setNwords(int nwords) {
        this.nwords = nwords;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getAnswert() {
        return answert;
    }

    public void setAnswert(String answert) {
        this.answert = answert;
    }
}
