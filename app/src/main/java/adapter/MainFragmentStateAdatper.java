package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import factory.FragmentFactory;
import utils.LogUtils;

/**
 * @author Administrator
 * @time 2016/8/25 12:26
 * @des FragmentStatePagerAdapter,会不断的创建，不会缓存Fragment适合大量的Fragment，但是我们可以
 *      在FragmentFactory中缓存
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MainFragmentStateAdatper extends FragmentStatePagerAdapter {
    private String[] str_title;

    public MainFragmentStateAdatper(FragmentManager fm) {
        super(fm);
    }
    public MainFragmentStateAdatper(FragmentManager fm, String[] str) {
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
