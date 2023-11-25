package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Hang;

import java.util.ArrayList;

public class HangAdapter extends RecyclerView.Adapter<HangAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Hang> list;
    public String tenHang;
    public HangAdapter(Context context, ArrayList<Hang> list) {
        this.context = context;
        this.list = list;
    }
    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position, int maHang);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnHang.setText(list.get(position).getTenHang());
        int mahang = list.get(position).getMahang();
        holder.btnHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        buttonClickListener.onButtonClick(position, mahang);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnHang = itemView.findViewById(R.id.btnHang);
        }
    }
}
