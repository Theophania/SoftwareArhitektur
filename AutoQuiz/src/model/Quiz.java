package model;

import java.util.List;

public class Quiz {
    private int id;
    private List <Question> questions;
    private int correctAnswers;
    private int falseAnswers;
    private int crtIndex;

    public Quiz(int id, List<Question> questions, int correctAnswers, int falseAnswers) {
        this.id = id;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.falseAnswers = falseAnswers;
        this.crtIndex = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getNextQuestion(){
        if(crtIndex >= questions.size())
            return null;
        Question question = this.questions.get(this.crtIndex);
        crtIndex++;
        return question;

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getFalseAnswers() {
        return falseAnswers;
    }

    public void setFalseAnswers(int falseAnswers) {
        this.falseAnswers = falseAnswers;
    }
}
