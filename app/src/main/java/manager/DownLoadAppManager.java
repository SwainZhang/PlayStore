package manager;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.AppInfoBean;
import factory.ThreadPoolFactory;
import utils.CommonUtils;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/5 12:19
 * @des  下载管理器，需要持续的记录当前下载的状态
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class DownLoadAppManager {
    private static DownLoadAppManager downLoadManager;
    private URL mURL;
    private Map<String,DownLoadInfo> mDownLoadMap=new HashMap<>();//记录正在下载的downloadinfo

    public static final int				STATE_UNDOWNLOAD		= 0;// 未下载
    public static final int				STATE_DOWNLOADING		= 1;// 下载中
    public static final int				STATE_PAUSEDOWNLOAD		= 2;// 暂停下载
    public static final int				STATE_WAITINGDOWNLOAD	= 3;// 等待下载
    public static final int				STATE_DOWNLOADFAILED	= 4;// 下载失败
    public static final int				STATE_DOWNLOADED		= 5;// 下载完成
    public static final int				STATE_INSTALLED			= 6;// 安装完成

    public static DownLoadAppManager getInstance() {
        if (downLoadManager == null) {
            synchronized (DownLoadAppManager.class) {
                if (downLoadManager == null) {
                    downLoadManager = new DownLoadAppManager();

                }
            }

        }
        return downLoadManager;
    }

    public void downLoadApp(DownLoadInfo info) {

        mDownLoadMap.put(info.packageName,info);//downloadinfo是在detailactivity的时候传递进来的
        //未下载状态
        info.state=STATE_UNDOWNLOAD;
        notifyObservers(info);
        //等待状态，本来应该在线程池里的（这里是设置3个的工作线程满了就进不去task了，所以先写等待
        info.state=STATE_WAITINGDOWNLOAD;
        notifyObservers(info);
        DownLoadTask downLoadTask = new DownLoadTask(info);
        info.task=downLoadTask;//用于取消下载（移出线程池）
        ThreadPoolFactory.getDownLoadThreadPool().execute(downLoadTask);

    }

    /**
     * 外界需要下载状态的时候被调用
     * @param itemData 将下载的状态暴露给holder
     */
    public DownLoadInfo getDownloadInfo(AppInfoBean itemData) {

        DownLoadInfo info = new DownLoadInfo();

        //统一给download属性赋值初始化
        generateDownloadInfo(itemData, info);

        /*=============== 未按下下载按钮的几种状态（静态的） ======begin=========*/

        //未下载(默认情况)
        info.state = STATE_UNDOWNLOAD;

        if(CommonUtils.isInstalled(UIUtils.getContext(),itemData.packageName)) {
           //已经安装
           info.state = STATE_INSTALLED;
            return  info;

            }

        //下载完成
        File file=new File(Environment.getExternalStorageDirectory(),itemData.name+".apk");
        if(file.exists()){
            if(file.length()==itemData.size){
                info.state = STATE_DOWNLOADED;
                return  info;

            }
        }

         /*=============== 未按下下载按钮的几种状态（静态的） ======end=========*/

        /*###############按下下载按钮的几种状态###begin############*/
        //（下载中 ，暂停下载，等待下载，下载失败）

        DownLoadInfo downLoadInfo = mDownLoadMap.get(itemData.packageName);
        if(downLoadInfo!=null){
            //说明是按下后四种状态之一 （这几种状态在进入下载任务的方法时才改变赋值了）
            return  downLoadInfo;
        }

       /*###############按下下载按钮的几种状态###end############*/
        return info;
    }

    /**
     * 添加downloadInfo类的属性
     * @param itemData 数据源
     * @param info   下载相关信息
     */
    public void generateDownloadInfo(AppInfoBean itemData, DownLoadInfo info) {
        info.fileName = itemData.name;

        File downloadedFile=new File(Environment.getExternalStorageDirectory(),info.fileName+".apk");

        //继续下载的时候调用
       if(downloadedFile.exists()){
            info.downlOadUrl = MyConstant.DOWNLOADBASEURL + "?name=" + itemData.downloadUrl + "&range" +
                    "="+downloadedFile.length();
            info.currentProgress= (int) downloadedFile.length();
           LogUtils.sf(info.currentProgress+"currentProgress"+itemData.size);
        }else{

        info.downlOadUrl = MyConstant.DOWNLOADBASEURL + "?name=" + itemData.downloadUrl + "&range" + "=0";
            info.currentProgress=0;

           LogUtils.sf(info.downlOadUrl);
        }

        info.packageName=itemData.packageName;
        info.maxProgress=(int)itemData.size;
        info.fileSize=itemData.size;
    }

    /**
     * 暂停下载任务
     * @param downLoadInfo
     */
    public void pauseDownload(DownLoadInfo downLoadInfo) {
        downLoadInfo.state=STATE_PAUSEDOWNLOAD;//在holer里面调用的时候是在下载状态下调用
       notifyObservers(downLoadInfo);
    }

    public void cancel(DownLoadInfo downLoadInfo) {
        ThreadPoolFactory.getNormalThreadPool().removeTask(downLoadInfo.task);
        //更新状态
        downLoadInfo.state=STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
    }


    class  DownLoadTask implements  Runnable{

        public DownLoadInfo downLoadInfo;

        public DownLoadTask(DownLoadInfo downLoadInfo) {
               this.downLoadInfo=downLoadInfo;
       }
        @Override
        public void run() {
            //正在访问网络请求下载apK

            String url=downLoadInfo.downlOadUrl;
            try {
                //走到run状态实际上已经在工作线程里面了
                downLoadInfo.state=STATE_DOWNLOADING;

                notifyObservers(downLoadInfo);

                mURL = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) mURL.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("GET");
                if(urlConnection.getResponseCode()==200){
                    InputStream inputStream = urlConnection.getInputStream();
                    //  "/data/data/"+ UIUtils.getContext().getPackageName()
                    File file=new File(Environment.getExternalStorageDirectory(),downLoadInfo.fileName+".apk");
                    FileOutputStream outputStream=new FileOutputStream(file,true);
                    byte [] buffer=new byte[1024*2];
                    int len=-1;
                    boolean isPause=false;
                    while ((len=inputStream.read(buffer))!=-1 ){

                        LogUtils.sf(downLoadInfo.state+"");
                        if(downLoadInfo.state==STATE_PAUSEDOWNLOAD){
                            isPause=true;
                            break;
                        }
                        outputStream.write(buffer,0,len);
                        outputStream.flush();


                        downLoadInfo.currentProgress+=len;//实时进度
                        downLoadInfo.state=STATE_DOWNLOADING;//这里还是下载中
LogUtils.sf(downLoadInfo.currentProgress+"current in "+downLoadInfo.fileSize);
                        notifyObservers(downLoadInfo);
                    }

                    if(isPause){//暂停
                        outputStream.close();
                        inputStream.close();
                        return;
                    }else{
                        downLoadInfo.state=STATE_DOWNLOADED;//下载完成
                        LogUtils.sf("下载成功"+downLoadInfo.fileName);
                        notifyObservers(downLoadInfo);
                    }

                    outputStream.close();
                    inputStream.close();
                }else{
                    LogUtils.sf(getClass().getSimpleName()+"httpConnection请求超时");
                    downLoadInfo.state=STATE_DOWNLOADFAILED;//下载失败
                    notifyObservers(downLoadInfo);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                downLoadInfo.state=STATE_DOWNLOADFAILED;
                notifyObservers(downLoadInfo);
            } catch (IOException e) {
                e.printStackTrace();
                downLoadInfo.state=STATE_DOWNLOADFAILED;
                notifyObservers(downLoadInfo);
            }finally {

            }
        }
    }

    /*-----------------自定义观察者模式-------相当于多个接口回调组合到一起了-------------*/


    /**
     * 接口回调发送变化的状态
     */
   public interface  DownloadObserver {
        void onDownloadInfoChanged(DownLoadInfo downLoadInfo);
    }

    List<DownloadObserver> observers = new ArrayList<DownloadObserver>();//谁实现了DownloadObserver谁接收数据，它就被添加这个集合中



    public void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer))
                observers.add(observer);
        }
    }

    public int countObservers(DownloadObserver observer) {
        return observers.size();
    }


    public synchronized void deleteObserver(DownloadObserver observer) {
        observers.remove(observer);
    }


    public synchronized void deleteObservers(DownloadObserver observer) {
        observers.clear();
    }

    public void notifyObservers(DownLoadInfo downLoadInfo) {
            for (DownloadObserver observer : observers) {
                observer.onDownloadInfoChanged(downLoadInfo);
            }
        }
    }





