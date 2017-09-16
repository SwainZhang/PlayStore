package base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import com.emery.test.playstore.R;

import factory.ThreadPoolFactory;
import utils.LogUtils;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/25 13:20
 * @des 控制每个fragment都有的几个View和数据的显示，子类可以专注数据的获取
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

      /*
             任何应用其实就只有4种页面类型
             ① 加载页面
             ② 错误页面
             ③ 空页面
             ④ 成功页面

             ①②③三种页面一个应用基本是固定的
             每一个fragment/activity对应的页面④就不一样
             进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
             */
public abstract class LoadingPager extends FrameLayout {
    public static  final int STATE_ERROR=0;
    public static  final int STATE_EMPTY=1;
    public static  final int STATE_LOADING=2;
    public static  final int STATE_SUCCESS=3;
    public static final int	STATE_NONE		= -1;			// 默认情况
    public   int STATE_CURRENT=STATE_NONE; //记录当前的状态(特别注意：这个不可以写成static（其他的常量无所谓）,那样就不能使得每个fragment都有自己的一个current（通过basefragment的继承关系来实现的），后面的fragment也就不能更新数据了)

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;


    public void setPosition(int position) {
        this.position = position;
    }

    private int position ;


    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isFirst() {
        return isFirst;
    }

    private boolean isFirst=true;
    public LoadingPager(Context context) {//不会再XML 中使用
        super(context);
        initCommonView();
        refreshUI(1);
        //loadData();

    }


    public int getPosition() {
        return position;
    }

    /**
     * @des 第一次初始化的时候，每次加载数据成功都会调用此方法刷新UI
     */
    private void refreshUI(int i) {//这里想当于我们平时在framlayout 的xml中控制view的显示
   //System.out.println("第"+i+"次刷新UI");
        //每次都先会走（）里的判断，但是状态只会一种所以最后也只会有一种View显示出来
       mLoadingView.setVisibility((STATE_CURRENT==STATE_LOADING||STATE_CURRENT==STATE_NONE) ? View.VISIBLE: View.GONE);
       mErrorView.setVisibility(STATE_CURRENT==STATE_ERROR ? View.VISIBLE: View.GONE);
       mEmptyView.setVisibility(STATE_CURRENT==STATE_EMPTY ? View.VISIBLE: View.GONE);
        // ④ 加载数据成功页面
        if(mSuccessView==null && STATE_CURRENT==STATE_SUCCESS){
            //创建成功视图,但是目前不知道子类的加载数据成功后View的类型
            mSuccessView =initSuccessView();
            this.addView(mSuccessView);
        }
        if(mSuccessView!=null ){
            mSuccessView .setVisibility(STATE_CURRENT==STATE_SUCCESS ? View.VISIBLE: View.GONE);
        }
    }


    //把我们每个fragment都会有的几个页面初始化，相当于在framlayout中加了几个控件，最后在basefragment中显示出来
    private void initCommonView() {
        // ① 加载页面
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading,null);
        this.addView(mLoadingView);//加入到framelayout中
        // ② 错误页面
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error,null);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        this.addView(mErrorView);
        // ③ 空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty,null);
        this.addView(mEmptyView);
        // ④ 成功页面 这个时候没有数据所以并不知道成功页面是什么样子，

    }

    // 数据加载的流程
            /*
             ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
             ② 异步加载数据  -->显示加载视图
             ③ 处理加载结果
             ① 成功-->显示成功视图
             ② 失败
             ① 数据为空-->显示空视图
             ② 数据加载失败-->显示加载失败的视图
             */

    /**
     * des 暴露给外界加载数据(每个fragment第一次加载数据）
     */
    public void loadData() {
      setData();

    }

    private void setData() {
        LogUtils.sf("开始加载数据了");
        if (STATE_CURRENT != STATE_SUCCESS && STATE_CURRENT != STATE_LOADING) {//确保处于加载状态的fragment不会再次加载

            //保证每次加载数据都是State_loading,而不是上次的状态
            int state=STATE_LOADING;
            STATE_CURRENT = state;
            refreshUI(2);
            //异步加载数据
            //new Thread(new LoadDataTask()).start();

            ThreadPoolFactory.getNormalThreadPool().execute(new LoadDataTask());
        }
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshUI(3);//主线程更新UI
           //STATE_CURRENT=STATE_NONE;
        }
    };
    class LoadDataTask implements Runnable {

        @Override
        public void run() {
            //加载数据
            LoadingDataResult tempState = initData();//需要返回一个状态来更新UI
            //处理加载结果，刷新UI
            STATE_CURRENT=tempState.getState();
            mHandler.sendEmptyMessageDelayed(0,2000);
            /*UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshUI(3);//主线程更新UI
                   // STATE_CURRENT=STATE_NONE;
                }
            });*/

        }
    }

    /**
     *
     * @return 返回一个状态值,用来更新UI
     */
    public abstract  LoadingDataResult initData();


    /**
     *
     * @return  返回一个成功加载数据页面的视图
     */
    public abstract View initSuccessView();


    /**
     * @dec 返回加载数据成功的三种状态
     */
    public enum LoadingDataResult{
        ERROR( STATE_ERROR  ),EMPTY( STATE_EMPTY  ),SUCCESS( STATE_SUCCESS);

        int state;

        public int getState() {
            return state;
        }

       private  LoadingDataResult(int state) {
            this.state = state;
        }
    }

}
