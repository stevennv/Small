package steven.small.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import steven.small.R;
import steven.small.activity.TrueFalseGameActivity;
import steven.small.model.Notification;
import steven.small.utils.Common;
import steven.small.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 10/23/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Notification> list;
    private Context context;
    private DatabaseReference mRoot;
    private SharedPreferencesUtils utils;

    public NotificationAdapter(Context context, List<Notification> list, DatabaseReference mRoot) {
        this.context = context;
        this.list = list;
        this.mRoot = mRoot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Notification userInfo = list.get(position);
        utils = new SharedPreferencesUtils(context);
        Glide.with(context).load(userInfo.getAvatar()).into(myViewHolder.civAvatar);
        final String content = "<font color= 'blue'>" + userInfo.getName() + "</font> đang mời bạn chơi!!!";
        myViewHolder.tvName.setText(Html.fromHtml(content), TextView.BufferType.SPANNABLE);
//        myViewHolder.tvName.setText(userInfo.getName());
        if (userInfo.isCheck()) {
            myViewHolder.tvStatus.setText("Đang đợi đối thủ chơi");
        }
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setCheck(false);
//                }
//                userInfo.setCheck(true);
//                notifyDataSetChanged();
                Log.d("31212123123", "onClick: " + userInfo.getKey());
                if (!userInfo.isCheck()) {
                    mRoot.child(userInfo.getKey()).child("Accept").setValue(true);
                    mRoot.child(userInfo.getKey()).child("isSelect").setValue(true);
                    Intent intent = new Intent(context, TrueFalseGameActivity.class);

                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Đang đợi đối thủ chơi!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        utils.saveKeyRival(userInfo.getKey());
//        if (userInfo.isCheck()) {
//            myViewHolder.llItem.setBackgroundColor(Color.GRAY);
//        } else {
//            myViewHolder.llItem.setBackgroundColor(Color.WHITE);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView civAvatar;
        public TextView tvName;
        public TextView tvStatus;
        public LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            civAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_notification);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item_noti);
        }
    }
}
