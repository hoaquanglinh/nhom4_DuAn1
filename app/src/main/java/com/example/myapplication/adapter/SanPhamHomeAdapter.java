package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.ThongTinChiTiet1Fragment;
import com.example.myapplication.fragment.ThongTinChiTietFragment;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SanPhamHomeAdapter extends RecyclerView.Adapter<SanPhamHomeAdapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList<SanPham> list;

    public SanPhamHomeAdapter(Context context, Activity activity, ArrayList<SanPham> list) {
        this.context = context;
        this.activity = activity;
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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChiTiet(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSP,tvGiaSP;
        ImageView imageHome;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear);
            imageHome = itemView.findViewById(R.id.imageHome);
            tvTenSP =itemView.findViewById(R.id.tvTenspHome);
            tvGiaSP =itemView.findViewById(R.id.tvGiaHome);
        }
    }

    private void openChiTiet(final SanPham sanPham) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPhamChiTiet", sanPham);

        ThongTinChiTiet1Fragment thongTinChiTietFragment1 = new ThongTinChiTiet1Fragment();
        thongTinChiTietFragment1.setArguments(bundle);

        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, thongTinChiTietFragment1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
