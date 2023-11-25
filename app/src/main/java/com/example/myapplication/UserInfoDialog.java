package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.model.NguoiDung;

import java.util.List;

public class UserInfoDialog extends DialogFragment {
    private static final String ARG_MATKND = "matknd";

    public static UserInfoDialog newInstance(int matknd) {
        UserInfoDialog fragment = new UserInfoDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_MATKND, matknd);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(requireContext());

        int matknd = getArguments().getInt(ARG_MATKND);

        List<NguoiDung> nguoiDungList = nguoiDungDAO.getAllByMAtknd(matknd);

        if (!nguoiDungList.isEmpty()) {
            NguoiDung nguoiDung = nguoiDungList.get(0);

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.item_thong_tin_nguoi_dung, null);

            TextView txtTennd = dialogView.findViewById(R.id.txt_Tennd);
            TextView txtGioiTinh = dialogView.findViewById(R.id.txt_GioiTinh);
            TextView txtNamSinh = dialogView.findViewById(R.id.txt_Namsinh);
            TextView txtDiaChi = dialogView.findViewById(R.id.txt_DiaChi);
            TextView txtSdt = dialogView.findViewById(R.id.txt_Sdt);
            TextView txtEmail = dialogView.findViewById(R.id.txt_Email);
            Button btnClose = dialogView.findViewById(R.id.btn_Close);

            txtTennd.setText(nguoiDung.getTen());
            txtGioiTinh.setText(nguoiDung.getGioiTinh());
            txtNamSinh.setText(String.valueOf(nguoiDung.getNamSinh()));
            txtDiaChi.setText(nguoiDung.getDiaChi());
            txtSdt.setText(nguoiDung.getSdt());
            txtEmail.setText(nguoiDung.getEmail());

            builder.setView(dialogView);

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            builder.setMessage("Không tìm thấy thông tin người dùng");
        }

        return builder.create();
    }


}