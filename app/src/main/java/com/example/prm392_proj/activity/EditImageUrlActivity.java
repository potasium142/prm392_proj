package com.example.prm392_proj.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.s3.S3ClientProvider;
import com.example.prm392_proj.util.RandomAlphaDigit;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;

public class EditImageUrlActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_PICK = 1001;
    ImageView foodImage;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image_url);

        imageURL = getIntent().getStringExtra("imageUrl");

        foodImage = findViewById(R.id.imageView);
        Picasso.get().load(imageURL).into(foodImage);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", imageURL);
            setResult(RESULT_OK, intent);
            finish();
        });

        Button chooseImageButton = findViewById(R.id.chooseImageButton);
        chooseImageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("image/*");
            startActivityForResult(intent1, REQUEST_IMAGE_PICK);
        });

        Button enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(v -> {
            TextInputEditText inputText = findViewById(R.id.inputText);
            String tmpURL = inputText.getText().toString();
            if (tmpURL.isEmpty()) {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            } else {
                imageURL = inputText.getText().toString();
                Picasso.get().load(imageURL).into(foodImage);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();

                String extension;

                //Check uri format to avoid null
                if (imageUri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                    //If scheme is a content
                    final MimeTypeMap mime = MimeTypeMap.getSingleton();
                    extension = mime.getExtensionFromMimeType(this.getContentResolver()
                            .getType(imageUri));
                } else {
                    //If scheme is a File
                    //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
                    extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(imageUri.getPath()))
                            .toString());

                }

                String key = RandomAlphaDigit.generateRandomAlphaDigit(10);
                key = key + "." + extension;
                S3ClientProvider.uploadImageToSpaceInBackground(this,
                        imageUri,
                        "",
                        key,
                        new S3ClientProvider.UploadCallback() {
                            @Override
                            public void onSuccess(String imageUrl) {
                                Handler handler = new Handler(Looper.getMainLooper());

// Inside your background thread
                                handler.post(() -> {
                                    Picasso.get().load(imageUrl).into(foodImage);
                                    imageURL = imageUrl;
                                });

                            }

                            @Override
                            public void onFailure(Exception e) {
                                Handler handler = new Handler(Looper.getMainLooper());

// Inside your background thread
                                handler.post(() -> {
                                    Toast.makeText(EditImageUrlActivity.this,
                                            "Failed to upload image",
                                            Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
            }
        }
    }
}