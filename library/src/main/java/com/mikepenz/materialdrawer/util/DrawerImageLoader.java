package com.mikepenz.materialdrawer.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by mikepenz on 24.03.15.
 */
public class DrawerImageLoader {
    private static DrawerImageLoader SINGLETON = null;

    private IDrawerImageLoader imageLoader;

    private DrawerImageLoader(IDrawerImageLoader loaderImpl) {
        imageLoader = loaderImpl;
    }

    public static DrawerImageLoader init(IDrawerImageLoader loaderImpl) {
        SINGLETON = new DrawerImageLoader(loaderImpl);
        return SINGLETON;
    }

    public static DrawerImageLoader getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new DrawerImageLoader(new IDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, int errorImgId, Drawable placeholder) {
                    //this won't do anything
                }

                @Override
                public void set(ImageView imageView, int resourceId, int errorImgId, Drawable placeholder) {
                    //this won't do anything
                }

                @Override
                public void cancel(ImageView imageView) {

                }

                @Override
                public Drawable placeholder(Context ctx) {
                    return null;
                }
            });
        }
        return SINGLETON;
    }

    public void setImage(ImageView imageView, Uri uri, int errorImgId) {
        if (imageLoader != null) {
            Drawable placeHolder = imageLoader.placeholder(imageView.getContext());

            if (placeHolder == null) {
                placeHolder = UIUtils.getPlaceHolder(imageView.getContext());
            }

            imageLoader.set(imageView, uri, errorImgId, placeHolder);
        }
    }

    public void setImage(ImageView imageView, int resourceId, int errorImgId) {
        if (imageLoader != null) {
            Drawable placeHolder = imageLoader.placeholder(imageView.getContext());

            if (placeHolder == null) {
                placeHolder = UIUtils.getPlaceHolder(imageView.getContext());
            }

            imageLoader.set(imageView, resourceId, errorImgId, placeHolder);
        }
    }

    public void cancelImage(ImageView imageView) {
        if (imageLoader != null) {
            imageLoader.cancel(imageView);
        }
    }

    public IDrawerImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(IDrawerImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public interface IDrawerImageLoader {
        public void set(ImageView imageView, Uri uri, int errorImgId, Drawable placeholder);

        public void set(ImageView imageView, int resourceId, int errorImgId, Drawable placeholder);

        public void cancel(ImageView imageView);

        public Drawable placeholder(Context ctx);
    }
}
