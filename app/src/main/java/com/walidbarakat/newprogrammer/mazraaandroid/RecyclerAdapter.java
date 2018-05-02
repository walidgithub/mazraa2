package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by PC on 15/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<RecyclerModel> RecyclerModel;
    private Context context;

    public RecyclerAdapter(List<RecyclerModel> recyclermodel, Context context) {
        RecyclerModel = recyclermodel;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {

        holder.TextName.setText(RecyclerModel.get(position).getName());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(RecyclerModel.get(position).getImg());
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        holder.imageView.setImageBitmap(bitmap);


        holder.imageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Gallery.deletedisc = RecyclerModel.get(position).getName();
                //Gallery.shareimage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(RecyclerModel.get(position).getImg());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Gallery.shareimage = bitmap;
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != RecyclerModel ? RecyclerModel.size() : 0);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView TextName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            TextName = (TextView) view.findViewById(R.id.TextName);
        }
    }


}
