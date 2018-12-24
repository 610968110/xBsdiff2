package com.lbx.bisdiff;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lbx.xbsdiff2.Bsdiff2;
import com.lbx.xbsdiff2.OnBsdiffMakeListener;

import java.io.File;

/**
 * @author lbx
 */
public class MainActivity extends AppCompatActivity {


    /**
     * 旧版本
     */
    private String old = getsdpath() + "old.apk";
    /**
     * 新版本
     */
    private String newp = getsdpath() + "new.apk";
    /**
     * 差分包
     */
    private String patch = getsdpath() + "patch.patch";
    /**
     * 旧版apk和差分包合并生成的新版apk
     */
    private String tmp = getsdpath() + "new_new.apk";
    private static String permissionW = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permissionW) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permissionW}, 0);
            }
        }
    }


    public void click(View view) {
        switch (view.getId()) {
            case R.id.bt_main:
                Bsdiff2.split(old, newp, patch, new OnBsdiffMakeListener() {
                    @Override
                    public void onStart() {
                        append("开始生成差分包");
                    }

                    @Override
                    public void onFinish(String path) {
                        append("生成差分包完成");
                        Bsdiff2.synthesis(Bsdiff2.getInstallApkPath(MainActivity.this),
                                tmp, patch, new OnBsdiffMakeListener() {
                                    @Override
                                    public void onStart() {
                                        append("开始合成新包");
                                    }

                                    @Override
                                    public void onFinish(String path) {
                                        append("合成新包完成：" + path);
                                        append("请点击安装");
                                    }
                                });
                    }
                });
                break;
            case R.id.bt_main2:
                Bsdiff2.install(MainActivity.this, "com.lbx.bisdiff.fileProvider", tmp);
                break;
            default:
                break;
        }
    }

    private String getsdpath() {g
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    private void append(String s) {
        mTextView.append(s + "\n");
    }
}
