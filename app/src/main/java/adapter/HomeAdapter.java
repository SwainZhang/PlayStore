package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/23 21:23
 * @des pager sliding tab strip 所有的Fragment的 adapter
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HomeAdapter extends PagerAdapter {
    private final String[] str;

    public  HomeAdapter(String[] str){
        this.str=str;
    }
    @Override
    public int getCount() {
        if(str!=null&str.length>0) {
            return str.length;
        }

        return  0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView tv=new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText(str[position]);
        container.addView(tv);
        return tv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

     //必须重写这个方法 不然会返回为空

    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }
}
