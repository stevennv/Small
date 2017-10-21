package steven.small.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import steven.small.R;

/**
 * Created by TruongNV on 10/5/2017.
 */

public class CoutTime extends CountDownTimer {
    private ProgressBar progressBar;
    private long millisInFuture;
    private int isEnd;
    private Context context;
    private checkFinish checkFinish;

    public CoutTime(long millisInFuture, long countDownInterval, ProgressBar progressBar, int isEnd, checkFinish checkFinish) {
        super(millisInFuture, countDownInterval);
        this.progressBar = progressBar;
        this.millisInFuture = millisInFuture;
        this.isEnd = isEnd;
        this.checkFinish = checkFinish;
    }

    @Override
    public void onTick(long l) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress((int) (l * 100 / millisInFuture));
        isEnd = 1;
    }

    @Override
    public void onFinish() {
        progressBar.setProgress(0);
        isEnd = 2;
        checkFinish.finished();
    }

    public interface checkFinish {
        void finished();
    }
}
