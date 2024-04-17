package com.org.myapplication.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.org.myapplication.CallActivity;
import com.org.myapplication.R;
import com.org.myapplication.databinding.FragmentFourBinding;
import com.org.myapplication.databinding.FragmentSecondBinding;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.ShowBottomEditDialog;
import com.org.myapplication.utils.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FourFragment extends Fragment {

    private static final int OPEN_GALLERY_REQUEST_CODE = 111;
    private FragmentFourBinding binding;

    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFourBinding.inflate(inflater, container, false);
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        try {
            Uri uri=Uri.parse(sp.get("img"));
            Util.l("img="+sp.get("img"));
            InputStream inputStream =getActivity(). getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // TODO 把获取到的图片放到ImageView上
            binding.iv.setImageBitmap(bitmap);
        }catch (Exception e){
            Util.l("imge="+e.getMessage());

        }

        Util.setText(binding.tv,"mytv",sp);
        Util.setText(binding.tv2,"mytv2",sp);
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                ShowBottomEditDialog.str=binding.tv.getText().toString();
                showBottomEditDialog.BottomDialog(getActivity(), new CallBack() {
                    @Override
                    public void onDo(Object o) {
                        String str= (String) o;
                        if(str!=null){
                            binding.tv.setText(str);
                            sp.put("mytv",str);
                        }

                    }
                });
            }
        });
        binding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                ShowBottomEditDialog.str=binding.tv2.getText().toString();
                showBottomEditDialog.BottomDialog(getActivity(), new CallBack() {
                    @Override
                    public void onDo(Object o) {
                        String str= (String) o;
                        if(str!=null){
                            binding.tv2.setText(str);
                            sp.put("mytv2",str);
                        }

                    }
                });
            }
        });
        return binding.getRoot();

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , "image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GALLERY_REQUEST_CODE) { // 检测请求码
            if (resultCode == Activity.RESULT_OK && data != null) {
                try {
                    Util.l("namei="+data.getData());
                    sp.put("img",data.getData().toString());
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    // TODO 把获取到的图片放到ImageView上
                    binding.iv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    private void startGallery() {
//        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        cameraIntent.setType("image/*");
//        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(cameraIntent, 1000);
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
//            sp.put("img",data.getData().toString());
//            Util.l("img="+data.getData().toString());
//            Uri returnUri = data.getData();
//            Bitmap bitmapImage = null;
//            try {
//                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
//                binding.iv.setImageBitmap(bitmapImage);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}