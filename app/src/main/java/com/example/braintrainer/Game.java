package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    TextView countDown;
    TextView scoreText;
    TextView question;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgain;
    TextView gameOver;

    int seconds = 30;

    int firstNumber;
    int secondNumber;
    String questionText;
    int answer;
    int rightAnswers = 0;
    int askedQuestions = 0;

    String[] operations = {" + " , " - "};
    ArrayList<Integer> allAnswers = new ArrayList<>();

    CountDownTimer mCountDownTimer;

    public void buttonClick(View v){
        Button b = (Button)v;
        if (b.getText().equals(Integer.toString(answer))){
            rightAnswers++;
        }

        askedQuestions++;
        setUp();
    }

    public void playAgainPressed(View v){
        gameOver.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        rightAnswers = 0;
        askedQuestions = 0;
        setUp();
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        setCountDownTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        countDown = findViewById(R.id.timer);
        scoreText = findViewById(R.id.score);
        question = findViewById(R.id.question);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        playAgain = findViewById(R.id.playAgainButton);
        gameOver = findViewById(R.id.gameOver);

        setUp();
        setCountDownTimer();
    }

    public void setUp(){
        setCountDownText();
        setQuestion();
        setButtons();
        setScore();
    }

    public void setCountDownTimer(){

        mCountDownTimer = new CountDownTimer(seconds * 1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds--;
                setCountDownText();
            }

            @Override
            public void onFinish() {
                seconds = 30;
                finishGame();
            }
        }.start();
    }

    public void finishGame(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        gameOver.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);
    }

    public void setButtons(){
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);

        int random = getRandomNumber(3);
        String rightAnswerString = Integer.toString(answer);
        buttons.get(random).setText(rightAnswerString);
        buttons.remove(random);

        for (Button b:buttons){

            while (b.getText().equals("")){
                int wrongAnswer = answer / 2 + getRandomNumber(answer);
                if (checkRepeat(wrongAnswer)){
                    String wrongAnswerString = Integer.toString(wrongAnswer);
                    b.setText(wrongAnswerString);
                }
            }
        }
    }

    public void setQuestion(){
        firstNumber = getRandomNumber(50);
        secondNumber = getRandomNumber(50);
        int random = getRandomNumber(1);
        questionText = firstNumber + operations[random] + secondNumber;

        if (random == 0) {
            answer = firstNumber + secondNumber;
        } else {
            answer = firstNumber - secondNumber;
        }
        allAnswers.add(1);

        question.setText(questionText);

    }

    public void setCountDownText(){
        String text;
        if (seconds < 10){
            text = "0" + seconds + " s";
        } else {
            text = seconds + " s";
        }
        countDown.setText(text);
    }

    public void setScore(){
        String score = rightAnswers + " / " + askedQuestions;
        scoreText.setText(score);
    }

    public int getRandomNumber(int max){
        return (int)Math.round(Math.random() * max);
    }

    public boolean checkRepeat(int number){
        boolean flag = true;
        for (int i : allAnswers){
            if (number == i){
                flag = false;
            }
        }
        return flag;
    }
}
