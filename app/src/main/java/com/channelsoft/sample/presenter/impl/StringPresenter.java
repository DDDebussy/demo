package com.channelsoft.sample.presenter.impl;


import com.channelsoft.sample.model.IStringModel;
import com.channelsoft.sample.model.impl.StringModel;
import com.channelsoft.sample.model.listener.IStringListener;
import com.channelsoft.sample.presenter.IStringPresenter;
import com.channelsoft.sample.view.IStringView;

import java.lang.ref.WeakReference;

/**
 * Created by amgl on 2015/11/28.
 */
public class StringPresenter implements IStringPresenter, IStringListener {
    private WeakReference<IStringView> stringViewRef;
    private IStringModel stringModel;

    public StringPresenter(IStringView stringView) {
        this.stringViewRef = new WeakReference<IStringView>(stringView);
        this.stringModel = new StringModel();
    }

    @Override
    public void setUrl(String url) {
        IStringView stringView = stringViewRef.get();
        if (stringView != null) {
            stringView.showLoding();
            stringModel.load(url, this);
        }
    }

    @Override
    public void onSuccess(String result) {
        IStringView stringView = stringViewRef.get();
        if (stringView != null) {
            stringView.hideLoding();
            stringView.showString(result);
        }
    }

    @Override
    public void onError() {
        IStringView stringView = stringViewRef.get();
        if (stringView != null) {
            stringView.hideLoding();
        }
    }
}
