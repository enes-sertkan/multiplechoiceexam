package com.example.multiplechoiceexam;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HelloApplication extends Application {

    //Make two ArrayLists of the type Question.
    ArrayList<Question> everyQuestion;     //for all questions
    ArrayList<Question> randomizedQuestions; //for 5 random Questions

    //Make a VBox with 5 questions and alternatives for each one.
    VBox vertQstnOptions;

    //Question 1
    //Create Label to display Question
    Label labelQ1;
    //Creating radio buttons for options
    RadioButton rbOfFirstQuestionA;
    RadioButton rbOfFirstQuestionB;
    RadioButton rbOfFirstQuestionC;
    RadioButton rbOfFirstQuestionD;

    //Creating a toggle group with only one pick from four alternatives
    ToggleGroup tg1;

    //Question 2
    Label labelQ2;
    RadioButton rbOfSecondQuestionA;
    RadioButton rbOfSecondQuestionB;
    RadioButton rbOfSecondQuestionC;
    RadioButton rbOfSecondQuestionD;
    ToggleGroup tg2;

    //Question 3
    Label labelQ3;
    RadioButton rbOfThirdQuestionA;
    RadioButton rbOfThirdQuestionB;
    RadioButton rbOfThirdQuestionC;
    RadioButton rbOfThirdQuestionD;
    ToggleGroup tg3;

    //Question 4
    Label labelQ4;
    RadioButton rbOfFourthQuestionA;
    RadioButton rbOfFourthQuestionB;
    RadioButton rbOfFourthQuestionC;
    RadioButton rbOfFourthQuestionD;
    ToggleGroup tg4;

    //Question 5
    Label labelQ5;
    RadioButton rbOfFifthQuestionA;
    RadioButton rbOfFifthQuestionB;
    RadioButton rbOfFifthQuestionC;
    RadioButton rbOfFifthQuestionD;
    ToggleGroup tg5;

    //Making layouts to display text vertically and horizontally
    VBox vBox;
    HBox hBoxButtons;
    Button submitBtn;
    Button calculateBtn;
    Button resultBtn;
    Scene scene;

    //Create String variables for each of the questions' alternatives.
    String firstSelected;
    String secondSelected;
    String thirdSelected;
    String fourthSelected;
    String fifthSelected;

    //Making the result default value as 0
    int result = 0;

    String fileOutputString = "";
    TextField studNameField;

    @Override
    public void start(Stage stage) throws IOException {

        //Root panel creation
        Pane root = new Pane();
        vertQstnOptions = new VBox();

        //Add DARK SEA GREEN color to background of root
        root.setBackground(Background.fill(Color.DARKSEAGREEN));

        everyQuestion = new ArrayList<>();
        randomizedQuestions = new ArrayList<>();

        vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));

        //Create label and set value node of application
        Label labelNote = new Label("You should answer the following question. Select a single answer from four choices.");
        labelNote.setFont(new Font("", 20));
        labelNote.setStyle("-fx-font-weight: bold");
        vBox.getChildren().add(labelNote);

        //functions
        //read question from file
        getQuestionsFromFile();

        //Select 5 random Question
        fiveRandomQuestionsFromFile();


        //Create HBox for Student's Name
        HBox stdNameHBox = new HBox();
        Label stdNameLbl = new Label("Student's Name: ");
        stdNameLbl.setAlignment(Pos.CENTER_LEFT);
        stdNameLbl.setFont(new Font("", 15));

        studNameField = new TextField("");
        studNameField.setMinWidth(400);

        stdNameHBox.setSpacing(20);

        HBox.setMargin(stdNameLbl, new Insets(25, 0, 40, 0));
        HBox.setMargin(studNameField, new Insets(25, 0, 40, 0));

        stdNameHBox.getChildren().add(stdNameLbl);
        stdNameHBox.getChildren().add(studNameField);

        //Add HBox to main VBox
        vBox.getChildren().add(stdNameHBox);

        Label labelQuestions = new Label("Questions");
        labelQuestions.setStyle("-fx-font-weight: bold");
        labelQuestions.setFont(new Font("", 18));

        vBox.getChildren().add(labelQuestions);

        //sets questions and Options
        QstnsOptionsSet();

        //Call function that will add button and its click listeners
        addButtons();

        //button disables
        submitBtn.setDisable(true);
        calculateBtn.setDisable(true);
        resultBtn.setDisable(true);

        //Enable button when there is some text in textfield
        studNameField.textProperty().addListener((observable, oldValue, newValue) -> {

            submitBtn.setDisable(false);
            calculateBtn.setDisable(false);
            resultBtn.setDisable(false);

        });

        //calculateGrade and set Score out of 100.
        calcResult();

        //append data in results.txt file
        saveResultinFile();

        root.getChildren().add(vBox);
        
        //Create scene with width = 900 and height = 580
        scene = new Scene(root, 900, 580);
        stage.setTitle("Multiple Choice Exam");
        stage.setScene(scene);
        stage.show();


    }

    void saveResultinFile() {

        //when submit button is clicked
        submitBtn.setOnAction(e -> {

            //add content into results.txt file
            writeInResultsTextFile(fileOutputString + " " + result);

            //Reset selected values
            studNameField.setText("");

            labelQ1.setText("1. " + randomizedQuestions.get(0).question);
            labelQ2.setText("2. " + randomizedQuestions.get(1).question);
            labelQ3.setText("3. " + randomizedQuestions.get(2).question);
            labelQ4.setText("4. " + randomizedQuestions.get(3).question);
            labelQ5.setText("5. " + randomizedQuestions.get(4).question);

            rbOfFirstQuestionA.setSelected(false);
            rbOfFirstQuestionB.setSelected(false);
            rbOfFirstQuestionC.setSelected(false);
            rbOfFirstQuestionD.setSelected(false);

            rbOfSecondQuestionA.setSelected(false);
            rbOfSecondQuestionB.setSelected(false);
            rbOfSecondQuestionC.setSelected(false);
            rbOfSecondQuestionD.setSelected(false);

            rbOfThirdQuestionA.setSelected(false);
            rbOfThirdQuestionB.setSelected(false);
            rbOfThirdQuestionC.setSelected(false);
            rbOfThirdQuestionD.setSelected(false);

            rbOfFourthQuestionA.setSelected(false);
            rbOfFourthQuestionB.setSelected(false);
            rbOfFourthQuestionC.setSelected(false);
            rbOfFourthQuestionD.setSelected(false);

            rbOfFifthQuestionA.setSelected(false);
            rbOfFifthQuestionB.setSelected(false);
            rbOfFifthQuestionC.setSelected(false);
            rbOfFifthQuestionD.setSelected(false);

        });
    }

    void calcResult() {

        //When Calculate Grade button is clicked
        calculateBtn.setOnAction(e -> {

            fileOutputString = studNameField.getText() + " ";

            result = 0;

            String optionOneSelection = null;
            String optionTwoSelection = null;
            String optionThreeSelection = null;
            String optionFourSelection = null;
            String optionFiveSelection = null;

            // compare selection with answer
            // if right + 20 else - 5
            if (rbOfFirstQuestionA.isSelected()) {

                firstSelected = rbOfFirstQuestionA.getText();

                if (firstSelected.equals(randomizedQuestions.get(0).answer)) {
                    result = result + 20;

                    rbOfFirstQuestionA.setTextFill(Color.DARKSEAGREEN);
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Correct] ");
                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Incorrect, Correct Answer is " +
                            randomizedQuestions.get(0).answer + "] ");
                }

                optionOneSelection = "A";
                resultBtn.setText(result + "/100");

            } else if (rbOfFirstQuestionB.isSelected()) {
                firstSelected = rbOfFirstQuestionB.getText();

                if (firstSelected.equals(randomizedQuestions.get(0).answer)) {
                    result = result + 20;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Correct] ");
                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Incorrect, Correct Answer is " +
                            randomizedQuestions.get(0).answer + "] ");

                }
                optionOneSelection = "B";


                resultBtn.setText(result + "/100");

            } else if (rbOfFirstQuestionC.isSelected()) {
                firstSelected = rbOfFirstQuestionC.getText();

                if (firstSelected.equals(randomizedQuestions.get(0).answer)) {
                    result = result + 20;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Correct] ");
                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Incorrect, Correct Answer is " +
                            randomizedQuestions.get(0).answer + "] ");

                }
                optionOneSelection = "C";


                resultBtn.setText(result + "/100");

            } else if (rbOfFirstQuestionD.isSelected()) {

                firstSelected = rbOfFirstQuestionD.getText();

                if (firstSelected.equals(randomizedQuestions.get(0).answer)) {
                    result = result + 20;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Correct] ");
                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(0).question + "\t [Incorrect, Correct Answer is " +
                            randomizedQuestions.get(0).answer + "] ");

                }
                optionOneSelection = "D";


                resultBtn.setText(result + "/100");

            } else if (getSelectedToggleNull(tg1)) {
                resultBtn.setText(result + "/100");
                optionOneSelection = "X";
            }


            if (rbOfSecondQuestionA.isSelected()) {
                secondSelected = rbOfSecondQuestionA.getText();

                if (secondSelected.equals(randomizedQuestions.get(1).answer)) {
                    result = result + 20;
                    labelQ2.setText("2. " + randomizedQuestions.get(1).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(1).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(1).answer + "] ");
                }
                optionTwoSelection = "A";


                resultBtn.setText(result + "/100");

            } else if (rbOfSecondQuestionB.isSelected()) {
                secondSelected = rbOfSecondQuestionB.getText();

                if (secondSelected.equals(randomizedQuestions.get(1).answer)) {
                    result = result + 20;
                    labelQ2.setText("2. " + randomizedQuestions.get(1).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(1).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(1).answer + "] ");
                }
                optionTwoSelection = "B";


                resultBtn.setText(result + "/100");

            } else if (rbOfSecondQuestionC.isSelected()) {
                secondSelected = rbOfSecondQuestionC.getText();

                if (secondSelected.equals(randomizedQuestions.get(1).answer)) {
                    result = result + 20;
                    labelQ2.setText("2. " + randomizedQuestions.get(1).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(1).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(1).answer + "] ");
                }
                optionTwoSelection = "C";


                resultBtn.setText(result + "/100");

            } else if (rbOfSecondQuestionD.isSelected()) {

                secondSelected = rbOfSecondQuestionD.getText();

                if (secondSelected.equals(randomizedQuestions.get(1).answer)) {
                    result = result + 20;
                    labelQ2.setText("2. " + randomizedQuestions.get(1).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ1.setText("1. " + randomizedQuestions.get(1).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(1).answer + "] ");
                }
                optionTwoSelection = "D";

                resultBtn.setText(result + "/100");

            } else if (getSelectedToggleNull(tg2)) {
                resultBtn.setText(result + "/100");
                optionTwoSelection = "X";
            }


            if (rbOfThirdQuestionA.isSelected()) {
                thirdSelected = rbOfThirdQuestionA.getText();

                if (thirdSelected.equals(randomizedQuestions.get(2).answer)) {
                    result = result + 20;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(2).answer + "] ");
                }
                optionThreeSelection = "A";


                resultBtn.setText(result + "/100");

            } else if (rbOfThirdQuestionB.isSelected()) {
                thirdSelected = rbOfThirdQuestionB.getText();

                if (thirdSelected.equals(randomizedQuestions.get(2).answer)) {
                    result = result + 20;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(2).answer + "] ");
                }
                optionThreeSelection = "B";


                resultBtn.setText(result + "/100");

            } else if (rbOfThirdQuestionC.isSelected()) {
                thirdSelected = rbOfThirdQuestionC.getText();

                if (thirdSelected.equals(randomizedQuestions.get(2).answer)) {
                    result = result + 20;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(2).answer + "] ");
                }
                optionThreeSelection = "C";


                resultBtn.setText(result + "/100");

            } else if (rbOfThirdQuestionD.isSelected()) {

                thirdSelected = rbOfThirdQuestionD.getText();

                if (thirdSelected.equals(randomizedQuestions.get(2).answer)) {
                    result = result + 20;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ3.setText("3. " + randomizedQuestions.get(2).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(2).answer + "] ");
                }
                optionThreeSelection = "D";
                resultBtn.setText(result + "/100");

            } else if (getSelectedToggleNull(tg3)) {
                resultBtn.setText(result + "/100");
                optionThreeSelection = "X";
            }


            if (rbOfFourthQuestionA.isSelected()) {
                fourthSelected = rbOfFourthQuestionA.getText();

                if (fourthSelected.equals(randomizedQuestions.get(3).answer)) {
                    result = result + 20;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(3).answer + "] ");
                }
                optionFourSelection = "A";

                resultBtn.setText(result + "/100");

            } else if (rbOfFourthQuestionB.isSelected()) {
                fourthSelected = rbOfFourthQuestionB.getText();

                if (fourthSelected.equals(randomizedQuestions.get(3).answer)) {
                    result = result + 20;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(3).answer + "] ");
                }
                optionFourSelection = "B";


                resultBtn.setText(result + "/100");

            } else if (rbOfFourthQuestionC.isSelected()) {
                fourthSelected = rbOfFourthQuestionC.getText();

                if (fourthSelected.equals(randomizedQuestions.get(3).answer)) {
                    result = result + 20;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(3).answer + "] ");
                }
                optionFourSelection = "C";


                resultBtn.setText(result + "/100");

            } else if (rbOfFourthQuestionD.isSelected()) {

                fourthSelected = rbOfFourthQuestionD.getText();

                if (fourthSelected.equals(randomizedQuestions.get(3).answer)) {
                    result = result + 20;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ4.setText("4. " + randomizedQuestions.get(3).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(3).answer + "] ");
                }
                optionFourSelection = "D";


                resultBtn.setText(result + "/100");

            } else if (getSelectedToggleNull(tg4)) {
                resultBtn.setText(result + "/100");
                optionFourSelection = "X";
            }


            if (rbOfFifthQuestionA.isSelected()) {
                fifthSelected = rbOfFifthQuestionA.getText();

                if (fifthSelected.equals(randomizedQuestions.get(4).answer)) {
                    result = result + 20;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(4).answer + "] ");
                }
                optionFiveSelection = "A";


                resultBtn.setText(result + "/100");


            } else if (rbOfFifthQuestionB.isSelected()) {
                fifthSelected = rbOfFifthQuestionB.getText();

                if (fifthSelected.equals(randomizedQuestions.get(4).answer)) {
                    result = result + 20;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(4).answer + "] ");
                }
                optionFiveSelection = "B";


                resultBtn.setText(result + "/100");

            } else if (rbOfFifthQuestionC.isSelected()) {
                fifthSelected = rbOfFifthQuestionC.getText();

                if (fifthSelected.equals(randomizedQuestions.get(4).answer)) {
                    result = result + 20;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(4).answer + "] ");
                }
                optionFiveSelection = "C";


                resultBtn.setText(result + "/100");

            } else if (rbOfFifthQuestionD.isSelected()) {

                fifthSelected = rbOfFifthQuestionD.getText();

                if (fifthSelected.equals(randomizedQuestions.get(4).answer)) {
                    result = result + 20;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question + "\t [Correct] ");

                } else {
                    result = result - 5;
                    labelQ5.setText("5. " + randomizedQuestions.get(4).question
                            + "\t [Incorrect, Correct Answer is " + randomizedQuestions.get(4).answer + "] ");
                }
                optionFiveSelection = "D";


                resultBtn.setText(result + "/100");

            } else if (getSelectedToggleNull(tg5)) {
                resultBtn.setText(result + "/100");
                optionFiveSelection = "X";
            }

            fileOutputString = studNameField.getText() + " " + optionOneSelection + optionTwoSelection +
                    optionThreeSelection + optionFourSelection + optionFiveSelection;
        });
    }


    //When none of the four option is selected make selected false
    // so output string will add X
    public boolean getSelectedToggleNull(ToggleGroup tg) {

        boolean nothingSelected = false;

        try {
            tg.getSelectedToggle().selectedProperty().toString();
        } catch (Exception e) {
            nothingSelected = true;
        }

        return nothingSelected;
    }

    void QstnsOptionsSet() {

        //Set each question label using values from arraylist of random question
        labelQ1 = new Label("1. " + randomizedQuestions.get(0).question);
        rbOfFirstQuestionA = new RadioButton(randomizedQuestions.get(0).opt1);
        rbOfFirstQuestionB = new RadioButton(randomizedQuestions.get(0).opt2);
        rbOfFirstQuestionC = new RadioButton(randomizedQuestions.get(0).opt3);
        rbOfFirstQuestionD = new RadioButton(randomizedQuestions.get(0).opt4);

        VBox.setMargin(labelQ1, new Insets(10, 0, 0, 0));

        vertQstnOptions.getChildren().add(labelQ1);
        vertQstnOptions.getChildren().add(rbOfFirstQuestionA);
        vertQstnOptions.getChildren().add(rbOfFirstQuestionB);
        vertQstnOptions.getChildren().add(rbOfFirstQuestionC);
        vertQstnOptions.getChildren().add(rbOfFirstQuestionD);


        labelQ2 = new Label("2. " + randomizedQuestions.get(1).question);
        rbOfSecondQuestionA = new RadioButton(randomizedQuestions.get(1).opt1);
        rbOfSecondQuestionB = new RadioButton(randomizedQuestions.get(1).opt2);
        rbOfSecondQuestionC = new RadioButton(randomizedQuestions.get(1).opt3);
        rbOfSecondQuestionD = new RadioButton(randomizedQuestions.get(1).opt4);

        vertQstnOptions.getChildren().add(labelQ2);
        vertQstnOptions.getChildren().add(rbOfSecondQuestionA);
        vertQstnOptions.getChildren().add(rbOfSecondQuestionB);
        vertQstnOptions.getChildren().add(rbOfSecondQuestionC);
        vertQstnOptions.getChildren().add(rbOfSecondQuestionD);


        labelQ3 = new Label("3. " + randomizedQuestions.get(2).question);
        rbOfThirdQuestionA = new RadioButton(randomizedQuestions.get(2).opt1);
        rbOfThirdQuestionB = new RadioButton(randomizedQuestions.get(2).opt2);
        rbOfThirdQuestionC = new RadioButton(randomizedQuestions.get(2).opt3);
        rbOfThirdQuestionD = new RadioButton(randomizedQuestions.get(2).opt4);

        vertQstnOptions.getChildren().add(labelQ3);
        vertQstnOptions.getChildren().add(rbOfThirdQuestionA);
        vertQstnOptions.getChildren().add(rbOfThirdQuestionB);
        vertQstnOptions.getChildren().add(rbOfThirdQuestionC);
        vertQstnOptions.getChildren().add(rbOfThirdQuestionD);


        labelQ4 = new Label("4. " + randomizedQuestions.get(3).question);
        rbOfFourthQuestionA = new RadioButton(randomizedQuestions.get(3).opt1);
        rbOfFourthQuestionB = new RadioButton(randomizedQuestions.get(3).opt2);
        rbOfFourthQuestionC = new RadioButton(randomizedQuestions.get(3).opt3);
        rbOfFourthQuestionD = new RadioButton(randomizedQuestions.get(3).opt4);

        vertQstnOptions.getChildren().add(labelQ4);
        vertQstnOptions.getChildren().add(rbOfFourthQuestionA);
        vertQstnOptions.getChildren().add(rbOfFourthQuestionB);
        vertQstnOptions.getChildren().add(rbOfFourthQuestionC);
        vertQstnOptions.getChildren().add(rbOfFourthQuestionD);


        labelQ5 = new Label("5. " + randomizedQuestions.get(4).question);
        rbOfFifthQuestionA = new RadioButton(randomizedQuestions.get(4).opt1);
        rbOfFifthQuestionB = new RadioButton(randomizedQuestions.get(4).opt2);
        rbOfFifthQuestionC = new RadioButton(randomizedQuestions.get(4).opt3);
        rbOfFifthQuestionD = new RadioButton(randomizedQuestions.get(4).opt4);

        vertQstnOptions.getChildren().add(labelQ5);
        vertQstnOptions.getChildren().add(rbOfFifthQuestionA);
        vertQstnOptions.getChildren().add(rbOfFifthQuestionB);
        vertQstnOptions.getChildren().add(rbOfFifthQuestionC);
        vertQstnOptions.getChildren().add(rbOfFifthQuestionD);

        radioButtonPaddingAndToggle();

        vertQstnOptions.setSpacing(5);

        //Adding ScrollPane as we can not fit 5 questions with options
        ScrollPane scrollPane = new ScrollPane(vertQstnOptions);
        scrollPane.setMinWidth(890);
        scrollPane.setMaxHeight(350);
        scrollPane.setStyle("-fx-background: rgb(255,255,224);\n -fx-background-color: rgb(255,255,224)");

        vBox.getChildren().add(scrollPane);
    }


    public void addButtons() {

        //Add butons in HBox horizontally
        hBoxButtons = new HBox();

        submitBtn = new Button("Submit");
        submitBtn.setMinWidth(120);
        submitBtn.setMaxWidth(120);

        calculateBtn = new Button("Calculate Grade");
        calculateBtn.setMinWidth(130);
        calculateBtn.setMaxWidth(130);

        resultBtn = new Button("");
        resultBtn.setMinWidth(80);
        resultBtn.setMaxWidth(80);

        hBoxButtons.getChildren().add(submitBtn);
        hBoxButtons.getChildren().add(calculateBtn);
        hBoxButtons.getChildren().add(resultBtn);

        //Set margin to buttons
        HBox.setMargin(submitBtn, new Insets(30, 0, 0, 0));
        HBox.setMargin(calculateBtn, new Insets(30, 0, 0, 500));
        HBox.setMargin(resultBtn, new Insets(30, 0, 0, 0));
        hBoxButtons.setSpacing(10);

        //Set color and text style for button
        submitBtn.setBackground(Background.fill(Color.DARKOLIVEGREEN));
        submitBtn.setStyle("-fx-font-weight: bold");

        //Change cursor when mouse if hovered on Buttons
        submitBtn.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                submitBtn.setCursor(Cursor.CROSSHAIR); //Change cursor to CROSSHAIR
            }
        });

        calculateBtn.setBackground(Background.fill(Color.DARKOLIVEGREEN));

        calculateBtn.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                calculateBtn.setCursor(Cursor.CROSSHAIR); //Change cursor to CROSSHAIR
            }
        });

        calculateBtn.setStyle("-fx-font-weight: bold");

        resultBtn.setBackground(Background.fill(Color.DARKOLIVEGREEN));
        resultBtn.setStyle("-fx-font-weight: bold");

        resultBtn.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                resultBtn.setCursor(Cursor.CROSSHAIR); //Change cursor to CROSSHAIR
            }
        });

        vBox.getChildren().add(hBoxButtons);

    }


    public void radioButtonPaddingAndToggle() {

        tg1 = new ToggleGroup();
        tg2 = new ToggleGroup();
        tg3 = new ToggleGroup();
        tg4 = new ToggleGroup();
        tg5 = new ToggleGroup();

        rbOfFirstQuestionA.setPadding(new Insets(0, 0, 0, 10));
        rbOfFirstQuestionB.setPadding(new Insets(0, 0, 0, 10));
        rbOfFirstQuestionC.setPadding(new Insets(0, 0, 0, 10));
        rbOfFirstQuestionD.setPadding(new Insets(0, 0, 20, 10));

        rbOfFirstQuestionA.setToggleGroup(tg1);
        rbOfFirstQuestionB.setToggleGroup(tg1);
        rbOfFirstQuestionC.setToggleGroup(tg1);
        rbOfFirstQuestionD.setToggleGroup(tg1);

        rbOfSecondQuestionA.setPadding(new Insets(0, 0, 0, 10));
        rbOfSecondQuestionB.setPadding(new Insets(0, 0, 0, 10));
        rbOfSecondQuestionC.setPadding(new Insets(0, 0, 0, 10));
        rbOfSecondQuestionD.setPadding(new Insets(0, 0, 20, 10));

        rbOfSecondQuestionA.setToggleGroup(tg2);
        rbOfSecondQuestionB.setToggleGroup(tg2);
        rbOfSecondQuestionC.setToggleGroup(tg2);
        rbOfSecondQuestionD.setToggleGroup(tg2);


        rbOfThirdQuestionA.setPadding(new Insets(0, 0, 0, 10));
        rbOfThirdQuestionB.setPadding(new Insets(0, 0, 0, 10));
        rbOfThirdQuestionC.setPadding(new Insets(0, 0, 0, 10));
        rbOfThirdQuestionD.setPadding(new Insets(0, 0, 20, 10));

        rbOfThirdQuestionA.setToggleGroup(tg3);
        rbOfThirdQuestionB.setToggleGroup(tg3);
        rbOfThirdQuestionC.setToggleGroup(tg3);
        rbOfThirdQuestionD.setToggleGroup(tg3);

        rbOfFourthQuestionA.setPadding(new Insets(0, 0, 0, 10));
        rbOfFourthQuestionB.setPadding(new Insets(0, 0, 0, 10));
        rbOfFourthQuestionC.setPadding(new Insets(0, 0, 0, 10));
        rbOfFourthQuestionD.setPadding(new Insets(0, 0, 20, 10));

        rbOfFourthQuestionA.setToggleGroup(tg4);
        rbOfFourthQuestionB.setToggleGroup(tg4);
        rbOfFourthQuestionC.setToggleGroup(tg4);
        rbOfFourthQuestionD.setToggleGroup(tg4);

        rbOfFifthQuestionA.setPadding(new Insets(0, 0, 0, 10));
        rbOfFifthQuestionB.setPadding(new Insets(0, 0, 0, 10));
        rbOfFifthQuestionC.setPadding(new Insets(0, 0, 0, 10));
        rbOfFifthQuestionD.setPadding(new Insets(0, 0, 20, 10));

        rbOfFifthQuestionA.setToggleGroup(tg5);
        rbOfFifthQuestionB.setToggleGroup(tg5);
        rbOfFifthQuestionC.setToggleGroup(tg5);
        rbOfFifthQuestionD.setToggleGroup(tg5);

    }

    //Function to add 5 random question to another arraylist
    public void fiveRandomQuestionsFromFile() {

        int[] randomNumber = new int[5];
        randomNumber = generateRandoms(1, 20);

        //Use randomNumber to add 5 questions in alRandom5Questions from alAllQuestions
        for (int i = 0; i < 5; i++) {
            int randomQuestionNo = randomNumber[i];
            randomizedQuestions.add(everyQuestion.get(randomQuestionNo));
        }
    }

    //Function to generate array of 5 random elements ranging from 1 to 20
    public static int[] generateRandoms(int min, int max) {

        Random random = new Random();

        int numberOfElements = 5;

        int[] randomValuesArray = new int[numberOfElements];
        int current = 0;
        int rem = max - min;

        for (int i = min; i < max && numberOfElements > 0; i++) {

            double prob = random.nextDouble();

            if (prob < ((double) numberOfElements) / (double) rem) {
                numberOfElements--;
                randomValuesArray[current++] = i;
            }
            rem--;
        }
        return randomValuesArray;
    }

    //Function to get questions and options from file and add to array list
    public void getQuestionsFromFile() {

        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/WorldCup.txt");
            Scanner sc = new Scanner(fis);

            while (sc.hasNext()) {

                //Read each line using nextLine
                String q = sc.nextLine();       //First line will be question
                String o1 = sc.nextLine();      //Second line will be option 1
                String o2 = sc.nextLine();      //Third line will be option 2
                String o3 = sc.nextLine();      //Fourth line will be option 3
                String o4 = sc.nextLine();      //Fifth line will be option 4
                String ans = sc.nextLine();     //Sixth line will be option Answer

                Question question = new Question(q, o1, o2, o3, o4, ans);
                everyQuestion.add(question);

                try {
                    //7th line is empty line
                    String emptyLine = sc.nextLine();
                } catch (Exception e) {
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Function to write in the results.txt file
    public void writeInResultsTextFile(String output) {
        File file = new File("results.txt");
        FileWriter fileWriter = null;

        try {

            //Write output in results.txt file
            fileWriter = new FileWriter(file, true);
            fileWriter.write(output + "\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


//Create Question class
class Question {
    //Create String variables for Question, 4 options and an answer
    String question;
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    String answer;


    //Create Constructor with arguments
    public Question(String question, String opt1, String opt2, String opt3, String opt4, String answer) {
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.answer = answer;
    }


}
