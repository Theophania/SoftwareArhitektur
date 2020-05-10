package sample;

import model.Question;
import model.Quiz;
import repository.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    private FileRepository repository;
    private Quiz quiz;

    public Controller() {
        this.repository = new FileRepository();
        generateQuiz();
    }

    public void generateQuiz() {
        Random rand = new Random();
        List<Question> questions = repository.getQuestions();
        List<Question> quizQuestions = new ArrayList<Question>();
        int counter = 1;
        while (counter <= 26){
            int questionNumber = rand.nextInt(questions.size() - 1);
            Question question = questions.get(questionNumber);
            question.setId(counter);
            quizQuestions.add(question);
            questions.remove(question);
            counter++;
        }
        this.quiz = new Quiz(1, quizQuestions, 0, 0);
    }

    public Question getNextQuestion() {
        return quiz.getNextQuestion();
    }

    public void setCorrectQuestion() {
        quiz.setCorrectAnswers(quiz.getCorrectAnswers() + 1);
    }

    public void setFalseQuestion() {
        quiz.setFalseAnswers(quiz.getFalseAnswers() + 1);
    }

    public int getRightAnswers() {
        return quiz.getCorrectAnswers();
    }

    public int getWrongAnswers() {
        return quiz.getFalseAnswers();
    }
}
