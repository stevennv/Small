package steven.small.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;

import java.io.File;

import steven.small.R;
import steven.small.model.UrlImage;
import steven.small.utils.BasicImageDownloader;
import steven.small.utils.Utils;

public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgToolbar;
    private ImageView imgDetail;
    private TextView tvTitleToolbar;
    private static final String TAG = ImageDetailActivity.class.getSimpleName();
    UrlImage animalItem;
    String imageTransitionName;
    String imageUrl;
    private File myImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        iniUI();
    }

    private void iniUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgToolbar = findViewById(R.id.img_menu_toolbar);
        imgToolbar.setImageResource(R.mipmap.ic_download);
        imgDetail = findViewById(R.id.ivProfile);
        tvTitleToolbar = findViewById(R.id.tv_title_toolbar);
//        supportPostponeEnterTransition();

        if (getIntent() != null) {
            animalItem = (UrlImage) getIntent().getSerializableExtra(AlbumActivity.EXTRA_ANIMAL_ITEM);
            imageTransitionName = getIntent().getStringExtra(AlbumActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
        }
        imageUrl = animalItem.getUrl();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgDetail.setTransitionName(imageTransitionName);
        }
        Log.d(TAG, "onCreate: " + imageUrl);
        Glide.with(this)
                .load(imageUrl)
                .into(imgDetail);
        imgToolbar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_menu_toolbar:
                AltexImageDownloader.writeToDisk(this, imageUrl, "Small");
                final AltexImageDownloader download = new AltexImageDownloader(new AltexImageDownloader.OnImageLoaderListener() {
                    @Override
                    public void onError(AltexImageDownloader.ImageError error) {

                    }

                    @Override
                    public void onProgressChange(int percent) {
                        tvTitleToolbar.setText(percent + "%");
                    }

                    @Override
                    public void onComplete(Bitmap result) {
                        tvTitleToolbar.setText("Download Done!!!");
                    }
                });
//                downloadImage(imageUrl, imageUrl);
                break;
        }
    }

    private void downloadImage(final String name, String urlImage) {
//        progressBar.setVisibility(View.VISIBLE);
//        handler = new DatabaseHandler(ShowPhotoActivity.this);
        final BasicImageDownloader downloader = new BasicImageDownloader(new BasicImageDownloader.OnImageLoaderListener() {
            @Override
            public void onError(BasicImageDownloader.ImageError error) {
                Toast.makeText(ImageDetailActivity.this, "Error code " + error.getErrorCode() + ": " +
                        error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.d("LOG_ERROR", error.getMessage());
            }

            @Override
            public void onProgressChange(int percent) {
            }


            @Override
            public void onComplete(Bitmap result) {
                        /* save the image - I'm gonna use JPEG */
                final Bitmap.CompressFormat mFormat = Bitmap.CompressFormat.PNG;
                        /* don't forget to include the extension into the file name */
                myImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        File.separator + "ApiFb" + File.separator + name + "." + mFormat.name().toLowerCase());


                BasicImageDownloader.writeToDisk(myImageFile, result, new BasicImageDownloader.OnBitmapSaveListener() {
                    @Override
                    public void onBitmapSaved() {
                        Toast.makeText(ImageDetailActivity.this, "Image saved as: " + myImageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onBitmapSaveError(BasicImageDownloader.ImageError error) {
                        Toast.makeText(ImageDetailActivity.this, "Error code " + error.getErrorCode() + ": " +
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }


                }, mFormat, false);
                Log.d("SIZE_LOG", String.valueOf(myImageFile.getAbsolutePath()) + "\n" + myImageFile.toString());
            }
        });
        downloader.download(urlImage, true);


    }
}
