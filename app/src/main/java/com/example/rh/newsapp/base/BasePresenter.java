package com.example.rh.newsapp.base;

import java.lang.ref.WeakReference;

/**
 * @author RH
 * @date 2018/3/20
 */
public class BasePresenter<V> {
    private WeakReference<V> weakReference;

    /**
     * 进行关联
     */
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    protected V getview() {
        return weakReference.get();
    }
}
