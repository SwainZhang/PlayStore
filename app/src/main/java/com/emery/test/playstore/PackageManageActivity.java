package com.emery.test.playstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import adapter.ApkManagerAdapter;
import base.LoadingPager;
import base.SimpleTitleActivity;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import domain.AppInfoData;
import eventbus.ApkDeletEvent;
import utils.ApkSearchUtils;
import utils.commonUtils;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public class PackageManageActivity extends SimpleTitleActivity {

    private List<AppInfoData> mAppInfoDatas;
    private ApkManagerAdapter mApkManagerAdapter;
    private ListView mListview;

    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        EventBus.getDefault().register(this);
        System.out.println("---------------TestApkFile---------");
        ApkSearchUtils utils=new ApkSearchUtils(this);
        try {
            mAppInfoDatas = utils.FindAllAPKFile(new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath()));
            for (AppInfoData  da: mAppInfoDatas) {
                commonUtils.syso("--------",da.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {

        mListview = new ListView(UIUtils.getContext());
        mApkManagerAdapter = new ApkManagerAdapter(mAppInfoDatas, mListview);
        mListview.setAdapter(mApkManagerAdapter);

        return mListview;

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void deleteApkAndShowDialog(final ApkDeletEvent event){
            new AlertDialog.Builder(PackageManageActivity.this)
                    .setTitle("删除安装包")
                    .setMessage("你将删除安装包文件")
                    .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            File file =new File(event.getPath());
                            file.delete();
                            Toast.makeText(PackageManageActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            mAppInfoDatas.remove(event.getPosition());
                            mApkManagerAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();

    }

    @Override
    protected void onStop() {
        super.onStop();
     EventBus.getDefault().unregister(this);
    }
}
