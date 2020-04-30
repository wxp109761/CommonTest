package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bean.Video;
import com.example.CommonTest.R;
import com.example.utils.FileManager;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
View rootView;
    private List<Video> videoList;
    Context context;
    private ClickInterface listener;

    public VideoAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context=context;
    }
    public void setOnclick(ClickInterface clickInterface) {
        this.listener = clickInterface;
    }
        //回调接口
    public interface ClickInterface {
        void onItemClick( int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        Video video= videoList.get(position);
        holder.name.setText(video.getName());
        holder.path.setText(video.getPath());
        holder.resolution.setText(video.getResolution());
        Bitmap bitmap=FileManager.getVideoBitmap(video.getPath());
        if(bitmap!=null){
            holder.imageView.setImageBitmap(bitmap);
        }
       //

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
       int id;
       TextView path;
       TextView name ;
       TextView resolution;// 分辨率
        ImageView imageView;
       long size;
       long date;
       long duration;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            path=itemView.findViewById(R.id.tv_path);
            name=itemView.findViewById(R.id.tv_name);
            resolution=itemView.findViewById(R.id.tv_fenbian);
            imageView=itemView.findViewById(R.id.video_img);
        }
    }
}
