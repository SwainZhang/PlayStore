package protocol;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import domain.AppInfoBean;
import domain.HomeBean;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/29 16:08
 * @des  對homefragment initdata 从网络拉取数据的抽取
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HomeProtocol  {
    HomeBean  homeBean;
    public HomeBean getDataFromNet(int index){

        //真正加载数据
        final  List<AppInfoBean> list=new ArrayList<>();
        String url= MyConstant.BASEURL+"home?index="+index;
        JSONObject jsonRequest=null;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, jsonRequest, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                homeBean= gson.fromJson(response.toString(), HomeBean.class);//得到了bean 的数据

                System.out.println("---homeBean---"+homeBean.list.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--" + error.getMessage());
            }
        });

        RequestQueue queue= Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);


        return  homeBean;

    }


}
