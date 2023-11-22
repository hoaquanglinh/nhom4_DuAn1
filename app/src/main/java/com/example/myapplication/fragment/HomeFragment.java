package com.example.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamHomeAdapter;
import com.example.myapplication.adapter.SlidePagerAdapter;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private SlidePagerAdapter slidePagerAdapter;
    RecyclerView recyclerView;
    SanPhamDAO dao;
    ArrayList<SanPham> list;
    SanPhamHomeAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
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

        recyclerView = rootView.findViewById(R.id.recyclerView);
        dao = new SanPhamDAO(getActivity());
        list = (ArrayList<SanPham>) dao.getAll();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SanPhamHomeAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}


