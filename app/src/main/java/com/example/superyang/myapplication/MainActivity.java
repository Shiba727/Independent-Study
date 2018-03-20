package com.example.superyang.myapplication;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;


public class MainActivity extends AppCompatActivity {


    private ImageView imageView;

    //底部選單按鈕觸發
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.history:
                    HistoryFragment fragment_H = new HistoryFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction_H = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction_H.replace(R.id.fram,fragment_H,"History");
                    fragmentTransaction_H.commit();
                    return true;
                case R.id.main:
                    SearchFragment fragment_M = new SearchFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction_M = getSupportFragmentManager().beginTransaction();
                    // fram is the id of fragmentLayout of activity_main.xml
                    fragmentTransaction_M.replace(R.id.fram,fragment_M,"Main");
                    // remember to commit fragment
                    fragmentTransaction_M.commit();
                    return true;
                case R.id.upload:
                    dialog.show();
                    UploadFragment fragment_U = new UploadFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction_U = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction_U.replace(R.id.fram,fragment_U,"Upload");
                    fragmentTransaction_U.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隱藏ActionBar
        getSupportActionBar().hide();

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        //底部選單物件宣告
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Dialog - Upload
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        String[] options = {"相簿","相機"};
        builder.setItems(options, listener);
        dialog = builder.create();

        // when app starts fragment will be displayed
        SearchFragment fragment_M = new SearchFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction_M = getSupportFragmentManager().beginTransaction();
        fragmentTransaction_M.replace(R.id.fram,fragment_M,"Main");
        fragmentTransaction_M.commit();
    }

    private AlertDialog dialog;

    //Dialog按鈕觸發介面
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                // open gallery
                case 0:
                    //讀取圖片
                    Intent intent = new Intent();
                    //開啟Pictures畫面Type設定為image
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    //取得照片後返回此畫面
                    startActivityForResult(intent, 0);
                    break;

                // open camera
                case 1:
                    Toast.makeText(MainActivity.this,"camera",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageView = findViewById(R.id.imageView_upload);
        if (resultCode == RESULT_OK) {
                if(imageView.getDrawable()!=null) {
                    imageView.setImageBitmap(null);
                    System.gc();
                }
                //更新ImageView
                Bitmap bitmap = data.getParcelableExtra("data");
                imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
