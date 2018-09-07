package com.intexh.pretendtoreadnews.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.app.App;

/**
 * Created by Frank on 2017/3/31.
 * Glide 4.0 正式版
 */

public enum GlideHelper {
    INSTANCE;


    //备用 后台头像地址不改变（只做本人头像显示）
    public void loadAvatar(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.oval_default_image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop();   //4.0版本圆形
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /*-------------------------- url地址图片 ---------------------------------*/
    /**
     * 加载url图片
     *
     * @param imageView
     * @param url
     */
    public void loadImage(int imgNumber, String url,ImageView imageView) {
        Glide.with(App.getAppContext())
                .load(url)
                .apply(new RequestOptions().placeholder(getDefaultPic(imgNumber)).centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载url图片
     *
     * @param imageView
     * @param url
     */
    public void loadImage( String url,ImageView imageView) {
        Glide.with(App.getAppContext())
                .load(url)
                .apply(new RequestOptions().error(R.mipmap.img_default_movie).centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载url图片
     *
     * @param imageView
     * @param url
     */
    public void loadImage(int imgNumber, String url,ImageView imageView,int dp) {
        RequestOptions options = new RequestOptions()
//                .placeholder(getDefaultPic(imgNumber))
                .centerCrop()
                .error(getDefaultPic(imgNumber))
                .transform(new RoundedCorners(UIUtil.dp2px(App.getAppContext(),dp)));
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }



    /**
     * 根据设置尺寸加载url
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public void loadImage(ImageView imageView, String url, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 带尺寸切角
     * @param imageView
     * @param url
     * @param width
     * @param height
     * @param dp
     */
    public void loadImage(ImageView imageView, String url, int width, int height, int dp) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(App.getAppContext(),dp)))
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 不带尺寸切角
     * @param imageView
     * @param url
     * @param dp
     */
    public void loadImage(ImageView imageView, String url, int dp) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(App.getAppContext(),dp)));
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView
     * @param url
     */
    public void loadCircleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.oval_default_image)
                .centerCrop()
                .circleCrop();   //4.0版本圆形
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
    /**
     * 带尺寸加载圆形图片
     *
     * @param imageView
     * @param url
     */
    public void loadCircleImage(ImageView imageView, String url, int width, int height) {
        RequestOptions options= new RequestOptions()
                .placeholder(R.mipmap.oval_default_image)
                .centerCrop()
                .circleCrop()
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }


/*-------------------------- 资源图片 ---------------------------------*/
    /**
     * 加载url图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadImage(ImageView imageView, int resIcon) {
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }

    /**
     * 根据设置尺寸加载url
     * @param imageView
     * @param resIcon
     * @param width
     * @param height
     */
    public void loadImage(ImageView imageView, int resIcon, int width, int height) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 带尺寸切角
     * @param imageView
     * @param resIcon
     * @param width
     * @param height
     * @param dp
     */
    public void loadImage(ImageView imageView, int resIcon, int width, int height, int dp) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(App.getAppContext(),dp)))
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 不带尺寸切角
     * @param imageView
     * @param resIcon
     * @param dp
     */
    public void loadImage(ImageView imageView, int resIcon, int dp) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(App.getAppContext(),dp)));
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadCircleImage(ImageView imageView, int resIcon) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop();   //4.0版本圆形
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }
    /**
     * 带尺寸加载圆形图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadCircleImage(ImageView imageView, int resIcon, int width, int height) {
        RequestOptions options
                = new RequestOptions()
                .centerCrop()
                .circleCrop()
                .override(width,height);
        Glide.with(App.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }


    private static int getDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
            case 4:
                return R.mipmap.img_default_movie;
        }
        return R.mipmap.img_four_bi_three;
    }


    //验证图片地址
   /* public GlideUrl getGlideUrl(String url) {
        //Authorization 请求头信息
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("Authorization", "http://cdn.dotasell.com")
                .build();
        return new GlideUrl(url, headers);
    }*/


}
