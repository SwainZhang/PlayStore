package adapter;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.emery.test.playstore.AppDetailActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import base.BaseHolder;
import base.SuperBaseAdapter;
import domain.AppInfoBean;
import domain.HomeBean;
import holder.AppItemHolder;
import manager.DownLoadAppManager;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/27 15:09
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HomeListViewAdapter extends SuperBaseAdapter<AppInfoBean> {
    int flag=-1;
    List<AppInfoBean> data=null;//新加载的数据
    List<AppInfoBean> datasource;


    public List<AppItemHolder> getHolder() {
        return holder;
    }

    List<AppItemHolder> holder =new ArrayList<>();
    public HomeListViewAdapter(List dataResource, AbsListView listView) {
        super(dataResource,listView);
        this.datasource=dataResource;
    }

    @Override
    public BaseHolder<AppInfoBean> getSpecialHolder(int postion) {
        AppItemHolder mItemHolder = new AppItemHolder();
        holder.add(mItemHolder);
        //初始化holder的时候绑定observer
        DownLoadAppManager.getInstance().addObserver(mItemHolder);
        return mItemHolder;

    }

    @Override
    public List<AppInfoBean> loadMore() throws Exception {
        SystemClock.sleep(2000);
        return loadMoreInHomeAdapter(datasource.size());
    }

    /**
     *
     * @param index 根据索引值来加载更多的数据 index =0,20,40
     * @return 返回新加载的数据的list<?>
     */
    int i=0;
    public List<AppInfoBean> loadMoreInHomeAdapter(int index) {


        //真正加载数据

        String url= MyConstant.BASEURL+"home?index="+ String.valueOf(index);
        JSONObject jsonRequest=null;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                LogUtils.sf(response.toString());
                Gson gson=new Gson();
                HomeBean bean= gson.fromJson(response.toString(), HomeBean.class);//得到了bean 的数据

                if(bean==null){
                    flag=1;//返回加载为空的状态
                }
                if(bean.list==null||bean.list.size()==0){
                    flag=1;//返回加载为空的状态
                }

                //拿到数据
                data = bean.list;


                LogUtils.sf("HomeListViewAdapter--loadmore"+data.size() + "");

            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--"+error.getMessage());
            }
        });

        RequestQueue queue= Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);

        return  data;

    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(UIUtils.getContext(),AppDetailActivity.class);
        intent.putExtra("packageName",datasource.get(position-1).packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }


}
