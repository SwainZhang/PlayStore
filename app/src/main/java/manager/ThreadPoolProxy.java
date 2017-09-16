package manager;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @time 2016/8/27 12:47
 * @des  管理创建线程池（看ThreadPoolFactory），执行任务，提交任务
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ThreadPoolProxy {
    ThreadPoolExecutor mThreadPoolExecutor; //只需创建一次
    int coreThreadSize;
    int maxPoorSize;
    long keepAliveTime;


    public ThreadPoolProxy(int coreThreadSize, int maxPoorSize, long keepAliveTime) {
        this.coreThreadSize = coreThreadSize;
        this.maxPoorSize = maxPoorSize;
        this.keepAliveTime = keepAliveTime;
        initThreadPoolProxy();
    }

    private  ThreadPoolExecutor initThreadPoolProxy(){

        if(mThreadPoolExecutor==null) {
           synchronized (ThreadPoolProxy.class){
               if(mThreadPoolExecutor==null) {
                   TimeUnit unit=TimeUnit.MILLISECONDS;
                   BlockingDeque<Runnable> workQueue =new LinkedBlockingDeque<Runnable>();//无界队列
                   ThreadFactory factory= Executors.defaultThreadFactory();//默认线程工厂
                   RejectedExecutionHandler handler=new ThreadPoolExecutor.AbortPolicy();//用于被拒绝任务的处理程序

                   mThreadPoolExecutor = new ThreadPoolExecutor(
                           coreThreadSize,//核心线程，当线程池创建的时候创建的待命的工作线程
                           maxPoorSize,
                           keepAliveTime,
                           unit,
                           workQueue,
                           factory,
                           handler
                   );
               }
           }

        }
        return  mThreadPoolExecutor;
    }


    /**
     * @param task 执行线程
     */
    public void execute(Runnable task){
        mThreadPoolExecutor.execute(task);
    }
    /**
     * @param task 提交线程
     */
    public void submit(Runnable task){
       mThreadPoolExecutor.submit(task);
    }

    /**
     *
     * @param task 移除线程
     */
    public void removeTask(Runnable task){
        mThreadPoolExecutor.remove(task);
    }

}
