package steven.small.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import steven.small.R;
import steven.small.adapter.InviteAdapter;
import steven.small.model.Invite;
import steven.small.model.UserInfo;
import steven.small.utils.SharedPreferencesUtils;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnInvite;
    private RecyclerView rvInvite;
    private InviteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mRoot;
    private List<Invite> list = new ArrayList<>();
    private Invite invited;
    private UserInfo userInfo;
    private SharedPreferencesUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        iniUI();
        getData();
    }

    protected void iniUI() {
        utils = new SharedPreferencesUtils(this);
        mRoot = FirebaseDatabase.getInstance()
                .getReference();
        userInfo = utils.getUserInfo();
        rvInvite = (RecyclerView) findViewById(R.id.rv_invite);
        btnInvite = (Button) findViewById(R.id.btn_invite);
        layoutManager = new LinearLayoutManager(this);
        rvInvite.setLayoutManager(layoutManager);
        btnInvite.setOnClickListener(this);

    }

    private void getData() {
        mRoot.child("HOME").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Invite invite = snapshot.getValue(Invite.class);
                    list.add(invite);
                }
                adapter = new InviteAdapter(InviteActivity.this, list, new InviteAdapter.onClick() {
                    @Override
                    public void clickConfirm(int postion) {
                        invited = list.get(postion);
                        Log.d("clickConfirm", "clickConfirm: " + invited.getId());

                    }
                });
                adapter.notifyDataSetChanged();
                rvInvite.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite:
                inviteRival(userInfo);
                break;
        }
    }

    private void inviteRival(final UserInfo userInfo) {
        String key = mRoot.child("posts").push().getKey();
        mRoot.child("HOME").child("HOME_" + invited.getId()).child("Invite").child(key).setValue(userInfo);
        mRoot.child("HOME").child("HOME_" + invited.getId()).child("Invite").child(key).child("Accept").setValue(false);
        mRoot.child("HOME").child("HOME_" + invited.getId()).child("Invite").child(key).child("key").setValue(key);
        mRoot.child("HOME").child("HOME_" + invited.getId()).child("Invite").child(key).child("isSelect").setValue(false);
        utils.saveRoomRival(invited.getId());
        utils.saveKeyRival(key);
        finish();
    }
}
