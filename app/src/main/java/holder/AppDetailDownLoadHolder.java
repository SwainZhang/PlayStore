package holder;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emery.test.playstore.R;

import java.io.File;

import base.BaseHolder;
import domain.AppFocusBeanManager;
import domain.AppInfoBean;
import manager.DownLoadAppManager;
import manager.DownLoadInfo;
import utils.CommonUtils;
import utils.commonUtils;
import utils.LogUtils;
import utils.UIUtils;
import views.ProgressButton;

/**
 * @author Administrator
 * @time 2016/9/2 16:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppDetailDownLoadHolder extends BaseHolder<AppInfoBean>  implements View.OnClickListener ,DownLoadAppManager.DownloadObserver
{
    private Button mAppDetailDownloadBtnFavo;
    private Button mAppDetailDownloadBtnShare;
    private ProgressButton mAppDetailDownloadBtnDownload;
    private AppInfoBean mData;

    private void assignViews(View view) {
        mAppDetailDownloadBtnFavo = (Button)view. findViewById(R.id.app_detail_download_btn_favo);
        mAppDetailDownloadBtnShare = (Button) view.findViewById(R.id.app_detail_download_btn_share);
        mAppDetailDownloadBtnDownload = (ProgressButton)view. findViewById(R.id.app_detail_download_btn_download);
    }


    @Override
    public View initHolderView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean itemData) {
        mData = itemData;

        mAppDetailDownloadBtnFavo.setOnClickListener(this);
        mAppDetailDownloadBtnShare .setOnClickListener(this);
        mAppDetailDownloadBtnDownload   .setOnClickListener(this);

        //根据返回的下载的状态给用户ui提示，用户根据提示点按钮该完成的事件
        DownLoadInfo downloadInfo = DownLoadAppManager.getInstance().getDownloadInfo(itemData);

        refreshProgressBtnUI(downloadInfo);


    }
    @Override
    public void onDownloadInfoChanged(final DownLoadInfo downLoadInfo) {

        //接口回调结果刷新Ui

        //过滤:避免任何一个应用正在下载的时候触发了downloadinfo的状态改变后打开其他的应用的时候，进度条也会refresh
       if(!downLoadInfo.packageName.equals(mData.packageName)){
            return;
        }

        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {

                refreshProgressBtnUI(downLoadInfo);

            }
        });




    }
    public void refreshProgressBtnUI(DownLoadInfo info) {

       mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
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
                mAppDetailDownloadBtnDownload.setText("下载");

                break;
            case DownLoadAppManager.STATE_DOWNLOADING:// 下载中

                // mAppDetailDownloadBtnDownload.setText("显示进度条");
                mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                mAppDetailDownloadBtnDownload.setProgressEnable(true);
                mAppDetailDownloadBtnDownload.setMax(info.maxProgress);
                mAppDetailDownloadBtnDownload.setProgress(info.currentProgress);
                int progress = (int) (info.currentProgress * 100.f / info.maxProgress + .5f);
                mAppDetailDownloadBtnDownload.setText(progress + "%");

                break;
            case DownLoadAppManager.STATE_PAUSEDOWNLOAD:// 暂停下载
                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case DownLoadAppManager.STATE_WAITINGDOWNLOAD:// 等待下载
                mAppDetailDownloadBtnDownload.setText("等待中...");
                break;
            case DownLoadAppManager.STATE_DOWNLOADFAILED:// 下载失败
                mAppDetailDownloadBtnDownload.setText("重试");
                break;
            case DownLoadAppManager.STATE_DOWNLOADED:// 下载完成
               // mAppDetailDownloadBtnDownload.setProgressEnable(false);
                mAppDetailDownloadBtnDownload.setText("安装");
                break;
            case DownLoadAppManager.STATE_INSTALLED:// 已安装
                mAppDetailDownloadBtnDownload.setText("打开");
                break;

            default:
                break;
        }
    }

    private void openApp(DownLoadInfo downloadInfo) {

        LogUtils.sf("openApp"+downloadInfo.packageName);
        CommonUtils.openApp(UIUtils.getContext(),downloadInfo.packageName);
    }

    private void installApp(DownLoadInfo downLoadInfo) {

        File file=new File(Environment.getExternalStorageDirectory(),downLoadInfo.fileName+".apk");
        commonUtils.installApp(UIUtils.getContext(),file);
    }

    private void cancelDownload(DownLoadInfo downLoadInfo) {
        DownLoadAppManager.getInstance().cancel(downLoadInfo);
    }

    private void pauseDownload(DownLoadInfo downLoadInfo) {
        LogUtils.sf("pauseDownload");
        DownLoadAppManager.getInstance().pauseDownload(downLoadInfo);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.app_detail_download_btn_favo://收藏
                Toast.makeText(UIUtils.getContext(), "收藏", Toast.LENGTH_SHORT).show();
                AppFocusBeanManager.getInstance().setFocusBean(mData);
                break;
            case  R.id.app_detail_download_btn_share: //分享
                Toast.makeText(UIUtils.getContext(), "分享", Toast.LENGTH_SHORT).show();

                break;
            case  R.id.app_detail_download_btn_download: //下载button
                DownLoadInfo downloadInfo = DownLoadAppManager.getInstance().getDownloadInfo(mData);
                downloadBtnClicked(downloadInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 根据状态来执行下一步的行为；
     * @param downloadInfo
     */
    private void downloadBtnClicked(DownLoadInfo downloadInfo) {
        switch (downloadInfo.state) {
            case DownLoadAppManager.STATE_UNDOWNLOAD:
                downLoadApp(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADING:
                pauseDownload(downloadInfo);

                break;
            case DownLoadAppManager.STATE_PAUSEDOWNLOAD:
               downLoadApp(downloadInfo);//继续下载
                break;
            case DownLoadAppManager.STATE_WAITINGDOWNLOAD:
                cancelDownload(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADFAILED:
                downLoadApp(downloadInfo);
                break;
            case DownLoadAppManager.STATE_DOWNLOADED:
                installApp(downloadInfo);
                break;
            case DownLoadAppManager.STATE_INSTALLED:
                openApp(downloadInfo);
                break;

            default:
                break;
        }

    }

    public void downLoadApp(DownLoadInfo downLoadInfo){
       /* //下载信息的配置
        DownLoadInfo downLoadInfo=new DownLoadInfo();
        downLoadInfo.downlOadUrl= MyConstant.DOWNLOADBASEURL+"?name="+mData.downloadUrl+"&range=0";
       // downLoadInfo.dstDirPath="/data/data/"+ UIUtils.getContext().getPackageName()+"/download";

        downLoadInfo.fileName=mData.name;
        downLoadInfo.packageName=mData.packageName;
        //执行下载任务*/
        DownLoadAppManager.getInstance().downLoadApp(downLoadInfo);
    }

    public void addObserverAndRerefresh() {
        DownLoadAppManager.getInstance().addObserver(this);
        // 手动刷新
        DownLoadInfo downLoadInfo = DownLoadAppManager.getInstance().getDownloadInfo(mData);
      // DownLoadAppManager.getInstance().notifyObservers(downLoadInfo);// 方式一(内容观察者自己调用refreshProgressBtnUI）
         refreshProgressBtnUI(downLoadInfo);//方式二（手动调用refreshProgressBtnUI）
    }

}

