增量更新框架
===
* 根据bsdiff制作

首先 引入该项目：

````xml
  compile 'com.lbx.xBsdiff2:1.0.0'
````

### 生成差分包 ：       

 @param oldpath  旧版本apk     
 @param newpath  新版本apk     
 @param patch    生成差分包路径        
 @param listener 回调     
        
 Bsdiff2.split(oldpath, newpath, patch,listener);
### 合成新包：     
@param oldpath  旧版本apk      
@param newpath  新版本apk生成路径      
@param patch    差分包路径       
@param listener 回调      
        
Bsdiff2.split(oldpath, newpath, patch,listener);
### 获取当前已安装的apk包：     
Bsdiff2.getInstallApkPath(context)
### 安装apk：    
provider是适配7.0以上的provider,7.0以下可传null        
Bsdiff2.install(context, provider, apkPath);
