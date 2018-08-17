package com.example.android.recyclerview.adepter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerview.R;
import com.example.android.recyclerview.model.ResponseModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProjectListAdepter extends RecyclerView.Adapter<ProjectListAdepter.ViewHolder> {
    private List<ResponseModel.DataBean> dataBeans;
    private Context context;
    private int count = 1;

    public ProjectListAdepter(List<ResponseModel.DataBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectlistrow, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtProjectName.setText(dataBeans.get(position).getQuotes_name() + "");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap myImage = getBitmapFromURL("http://rajviinfotech.in/quotes/public/uploads/" + dataBeans.get(position).getQuotes_image());
        Drawable dr = new BitmapDrawable(myImage);
        holder.imageView.setBackgroundDrawable(dr);
    }

    private Bitmap getBitmapFromURL(String s) {
        try {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProjectName, txtprojectDate;
        public Button mAdd, mView;
        RelativeLayout imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProjectName = (TextView) itemView.findViewById(R.id.txtProjectName);
            imageView = (RelativeLayout) itemView.findViewById(R.id.imgBackGround);

        }
    }
}
