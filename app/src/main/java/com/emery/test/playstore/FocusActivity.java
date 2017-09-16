package com.emery.test.playstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import base.LoadingPager;
import base.SimpleTitleActivity;
import domain.AppFocusBeanManager;
import domain.AppInfoBean;
import holder.AppDetailInfoHolder;
import utils.UIUtils;

/**
 * Created by MyPC on 2017/3/26.
 */

public class FocusActivity extends SimpleTitleActivity  {

    private List<AppInfoBean> mFocusBean;

    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        mFocusBean = AppFocusBeanManager.getInstance().getFocusBean();
        if (mFocusBean != null && mFocusBean.size() != 0) {
            return LoadingPager.LoadingDataResult.SUCCESS;
        }

        return LoadingPager.LoadingDataResult.EMPTY;
    }

    @Override
    public View onSuccessView() {
        final LinearLayout inflate = (LinearLayout) LayoutInflater.from(UIUtils.getContext()).inflate(R
                        .layout.activity_focus,
                null);
        final ListView lv_focus = (ListView) inflate.findViewById(R.id.focus_lv);
        final MyAdapter myAdapter = new MyAdapter();
        lv_focus.setAdapter(myAdapter);
        lv_focus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FocusActivity.this);
                builder.setTitle("取消收藏")
                        .setMessage("您将移除该条收藏")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFocusBean.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });


        return inflate;
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mFocusBean.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //信息部分内容
            AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
            View holderView = appDetailInfoHolder.getHolderView();
            appDetailInfoHolder.setDataAndRefreshHolderView(mFocusBean.get(position));
            return holderView;
        }


    }
}