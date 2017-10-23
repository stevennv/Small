package steven.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import steven.small.R;
import steven.small.adapter.NotificationAdapter;
import steven.small.model.Invite;
import steven.small.model.Notification;
import steven.small.model.UserInfo;
import steven.small.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 10/23/2017.
 */

public class NotificationDialog extends Dialog implements View.OnClickListener {
    private ImageView imgClose;
    private RecyclerView rvNoti;
    private NotificationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mRoot;
    private DatabaseReference refInvite;
    private SharedPreferencesUtils utils;
    private UserInfo userInfo;
    private List<Notification> list = new ArrayList<>();

    public NotificationDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notification);
        iniUI();
        getData();
    }

    protected void iniUI() {
        utils = new SharedPreferencesUtils(getContext());
        userInfo = utils.getUserInfo();
        mRoot = FirebaseDatabase.getInstance().getReference();
        refInvite = mRoot.child("HOME").child("HOME_" + userInfo.getId()).child("Invite");
        rvNoti = (RecyclerView) findViewById(R.id.rv_noti);
        imgClose = (ImageView) findViewById(R.id.img_close);
        layoutManager = new LinearLayoutManager(getContext());
        rvNoti.setLayoutManager(layoutManager);
        imgClose.setOnClickListener(this);
    }

    void getData() {
        mRoot.child("HOME").child("HOME_" + userInfo.getId()).child("Invite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notification = snapshot.getValue(Notification.class);
                    list.add(notification);
                }
                adapter = new NotificationAdapter(getContext(), list, refInvite);
                adapter.notifyDataSetChanged();
                rvNoti.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
