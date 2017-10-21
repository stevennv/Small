package steven.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import steven.small.R;

/**
 * Created by Admin on 10/21/2017.
 */

public class GuideDialog extends Dialog {
    private TextView tvContent;
    private Button btnOk;
    private Context context;
    public onClick onClick;

    public GuideDialog(@NonNull Context context, onClick onClick) {
        super(context);
        this.context = context;
        this.onClick = onClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide);
        iniUI();
    }

    protected void iniUI() {
        tvContent = (TextView) findViewById(R.id.tv_guide);
        btnOk = (Button) findViewById(R.id.btn_ok);
        InputStream input;
        AssetManager assetManager = context.getAssets();
        try {
            input = assetManager.open("guide_play.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            // byte buffer into a string
            String text = new String(buffer);
            tvContent.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onClick.confirm();
            }
        });

    }

    public interface onClick {
        void confirm();
    }
}
