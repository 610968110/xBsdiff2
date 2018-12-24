package com.lbx.xbsdiff2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2018/12/18.
 */

public class Bsdiff2 {

    private static ExecutorService mPool;

    static {
        System.loadLibrary("native-lib");
        mPool = new ThreadPoolExecutor(2, 4, 1000 * 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), (ThreadFactory) Thread::new);
    }

    /**
     * 生成差分包
     *
     * @param oldpath  旧版本apk
     * @param newpath  新版本apk
     * @param patch    生成差分包路径
     * @param listener 回调
     */
    public static void split(String oldpath, String newpath, String patch, OnBsdiffMakeListener listener) {
        mPool.execute(() -> {
            if (listener != null) {
                UIUtil.getInstance().runOnUIThread(listener::onStart);
            }
            try {
                xBsdiff2.getInstance().diff(oldpath, newpath, patch);
            } catch (Exception ignored) {
            } finally {
                if (listener != null) {
                    UIUtil.getInstance().runOnUIThread(() -> listener.onFinish(patch));
                }
            }
        });
    }

    /**
     * 合成新的apk
     *
     * @param oldpath  旧版本apk
     * @param newpath  新版本apk生成路径
     * @param patch    差分包路径
     * @param listener 回调
     */
    public static void synthesis(String oldpath, String newpath, String patch, OnBsdiffMakeListener listener) {
        mPool.execute(() -> {
            if (listener != null) {
                UIUtil.getInstance().runOnUIThread(listener::onStart);
            }
            try {
                xBsdiff2.getInstance().patch(oldpath, newpath, patch);
            } catch (Exception ignored) {
            } finally {
                if (listener != null) {
                    UIUtil.getInstance().runOnUIThread(() -> listener.onFinish(newpath));
                }
            }
        });
    }

    public static String getInstallApkPath(Context context) {
        return context.getApplicationInfo().sourceDir;
    }

    /**
     * 安装app
     *
     * @param context      context
     * @param fileProvider 7.0及以上需要传入provider
     * @param apkPath      apk路径
     * @return 是否安装成功
     */
    public static boolean install(Context context, String fileProvider, String apkPath) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(apkPath);
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                apkUri = FileProvider.getUriForFile(context, fileProvider, file);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                apkUri = Uri.fromFile(file);
            }
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
