package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangAdapter;
import com.example.myapplication.adapter.SanPhamHomeAdapter;
import com.example.myapplication.adapter.SlidePagerAdapter;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

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

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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
        handler.postDelayed(runnable, 3000); // Thời gian chờ trước khi chuyển đổi tự động (3 giây)

        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        hangDAO = new HangDAO(getActivity());
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        recyclerViewHang = rootView.findViewById(R.id.recyclerViewHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHang.setLayoutManager(layoutManager);
        hangAdapter = new HangAdapter(getContext(), listHang);
        recyclerViewHang.setAdapter(hangAdapter);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        dao = new SanPhamDAO(getActivity());
        list = (ArrayList<SanPham>) dao.getAll();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        hangAdapter.setOnButtonClickListener(new HangAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position, int maHang) {
                ArrayList<SanPham> listSP = new ArrayList<SanPham>();
                listSP = (ArrayList<SanPham>) dao.getAllByMaHang(maHang);
                list.clear();
                list.addAll(listSP);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new SanPhamHomeAdapter(getContext(), getActivity(), list);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}