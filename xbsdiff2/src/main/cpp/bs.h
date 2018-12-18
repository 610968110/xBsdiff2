#ifndef BSDIFFPATCH_BS_H
#define BSDIFFPATCH_BS_H
#endif //BSDIFFPATCH_BS_H
#include <malloc.h>
#include <jni.h>
int mydiff(int argc,char *argv[]);
int mypatch(int argc,char * argv[]);
JNIEXPORT jint JNICALL
Java_com_lbx_xbsdiff2_xBsdiff2_patch
        (JNIEnv *env, jobject instance, jstring oldpath_, jstring newpath_,jstring patch_);
JNIEXPORT jint JNICALL
Java_com_lbx_xbsdiff2_xBsdiff2_diff
        (JNIEnv *env, jobject instance, jstring oldpath_, jstring newpath_, jstring patch_);
