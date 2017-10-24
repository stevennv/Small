package steven.small.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import steven.small.R;
import steven.small.model.Invite;
import steven.small.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 10/23/2017.
 */

public class InviteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Invite> list;
    private onClick onClick;
    private SharedPreferencesUtils utils;

    public InviteAdapter(Context context, List<Invite> list, onClick onClick) {
        this.context = context;
        this.list = list;
        this.onClick = onClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_invite, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Invite invite = list.get(position);
        utils = new SharedPreferencesUtils(context);
        Glide.with(context).load(invite.getAvatar()).into(myViewHolder.civAvatar);
        myViewHolder.tvName.setText(invite.getName());
        if (invite.getId().equals(utils.getUserInfo().getId())) {
            myViewHolder.llItem.setVisibility(View.GONE);
        }
        if (invite.isOnline()) {
            myViewHolder.imgOnline.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.imgOnline.setVisibility(View.VISIBLE);
        }
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                notifyDataSetChanged();
                onClick.clickConfirm(position);
            }
        });
        if (invite.isSelect()) {
            myViewHolder.llItem.setBackgroundColor(Color.GRAY);
        } else {
            myViewHolder.llItem.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgOnline;
        public ImageView imgOffline;
        public CircleImageView civAvatar;
        public TextView tvName;
        public LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgOnline = (ImageView) itemView.findViewById(R.id.img_online);
            imgOffline = (ImageView) itemView.findViewById(R.id.img_offline);
            civAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item_invite);
        }
    }

    public interface onClick {
        void clickConfirm(int postion);
    }
}
