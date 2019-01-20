package com.youzi.framework.common.util.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.MessageException;
import com.youzi.framework.common.error.PermissionException;
import com.youzi.framework.common.util.file.FileUtil;
import com.youzi.framework.common.util.req.OnStartActivityResultCallback;
import com.youzi.framework.common.util.req.ReqCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * 功能描述:权限请求工具 和 快速调用系统处理的工具类
 * Created by LuoHaifeng on 2017/5/3.
 * Email:496349136@qq.com
 */

public class QuickPermissionUtil {
    /**
     * 获取所有在manifest中定义过的危险权限
     *
     * @param activity
     * @return
     */
    public static List<String> getManifestShouldRequestPermissions(Activity activity) {
        ArrayList<String> dangerousPermissions = new ArrayList<>();

        try {
            PackageManager pm = activity.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] requested = packageInfo.requestedPermissions;
            for (String str : requested) {
                try {
                    PermissionInfo info = pm.getPermissionInfo(str, PackageManager.GET_META_DATA);
                    boolean isDangerous = info.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS;
                    if (isDangerous) {
                        dangerousPermissions.add(str);
                    }
                } catch (PackageManager.NameNotFoundException ignored) {

                }
            }
        } catch (PackageManager.NameNotFoundException var10) {
            var10.printStackTrace();
        }

