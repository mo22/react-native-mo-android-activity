package de.mxs.reactnativemoandroidactivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.blob.BlobModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class ReactNativeMoAndroidActivity extends ReactContextBaseJavaModule {
    @Override
    public @Nonnull
    String getName() {
        return "ReactNativeMoAndroidActivity";
    }

    @ReactMethod
    public void isPackageInstalled(String pkg, Promise promise) {
        try {
            getReactApplicationContext().getPackageManager().getPackageInfo(pkg, 0);
            promise.resolve(true);
        } catch (PackageManager.NameNotFoundException ex) {
            promise.resolve(false);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }

    private Intent createIntentFromMap(ReadableMap args) {
        String action = Objects.requireNonNull(args.getString("action"));
        try {
            Field field = Intent.class.getField(action);
            Object value = field.get(null);
            if (value instanceof String) {
                action = (String)value;
            }
        } catch (NoSuchFieldException ex) {
          // ignore.
        } catch (IllegalAccessException ex) {
          // ignore
        }
        Intent intent = new Intent(action);
        if (args.hasKey("type")) {
            intent.setType(args.getString("type"));
        }
        if (args.hasKey("package")) {
            intent.setPackage(args.getString("package"));
        }
        if (args.hasKey("data")) {
            intent.setData(Uri.parse(args.getString("data")));
        }
        if (args.hasKey("extra_stream")) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(args.getString("extra_stream")));
        }
        if (args.hasKey("extras")) {
            // android.intent.extra.STREAM ?
            // to Url?
            intent.putExtras(Arguments.toBundle(args.getMap("extras")));
        }
        return intent;
    }

    @ReactMethod
    public void startActivity(ReadableMap args, Promise promise) {
        try {
            Intent intent = createIntentFromMap(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getReactApplicationContext().startActivity(intent);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }

    private int currentRequestCode = 0;

    @ReactMethod
    public void startActivityForResult(ReadableMap args, Promise promise) {
        try {
            Intent intent = createIntentFromMap(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            currentRequestCode = (currentRequestCode + 1) % 10000;
            int requestCode = 10000 + currentRequestCode;
            ActivityEventListener listener = new ActivityEventListener() {
                @Override
                public void onActivityResult(Activity activity, int code, int result, @Nullable Intent intent) {
                    if (code != requestCode) return;
                    getReactApplicationContext().removeActivityEventListener(this);
                    Log.i("XXX", "onActivityResult code=" + code + " result=" + result + " intent=" + intent);
                    WritableMap res = Arguments.createMap();
                    res.putInt("result", result);
                    promise.resolve(res);
                }
                @Override
                public void onNewIntent(Intent intent) {
                }
            };
            getReactApplicationContext().addActivityEventListener(listener);
            Log.i("XXX", "startActivityForResult intent=" + intent + " requestCode=" + requestCode);
            boolean res = getReactApplicationContext().startActivityForResult(intent, requestCode, null);
            if (!res) {
                throw new RuntimeException("cannot start activity");
            }
        } catch (Exception ex) {
          promise.reject(ex);
        }
    }
}
