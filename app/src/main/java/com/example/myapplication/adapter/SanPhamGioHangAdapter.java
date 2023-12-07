package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.ThongTinChiTietFragment;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SanPhamGioHangAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    MauSacDAO mauSacDAO;
    SanPhamDAO dao;
    private ArrayList<SanPham> list;
    TextView tvtensp, tvgiasp, tvmau, tvsoluong;
    ImageView imageView;
    private Activity activity;
    TaiKhoanNDDAO nddao;
    GioHangDao gioHangDao;
    double tong;
    int matknd;
    Button tang, giam;
    int ma;
    public SanPhamGioHangAdapter(@NonNull Context context, ArrayList<SanPham> list, Activity activity, SanPhamDAO dao) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.dao = dao;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(double gia);
    }

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham_giohang, null);
        }

        gioHangDao = new GioHangDao(context);

        final SanPham item = list.get(position);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        if (item != null) {
            tvtensp = v.findViewById(R.id.tvTensp1);
            tvtensp.setText(item.getTensp());

            mauSacDAO = new MauSacDAO(context);
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            tvmau = v.findViewById(R.id.tvMau1);
            tvmau.setText("Màu: " + mauSac.getTenMau());

            tvgiasp = v.findViewById(R.id.tvGia1);
            String giaviet = numberFormat.format(item.getGiasp());
            tvgiasp.setText("Giá: " + giaviet + " đ");

            Uri imageUri = Uri.parse(item.getAnh());
            imageView = v.findViewById(R.id.imageSP1);
            imageView.setImageURI(imageUri);

            tong = 0;
            for (SanPham sp : list) {
                tong += sp.getGiasp() * sp.getSoluong();
            }

            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(tong);
            }

            int id = list.get(position).getMasp();

            tang = v.findViewById(R.id.btntang1);
            giam = v.findViewById(R.id.btngiam1);

            if (ma == 2){
                tang.setEnabled(false);
                giam.setEnabled(false);
            }

            Log.d("ma", "getView: " + ma);
            tang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getSoluong() >= item.getKhoHang()) {
                        Toast.makeText(context, "Số lượng không được lớn hơn kho hàng", Toast.LENGTH_SHORT).show();
                        item.setSoluong(item.getKhoHang());
                        notifyDataSetChanged();
                    } else {
                        item.setSoluong(item.getSoluong() + 1);
                        dao.updateSL(item.getMasp(), item.getSoluong());
                        tong += item.getGiasp();
                    }
                    notifyDataSetChanged();
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(tong);
                    }
                    notifyDataSetChanged();
                }
            });
            giam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setSoluong(item.getSoluong() - 1);
                    dao.updateSL(item.getMasp(), item.getSoluong());
                    if (item.getSoluong() == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete");
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gioHangDao.delete(String.valueOf(id));
                                list.clear();
                                list.addAll(gioHangDao.getSanPhamInGioHangByMatkd(matknd));
                                notifyDataSetChanged();
                                dialog.cancel();
                                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                                item.setSoluong(1);
                                dao.updateSL(id, item.getSoluong());
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                item.setSoluong(1);
                                dao.updateSL(id, item.getSoluong());
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        builder.show();

                        notifyDataSetChanged();
                    }
                    tong -= item.getGiasp();

                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(tong);
                    }
                    notifyDataSetChanged();
                }
            });

            tvsoluong = v.findViewById(R.id.tvsoluong);
            tvsoluong.setText(String.valueOf(item.getSoluong()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open(item);
                }
            });
            SharedPreferences pref = activity.getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String user = pref.getString("USERNAME", "");
            nddao = new TaiKhoanNDDAO(context);
            matknd = nddao.getMatkndFromTaikhoannd(user);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int id = list.get(position).getMasp();
                    xoa(id);
                    notifyDataSetChanged();
                    return false;
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return v;
    }

    private void open(final SanPham sanPham) {
        // Tạo Bundle và truyền thông tin sản phẩm vào Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPhamChiTiet", sanPham);

        ThongTinChiTietFragment thongTinChiTietFragment = new ThongTinChiTietFragment();
        thongTinChiTietFragment.setArguments(bundle);

        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, thongTinChiTietFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void xoa(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gioHangDao.delete(String.valueOf(id));
                list.clear();
                list.addAll(gioHangDao.getSanPhamInGioHangByMatkd(matknd));
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
    public void setMa(int ma) {
        this.ma = ma;
    }
}