package steven.small.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import steven.small.R;
import steven.small.dialog.GraphDialog;
import steven.small.dialog.Question;
import steven.small.utils.ToastUltis;
import steven.small.utils.Utils;

public class QuestionGameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = QuestionGameActivity.class.getSimpleName();
    private Question question1;
    private List<Question> list = new ArrayList<>();
    private DatabaseReference mRoot;
    private ImageView imgQuestion;
    private TextView tvQuestion;
    private Button btnAnswerA;
    private Button btnAnswerB;
    private Button btnAnswerC;
    private Button btnAnswerD;
    private ImageView imgCall;
    private ImageView imgAsk;
    private ImageView img50;
    private ImageView imgChangeQuestion;
    private int myAnswer;
    private int correctAnswer;
    private LayoutInflater inflater;
    private int number = 0;
    private List<Integer> listAnswer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_game);
        mRoot = FirebaseDatabase.getInstance().getReference();
        iniUI();
        getData();
    }

    protected void iniUI() {
        inflater = getLayoutInflater();
        imgCall = findViewById(R.id.img_call);
        imgAsk = findViewById(R.id.img_ask);
        img50 = findViewById(R.id.img_50_50);
        imgChangeQuestion = findViewById(R.id.img_change_question);
        imgQuestion = findViewById(R.id.img_question);
        tvQuestion = findViewById(R.id.tv_question);
        btnAnswerA = findViewById(R.id.btn_answerA);
        btnAnswerB = findViewById(R.id.btn_answerB);
        btnAnswerC = findViewById(R.id.btn_answerC);
        btnAnswerD = findViewById(R.id.btn_answerD);
        listAnswer.add(1);
        listAnswer.add(2);
        listAnswer.add(3);
        listAnswer.add(4);
        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
        imgCall.setOnClickListener(this);
        imgAsk.setOnClickListener(this);
        img50.setOnClickListener(this);
        imgChangeQuestion.setOnClickListener(this);
    }

    private void getData() {
        setVisibaleAnswer();
        mRoot.child("CHECK_QUESTION").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    list.add(question);
                }
                if (number < list.size()) {
                    question1 = list.get(number);
                    if (question1.getUrl() != null) {
                        imgQuestion.setVisibility(View.VISIBLE);
                        Glide.with(QuestionGameActivity.this).load(question1.getUrl()).into(imgQuestion);
                    } else {
                        imgQuestion.setVisibility(View.GONE);
                    }
                    tvQuestion.setText(question1.getQuestion());
                    btnAnswerA.setText("A. " + question1.getAnwserA());
                    btnAnswerB.setText("B. " + question1.getAnwserB());
                    btnAnswerC.setText("C. " + question1.getAnwserC());
                    btnAnswerD.setText("D. " + question1.getAnwserD());
                    correctAnswer = question1.getAnwserCorrect();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(QuestionGameActivity.this)
                            .setMessage(getString(R.string.complete_game))
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        setDisableButton();
        switch (view.getId()) {
            case R.id.btn_answerA:
                myAnswer = 1;
                checkAnswer();
                break;
            case R.id.btn_answerB:
                myAnswer = 2;
                checkAnswer();
                break;
            case R.id.btn_answerC:
                myAnswer = 3;
                checkAnswer();
                break;
            case R.id.btn_answerD:
                myAnswer = 4;
                checkAnswer();
                break;
            case R.id.img_call:
                callForDoremon();
                break;
            case R.id.img_ask:
                GraphDialog dialog = new GraphDialog(this);
                dialog.show();
                break;
            case R.id.img_50_50:
                suggest50(correctAnswer);
                break;
        }
    }

    private void checkAnswer() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                animationCorrectAnwser();
            }

            @Override
            public void onFinish() {
                if (myAnswer == correctAnswer) {
                    ToastUltis.showCustomToast(inflater, QuestionGameActivity.this, true, 500);
                    number++;
                    getData();
                    setEnableButton();
                } else {
                    ToastUltis.showCustomToast(inflater, QuestionGameActivity.this, false, 500);
                }

            }
        }.start();

    }

    private void animationCorrectAnwser() {
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(3); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        switch (correctAnswer) {
            case 1:
                btnAnswerA.startAnimation(animation);
                break;
            case 2:
                btnAnswerB.startAnimation(animation);
                break;
            case 3:
                btnAnswerC.startAnimation(animation);
                break;
            case 4:
                btnAnswerD.startAnimation(animation);
                break;
        }
    }

    private void setDisableButton() {
        btnAnswerA.setEnabled(false);
        btnAnswerB.setEnabled(false);
        btnAnswerC.setEnabled(false);
        btnAnswerD.setEnabled(false);
    }

    private void setEnableButton() {
        btnAnswerA.setEnabled(true);
        btnAnswerB.setEnabled(true);
        btnAnswerC.setEnabled(true);
        btnAnswerD.setEnabled(true);
    }

    private void callForDoremon() {
        Random r = new Random();
        float ratio = r.nextFloat();
        if (ratio < 0.70f) {
            ToastUltis.showCustomToast(getLayoutInflater(), this, true, 500);
        } else {
            ToastUltis.showCustomToast(getLayoutInflater(), this, false, 500);
        }
    }

    private void suggest50(int correct) {
        setEnableButton();
        if (correct == 1) {
            Random random = new Random();
            int first = random.nextInt(3) + 2;
            setInvisiableAnswer(first);
            int second = Utils.RandomNumberRange(4, 2, first);
            setInvisiableAnswer(second);
            Log.d(TAG, "suggest50: " + first + "\n" + second);
        } else if (correct == 4) {
            Random r = new Random();
            int first = r.nextInt(2) + 1;
            setInvisiableAnswer(first);
            int second = Utils.RandomNumberRange(3, 1, first);
            setInvisiableAnswer(second);
        } else if (correct == 2) {
            int first = Utils.RandomNumberRange(1, 4, 2);
            setInvisiableAnswer(first);
            int second;
            do {
                second = Utils.RandomNumberRange(1, 4, 2);
                Log.d(TAG, "suggest50: " + second);
            } while (second == first);
            setInvisiableAnswer(second);
        } else {
            int first = Utils.RandomNumberRange(1, 4, 3);
            setInvisiableAnswer(first);
            int second;
            do {
                second = Utils.RandomNumberRange(1, 4, 3);
                Log.d(TAG, "suggest50: " + second);
            } while (second == first);
            setInvisiableAnswer(second);
        }
        img50.setVisibility(View.INVISIBLE);
    }

    private void setInvisiableAnswer(int number) {
        switch (number) {
            case 1:
                btnAnswerA.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btnAnswerB.setVisibility(View.INVISIBLE);
                break;
            case 3:
                btnAnswerC.setVisibility(View.INVISIBLE);
                break;
            case 4:
                btnAnswerD.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void setVisibaleAnswer() {
        btnAnswerA.setVisibility(View.VISIBLE);
        btnAnswerB.setVisibility(View.VISIBLE);
        btnAnswerC.setVisibility(View.VISIBLE);
        btnAnswerD.setVisibility(View.VISIBLE);
    }
}
