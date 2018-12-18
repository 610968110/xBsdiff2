package com.lbx.xbsdiff2;

import android.os.Handler;
import android.os.Looper;

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

public class UIUtil {

    private static UIUtil INSTANCE;
    private static Handler mHandler;

    public static UIUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (UIUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UIUtil();
                }
            }
        }
        return INSTANCE;
    }

    private UIUtil() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void runOnUIThread(Runnable r) {
        if (mHandler != null) {
            mHandler.post(r);
        }
    }
}
