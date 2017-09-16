package com.emery.test.playstore;

import android.view.View;
import android.view.WindowManager;

import base.LoadingPager;
import base.SimpleTitleActivity;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eventbus.FeedBackEvent;
import holder.FeedBackHolder;

/**
 * Created by MyPC on 2016/12/20.
 */

public class FeedbackActivity extends SimpleTitleActivity {

    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        EventBus.getDefault().register(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new FeedBackHolder().getHolderView();
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void finish(FeedBackEvent event){
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
