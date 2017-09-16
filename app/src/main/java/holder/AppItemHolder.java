package holder;

import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import base.BaseHolder;
import domain.AppInfoBean;
import manager.DownLoadAppManager;
import manager.DownLoadInfo;
import utils.commonUtils;
import utils.MyConstant;
import utils.UIUtils;
import views.CircleProgressView;

/**
 * @author Administrator
 * @time 2016/8/27 16:16
 * @des HomeListViewAdapter使用的HomeHolder
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */


public class AppItemHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener,
        DownLoadAppManager.DownloadObserver {

    private ImageView mItemAppinfoIvIcon;
    private TextView mItemAppinfoTvTitle;
    private RatingBar mItemAppinfoRbStars;
    private TextView mItemAppinfoTvSize;
    private LinearLayout mItemAppinfoPcvProgress;
    private TextView mItemAppinfoTvDes;
    public CircleProgressView mCircleProgressView;

    public AppInfoBean getData() {
        return mData;
    }

    private AppInfoBean mData;

    private void assignViews(View view) {
        mItemAppinfoIvIcon = (ImageView) view.findViewById(R.id.item_appinfo_iv_icon);
        mItemAppinfoTvTitle = (TextView) view.findViewById(R.id.item_appinfo_tv_title);
        mItemAppinfoRbStars = (RatingBar) view.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppinfoTvSize = (TextView) view.findViewById(R.id.item_appinfo_tv_size);
        //mItemAppinfoPcvProgress = (LinearLayout) view.findViewById(R.id
        // .item_appinfo_pcv_progress);
        mItemAppinfoTvDes = (TextView) view.findViewById(R.id.item_appinfo_tv_des);
        mCircleProgressView = (CircleProgressView) view.findViewById(R.id
                .item_appinfo_circle_progress);
    }

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);
        //初始化孩子对象
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean itemData) {

        mData = itemData;
        Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL + itemData.iconUrl)
                .placeholder(R.drawable.ic_default).into(mItemAppinfoIvIcon);


        mItemAppinfoTvTitle.setText(itemData.name);
        mItemAppinfoRbStars.setRating(itemData.stars);
        mItemAppinfoTvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), itemData.size));
        // mItemAppinfoPcvProgress
        mItemAppinfoTvDes.setText(itemData.des);

        //不然item在复用的时候会导致其它item用之前的缓存
        mCircleProgressView.setCurProgress(0);
        mCircleProgressView.setTv_des("下载");
        mCircleProgressView.setIvIcon(R.drawable.ic_download);

        mCircleProgressView.setOnClickListener(this);
 /*=============== 根据不同的状态给用户提示 ===使得每个item都有自己的状态============*/
        DownLoadInfo info = DownLoadAppManager.getInstance().getDownloadInfo(itemData);
        refreshCircleProgressUI(info);


    }

    /**
     * 根据状态来执行下一步的行为；
     *
     * @param downloadInfo
     */
    private void downloadBtnClicked(DownLoadInfo downloadInfo) {
        switch (downloadInfo.state) {
            case DownLoadAppManager.STATE_UNDOWNLOAD:
                DownLoadAppManager.getInstance().downLoadApp(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADING:
                DownLoadAppManager.getInstance().pauseDownload(downloadInfo);
                break;
            case DownLoadAppManager.STATE_PAUSEDOWNLOAD:
                DownLoadAppManager.getInstance().downLoadApp(downloadInfo);//继续下载
                break;
            case DownLoadAppManager.STATE_WAITINGDOWNLOAD:
                DownLoadAppManager.getInstance().cancel(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADFAILED:
                DownLoadAppManager.getInstance().downLoadApp(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADED:
                File file = new File(Environment.getExternalStorageDirectory(), downloadInfo
                        .fileName + ".apk");
                commonUtils.installApp(UIUtils.getContext(), file);
                break;
            case DownLoadAppManager.STATE_INSTALLED:
                commonUtils.openApp(UIUtils.getContext(), downloadInfo.packageName);
                break;

            default:
                break;
        }

    }


    @Override
    public void onDownloadInfoChanged(final DownLoadInfo downLoadInfo) {
        //接口回调结果刷新Ui

        //过滤:避免任何一个应用正在下载的时候触发了downloadinfo的状态改变后打开其他的应用的时候，进度条也会refresh
        if (downLoadInfo.packageName != mData.packageName) {
            return;
        }

        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                refreshCircleProgressUI(downLoadInfo);

            }
        });
    }

    public void refreshCircleProgressUI(DownLoadInfo info) {

        switch (info.state) {
            /**
             状态(编程记录)  	|  给用户的提示(ui展现)
             ----------------|----------------------
             未下载			|下载
             下载中			|显示进度条
             暂停下载			|继续下载
             等待下载			|等待中...
             下载失败 			|重试
             下载完成 			|安装
             已安装 			|打开
             */
            case DownLoadAppManager.STATE_UNDOWNLOAD:// 未下载
                mCircleProgressView.setTv_des("下载");
                mCircleProgressView.setIvIcon(R.drawable.ic_download);
                break;
            case DownLoadAppManager.STATE_DOWNLOADING:// 下载中

                // mAppDetailDownloadBtnDownload.setText("显示进度条");
                mCircleProgressView.setIvIcon(R.drawable.ic_pause);
                mCircleProgressView.setProgressEnable(true);
                mCircleProgressView.setMaxProgress(info.maxProgress);
                mCircleProgressView.setCurProgress(info.currentProgress);
                int progress = (int) (info.currentProgress * 100.f / info.maxProgress + .5f);
                mCircleProgressView.setTv_des(progress + "%");

                break;
            case DownLoadAppManager.STATE_PAUSEDOWNLOAD:// 暂停下载
                mCircleProgressView.setIvIcon(R.drawable.ic_resume);
                mCircleProgressView.setTv_des("继续下载");
                break;
            case DownLoadAppManager.STATE_WAITINGDOWNLOAD:// 等待下载
                mCircleProgressView.setIvIcon(R.drawable.ic_pause);
                mCircleProgressView.setTv_des("等待中...");
                break;
            case DownLoadAppManager.STATE_DOWNLOADFAILED:// 下载失败
                mCircleProgressView.setIvIcon(R.drawable.ic_redownload);
                mCircleProgressView.setTv_des("重试");
                break;
            case DownLoadAppManager.STATE_DOWNLOADED:// 下载完成

                mCircleProgressView.setIvIcon(R.drawable.ic_install);
                mCircleProgressView.setTv_des("安装");
                break;
            case DownLoadAppManager.STATE_INSTALLED:// 已安装
                mCircleProgressView.setIvIcon(R.drawable.ic_install);
                mCircleProgressView.setTv_des("打开");
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.item_appinfo_circle_progress: //下载button
                DownLoadInfo downloadInfo = DownLoadAppManager.getInstance().getDownloadInfo(mData);
                downloadBtnClicked(downloadInfo);
                break;
            default:
                break;
        }


    }
}