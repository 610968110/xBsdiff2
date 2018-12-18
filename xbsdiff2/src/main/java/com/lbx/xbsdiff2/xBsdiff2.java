package com.lbx.xbsdiff2;

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

public class xBsdiff2 {

    private static xBsdiff2 INSTANCE;

    public static xBsdiff2 getInstance() {
        if (INSTANCE == null) {
            synchronized (xBsdiff2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new xBsdiff2();
                }
            }
        }
        return INSTANCE;
    }

    private xBsdiff2() {
    }

    //生成差分包
    public native int diff(String oldpath, String newpath, String patch);

    //旧apk和差分包合并
    public native int patch(String oldpath, String newpath, String patch);
}
