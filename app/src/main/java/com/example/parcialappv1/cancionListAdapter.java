package com.example.parcialappv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcialappv1.data.cancion;

import java.util.ArrayList;

public class cancionListAdapter extends RecyclerView.Adapter<cancionListAdapter.cancionViewHolder> {
    public final ArrayList<cancion> cCancion;
    private LayoutInflater mInflater;

    public cancionListAdapter(Context context, ArrayList<cancion> ccancion) {
        this.cCancion = ccancion;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public cancionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.from(parent.getContext()).inflate(R.layout.activity_musicitem, null);
        return new cancionViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(cancionViewHolder holder, int position) {
        cancion mCurrent = cCancion.get(position);
        holder.cancion.setText(mCurrent.cancion + "-"+ mCurrent.artista+ "-" + mCurrent.duracion);
    }

    @Override
    public int getItemCount() {
        return cCancion.size();
    }

    class cancionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView cancion;
        private cancionListAdapter adapter;

        public cancionViewHolder(@NonNull View itemView, cancionListAdapter adapter) {
            super(itemView);
            cancion = itemView.findViewById(R.id.music_item);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
