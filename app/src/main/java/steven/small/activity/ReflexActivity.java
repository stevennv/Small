package steven.small.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import steven.small.R;
import steven.small.model.OTT;

public class ReflexActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgQuestion;
    private ImageView imgAnswer1;
    private ImageView imgAnswer2;
    private RelativeLayout rlAnswer1;
    private RelativeLayout rlAnswer2;
    private List<OTT> list = new ArrayList<>();
    private OTT ott;
    private int question;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex);
        iniUI();
    }

    protected void iniUI() {
        imgQuestion = (ImageView) findViewById(R.id.img_question);
        imgAnswer1 = (ImageView) findViewById(R.id.btn_answer1);
        imgAnswer2 = (ImageView) findViewById(R.id.btn_answer2);
        rlAnswer1 = (RelativeLayout) findViewById(R.id.rl_answer1);
        rlAnswer2 = (RelativeLayout) findViewById(R.id.rl_answer2);
        addQuestion();
        rlAnswer1.setOnClickListener(this);
        rlAnswer2.setOnClickListener(this);
    }

    private void addQuestion() {
        list.add(new OTT(1, R.mipmap.zero_finger_blue, R.mipmap.two_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(2, R.mipmap.zero_finger_red, R.mipmap.two_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.two_finger_blue, R.mipmap.zero_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(2, R.mipmap.two_finger_red, R.mipmap.zero_finger_yellow, R.mipmap.five_fingers_yellow));
        list.add(new OTT(1, R.mipmap.five_fingers_blue, R.mipmap.zero_finger_yellow, R.mipmap.two_finger_yellow));
        list.add(new OTT(2, R.mipmap.five_fingers_red, R.mipmap.zero_finger_yellow, R.mipmap.two_finger_yellow));
        Random random = new Random();
        question = random.nextInt(5);
        ott = list.get(question);
        imgQuestion.setImageResource(ott.getImage1());
        imgAnswer1.setImageResource(ott.getImage2());
        imgAnswer2.setImageResource(ott.getImage3());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_answer1:
                if (question == 0 || question == 3 || question == 4) {
                    endGame();
                } else {
                    score++;
                    addQuestion();
                }
                break;
            case R.id.rl_answer2:
                if (question == 0 || question == 3 || question == 4) {
                    score++;
                    addQuestion();
                } else {
                    endGame();
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
    }
}