        return dangerousPermissions;
    }

    public static Observable<Boolean> requestManifestPermissions(Activity activity) {
        return new RxPermissions(activity).request(getManifestShouldRequestPermissions(activity).toArray(new String[]{}));
    }

    /**
     * 请求打电话权限
     *
     * @param activity Context实例
     * @return 可订阅者
     */
    public static Observable<Boolean> requestCallPermission(Activity activity) {
        return new RxPermissions(activity)
                .request(Manifest.permission.CALL_PHONE);
    }

    /**
     * 请求相机权限
     *
     * @param activity Context实例
     * @return 权限可订阅者
     */
    public static Observable<Boolean> requestCameraPermission(final Activity activity) {
        return new RxPermissions(activity)
                .request(Manifest.permission.CAMERA);
    }

    public static Observable<Boolean> requestCameraAndExternalStoragePermission(final Activity activity) {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        } else {
            permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        }
        return new RxPermissions(activity).request(permissions);
    }


    /***
     * 请求电话状态读取权限
     * @param activity Context实例
     * @return 权限可订阅者
     */
    public static Observable<Boolean> requestReadPhonePermission(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
        return new RxPermissions(activity).request(permissions);
    }

    /***
     * 请求外部存储权限（如果是android 6.0以上新增请求Manifest.permission.READ_EXTERNAL_STORAGE）
     * @param activity Context实例
     * @return 权限可订阅者
     */
    public static Observable<Boolean> requestExternalStoragePermission(Activity activity) {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
            permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }

        return new RxPermissions(activity).request(permissions);
    }

    /**
     * 请求拨打电话,先获取拨打权限，如果获取成功则拨打电话
     *
     * @param activity    Context实例
     * @param phoneNumber 需要拨打的电话号码
     */
    @SuppressLint("CheckResult")
    public static void requestDirectCall(final Activity activity, final String phoneNumber) {
        QuickPermissionUtil.requestCallPermission(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                                    + phoneNumber)));
                        } else {
                            Config.getUiProvider().provideToast().showError("请在授予拨打电话权限后重试");
                        }
                    }
                });
    }

    /**
     * 请求拨打电话,先获取拨打权限，如果获取成功则拨打电话
     *
     * @param activity    Context实例
     * @param phoneNumber 需要拨打的电话号码
     */
    public static void requestCall(final Activity activity, final String phoneNumber) {
        QuickPermissionUtil.requestCallPermission(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                                    + phoneNumber)));
                        } else {
                            Config.getUiProvider().provideToast().showError("请在授予拨打电话权限后重试");
                        }
                    }
                });
    }

    /**
     * 请求安装apk文件,兼容android7.0
     *
     * @param activity          Context实例
     * @param apkFilePath       apk文件路径
     * @param providerAuthority FileProvider的Authority标志
     */
    public static Observable<Object> requestInstallApk(final Activity activity, final String apkFilePath, final String providerAuthority) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                File apkFile = new File(apkFilePath);
                if (!apkFile.exists()) {
                    e.onError(new Exception(apkFilePath + "file not exist"));
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //兼容7.0 FileUriExposedException
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(activity, providerAuthority, apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                }
                activity.startActivity(intent);
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    public static Observable<Object> requestInstallApk(final Activity activity, final String apkFilePath) {
        return requestInstallApk(activity, apkFilePath, Config.getFileProviderAuthorities());
    }

    /**
     * 请求通话记录权限
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestCallLog(final Activity activity) {
        return new RxPermissions(activity)
                .request(Manifest.permission.READ_CALL_LOG);
    }

    /**
     * 请求获取用户短息权限
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestSMS(final Activity activity) {
        return new RxPermissions(activity)
                .request(Manifest.permission.READ_SMS);
    }

    /**
     * 请求获取用户联系人权限
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestContacts(final Activity activity) {
        return new RxPermissions(activity)
                .request(Manifest.permission.READ_CONTACTS);
    }

    /**
     * 请求调用系统相机进行拍照,兼容android7.0
     *
     * @param activity          Context实例
     * @param savePath          拍照文件的保存路径
     * @param providerAuthority FileProvider的Authority标志
     * @return 拍照结果的监听
     */
    public static Observable<String> requestTakePhoto(final FragmentActivity activity, final String savePath, final String providerAuthority) {
        // 激活系统的照相机进行拍照
        final Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");

        //保存照片到指定的路径
        File file = new File(savePath);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //兼容7.0 FileUriExposedException
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, providerAuthority, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        final Observable<String> req = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                ReqCompat.startActivityForResult(activity, intent, new OnStartActivityResultCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == RESULT_OK) {
                            emitter.onNext(savePath);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });

        return requestCameraAndExternalStoragePermission(activity)
                .flatMap(new Function<Boolean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return req;
                        }
                        throw new PermissionException("请在授予照相机和存储权限后重试");
                    }
                });
    }

    /**
     * 请求调用系统相机进行拍照,兼容android7.0,并默认在缓存目录生成一个随机名称的图片文件作为保存路径
     *
     * @param activity          Context实例
     * @param providerAuthority FileProvider的Authority标志
     * @return 拍照结果的监听
     */
    public static Observable<String> requestTakePhoto(final FragmentActivity activity, final String providerAuthority) {
        return requestTakePhoto(activity, FileUtil.getRandomPictureFile().getAbsolutePath(), providerAuthority);
    }

    public static Observable<String> requestTakePhoto(final FragmentActivity activity) {
        return requestTakePhoto(activity, Config.getFileProviderAuthorities());
    }

    public static Observable<String> requestCropPhoto(final FragmentActivity activity, String srcFilePath, final String savePath, final String providerAuthority, int width, int height, boolean supportScale) {
        Uri imageUri;
        Uri outputUri;
        File inputFile = new File(srcFilePath);
        final File outputFile = new File(savePath);

        final Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(activity, providerAuthority, inputFile);
            outputUri = Uri.fromFile(outputFile);
        } else {
            imageUri = Uri.fromFile(inputFile);
            outputUri = Uri.fromFile(outputFile);
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        //设置宽高比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("scale", supportScale);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("circleCrop", false);
        intent.putExtra("return-data", false);

        final Observable<String> req = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                ReqCompat.startActivityForResult(activity, intent, new OnStartActivityResultCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == RESULT_OK) {
                            emitter.onNext(savePath);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });

        return requestExternalStoragePermission(activity)
                .flatMap(new Function<Boolean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return req;
                        }
                        throw new PermissionException("请在授予存储权限后重试");
                    }
                });
    }

    public static Observable<String> requestCropPhoto(final FragmentActivity activity, String srcFilePath, final String savePath, int width, int height, boolean supportScale) {
        return requestCropPhoto(activity, srcFilePath, savePath, Config.getFileProviderAuthorities(), width, height, supportScale);
    }

    public static Observable<String> requestCropPhoto(final FragmentActivity activity, String srcFilePath, int width, int height, boolean supportScale) {
        File cropOutputFile;
        if (Environment.isExternalStorageEmulated()) {//交给系统工具的，优先使用外部目录
            cropOutputFile = new File(Environment.getExternalStorageDirectory(), FileUtil.getRandomFileName());
        } else {
            cropOutputFile = new File(Environment.getDownloadCacheDirectory(), FileUtil.getRandomFileName());
        }
        return requestCropPhoto(activity, srcFilePath, cropOutputFile.getAbsolutePath(), width, height, supportScale);
    }
}
