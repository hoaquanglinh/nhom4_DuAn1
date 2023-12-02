package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.ManHinhDangNhap;
import com.example.myapplication.R;
import com.example.myapplication.model.TaiKhoanND;

public class DoMatKhauFragment extends Fragment {
    EditText matkhau, rematkhau, matkhaucu;
    TaiKhoanNDDAO nddao;
    TaiKhoanND tknd;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_mat_khau, container, false);

        toolbar = view.findViewById(R.id.toolbardoimk);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Đổi mật kẩu");

        nddao = new TaiKhoanNDDAO(getContext());
        tknd = new TaiKhoanND();

        matkhau = view.findViewById(R.id.edPasswordDMK);
        rematkhau = view.findViewById(R.id.edRePasswordDMK);
        matkhaucu = view.findViewById(R.id.edPasswordCu);

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        String pass = i.getStringExtra("pass");

        nddao = new TaiKhoanNDDAO(getActivity());
        int matknd = nddao.getMatkndFromTaikhoannd(user);

        view.findViewById(R.id.btnDoiMK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mk = matkhau.getText().toString();
                String remk = rematkhau.getText().toString();
                String mkcu = matkhaucu.getText().toString();

                if(mk.trim().isEmpty() || remk.trim().isEmpty() || mkcu.isEmpty()){
                    Toast.makeText(getActivity(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(mk.equals(remk)){
                        if(mkcu.equals(pass)){
                            if(nddao.updatePassByMaTKND(matknd, mk) > 0){
                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ManHinhDangNhap.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
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