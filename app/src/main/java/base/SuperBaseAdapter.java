package base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import factory.ThreadPoolFactory;
import holder.LoadMOreHolder;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/27 15:40
 * @des  针对所有的Fragment 的llistView抽取的adapter
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public abstract  class  SuperBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final int VIEWTYPE_LOADMORE = 0;
    private static final int VIEWTYPE_NORMAL = 1;
    private List<T> dataResource=new ArrayList<>();
    private LoadMOreHolder mLoadmoreholder;

    public SuperBaseAdapter(List<T> dataResource, AbsListView listView){
        this. dataResource=dataResource;
        listView.setOnItemClickListener(this);//listview item   的点击事件
    }

    @Override
    public int getCount() {
        if(dataResource!=null){
            return  dataResource.size()+1;//多了一个加载更多的itemview
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return dataResource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder<T> holder=null;

        if(convertView!=null){
            holder=(BaseHolder)convertView.getTag();

        }else{
            if(getItemViewType(position)==VIEWTYPE_LOADMORE){
                //应该返回一个加载更多的holder

                holder= (BaseHolder<T>) getLoadMOreHolder();
            }else {
                holder = getSpecialHolder(position);//可以直接new baseholder 但是马上就要实现里面的方法，但是这应该是baseholder不同的子类该完成的事情,

            }
            }



      /*--------------- 加了一个加载更多后数据展示 ---------------*/
        if(getItemViewType(position)==VIEWTYPE_LOADMORE){
            //到了这里mLoadmoreholder肯定不是空，应该拉取更多的数据

            if(hasMoreData()){//有加载更多
                loadMoreData();//获得加载更多的数据，并且让加载更多视图出现
            }else{
                //没有加载更多的时候，就不让加载更多的视图出现。
                mLoadmoreholder.setDataAndRefreshHolderView(LoadMOreHolder.STATE_NONE);
            }


        }else {
            holder.setDataAndRefreshHolderView(getItem(position));//没加加载更多的itemView数据展示

        }



        return holder.mHolderView;
    }

    /**
     *
     * @return 默认有加载更多，子类可以重写该方法，取消加载更多的itemview；
     */
    public boolean hasMoreData() {
        return  true;
    }

    //加载更多的数据
    private void loadMoreData() {

        mLoadmoreholder.setDataAndRefreshHolderView(LoadMOreHolder.STATE_LOADING);
        ThreadPoolFactory.getNormalThreadPool().execute(new LoadMoreDataTsk());
    }

    private class LoadMoreDataTsk implements Runnable {
        List<T> loadMore  =new ArrayList<>();
       int first=0;
        @Override
        public void run() {

            int state=LoadMOreHolder.STATE_LOADING;

            try {
                //加载更多

                loadMore = loadMore();
 System.out.println("----loadmore----"+loadMore);
                //处理加载数据的结果
                if (loadMore == null) {//没有更多数据

                    state=LoadMOreHolder.STATE_NONE;
                }else if(loadMore.size()<20){//服务器指定返回20条
                    state=LoadMOreHolder.STATE_NONE;
                }else{
                    state=LoadMOreHolder.STATE_LOADING;
                }


            } catch (Exception e) {
                state=LoadMOreHolder.STATE_RETRY;
                e.printStackTrace();
            }

            final int mState=state;
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //更新loadMore视图
                 //  mLoadmoreholder.setDataAndRefreshHolderView(mState);//发现getView()里会判断是不是这个itemview ,这里没有必要
                    //更新listview
                    if(loadMore!=null) {
                        dataResource.addAll(loadMore);//将加载的数据加入原来的list
                        notifyDataSetChanged();//刷新数据
                    }
                }
            });



        }
    }

    // TODO: 2016/8/28 真正加载更多的数据
    public List<T> loadMore() throws Exception {
        System.out.println("==superbaseadapter=="+dataResource);
        return  null;
    };

    protected  LoadMOreHolder getLoadMOreHolder(){
        mLoadmoreholder = new LoadMOreHolder();
        return mLoadmoreholder;
    }




    /*---------重写方法--------ListView 可以显示多种viewType----begin----------------*/


    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//默认只有一种
    }

    @Override
    public int getItemViewType(int position) {//返回不同类型的item view(返回值类型 0~getViewTypeCount()-1)
        //如果滑动到底部，那就应该显示加载更多

        if(position==getCount()-1){

             return  VIEWTYPE_LOADMORE;//0
        }else{
            return  getNormalViewType(position);
        }

    }




    /*---------重写方法--------ListView 可以显示多种viewType----end----------------*/

    /**
     *
     * @return 想要得到不同类型的itemViewType 就要重写该方法，默认有两种。一种是normal 一种是loadingmore,
     */

    public int getNormalViewType(int position) {
        return  VIEWTYPE_NORMAL;//1
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(getItemViewType(position)==VIEWTYPE_LOADMORE){//说明点击了。加载更多或者retry
            try {
                loadMoreData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            onNormalItemClick(parent,view,position,id);//点击listview的item
        }
    }

    /**
     *  子类重新此方法完成listview 的 item 的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    public abstract BaseHolder<T> getSpecialHolder(int position);

}
