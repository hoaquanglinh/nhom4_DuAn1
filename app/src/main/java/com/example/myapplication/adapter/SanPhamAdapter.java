package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.ProductFragment;
import com.example.myapplication.fragment.ThongTinChiTietFragment;
import com.example.myapplication.fragment.UpdateFragment;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    MauSacDAO mauSacDAO;
    SanPhamDAO dao;
    private ArrayList<SanPham> list;
    TextView tvtensp, tvgiasp, tvmau, tvkhohang;
    Button btnxoa;
    ImageView imageView;
    private Activity activity;
    TaiKhoanNDDAO nddao;
    public SanPhamAdapter(@NonNull Context context, ArrayList<SanPham> list, Activity activity, SanPhamDAO dao) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.dao = dao;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_product, null);
        }

        final SanPham item = list.get(position);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        if (item != null) {
            tvtensp = v.findViewById(R.id.tvTensp);
            tvtensp.setText(item.getTensp());

            mauSacDAO = new MauSacDAO(context);
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            tvmau = v.findViewById(R.id.tvMau);
            tvmau.setText("Màu: " + mauSac.getTenMau());

            tvgiasp = v.findViewById(R.id.tvGia);
            String giaviet = numberFormat.format(item.getGiasp());
            tvgiasp.setText("Giá: " + giaviet + " đ");

            tvkhohang = v.findViewById(R.id.tvKho);
            tvkhohang.setText("Kho: " + item.getKhoHang());

            Uri imageUri = Uri.parse(item.getAnh());
            imageView = v.findViewById(R.id.imageSP);
            imageView.setImageURI(imageUri);

            btnxoa = v.findViewById(R.id.btnXoa);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openEditProductFragment(item);
                    return false;
                }
            });

            SharedPreferences pref = activity.getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String user = pref.getString("USERNAME", "");
            String pass = pref.getString("PASSWORD", "");
            nddao = new TaiKhoanNDDAO(context);
            int matknd = nddao.getMatkndFromTaikhoannd(user, pass);
            btnxoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Bạn có muốn xóa không?");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = list.get(position).getMasp();
                            dao.delete(String.valueOf(id));
                            list.clear();
                            list.addAll(dao.getAllByMAtknd(matknd));
                            notifyDataSetChanged();
                            dialog.cancel();
                            Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    builder.show();

                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   open(item);
                }
            });
        }

        return v;
    }
    private void open(final SanPham sanPham) {
        // Tạo Bundle và truyền thông tin sản phẩm vào Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPhamChiTiet", sanPham);

        // Tạo Fragment và truyền Bundle vào Fragment
        ThongTinChiTietFragment thongTinChiTietFragment = new ThongTinChiTietFragment();
        thongTinChiTietFragment.setArguments(bundle);

        // Gửi sự kiện tới FragmentActivity để thay thế Fragment hiện tại bằng Fragment chỉnh sửa
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, thongTinChiTietFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void openEditProductFragment(final SanPham sanPham) {

        // Tạo Bundle và truyền thông tin sản phẩm vào Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPham", sanPham);

        // Tạo Fragment và truyền Bundle vào Fragment
        UpdateFragment updateFragment = new UpdateFragment();
        updateFragment.setArguments(bundle);

        // Gửi sự kiện tới FragmentActivity để thay thế Fragment hiện tại bằng Fragment chỉnh sửa
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, updateFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}

