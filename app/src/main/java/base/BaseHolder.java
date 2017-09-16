package base;

import android.view.View;

/**
 * @author Administrator
 * @time 2016/8/27 17:31
 * @des ListView的holder的基类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public abstract  class BaseHolder<T> {

    public View getHolderView() {
        return mHolderView;
    }

    public View mHolderView;

        //holder必须持有孩子对象

        private T mData;

        public BaseHolder(){
        //初始根化布局
        mHolderView=initHolderView();
        //绑定Tag
        mHolderView.setTag(this);//自己有孩子，就可以做holder
    }

    /**
     *
     * @return  初始item 的根布局和拿到根布局的child
     */
    public  abstract View initHolderView();

    /**
     *
     * @param itemData 设置数据刷新视图
     */
    public void setDataAndRefreshHolderView(T itemData) {
        //保存数据
        mData = itemData;
        //为holder的child设置数
        refreshHolderView(itemData);
    }

    /**
     *
     * @param itemData 设置数据给holder的child
     */
    public abstract void refreshHolderView(T itemData);


}
