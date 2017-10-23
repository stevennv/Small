package steven.small.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;

import java.util.Random;

import steven.small.R;
import steven.small.dialog.GuideDialog;
import steven.small.utils.CoutTime;
import steven.small.utils.SharedPreferencesUtils;
import steven.small.utils.ToastUltis;

public class TrueFalseGameActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = TrueFalseGameActivity.class.getSimpleName();
    private Activity mActivity;
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
    private SharedPreferencesUtils utils;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false_game);
        iniUI();
    }

    protected void iniUI() {
        inflater = getLayoutInflater();
        utils = new SharedPreferencesUtils(this);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvTrue = (TextView) findViewById(R.id.tv_true);
        tvFalse = (TextView) findViewById(R.id.tv_false);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_rival);
        tvScore = (TextView) findViewById(R.id.tv_score);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Baloo-Regular.ttf");
        tvQuestion.setTypeface(font);
        topScore = utils.getHightScoreGame1();
        tvTrue.setOnClickListener(this);
        tvFalse.setOnClickListener(this);
        waitReady();
    }

    private void creatQuestion(int level) {
        coutTimer = new CoutTime(3000, 250, progressBar, isEnd, new CoutTime.checkFinish() {
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        coutTimer.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_true:
                coutTimer.cancel();
                if (isEnd == 2) {
                    Toast.makeText(TrueFalseGameActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (isTrue) {
                        numberQuestion++;
                        new CountDownTimer(1000, 250) {
                            @Override
                            public void onTick(long l) {
                                ToastUltis.showCustomToast(inflater, TrueFalseGameActivity.this, true, 500);
                            }

                            @Override
                            public void onFinish() {

                                creatQuestion(1);
                            }
                        }.start();

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        endGame();
                    }
                }

                break;
            case R.id.tv_false:
                coutTimer.cancel();
                if (isEnd == 2) {
                    Toast.makeText(TrueFalseGameActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (!isTrue) {
                        numberQuestion++;
                        new CountDownTimer(1000, 250) {
                            @Override
                            public void onTick(long l) {
                                ToastUltis.showCustomToast(inflater, TrueFalseGameActivity.this, true
                                        , 500);
                            }

                            @Override
                            public void onFinish() {
                                creatQuestion(1);
                            }
                        }.start();
                    } else {
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
        if (numberQuestion > topScore) {
            utils.saveHightScoreGame1(numberQuestion);
        }
    }

    void waitReady() {
        GuideDialog dialog = new GuideDialog(this, new GuideDialog.onClick() {
            @Override
            public void confirm() {
                creatQuestion(1);
            }
        });
        dialog.show();
    }
}
