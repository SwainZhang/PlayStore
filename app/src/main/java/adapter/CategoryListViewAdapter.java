package adapter;

import android.widget.AbsListView;

import java.util.List;

import base.BaseHolder;
import base.SuperBaseAdapter;
import domain.CategoryInfoBean;
import holder.CategoryHolder;
import holder.CategoryTitleHolder;

/**
 * @author Administrator
 * @time 2016/9/1 16:47
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CategoryListViewAdapter extends SuperBaseAdapter<CategoryInfoBean> {
    private List<CategoryInfoBean> data;
    private CategoryInfoBean mBean;

    public CategoryListViewAdapter(List<CategoryInfoBean> dataResource, AbsListView listView) {
        super(dataResource, listView);
        this.data=dataResource;
    }

    @Override
    public BaseHolder<CategoryInfoBean> getSpecialHolder(int position) {
        mBean = data.get(position);
        if (mBean.isTitle()) {//这个item是title 类型的
            return new CategoryTitleHolder();
        } else if (data.get(position).getTitle() == null) {
            return new CategoryHolder();
        }
        return  new CategoryHolder();
    }
    @Override
    public boolean hasMoreData() {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;//2+1=3种type
    }

    @Override
    public int getNormalViewType(int position) {//这里根据Range=0---getViewTypeCount-1的值这里可以返回0,1,2
        if(data.get(position).isTitle()){
            return super.getNormalViewType(position)+1;//2 ,原来已经有0和1
        }
        return super.getNormalViewType(position);//原来的1
    }
}
