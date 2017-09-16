package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import factory.FragmentFactory;
import utils.LogUtils;

/**
 * @author Administrator
 * @time 2016/8/25 10:56
 * @des  FragmentPagerAdapter 会缓存fragment ，不会重复创建（如果fragment)比较多不建议使用
 *       FragmentStatePagerAdapter 不会缓存Fragment,但是会缓存Fragment 的状态
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    private String[] str_title;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public MainFragmentAdapter(FragmentManager fm, String[] str) {
        super(fm);
        this.str_title=str;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FragmentFactory.getFragment(position);
        LogUtils.sf("初始化"+fragment.getClass().getSimpleName());

        return fragment;
    }  
    @Override
    public int getCount() {
        if (str_title != null) {

            return  str_title.length;
        }
        return 0;
    }
    //必须重写这个方法 不然会返回为空

    @Override
    public CharSequence getPageTitle(int position) {
        return str_title[position];
    }
}
