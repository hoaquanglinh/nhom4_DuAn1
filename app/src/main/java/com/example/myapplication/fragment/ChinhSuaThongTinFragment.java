package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chinh_sua_thong_tin, container, false);

        toolbar = view.findViewById(R.id.toolbarThongTin);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Chỉnh sửa thông tin");

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        nddao = new TaiKhoanNDDAO(getActivity());
        int matknd = nddao.getMatkndFromTaikhoannd(user);

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
                if (edHoTen.getText().toString().trim().isEmpty() || edNamSinh.getText().toString().trim().isEmpty() || edDiaChi.getText().toString().trim().isEmpty() || edSDT.getText().toString().trim().isEmpty() || edEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        int namsinh = Integer.parseInt(edNamSinh.getText().toString());

                        if (!rdoNam.isChecked() && !rdoNu.isChecked() && !rdoKhac.isChecked()){
                            Toast.makeText(getContext(), "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                        }else{
                            item.setTen(edHoTen.getText().toString());

                            if(rdoNam.isChecked()){
                                item.setGioiTinh("Nam");
                            }else if(rdoNu.isChecked()){
                                item.setGioiTinh("Nữ");
                            }else{
                                item.setGioiTinh("Khác");
                            }

                            item.setNamSinh(namsinh);
                            item.setDiaChi(edDiaChi.getText().toString());
                            item.setSdt(edSDT.getText().toString());
                            item.setEmail(edEmail.getText().toString());
                            item.setMatknd(matknd);

                            if (!list.isEmpty()){
                                dao.update(item);
                            }else{
                                dao.insert(item);
                            }
                            Toast.makeText(getContext(), "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            PersonFragment fragment = new PersonFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.flContent, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Năm sinh phải là sô", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}