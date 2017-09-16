package factory;


import android.support.v4.util.SparseArrayCompat;

import base.BaseFragment;
import fragment.AppFragment;
import fragment.CategoryFragment;
import fragment.GameFragment;
import fragment.HomeFragment;
import fragment.HotFragment;
import fragment.RecommedFragment;
import fragment.SubjectFragment;

/**
 * @author Administrator
 * @time 2016/8/25 10:24
 * @des 创建fragment的工厂(在mainactivity 里 pagerselected 和 为fragment设置的mainfragmentadapter 会使用）
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME=0;
    public static final int FRAGMENT_APP=1;
    public static final int FRAGMENT_GAME=2;
    public static final int FRAGMENT_SUBJECT=3;
    public static final int FRAGMENT_RECOMMEND=4;
    public static final int FRAGMENT_CATEGORY=5;
    public static final int FRAGMENT_HOT=6;


    // public static Map<Integer,Fragment> mCacheFragmentMap=new HashMap<Integer,Fragment>();//缓存fragment

    //用sparseArray代替hashmap,
    public static SparseArrayCompat<BaseFragment> mCacheFragmentMap=new SparseArrayCompat<BaseFragment>();

    public static BaseFragment getFragment(int position){


        BaseFragment fragment=null;

       /* if(mCacheFragmentMap.containsKey(position)){
            fragment=mCacheFragmentMap.get(position);//posiotn的fragment存在就取出
            return fragment;
        }*/

        BaseFragment temp_fragment = mCacheFragmentMap.get(position);//取出fragment
        if(temp_fragment!=null){//说明存在
            fragment=temp_fragment;
            return  fragment;
        }

        switch (position) {
            case FRAGMENT_HOME://主页
                fragment =new HomeFragment();
                break;
            case FRAGMENT_APP://APP
                fragment =new AppFragment();
                break;
            case FRAGMENT_GAME://游戏
                fragment= new GameFragment();
                break;
            case FRAGMENT_SUBJECT://主题
                 fragment =new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND://推荐
                fragment=new RecommedFragment();
                break;
            case FRAGMENT_CATEGORY://分类
                fragment=new CategoryFragment();
                break;
            case FRAGMENT_HOT://排行
                fragment=new HotFragment();
                break;

            default:
                break;
        }

        mCacheFragmentMap.put(position,fragment);//将frament缓存起来
        return  fragment;
    }
}
