package steven.small.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import steven.small.R;
import steven.small.activity.AlbumActivity;
import steven.small.model.UrlImage;

/**
 * Created by Admin on 10/26/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UrlImage> list;
    private Context context;
    private final AnimalItemClickListener animalItemClickListener;

    public ImageAdapter(Context context, List<UrlImage> list, AnimalItemClickListener animalItemClickListener) {
        this.context = context;
        this.list = list;
        this.animalItemClickListener = animalItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        Glide.with(context).load(list.get(position).getUrl()).placeholder(R.mipmap.five_fingers_blue).into(myViewHolder.imgThumb);
        ViewCompat.setTransitionName(myViewHolder.imgThumb, list.get(position).getUrl());
        myViewHolder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animalItemClickListener.onAnimalItemClick(holder.getAdapterPosition(), list.get(position), myViewHolder.imgThumb);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumb;
        public CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.img_thumb);
            cv = itemView.findViewById(R.id.card_view);
        }
    }

    public interface AnimalItemClickListener {
        void onAnimalItemClick(int pos, UrlImage animalItem, ImageView shareImageView);
    }
}
