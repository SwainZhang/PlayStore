package fragment;

import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import adapter.HomeListViewAdapter;
import base.BaseFragment;
import base.LoadingPager;
import domain.AppInfoBean;
import domain.HomeBean;
import factory.ListViewFactory;
import holder.AppItemHolder;
import holder.PictureHolderView;
import manager.DownLoadAppManager;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/25 10:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HomeFragment extends BaseFragment {
    private int flag=0;
    private List<AppInfoBean> mData;//listview 的数据源
    private List<String>  pictures;//轮播图
    private HomeBean bean;
    private HomeListViewAdapter mHomeListViewAdapter;

    @Override
    public LoadingPager.LoadingDataResult initData() {


        //真正加载数据

        String url= MyConstant.BASEURL+"home?index=0";
        JSONObject jsonRequest=null;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                LogUtils.sf(response.toString());
                Gson gson=new Gson();
                bean= gson.fromJson(response.toString(), HomeBean.class);//得到了bean 的数据

                if(bean==null){
                    flag=1;//返回加载为空的状态
                }
                if(bean.list==null||bean.list.size()==0){
                    flag=1;//返回加载为空的状态
                }

                    //拿到数据
                    mData = bean.list;
                    pictures = bean.picture;

                    LogUtils.sf(mData.size() + "");





                System.out.println("---homebean---"+bean.list.size());
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--"+error.getMessage());
            }
        });

        RequestQueue queue= Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);

        if(flag==1) {
            return LoadingPager.LoadingDataResult.EMPTY;//返回加载为空的状态
        }


       /* HomeProtocol protocol=new HomeProtocol();
         bean = protocol.getDataFromNet(0);
        System.out.println("------"+bean.list.size());
        if(this.bean ==null){
            flag=1;//返回加载为空的状态
        }
        if(this.bean.list==null|| this.bean.list.size()==0){
            flag=1;//返回加载为空的状态
        }

        //拿到数据
        mData = this.bean.list;
        pictures = this.bean.picture;

        LogUtils.sf(mData.size() + "");*/
        return LoadingPager.LoadingDataResult.SUCCESS;//返回加载成功的状态

      }




    @Override
    public View initSuccessView() {
        ListView listView= ListViewFactory.getListView();
        PictureHolderView pictureholder=new PictureHolderView();
        pictureholder.setDataAndRefreshHolderView(bean.picture);
        View holderView=pictureholder.getHolderView();
        listView.addHeaderView(holderView);//得到轮播图
        mHomeListViewAdapter = new HomeListViewAdapter(mData, listView);
        listView.setAdapter(mHomeListViewAdapter);
        return listView;
    }

   @Override
    public void onPause() {
       //移除观察者
        if(mHomeListViewAdapter!=null) {
            List<AppItemHolder> holder = mHomeListViewAdapter.getHolder();
            for (AppItemHolder h : holder) {
                LogUtils.sf("onPause++++mHomeListViewAdapter"+h.toString());
                DownLoadAppManager.getInstance().deleteObserver(h);
            }
        }
       super.onPause();
    }

    @Override
    public void onResume() {

        //再次添加downloadObserver 并且手动刷新状态
        if(mHomeListViewAdapter!=null) {
            List<AppItemHolder> holder = mHomeListViewAdapter.getHolder();//adapter 持有holder 而fragment又持有adpter

            for (AppItemHolder h : holder) {
                LogUtils.sf("onResume\\\\\\mHomeListViewAdapter"+h.toString());

                DownLoadAppManager.getInstance().addObserver(h);
            }
        //刷新状态
            mHomeListViewAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
