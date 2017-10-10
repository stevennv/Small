package steven.small.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import steven.small.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGame;
    private Button btnReflex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
    }

    protected void iniUI() {
        btnGame = (Button) findViewById(R.id.btn_game);
        btnReflex = (Button) findViewById(R.id.btn_game_reflex);
        btnGame.setOnClickListener(this);
        btnReflex.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_game:
                Intent intent = new Intent(this, TrueFalseGameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_game_reflex:
                Intent intent1 = new Intent(this, ReflexActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
