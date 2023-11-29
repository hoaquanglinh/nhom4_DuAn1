package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;

public class ChinhSuaThongTinFragment extends Fragment {
    EditText edHoTen, edNamSinh, edDiaChi, edSDT, edEmail;
    RadioButton rdoNam, rdoNu, rdoKhac;
    NguoiDungDAO dao;
    TaiKhoanNDDAO nddao;
    NguoiDung item;
    ArrayList<NguoiDung> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chinh_sua_thong_tin, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        nddao = new TaiKhoanNDDAO(getActivity());
        int matknd = nddao.getMatkndFromTaikhoannd(user, pass);

        edHoTen = view.findViewById(R.id.edTenNguoiDung);
        edNamSinh = view.findViewById(R.id.edNamSinh);
        edDiaChi = view.findViewById(R.id.edDiaChi);
        edSDT = view.findViewById(R.id.edSDT);
        edEmail = view.findViewById(R.id.edEmail);
        rdoNam = view.findViewById(R.id.gtNam);
        rdoNu = view.findViewById(R.id.gtNu);
        rdoKhac = view.findViewById(R.id.gtKhac);
        item = new NguoiDung();
        list = new ArrayList<>();
        nddao = new TaiKhoanNDDAO(getContext());
        dao = new NguoiDungDAO(getContext());

        list = (ArrayList<NguoiDung>) dao.getAllByMAtknd(matknd);


        if (!list.isEmpty()){
            item = list.get(0);

            edHoTen.setText(item.getTen());
            if (item.getGioiTinh().equalsIgnoreCase("Nam")){
                rdoNam.setChecked(true);
            }else if(item.getGioiTinh().equalsIgnoreCase("Nữ")){
                rdoNu.setChecked(true);
            }else{
                rdoKhac.setChecked(true);
            }

            edNamSinh.setText(String.valueOf(item.getNamSinh()));
            edDiaChi.setText(item.getDiaChi());
            edSDT.setText(item.getSdt());
            edEmail.setText(item.getEmail());
        }

        view.findViewById(R.id.btnLuu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setTen(edHoTen.getText().toString());

                if(rdoNam.isChecked()){
                    item.setGioiTinh("Nam");
                }else if(rdoNu.isChecked()){
                    item.setGioiTinh("Nữ");
                }else{
                    item.setGioiTinh("Khác");
                }

                item.setNamSinh(Integer.parseInt(edNamSinh.getText().toString()));
                item.setDiaChi(edDiaChi.getText().toString());
                item.setSdt(edSDT.getText().toString());
                item.setEmail(edEmail.getText().toString());
                item.setMatknd(matknd);

                if (!list.isEmpty()){
                    if (dao.update(item)) {
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    long insert = dao.insert(item);
                    if (insert > 0) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }
}