package dev.tabzy.saywatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends WearableActivity {

    private TextView mScoreView;
    private TextView mCountdown;
    private TextView mGameScoreValue;
    private TextClock textClock;
    private final ArrayList<Character> sequence = new ArrayList<>();
    private final ArrayList<Character> entered = new ArrayList<>();
    @SuppressWarnings("SpellCheckingInspection")
    private final String chars = "RGBY";
    private int step = 0;
    private int playerScore = 0;
    private final Random rand = new Random();
    private boolean gameOver = false;
    private boolean seqComp = false;

    private Thread game;
    private final long nap = 1000;

    private Button rB;
    private Button gB;
    private Button bB;
    private Button yB;


    private final long buzz = 200;
    private final VibrationEffect vibe = VibrationEffect.createOneShot(buzz, VibrationEffect.DEFAULT_AMPLITUDE);
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mScoreView = findViewById(R.id.gameScoreText);
        mCountdown = findViewById(R.id.countdown);
        mGameScoreValue = findViewById(R.id.gameScoreValue);
        textClock = findViewById(R.id.textClock);

        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Enables Always-on
        setAmbientEnabled();

        game = new Thread("gameThread") {
            public void run() {
                startGame();
            }
        };
        game.start();
    }

    private void setupGameButtons() {

        rB = findViewById(R.id.red_button);
        rB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addToEntry('R');
                tappedButton('R');
                step++;
                checkSequence();
                seqComp = SequenceComplete();
            }
        });


        gB = findViewById(R.id.green_button);
        gB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addToEntry('G');
                tappedButton('G');
                step++;
                checkSequence();
                seqComp = SequenceComplete();
            }
        });


        bB = findViewById(R.id.blue_button);
        bB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //BlueButton Stuff
                addToEntry('B');
                tappedButton('B');
                step++;
                checkSequence();
                seqComp = SequenceComplete();
            }
        });


        yB = findViewById(R.id.yellow_button);
        yB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //YellowButton Stuff
                addToEntry('Y');
                tappedButton('Y');
                step++;
                checkSequence();
                seqComp = SequenceComplete();
            }
        });

    }//setupGameButtons

    private boolean SequenceComplete() {
        return sequence.size() == step;
    }

    private void generateSequence() {
        for (int i = 0; i < 4; i++) {
            int j = rand.nextInt(4);
            sequence.add(i, chars.charAt(j));
            seqComp = false;
        }
    }//generateSequence

    private void addToSequence() {
        int i = sequence.size();
        int j = rand.nextInt(4);
        sequence.add(i, chars.charAt(j));
        entered.clear();
        step = 0;
        seqComp = false;
    }//addToSequence

    private void checkSequence() {
        if (entered.get(step - 1) != sequence.get(step - 1)) {
            gameOver = true;
            Toast.makeText(getApplicationContext(), R.string.gameOver, Toast.LENGTH_SHORT)
                    .show();
        } else {
            addScore();
        }
    }//checkSequence

    private void showSequence() {
        rB.setClickable(false);
        gB.setClickable(false);
        bB.setClickable(false);
        yB.setClickable(false);
        for (int i = 0; i < sequence.size(); i++) {
            Character b = sequence.get(i);
            blinkButton(b);
            try {
                Thread.sleep(nap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rB.setClickable(true);
        gB.setClickable(true);
        bB.setClickable(true);
        yB.setClickable(true);

    }

    private void blinkButton(Character b) {
        switch (b) {
            case 'R': {
                rB.setBackgroundResource(R.drawable.red_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rB.setBackgroundResource(R.drawable.red_button);
                break;
            }
            case 'G': {
                gB.setBackgroundResource(R.drawable.green_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gB.setBackgroundResource(R.drawable.green_button);
                break;
            }
            case 'B': {
                bB.setBackgroundResource(R.drawable.blue_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bB.setBackgroundResource(R.drawable.blue_button);
                break;
            }
            case 'Y': {
                yB.setBackgroundResource(R.drawable.yellow_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                yB.setBackgroundResource(R.drawable.yellow_button);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + b);
        }//switch
    }

    private void tappedButton(Character b) {
        switch (b) {
            case 'R': {
                //rB.setBackgroundResource(R.drawable.red_button_active);
                v.vibrate(vibe);
                try {
                    rB.setBackgroundResource(R.drawable.red_button_active);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rB.setBackgroundResource(R.drawable.red_button);

                break;
            }
            case 'G': {
                gB.setBackgroundResource(R.drawable.green_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gB.setBackgroundResource(R.drawable.green_button);
                break;
            }
            case 'B': {
                bB.setBackgroundResource(R.drawable.blue_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bB.setBackgroundResource(R.drawable.blue_button);
                break;
            }
            case 'Y': {
                yB.setBackgroundResource(R.drawable.yellow_button_active);
                v.vibrate(vibe);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                yB.setBackgroundResource(R.drawable.yellow_button);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + b);
        }//switch
    }


    private void addToEntry(Character c) {
        if (entered.size() == 0) {
            entered.add(0, c);
        } else {
            entered.add(entered.size(), c);
        }
    }//addToEntry

    private void addScore() {
        playerScore += 10;
        String scoreText;
        scoreText = "" + playerScore;
        mGameScoreValue.setText(scoreText);
    }


    private void countdown() throws InterruptedException {
        mScoreView.setText("");
        mGameScoreValue.setText("");
        textClock.onVisibilityAggregated(false);

        int count = 3;
        int steps = 3;
        for (int i = 0; i <= steps; i++) {
            Thread.sleep(nap);
            mCountdown.setText("" + count);
            count--;
        }

        mCountdown.setText("");
        mScoreView.setText(R.string.score);
        mGameScoreValue.setText("" + playerScore);
        textClock.onVisibilityAggregated(true);
    }

/////////////////////////////////////////////
///////////  MAIN GAME LOOP  ////////////////
/////////////////////////////////////////////

    private void startGame() {
        boolean gameActive = false;
        setupGameButtons();

        while (!gameOver) { //MAIN GAME LOOP
            if (!gameActive) {
                generateSequence();
                try {
                    countdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameActive = true;
                showSequence();
            } else {// --====== GAME RUNNING ======--
                if (seqComp) {//sequence is completed
                    addToSequence();

                    try {
                        countdown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    showSequence();
                }//seqComp
            }//gameRunning
        }//gameOver

        Intent MainPage = new Intent(GameActivity.this, MainActivity.class);
        if (playerScore > MainActivity.playerHighScore)
            MainPage.putExtra("highScore", playerScore);
        game.interrupt();
        game = null;
        startActivity(MainPage);
        finish();

    }//startGame()


}//CLASS
