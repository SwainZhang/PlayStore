package com.emery.test.playstore;

import android.view.View;

import base.LoadingPager;
import base.SimpleTitleActivity;
import holder.LeftPersonHolder;


/**
 * Created by MyPC on 2017/1/5.
 */

public class LeftPersonActivity extends SimpleTitleActivity {
    @Override
    public LoadingPager.LoadingDataResult onLoadData() {

        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new LeftPersonHolder().getHolderView();
    }


}
