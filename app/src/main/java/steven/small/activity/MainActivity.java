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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.widget.LikeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import steven.small.R;
import steven.small.dialog.NotificationDialog;
import steven.small.model.UserInfo;
import steven.small.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnGame;
    private Button btnReflex;
    private Button btnInvite;
    private Button btnLogout;
    private LikeView likeView;
    private ImageView ibLoginFb;
    private LinearLayout llLogin;
    private CallbackManager callbackManager;
    private SharedPreferencesUtils utils;
    private CircleImageView civAvatar;
    private TextView tvName;
    private DatabaseReference mRoot;
    private RelativeLayout rlNoti;
    private TextView tvNumberNoti;
    private int numberNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
//        getKeyHash();
        iniUI();
        updateUI();
    }

    protected void iniUI() {
        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(this);
        btnGame = (Button) findViewById(R.id.btn_game);
        btnReflex = (Button) findViewById(R.id.btn_game_reflex);
        btnInvite = (Button) findViewById(R.id.btn_invite);
        btnLogout = (Button) findViewById(R.id.btn_log_out);
        tvNumberNoti = (TextView) findViewById(R.id.tv_number_noti);
        civAvatar = (CircleImageView) findViewById(R.id.civ_avatar);
        ibLoginFb = (ImageView) findViewById(R.id.ib_login_fb);
        rlNoti = (RelativeLayout) findViewById(R.id.rl_noti);
        tvName = (TextView) findViewById(R.id.tv_name);
        llLogin = (LinearLayout) findViewById(R.id.ll_login);
        ibLoginFb.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnReflex.setOnClickListener(this);
        btnInvite.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        rlNoti.setOnClickListener(this);
        if (AccessToken.getCurrentAccessToken() != null) {
            listenNotifi();
            if (utils.getRoomRival() != null || !utils.getRoomRival().equals("")) {
                listenAccept();
            }
        }
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
            case R.id.ib_login_fb:
//                if (AccessToken.getCurrentAccessToken() != null) {
                loginfb();
//                } else {
//                    Toast.makeText(MainActivity.this, "LOGIN123", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.btn_invite:
                Intent intent2 = new Intent(this, InviteActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_log_out:
                logOut();
                break;
            case R.id.rl_noti:
                if (numberNoti != 0) {
                    NotificationDialog dialog = new NotificationDialog(this);
                    dialog.show();
                }
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

    private void loginfb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "LOGIN", Toast.LENGTH_SHORT).show();

                Profile profile = Profile.getCurrentProfile();
                String name = profile.getName();
                String id = profile.getId();
                String avatar = "http://graph.facebook.com/"
                        + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                UserInfo userInfo = new UserInfo(name, id, avatar);
                utils.saveUserInfo(userInfo);
                Glide.with(MainActivity.this).load(avatar).into(civAvatar);
                tvName.setText(name);

                mRoot.child("HOME").child("HOME_" + id).setValue(userInfo);
                mRoot.child("HOME").child("HOME_" + id).child("IS_ONLINE").setValue(true);
                llLogin.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("123", "onError: " + error.getLocalizedMessage());
            }
        });
    }

    private void updateUI() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Glide.with(MainActivity.this).load(utils.getUserInfo().getAvatar()).into(civAvatar);
            tvName.setText(utils.getUserInfo().getName());
            llLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }
    }

    private void logOut() {
        LoginManager.getInstance().logOut();
        llLogin.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.GONE);
    }

    private void listenNotifi() {
        mRoot.child("HOME").child("HOME_" + utils.getUserInfo().getId()).child("Invite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long numberNoti = dataSnapshot.getChildrenCount();
                Log.d(TAG, "onDataChange: " + numberNoti);
                if (numberNoti == 0) {
                    tvNumberNoti.setVisibility(View.GONE);
                } else {
                    tvNumberNoti.setVisibility(View.VISIBLE);
                    tvNumberNoti.setText(numberNoti + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void listenAccept() {
        mRoot.child("HOME").child("HOME_" + utils.getRoomRival()).child("Invite")
                .child(utils.getKeyRival()).child("Accept").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    boolean check = dataSnapshot.getValue(Boolean.class);
                    if (check == true) {
                        Intent intent = new Intent(MainActivity.this, TrueFalseGameActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        utils.saveKeyRival("");
        utils.saveRoomRival("");
    }

}
