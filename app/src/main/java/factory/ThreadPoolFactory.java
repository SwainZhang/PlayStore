package factory;

import manager.ThreadPoolProxy;

/**
 * @author Administrator
 * @time 2016/8/27 14:18
 * @des 创建线程池
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ThreadPoolFactory {
    static ThreadPoolProxy mNormalThreadPool;
    static ThreadPoolProxy mDownLoadThreadPool;

    //创建一个普通的线程池
    public static ThreadPoolProxy getNormalThreadPool(){
        if(mNormalThreadPool==null){
            synchronized (ThreadPoolProxy.class){
                if(mNormalThreadPool==null) {
                    mNormalThreadPool = new ThreadPoolProxy(5, 5, 3000);
                }
            }
        }
        return  mNormalThreadPool;
    }


    //创建一个下载使用的线程池
    public static ThreadPoolProxy getDownLoadThreadPool(){
        if(mDownLoadThreadPool==null){
            synchronized (ThreadPoolProxy.class){
                if(mDownLoadThreadPool==null) {
                    mDownLoadThreadPool = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }
        return  mDownLoadThreadPool;
    }
}
