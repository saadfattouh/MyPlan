package com.example.myplan.model;

import java.util.ArrayList;

public class Question {

    private int id;
    private String question;
    private String lectureName;
    private int numOfAnswers;
    private ArrayList<String> answers;
    private int selectedAnswer;
    private int writeAnswer;

    public Question(int id, String question, String lectureName, int numOfAnswers, ArrayList<String> answers, int selectedAnswer, int writeAnswer) {
        this.id = id;
        this.question = question;
        this.lectureName = lectureName;
        this.numOfAnswers = numOfAnswers;
        this.answers = answers;
        this.selectedAnswer = selectedAnswer;
        this.writeAnswer = writeAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public int getNumOfAnswers() {
        return numOfAnswers;
    }

    public void setNumOfAnswers(int numOfAnswers) {
        this.numOfAnswers = numOfAnswers;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getWriteAnswer() {
        return writeAnswer;
    }

    public void setWriteAnswer(int writeAnswer) {
        this.writeAnswer = writeAnswer;
    }
}
