package com.emery.test.playstore;

import android.view.View;

import base.LoadingPager;
import base.SimpleTitleActivity;
import holder.AboutUsHolder;

/**
 * Created by MyPC on 2016/12/20.
 */

public class AboutActivity extends SimpleTitleActivity {
    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new AboutUsHolder().getHolderView();
    }
}
