package com.svnit.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint;
    private Button ConfirmButton;
    private EditText editTextGuess;

    Random r = new Random();
    int random;

    int remainingRight = 10;

    boolean twoDigit, threeDigit, fourDigit;

    ArrayList<Integer> guessList = new ArrayList<>();

    int userAttempt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewHint = findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewRight = findViewById(R.id.textViewRight);
        ConfirmButton = findViewById(R.id.buttonConfirm);
        editTextGuess = findViewById(R.id.editTextGuess);

        twoDigit = getIntent().getBooleanExtra("two",false);
        threeDigit = getIntent().getBooleanExtra("three",false);
        fourDigit = getIntent().getBooleanExtra("four",false);

        if (twoDigit){
            random = r.nextInt(90)+10;
        }
        if (threeDigit){
            random = r.nextInt(900)+100;
        }
        if (fourDigit){
            random = r.nextInt(9000)+1000;
        }
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals("")){
                    Toast.makeText(GameActivity.this,"Please Enter a Guess",Toast.LENGTH_LONG).show();
                }
                else{
                    textViewHint.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewLast.setVisibility(View.VISIBLE);

                    remainingRight--;
                    userAttempt++;

                    int userGuess = Integer.parseInt(guess);
                    guessList.add(userGuess);
                    textViewLast.setText("Your last guess is : " + guess);
                    textViewRight.setText("Your remaining guesses: "+ remainingRight);

                    if (random == userGuess){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulations. My guess was " + random+"\n\n You know my number in "+ userAttempt+" attempts.\n\nYour guesses : "+guessList+" \n\nWould you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();
                    }
                    else if (random < userGuess){
                        textViewHint.setText("Decrease your guess");
                    }
                    else{
                        textViewHint.setText("Increase your guess");
                    }

                    if (remainingRight == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, your guesses are over.\n\n"+"My guess was " + random+".\n\nYour guesses were  "+guessList+" \n\nWould you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();
                    }

                    editTextGuess.setText("");
                }
            }
        });
    }
}