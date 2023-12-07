package com.example.myapplication.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.model.SanPham;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class QuanLySPFragment extends Fragment  {
    ListView listView;
    SanPhamDAO dao;
    SanPhamAdapter adapter;
    ArrayList<SanPham> list;
    SearchView searchView;
    Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_s_p, container, false);
        toolbar = view.findViewById(R.id.toolbarQLSP);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Quản lý sản phẩm");
        listView = view.findViewById(R.id.lvQLSP);
        dao = new SanPhamDAO(getActivity());

        capNhat();
        return view;
    }
    void capNhat() {
        list = (ArrayList<SanPham>) dao.getAll();
        adapter = new SanPhamAdapter(getContext(), list, getActivity(), dao);
        listView.setAdapter(adapter);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                timKiem(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void timKiem(String query) {
        ArrayList<SanPham> list2 = new ArrayList<>();
        String duLieuNhapVao = chuyenVeKhongDau(query.toLowerCase());
        for (SanPham sp : list) {
            String duLieuTrongList = chuyenVeKhongDau(sp.getTensp().toLowerCase());
            if (duLieuTrongList.contains(duLieuNhapVao)) {
                list2.add(sp);
            }
        }
        adapter = new SanPhamAdapter(getContext(),list2, getActivity(), dao);
        listView.setAdapter(adapter);
    }

    private String chuyenVeKhongDau(String string) {
        String boDau = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(boDau).replaceAll("");
    }
}