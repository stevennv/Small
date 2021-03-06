package steven.small.activity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import steven.small.R;
import steven.small.dialog.GuideDialog;
import steven.small.model.OTT;
import steven.small.utils.CoutTime;
import steven.small.utils.SharedPreferencesUtils;

public class ReflexActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ReflexActivity.class.getSimpleName();
    private ImageView imgQuestion;
    private ImageView imgAnswer1;
    private ImageView imgAnswer2;
    private RelativeLayout rlAnswer1;
    private RelativeLayout rlAnswer2;
    private List<OTT> list = new ArrayList<>();
    private OTT ott;
    private int question;
    private int score = 0;
    private CoutTime coutTime;
    private int isEnd;
    private int topScore;
    private SharedPreferencesUtils utils;
    private ProgressBar progressBar;
    private DatabaseReference mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex);
        iniUI();
    }

    protected void iniUI() {
        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(this);
        topScore = utils.getHightScoreGame2();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_rival);
        imgQuestion = (ImageView) findViewById(R.id.img_question);
        imgAnswer1 = (ImageView) findViewById(R.id.btn_answer1);
        imgAnswer2 = (ImageView) findViewById(R.id.btn_answer2);
        rlAnswer1 = (RelativeLayout) findViewById(R.id.rl_answer1);
        rlAnswer2 = (RelativeLayout) findViewById(R.id.rl_answer2);

        rlAnswer1.setOnClickListener(this);
        rlAnswer2.setOnClickListener(this);
        GuideDialog dialog = new GuideDialog(this, new GuideDialog.onClick() {
            @Override
            public void confirm() {
                addQuestion();
            }
        });
        dialog.show();
    }

    private void addQuestion() {
        coutTime = new CoutTime(3000, 250, progressBar, isEnd, new CoutTime.checkFinish() {
            @Override
            public void finished() {
                endGame();
            }
        });
        coutTime.start();
        list.add(new OTT(1, R.mipmap.zero_finger_blue, R.mipmap.two_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.zero_finger_blue, R.mipmap.five_fingers_yellow, R.mipmap.two_finger_yellow));
        list.add(new OTT(1, R.mipmap.zero_finger_red, R.mipmap.two_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.zero_finger_red, R.mipmap.five_fingers_yellow, R.mipmap.two_finger_yellow));
        list.add(new OTT(1, R.mipmap.two_finger_blue, R.mipmap.five_fingers_yellow, R.mipmap.zero_finger_yellow));
        list.add(new OTT(1, R.mipmap.two_finger_blue, R.mipmap.zero_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.two_finger_red, R.mipmap.zero_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.two_finger_red, R.mipmap.five_fingers_yellow, R.mipmap.zero_finger_yellow));
        list.add(new OTT(1, R.mipmap.five_fingers_blue, R.mipmap.zero_finger_yellow, R.mipmap.two_finger_yellow));
        list.add(new OTT(1, R.mipmap.five_fingers_blue, R.mipmap.two_finger_yellow, R.mipmap.zero_finger_yellow));
        list.add(new OTT(1, R.mipmap.five_fingers_red, R.mipmap.zero_finger_yellow, R.mipmap.two_finger_yellow));
        list.add(new OTT(1, R.mipmap.five_fingers_red, R.mipmap.two_finger_yellow, R.mipmap.zero_finger_yellow));
        Random random = new Random();
        question = random.nextInt(11);
        ott = list.get(question);
        imgQuestion.setImageResource(ott.getImage1());
        imgAnswer1.setImageResource(ott.getImage2());
        imgAnswer2.setImageResource(ott.getImage3());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_answer1:
                coutTime.cancel();
                if (isEnd == 2) {
                    Toast.makeText(ReflexActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (question == 0 || question == 3 || question == 4 || question == 6 || question == 8 || question == 11) {
                        endGame();
                    } else {
                        score++;
                        new CountDownTimer(1000, 250) {
                            @Override
                            public void onTick(long l) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                addQuestion();
                            }
                        }.start();

                    }
                }


                break;
            case R.id.rl_answer2:
                coutTime.cancel();
                if (isEnd == 2) {
                    Toast.makeText(ReflexActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    if (question == 0 || question == 3 || question == 4 || question == 6 || question == 8 || question == 11) {
                        score++;
                        new CountDownTimer(1000, 250) {
                            @Override
                            public void onTick(long l) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                addQuestion();
                            }
                        }.start();
                    } else {
                        endGame();
                    }
                }

                break;
        }

    }

    private void endGame() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.end_game) + ". Your score is " + score)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
        if (score > topScore) {
            utils.saveHightScoreGame1(score);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        coutTime.cancel();
    }
}
