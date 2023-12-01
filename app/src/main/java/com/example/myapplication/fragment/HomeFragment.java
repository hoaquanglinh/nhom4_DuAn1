package com.example.myapplication.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangAdapter;
import com.example.myapplication.adapter.SanPhamHomeAdapter;
import com.example.myapplication.adapter.SlidePagerAdapter;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.Slide;
import com.google.android.material.tabs.TabLayout;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private SlidePagerAdapter slidePagerAdapter;
    RecyclerView recyclerView, recyclerViewHang;
    SanPhamDAO dao;
    ArrayList<SanPham> list;
    SanPhamHomeAdapter adapter;
    HangAdapter hangAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    TaiKhoanNDDAO nddao;
    SearchView searchView;
    Toolbar toolbar;
    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbarHome);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        // Khởi tạo danh sách các Slide
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(R.drawable.img));
        slideList.add(new Slide(R.drawable.img_1));
        slideList.add(new Slide(R.drawable.img_2));

        // Khởi tạo và thiết lập SlidePagerAdapter cho ViewPager
        slidePagerAdapter = new SlidePagerAdapter(getContext(), slideList);
        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(slidePagerAdapter);

        // Kết nối TabLayout với ViewPager
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Chuyển đổi tự động slide
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem >= slidePagerAdapter.getCount()) {
                    nextItem = 0;
                }
                viewPager.setCurrentItem(nextItem);
                handler.postDelayed(this, 3000); // Thời gian chuyển đổi tự động (3 giây)
            }
        };
        handler.postDelayed(runnable, 3000);

        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        hangDAO = new HangDAO(getActivity());
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        recyclerViewHang = rootView.findViewById(R.id.recyclerViewHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHang.setLayoutManager(layoutManager);
        hangAdapter = new HangAdapter(getContext(), listHang);
        recyclerViewHang.setAdapter(hangAdapter);

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        nddao = new TaiKhoanNDDAO(getActivity());
        int matknd = nddao.getMatkndFromTaikhoannd(user);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        dao = new SanPhamDAO(getActivity());
        list = (ArrayList<SanPham>) dao.getAllExceptMAtknd(matknd);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        hangAdapter.setOnButtonClickListener(new HangAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position, int maHang) {
                ArrayList<SanPham> listSP = new ArrayList<SanPham>();
                listSP = (ArrayList<SanPham>) dao.getAllByMaHang(maHang);
                ArrayList<SanPham> listSPVjp = new ArrayList<>();
                for (SanPham sanPham : listSP) {
                    if (sanPham.getMatknd() != matknd) {
                        listSPVjp.add(sanPham);
                    }
                }
                list.clear();
                list.addAll(listSPVjp);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new SanPhamHomeAdapter(getContext(), getActivity(), list);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

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
        adapter = new SanPhamHomeAdapter(getContext(), getActivity(), list2);
        recyclerView.setAdapter(adapter);
    }

    private String chuyenVeKhongDau(String string) {
        String boDau = Normalizer.normalize(string, Normalizer.Form.NFD);
        // InCOMBINING_DIACRITICAL_MARKS
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(boDau).replaceAll("");
    }
}