package com.example.videoplayerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayerapp.R;
import com.example.videoplayerapp.VideoSModal;

import java.util.ArrayList;

public class VideoSAdapter extends RecyclerView.Adapter<VideoSAdapter.ViewHolder> {
    private ArrayList<VideoSModal> videoSModalArrayList;
    private Context context;

    public VideoSAdapter(ArrayList<VideoSModal> videoSModalArrayList, Context context, VideoClickInterface videoClickInterface) {
        this.videoSModalArrayList = videoSModalArrayList;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    private VideoClickInterface videoClickInterface;

    @NonNull
    @Override
    public VideoSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_s_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoSAdapter.ViewHolder holder, int position) {
    VideoSModal videoSModal = videoSModalArrayList.get(position);
    holder.thumbnailIV.setImageBitmap(videoSModal.getThumbNail());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            videoClickInterface.onVideoClick(position);
        }
    });
    }

    @Override
    public int getItemCount() {
        return videoSModalArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView thumbnailIV;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.idIVThumbnail);
        }
    }
    public interface VideoClickInterface{
        void onVideoClick(int position);
    }
}