/* 
 * @(#)BitmapCache.java    Created on 2013-9-17
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.winupon.andframe.bigapple.cache.impl;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

import com.winupon.andframe.bigapple.cache.Cache;
import com.winupon.andframe.bigapple.cache.core.LruCache;

/**
 * 安卓 Bitmap图片缓存，使用了软引用，使得更容易被回收
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-9-17 下午8:45:33 $
 */
public class BitmapMemoryCache implements Cache<String, Bitmap> {
    private static LruCache<String, SoftReference<Bitmap>> bitmapMemoryCache;

    public BitmapMemoryCache(int size) {
        bitmapMemoryCache = new LruCache<String, SoftReference<Bitmap>>(size) {
            @Override
            protected int sizeOf(String key, SoftReference<Bitmap> bitmapRef) {
                return bitmapRef.get().getRowBytes() * bitmapRef.get().getHeight();
            }
        };
    }

    @Override
    public Bitmap put(String key, Bitmap bitmap) {
        SoftReference<Bitmap> bitmapRef = bitmapMemoryCache.put(key, new SoftReference<Bitmap>(bitmap));
        return null != bitmapRef ? bitmapRef.get() : null;
    }

    @Override
    public Bitmap get(String key) {
        SoftReference<Bitmap> bitmapRef = bitmapMemoryCache.get(key);
        return null != bitmapRef ? bitmapRef.get() : null;
    }

    @Override
    public Bitmap remove(String key) {
        SoftReference<Bitmap> bitmapRef = bitmapMemoryCache.remove(key);
        return null != bitmapRef ? bitmapRef.get() : null;
    }

    @Override
    public void removeAll() {
        bitmapMemoryCache.evictAll();
    }

    @Override
    public void destroy() {
        bitmapMemoryCache.evictAll();
        bitmapMemoryCache = null;
    }

    @Override
    public int maxSize() {
        return bitmapMemoryCache.maxSize();
    }

    @Override
    public int size() {
        return bitmapMemoryCache.size();
    }

}
