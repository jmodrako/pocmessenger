package com.nokia.nokiamessenger;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class BasePresenter<View> {

    @Nullable private View view;

    @CallSuper
    public void attachView(@NonNull View view) {
        this.view = view;
    }

    @NonNull
    protected View view() {
        return view == null ? emptyView() : view;
    }

    protected abstract View emptyView();

    @CallSuper
    public void detachView() {
        view = null;
    }
}
