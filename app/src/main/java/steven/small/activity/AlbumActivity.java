package steven.small.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import steven.small.R;
import steven.small.adapter.ImageAdapter;
import steven.small.customview.SpacesItemDecoration;
import steven.small.model.UrlImage;

public class AlbumActivity extends AppCompatActivity  {
    private static final int SELECT_PICTURE = 1;
    String selectedImagePath;
    private Button btnCamera;
    private RecyclerView rvImage;
    private DatabaseReference mRoot;
    private RecyclerView.LayoutManager layoutManager;
    private ImageAdapter adapter;
    private List<UrlImage> list = new ArrayList<>();
    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        iniUI();
        getData();
    }

    protected void iniUI() {
        mRoot = FirebaseDatabase.getInstance().getReference();
        btnCamera = findViewById(R.id.btn_camera);
        rvImage = findViewById(R.id.rv_sticker);
        layoutManager = new GridLayoutManager(this, 2);
        rvImage.setLayoutManager(layoutManager);
        rvImage.addItemDecoration(new SpacesItemDecoration(10));
//        rvImage.setHasFixedSize(true);
    }

    private void getData() {
        mRoot.child("IMAGE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UrlImage url = snapshot.getValue(UrlImage.class);
                    list.add(url);
                }
                adapter = new ImageAdapter(AlbumActivity.this, list, new ImageAdapter.AnimalItemClickListener() {
                    @Override
                    public void onAnimalItemClick(int pos, UrlImage animalItem, ImageView shareImageView) {
                        Intent intent = new Intent(AlbumActivity.this, ImageDetailActivity.class);
                        intent.putExtra(EXTRA_ANIMAL_ITEM, animalItem);
                        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                AlbumActivity.this,
                                shareImageView,
                                ViewCompat.getTransitionName(shareImageView));

                        startActivity(intent, options.toBundle());
                    }
                });
                adapter.notifyDataSetChanged();
                rvImage.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

//    private void actionCamera() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,
//                "Select Picture"), SELECT_PICTURE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//            }
//        }
//    }
//
//    public String getPath(Uri uri) {
//        // just some safety built in
//        if (uri == null) {
//            // TODO perform some logging or show user feedback
//            return null;
//        }
//        // try to retrieve the image from the media store first
//        // this will only work for images selected from gallery
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if (cursor != null) {
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            String path = cursor.getString(column_index);
//            cursor.close();
//            return path;
//        }
//        // this is our fallback here
//        return uri.getPath();
//    }




