package steven.small.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import steven.small.R;
import steven.small.utils.CoutTime;

public class TrueFalseGameActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = TrueFalseGameActivity.class.getSimpleName();
    private TextView tvQuestion;
    private TextView tvTrue;
    private TextView tvFalse;
    private int firstNumber;
    private int secondNumber;
    private int resultTrueNumber;
    private int resultFalseNumber;
    private int errorNumber;
    private boolean isTrue;
    int numberQuestion = 0;
    private TextView tvScore;
    private ProgressBar progressBar;
    private int isEnd = 0;
    private CoutTime coutTimer;
    private int topScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_game);
        iniUI();
    }

    protected void iniUI() {
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvTrue = (TextView) findViewById(R.id.tv_true);
        tvFalse = (TextView) findViewById(R.id.tv_false);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_rival);
        tvScore = (TextView) findViewById(R.id.tv_score);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Baloo-Regular.ttf");
        tvQuestion.setTypeface(font);
        setUpTopScore(numberQuestion);
        creatQuestion(1);
        tvTrue.setOnClickListener(this);
        tvFalse.setOnClickListener(this);
    }

    private void creatQuestion(int level) {
        coutTimer = new CoutTime(5000, 250, progressBar, isEnd, new CoutTime.checkFinish() {
            @Override
            public void finished() {
                endGame();
            }
        });
        coutTimer.start();
        if (level == 1) {
            Random random = new Random();
            firstNumber = random.nextInt(10) + 1;
            secondNumber = random.nextInt(10) + 1;
            Log.d(TAG, "creatQuestion: " + firstNumber + "\n" + secondNumber);
            resultTrueNumber = firstNumber + secondNumber;
            errorNumber = random.nextInt(5) + 1;
            int check = random.nextInt(2);
            if (check == 0) {
                resultFalseNumber = resultTrueNumber + errorNumber;
            } else {
                resultFalseNumber = resultTrueNumber - errorNumber;
            }
            isTrue = random.nextBoolean();
            if (isTrue) {
                tvQuestion.setText(firstNumber + " + " + secondNumber + " = " + resultTrueNumber);
            } else {
                tvQuestion.setText(firstNumber + " + " + secondNumber + " = " + resultFalseNumber);
            }
        }
        tvScore.setText("Điểm của bạn : " + numberQuestion + "\n " + "Điểm cao nhất : " + topScore);
        setUpTopScore(numberQuestion);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_true:
                Log.d(TAG, "onClick: " + isEnd);
                coutTimer.cancel();
                if (isEnd == 2) {
                    Toast.makeText(TrueFalseGameActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (isTrue) {
                        Toast.makeText(TrueFalseGameActivity.this, "Đúng rồi", Toast.LENGTH_SHORT).show();
                        numberQuestion++;

                        creatQuestion(1);
                    } else {
                        Toast.makeText(TrueFalseGameActivity.this, "Sai rồi", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        endGame();
                    }
                }

                break;
            case R.id.tv_false:
                Log.d(TAG, "onClick: " + isEnd);
                coutTimer.cancel();
                if (isEnd == 2) {
                    Toast.makeText(TrueFalseGameActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (!isTrue) {
                        Toast.makeText(TrueFalseGameActivity.this, "Đúng rồi", Toast.LENGTH_SHORT).show();
                        numberQuestion++;
//                        tvScore.setText("Điểm của bạn : " + numberQuestion + " ");
                        creatQuestion(1);
                    } else {
                        Toast.makeText(TrueFalseGameActivity.this, "Sai rồi", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        endGame();
                    }
                }
                break;
        }
    }

    private void endGame() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.end_game) + ". Your score is " + numberQuestion)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }

    private void setUpTopScore(int currentScore) {
        SharedPreferences utils = getSharedPreferences("SMALL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = utils.edit();
        topScore = utils.getInt("TOP_GAME_1", 0);
        if (currentScore > topScore)
            editor.putInt("TOP_GAME_1", currentScore);

    }
}
