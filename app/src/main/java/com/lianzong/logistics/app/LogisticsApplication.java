package com.lianzong.logistics.app;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lianzong.logistics.app.utils.SystemUtils;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class LogisticsApplication extends Application {

    public static boolean sIsSuportAccount = false;
    public static String sVersionType;
    public static boolean sIsVersionDebug;
    public static int sDebugModuleVisibility;

    public static int sNonOnlineModuleVisibility;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("wsl", "LogisticsApplication onCreate");

        loadConfigs();

        //initialize and create the image loader logic
        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, int errorImgId, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).error(errorImgId).into(imageView);
            }

            @Override
            public void set(ImageView imageView, int resourceId, int errorImgId, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(resourceId).placeholder(placeholder).error(errorImgId).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });

        /*
        TODO FIND LOGIC TO SUPPORT GLIDE
        //initialize and create the image loader logic
        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
        */
    }

    private void loadConfigs() {
        sNonOnlineModuleVisibility = SystemUtils.getBooleanMetaValue(this, "cfg_all_module_type") ? View.VISIBLE : View.GONE;
        sIsSuportAccount = SystemUtils.getBooleanMetaValue(this, "cfg_enable_account");
        sVersionType = SystemUtils.getStringMetaValue(this, "cfg_version");
        sIsVersionDebug = TextUtils.equals("debug", sVersionType);
        sDebugModuleVisibility = sIsVersionDebug ? View.VISIBLE : View.GONE;
    }
}
