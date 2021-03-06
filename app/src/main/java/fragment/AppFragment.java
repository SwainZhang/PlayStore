package fragment;


import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

import adapter.AppListViewAdapter;
import base.BaseFragment;
import base.LoadingPager;
import base.LoadingPager.LoadingDataResult;
import domain.AppInfoBean;
import domain.HomeBean;
import factory.ListViewFactory;
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
public class AppFragment extends BaseFragment {
    private int flag = 0;
    private List<AppInfoBean> mData;//listview 的数据源
    private List<String> pictures;//轮播图
    private HomeBean bean;

    @Override
    public LoadingDataResult initData() {

  LogUtils.sf("---AppFragment---");

        //真正加载数据

        String url = MyConstant.BASEURL + "app?index=0";
        JSONArray jsonrequest=null;
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, jsonrequest, new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {


                LogUtils.sf("appFragment"+response.toString());
                Gson gson = new Gson();
                List<AppInfoBean> list = gson.fromJson(response.toString(), new TypeToken<List<AppInfoBean>>() {
                }.getType());//得到了bean 的数据


                if (list == null ||list.size() == 0) {
                    flag = 1;//返回加载为空的状态
                }

                //拿到数据
                mData = list;


                LogUtils.sf(mData.size() + "");


                System.out.println("---appFragment--bean.list.size()-" + list.size());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);

        if (flag == 1) {
            return LoadingDataResult.EMPTY;//返回加载为空的状态
        }


        return LoadingDataResult.SUCCESS;//返回加载成功的状态

    }

    @Override
    public View initSuccessView() {

        ListView listView= ListViewFactory.getListView();
        listView.setAdapter(new AppListViewAdapter(mData,listView));
        return listView;

    }
}