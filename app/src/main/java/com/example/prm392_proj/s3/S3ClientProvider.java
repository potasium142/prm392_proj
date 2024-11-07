package com.example.prm392_proj.s3;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class S3ClientProvider {
    private static final String ACCESS_KEY = "DO00NQGM72CZJZY9Q7QA";
    private static final String SECRET_KEY = "WNKLKUDQq1C18YjNMcWoXTsaV0h0sSaa3t77eZU8PM4";
    static String endPoint = "https://prm392.sgp1.digitaloceanspaces.com";

    public static AmazonS3Client getS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        // Initialize AmazonS3Client directly
        AmazonS3Client s3Client = new AmazonS3Client(credentials);
        s3Client.setEndpoint(endPoint);

        return s3Client;
    }

    public static String getFilePathFromUri(Context context, Uri uri) {
        String fileName = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex != -1) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
    }

    public static File copyUriToTempFile(Context context, Uri uri) throws IOException {
        String fileName = getFilePathFromUri(context, uri);
        File tempFile = new File(context.getCacheDir(), fileName);

        try (InputStream inputStream = context.getContentResolver()
                .openInputStream(uri); OutputStream outputStream = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

        return tempFile;
    }

    public static void uploadImageToSpaceInBackground(Context context, Uri imageUri, String bucketName, String keyName, UploadCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Perform the upload operation in a background thread
                uploadImageToSpace(context, imageUri, bucketName, keyName);

            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailure(e);
            }

            String imageUrl = endPoint + "/" + keyName;
            callback.onSuccess(imageUrl);
        });
    }

    public static void uploadImageToSpace(Context context, Uri imageUri, String bucketName, String keyName) {
        AmazonS3Client s3Client = S3ClientProvider.getS3Client();

        try {
            File tempFile = copyUriToTempFile(context, imageUri);

            // Create PutObjectRequest with File
            PutObjectRequest request = new PutObjectRequest(bucketName, keyName, tempFile);
            request.setCannedAcl(CannedAccessControlList.PublicRead);
            s3Client.putObject(request);

            Log.d("S3Client", "Image uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("S3Client", "Image upload failed.");
        }
    }

    public interface UploadCallback {
        void onSuccess(String imageUrl);

        void onFailure(Exception e);
    }
}
