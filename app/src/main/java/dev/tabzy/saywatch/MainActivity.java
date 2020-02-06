package dev.tabzy.saywatch;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {


    public static int playerHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getInt("highScore") > playerHighScore) {
                playerHighScore = extras.getInt("highScore");
            }

        }
        TextView mHighScore;
        mHighScore = findViewById(R.id.highScore);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.highScore), playerHighScore);
//        String tmp = R.string.highScore + String.valueOf(playerHighScore);
        mHighScore.setText(text);

        // Enables Always-on
        setAmbientEnabled();

        setupButtons();
    }//onCreate

    private void setupButtons() {
        final Button startButton = findViewById(R.id.start_btn);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //trigger code

                Intent GamePage = new Intent(MainActivity.this, GameActivity.class);
                startActivity(GamePage);
                finish();

            }
        });
    }

}//CLASS
