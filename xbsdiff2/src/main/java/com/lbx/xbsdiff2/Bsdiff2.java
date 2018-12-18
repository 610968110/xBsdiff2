package com.lbx.xbsdiff2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

    public static void install(Context context, String apkPath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(i);
    }
}
