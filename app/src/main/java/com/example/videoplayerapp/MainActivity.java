package com.example.videoplayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.videoplayerapp.Manifest.permission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VideoSAdapter.VideoClickInterface {
    private static String READ_EXTERNAL_STORAGE;
    private RecyclerView videoS;
    private ArrayList<VideoSModal> videoSModalArrayList;
    private VideoSAdapter videoSAdapter;
    private static final int STORAGE_PERMISSION =101;
    private Object EXTERNAL_CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoS = findViewById(R.id.idSVideos);
        videoSModalArrayList = new ArrayList<>();
        videoSAdapter = new VideoSAdapter(videoSModalArrayList,this,this::onVideoClick);
        videoS.setLayoutManager(new GridLayoutManager(this,2));
        videoS.setAdapter(videoSAdapter);
        String READ_EXTERNAL_STORAGE = null;
        if(ContextCompat.checkSelfPermission(MainActivity.this, MainActivity.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, STORAGE_PERMISSION);
        }else{
            getVideos();
        }

}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                getVideos();
            }else{
                Toast.makeText(this,"The App will not work without  permission..!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    private void getVideos(){
        ContentResolver contentResolver =getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String videoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
               Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath,MediaStore.Images.Thumbnails.MINI_KIND);
               videoSModalArrayList.add(new VideoSModal(videoTitle,videoPath,videoThumbnail));
            }while (cursor.moveToNext());
        }
        videoSAdapter.notifyDataSetChanged();
}

    @Override
    public void onVideoClick(int position) {
        Intent i = new Intent(MainActivity.this,VideoPlayerActivity.class);
        i.putExtra("videoName",videoSModalArrayList.get(position).getVideoName());
        i.putExtra("VideoPath",videoSModalArrayList.get(position).getVideoPath());
        startActivities(i);

    }

    private void startActivities(Intent i) {
    }


}