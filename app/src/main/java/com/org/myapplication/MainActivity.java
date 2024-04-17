package com.org.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.org.myapplication.fragment.FirstFragment;
import com.org.myapplication.fragment.FourFragment;
import com.org.myapplication.fragment.SecondFragment;
import com.org.myapplication.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;
    private static final int INDEX_HOME_FRAGMENT = 0;
    private FragmentTransaction transaction;
    private static final int INDEX_ORDER_FRAGMENT = 1;
    private static final int INDEX_MY_FRAGMENT = 2;

    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        initToolbar();
        initFragment();
        initNavigationBottom();
    }






    @SuppressLint("ResourceAsColor")
    private void initNavigationBottom() {
        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initFragment() {
//        FirstFragment diariesFragment = getFragment();
//        if (diariesFragment == null) {
//            diariesFragment = new FirstFragment();
//            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
//        }

        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmentList.add(new FirstFragment());
//            fragmentList.add(new SecondFragment());
//            fragmentList.add(new ThirdFragment());
//            fragmentList.add(new FourFragment());

            if (fragmentList.size() == 1) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                 transaction = fragmentManager.beginTransaction();
                for (int i = 0; i < fragmentList.size(); i++) {
                    Fragment fragment = fragmentList.get(i);
                    transaction.add(R.id.content, fragment);
                    transaction.hide(fragment);
                }
                transaction.show(fragmentList.get(INDEX_HOME_FRAGMENT));
                transaction.commit();
            }
        }
    }

    private FirstFragment getFragment() {
        return (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private void initToolbar() {
        //设置顶部状态栏为透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.menu_diary:
                changeFragment(0);
////                MeController.setToolbarVisibility(this);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new FirstFragment(), R.id.content);
                break;
            case R.id.menu_new:
                changeFragment(1);
//                bottomNavigationView.setVisibility(View.GONE);
//                MeController.setToolbarVisibility(this);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new SecondFragment (), R.id.content);
                break;
//            case R.id.menu_state:
//                changeFragment(2);
////                findViewById(R.id.toolbar).setVisibility(View.GONE);
////                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
////                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new ThirdFragment(), R.id.content);
//                break;
//            case R.id.menu_test:
//                changeFragment(3);
////                findViewById(R.id.toolbar).setVisibility(View.GONE);
////                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
////                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new FourFragment(), R.id.content);
//                break;
        }
        return true;
    };

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
         transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }
}