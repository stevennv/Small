package steven.small.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.LikeView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import steven.small.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGame;
    private Button btnReflex;
    private LikeView likeView;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
//        getKeyHash();
        iniUI();
    }

    protected void iniUI() {
        btnGame = (Button) findViewById(R.id.btn_game);
        btnReflex = (Button) findViewById(R.id.btn_game_reflex);
        likeView = (LikeView) findViewById(R.id.like_view);
//        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
//        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        String pageUrlToLike = "https://www.facebook.com/FacebookDevelopers";
//        likeView.setObjectIdAndType(pageUrlToLike, LikeView.ObjectType.PAGE);
        likeView.setObjectIdAndType("19292868552", LikeView.ObjectType.PAGE);

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

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "steven.small",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
