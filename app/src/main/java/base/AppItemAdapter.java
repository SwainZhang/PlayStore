package base;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.emery.test.playstore.AppDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import domain.AppInfoBean;
import holder.AppItemHolder;
import manager.DownLoadAppManager;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/2 12:53
 * @des    抽取此item 是为了同一 应用 首页  游戏的 item 点击事件
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public abstract class AppItemAdapter extends SuperBaseAdapter<AppInfoBean> {
    int flag=-1;
    List<AppInfoBean> data=null;//新加载的数据
    List<AppInfoBean> datasource;

    public List<AppItemHolder> getHolders() {
        return holders;
    }

    List<AppItemHolder> holders=new ArrayList<>();
    public AppItemAdapter(List dataResource, AbsListView listView) {
        super(dataResource,listView);
        this.datasource=dataResource;
    }

    @Override
    public BaseHolder<AppInfoBean> getSpecialHolder(int postion) {
        AppItemHolder itemHolder = new AppItemHolder();
        holders.add(itemHolder);
        //holder 初始化的时候添加observer
        DownLoadAppManager.getInstance().addObserver(itemHolder);
        return itemHolder;

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
    public abstract String getExtraRequestParmas();

    public List<AppInfoBean> loadMoreInHomeAdapter(int index) {
        //真正加载更多数据

        String url = MyConstant.BASEURL + getExtraRequestParmas() + String.valueOf(index);
        System.out.println("url===" + url);

        i++;
        System.out.println("第" + i + "次触发加载更多");

        final JSONArray jsonArray=null;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,jsonArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Gson gson=new Gson();
                List<AppInfoBean> list = gson.fromJson(response.toString(), new TypeToken<List<AppInfoBean>>() {
                }.getType());
                data=list;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.sf(error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);
        return  data;
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(UIUtils.getContext(),AppDetailActivity.class);
        intent.putExtra("packageName",datasource.get(position).packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }


}
