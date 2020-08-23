package com.mvpgrid.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mvpgrid.R;
import com.mvpgrid.model.GridData;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.MyViewHolder> {
    private java.util.List<GridData> List;
    private View.OnClickListener onclick;
    private Context context;

    public ImageGridAdapter(Context context, java.util.List<GridData> List, View.OnClickListener onclick) {
        this.context = context;
        this.List = List;
        this.onclick = onclick;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGrid;

        MyViewHolder(View view) {
            super(view);
            ivGrid = view.findViewById(R.id.ivGrid);
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        GridData item = List.get(position);
        try {
            Picasso.with(context).load(item.getImages().get(0).getLink()).into(holder.ivGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.ivGrid.setTag(item);
        holder.ivGrid.setOnClickListener(onclick);

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

}