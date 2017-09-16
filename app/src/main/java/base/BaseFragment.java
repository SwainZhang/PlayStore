package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import utils.UIUtils;

/**
             * @author Administrator
             * @time 2016/8/25 13:11
             * @des ${TODO}
             * @updateAuthor $Author$
             * @updateDate $Date$
             * @updateDes ${TODO}
             */


             //页面显示分析
             //Fragment共性-->页面共性-->视图的展示

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
public abstract  class BaseFragment  extends Fragment {

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    private LoadingPager mLoadingPager;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mLoadingPager==null){//第一次执行
                   mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                       @Override
                       public LoadingDataResult initData() {//现在basefragment 本身是一个父类不知道子类有什么数据,但是子类肯定有数据，必须实现。每个fragment都应该有的一部分显示出来



                           //现在basefragment 本身是一个父类不知道子类有什么数据,但是子类肯定有数据，必须实现。每个fragment都应该有的一部分显示出来
                           return BaseFragment.this.initData();
                       }

                       @Override
                       public View initSuccessView() {
                           return BaseFragment.this.initSuccessView();
                       }
                   };
               }

       //mLoadingPager.loadData();//更新数据(这里不合适，应该在fragment选中的时候加载数据）
        return mLoadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mLoadingPager.loadData();//更新数据(这里也不合适，应该在fragment选中的时候加载数据）
    }

    /**
     * @call 加载数据的时候调用
     * @des 这里是每个fragment 真正来加载数据的部分。
     * @return 真正来加载数据的部分。返回一个状态值,用来更新UI
     */
    public abstract LoadingPager.LoadingDataResult initData();

    /**
     *
     * @return  返回一个成功加载数据页面的视图
     */
    public abstract View initSuccessView();
}
