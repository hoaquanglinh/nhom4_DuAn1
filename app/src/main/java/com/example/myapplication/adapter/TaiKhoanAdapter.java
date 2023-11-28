package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.UserInfoDialog;
import com.example.myapplication.fragment.QuanLyNguoiDungFragment;
import com.example.myapplication.model.TaiKhoanND;

import java.util.ArrayList;

public class TaiKhoanAdapter extends ArrayAdapter<TaiKhoanND> {

    private Context context;
    QuanLyNguoiDungFragment fragment;
    private ArrayList<TaiKhoanND> list;
    TextView tvMaTaiKhoan, tvTenTaiKhoan, tvMatKhau ;
    ImageButton btnDelete_tk;
    public TaiKhoanAdapter(@NonNull Context context, QuanLyNguoiDungFragment fragment, ArrayList<TaiKhoanND> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_qlthanhvien, null);
        }
        final TaiKhoanND item = list.get(position);
        if (item != null) {
            tvMaTaiKhoan = v.findViewById(R.id.txt_MaTaiKhoan);
            tvMaTaiKhoan.setText( ""+item.getMaTKND());
            tvTenTaiKhoan = v.findViewById(R.id.txt_TenTaiKhoan);
            tvTenTaiKhoan.setText( ""+item.getTaiKhoanND());
            tvMatKhau = v.findViewById(R.id.txt_MatKhau);
            tvMatKhau.setText(""+item.getMatKhauND());

            btnDelete_tk = v.findViewById(R.id.btnDelete_tk);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo đối tượng UserInfoDialog và truyền giá trị matknd
                UserInfoDialog dialog = UserInfoDialog.newInstance(item.getMaTKND());
                // Hiển thị hộp thoại
                dialog.show(fragment.getChildFragmentManager(), "userInfoDialog");
            }
        });
        btnDelete_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức xóa
                fragment.xoa(String.valueOf(item.getMaTKND()));
            }
        });
        return v;
    }
}
