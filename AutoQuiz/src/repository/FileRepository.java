package repository;

import model.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRepository {
    private List<Question> questions;

    public FileRepository() {
        this.questions = new ArrayList<Question>();
        File file = new File("C:\\Users\\Malina\\IdeaProjects\\labo5\\src\\repository\\quizQuestions");
        try{
            Scanner sc = new Scanner(file);
            int crtId = 1;
            while (sc.hasNextLine()) {
                String frage = sc.nextLine();
                String answer1 = sc.nextLine();
                String answer2 = sc.nextLine();
                String answer3 = sc.nextLine();
                ArrayList<String> answers = new ArrayList<String>();
                answers.add(answer1);
                answers.add(answer2);
                answers.add(answer3);
                String correctAnswer = sc.nextLine();
                Question question = new Question(crtId, frage, answers, correctAnswer);
                this.questions.add(question);
                crtId++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void delete(Question item){
        for(Question question:questions){
            if(question.getId() == item.getId()){
                questions.remove(question);
                break;
            }
        }
    }
}
