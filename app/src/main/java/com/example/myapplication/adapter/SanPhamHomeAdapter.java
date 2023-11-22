package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SanPhamHomeAdapter extends RecyclerView.Adapter<SanPhamHomeAdapter.ViewHolder>{

    private Context context;
    private ArrayList<SanPham> list;

    public SanPhamHomeAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String giaviet = numberFormat.format(list.get(position).getGiasp());
        holder.tvTenSP.setText(list.get(position).getTensp());

        holder.tvGiaSP.setText(giaviet + " đ");

        Uri imageUri = Uri.parse(list.get(position).getAnh());
        holder.imageHome.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSP,tvGiaSP;
        ImageView imageHome;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHome = itemView.findViewById(R.id.imageHome);
            tvTenSP =itemView.findViewById(R.id.tvTenspHome);
            tvGiaSP =itemView.findViewById(R.id.tvGiaHome);
        }
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int spacing;
        private int spanCount;

        public SpaceItemDecoration(int spacing, int spanCount) {
            this.spacing = spacing;
            this.spanCount = spanCount;
        }

    }
}
