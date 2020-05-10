package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Question;
import sample.Controller;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class View extends Application implements EventHandler<ActionEvent> {
    private Button correctButton;
    private Controller controller;
    private Question question;
    private Button frage;
    private Button button;
    private Label timeLabel;
    private Integer seconds;
    private boolean started;
    private boolean correct;
    private Button score;
    private Button next;


    public View(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        seconds =1800;

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Fragebogen");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 800, 600);

        //Start Pane
        Label label1 = new Label("Fuhrerscheinfragebogen");
        label1.setMinSize(60,60);
        label1.setStyle("-fx-font-size:20");
        Button start = new Button("START");
        start.setMinSize(60,60);
        start.setStyle("-fx-font-size:40");
        start.setOnAction(e -> {
                primaryStage.setScene(scene);
                doTime();
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,start);
        layout1.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout1, 800,600);
        primaryStage.setScene(scene1);
        primaryStage.show();

        question = controller.getNextQuestion();
        timeLabel = new Label();

        /**
         * Buttons to be introduced
         */
        frage = new Button(question.getId() + ". " + question.getQuestion());
        button = new Button("A. " + question.getAnswers().get(0) +
                "\nB. " + question.getAnswers().get(1) +
                "\nC. " + question.getAnswers().get(2));
        Button button1 = new Button("A");
        Button button2 = new Button("B");
        Button button3 = new Button("C");
        next = new Button("NEXT");
        score = new Button("CORRECT: " + controller.getRightAnswers() + "\nWRONG: " + controller.getWrongAnswers());
        correctButton = new Button();

        /**
         * Buttons size and fonts
         */
        button1.setMinSize(75, 75);
        button2.setMinSize(75, 75);
        button3.setMinSize(75, 75);
        frage.setMinSize(75, 75);
        next.setMinSize(75, 75);
        timeLabel.setMinSize(60,60);
        button1.setStyle("-fx-font-size:40");
        button2.setStyle("-fx-font-size:40");
        button3.setStyle("-fx-font-size:40");
        next.setStyle("-fx-font-size:40");
        button.setStyle("-fx-font-size:20");
        frage.setStyle("-fx-font-size:20");
        timeLabel.setStyle("-fx-font-size:20");

        AtomicReference<String> correctAnswer = new AtomicReference<>(question.getCorrectAnswer());
        if (correctAnswer.get() == question.getAnswers().get(0))
            correctButton = button1;
        else if (correctAnswer.get() == question.getAnswers().get(1))
            correctButton = button2;
        else
            correctButton = button3;
        correct = false;

        button1.setOnAction(this);
        button2.setOnAction(this);
        button3.setOnAction(this);

        next.setOnAction(e -> {
            if (correct == true) {
                controller.setCorrectQuestion();
            } else {
                controller.setFalseQuestion();
            }
            score.setText("CORRECT: " + controller.getRightAnswers() + "\nWRONG: " + controller.getWrongAnswers());
            question = controller.getNextQuestion();
            if (question == null || controller.getWrongAnswers() == 5 ) {
                //System.exit(0);
                primaryStage.setScene(myFunction(primaryStage));
                primaryStage.show();
            }
            frage.setText(question.getId() + ". " + question.getQuestion());
            button.setText("A. " + question.getAnswers().get(0) +
                    "\nB. " + question.getAnswers().get(1) +
                    "\nC. " + question.getAnswers().get(2));
            correctAnswer.set(question.getCorrectAnswer());
            if (correctAnswer.get().equals(question.getAnswers().get(0)))
                correctButton = button1;
            else if (correctAnswer.get().equals(question.getAnswers().get(1)))
                correctButton = button2;
            else
                correctButton = button3;
            correct=false;
        });

        GridPane gridPane = new GridPane();

        HBox hbox = new HBox(50);
        hbox.setPadding(new Insets(50, 50, 50, 50));
        hbox.getChildren().addAll(button1, button2, button3, next, score);
        hbox.setAlignment(Pos.CENTER);

        HBox hbox1 = new HBox(20);
        hbox1.setPadding(new Insets(10, 10, 10, 10));
        hbox1.getChildren().addAll(timeLabel);
        hbox1.setAlignment(Pos.CENTER);

        layout.setTop(hbox1);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(frage, 1, 1);
        gridPane.add(button, 1, 2);

        layout.setCenter(gridPane);
        layout.setBottom(hbox);
        primaryStage.setTitle("Chestionar auto");
    }

    private void doTime() {
        Timeline time= new Timeline();
        KeyFrame frame= new KeyFrame(Duration.seconds(1), event -> {
            seconds--;
            Integer minutes = seconds / 60;
            Integer leftSeconds = seconds % 60;
            timeLabel.setText("Time: " + minutes.toString() + ":" + leftSeconds.toString());
            if (seconds <= 0) {
                System.exit(0);
            }
        });
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!=null){
            time.stop();
        }
        time.play();
    }

    public void menu(String[] args){
        launch(args);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == correctButton) {
            correct = true;
        } else {
            correct = false;
        }
    }

    public Scene myFunction(Stage primaryStage){
        Label label2 = new Label("ENDE");
        Label label = new Label();
        label2.setMinSize(60,60);
        label2.setStyle("-fx-font-size:20");
        label.setMinSize(60,60);
        label.setStyle("-fx-font-size:20");
        Button restart = new Button("RESTART");
        restart.setMinSize(60,60);
        restart.setStyle("-fx-font-size:40");
        restart.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        if(controller.getWrongAnswers() == 5)
            label.setText("Durchgefallen");
        else
            label.setText("Fuhrerschein genommen!");
        Button finish = new Button("ENDE");
        finish.setMinSize(60,60);
        finish.setStyle("-fx-font-size:40");
        finish.setOnAction(e -> {
            System.exit(0);
        });
        VBox layout2 = new VBox(20);
        Button score2 = new Button();
        score.setMinSize(60,60);
        score.setStyle("-fx-font-size:15");
        layout2.getChildren().addAll(label2, label,restart,finish, score);
        layout2.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(layout2, 800,600);
        return scene2;
    }

}
