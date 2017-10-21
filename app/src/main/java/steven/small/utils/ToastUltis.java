package steven.small.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import steven.small.R;

/**
 * Created by Admin on 10/21/2017.
 */

public class ToastUltis {
    public static void showCustomToast(LayoutInflater inflater, Context context, boolean condition, int duration) {
        final Toast toast = new Toast(context);
        View layout = inflater.inflate(R.layout.custom_toast,
                null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        ImageView imageView = layout.findViewById(R.id.img_icon);
        TextView tvContent = layout.findViewById(R.id.tv_toast);
        if (condition == true) {
            imageView.setImageResource(R.mipmap.icon_true);
            tvContent.setText("Chính xác!!!");
        } else {
            imageView.setImageResource(R.mipmap.icon_false);
            tvContent.setText("Sai rồi!!!");
        }
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }
}
