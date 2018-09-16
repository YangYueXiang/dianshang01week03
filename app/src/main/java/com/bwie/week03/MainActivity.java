package com.bwie.week03;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.adapter.Adapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

import bean.Bean;
import butterknife.BindView;
import butterknife.ButterKnife;
import di.IContract;
import di.IPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IContract.iview {

    @BindView(R.id.img_head)
    SimpleDraweeView imgHead;

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
            String iconurl = data.get("iconurl");
            Uri uri = Uri.parse(iconurl);
            imgHead.setImageURI(uri);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private IPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imgHead.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        iPresenter = new IPresenter();
        iPresenter.attachView(this);
        iPresenter.requestInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head:
                toLogin();
                break;
        }
    }

    private void toLogin() {
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
        UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView(this);
    }


    @Override
    public void showData(final List<Bean.DataBean> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 Toast.makeText(MainActivity.this, "data:" + data, Toast.LENGTH_SHORT).show();
                //创建布局管理器
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                //设置布局管理器
                recyclerView.setLayoutManager(manager);
                //创建适配器
                Adapter adapter = new Adapter(MainActivity.this,data);
                recyclerView.setAdapter(adapter);


            }
        });
    }
}
