package fragment;

import android.view.View;

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

import adapter.RecommendAdapter;
import base.BaseFragment;
import base.LoadingPager;
import domain.HomeBean;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;
import views.flyin.ShakeListener;
import views.flyin.StellarMap;

/**
 * @author Administrator
 * @time 2016/8/25 10:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class RecommedFragment extends BaseFragment {

    private int flag = 0;
    private List<String> mData;//飞入飞出 的数据源
    private HomeBean bean;
    private ShakeListener mShakeListener;

    @Override
    public LoadingPager.LoadingDataResult initData() {

        LogUtils.sf("---RecommedFragment---");

        //真正加载数据

        String url = MyConstant.BASEURL + "recommend?index=0";
        JSONArray jsonrequest=null;
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, jsonrequest, new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {


                LogUtils.sf("RecommedFragment"+response.toString());
                Gson gson = new Gson();
                List<String> list = gson.fromJson(response.toString(), new TypeToken<List<String>>() {
                }.getType());//得到了bean 的数据


                if (list == null ||list.size() == 0) {
                    flag = 1;//返回加载为空的状态
                }

                //拿到数据
                mData = list;


                LogUtils.sf(mData.size() + "");


                System.out.println("---RecommedFragment--bean.list.size()-" + list.size());

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
            return LoadingPager.LoadingDataResult.EMPTY;//返回加载为空的状态
        }


        return LoadingPager.LoadingDataResult.SUCCESS;//返回加载成功的状态

    }

    @Override
    public View initSuccessView() {

        final StellarMap stellarMap=new StellarMap(UIUtils.getContext());
        final RecommendAdapter adapter = new RecommendAdapter(mData);
        stellarMap.setAdapter(adapter);

        //设置第一页显示
        stellarMap.setGroup(0,true);

        //设置把屏幕拆分成多少个格子
        stellarMap.setRegularity(15,20);

        //加入摇一摇切换
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener(){
            @Override
            public void onShake() {
                  int currentGroup= stellarMap.getCurrentGroup();    //拿到当前的group
                 if(currentGroup==adapter.getGroupCount()-1){//最后一组l
                     currentGroup=0;
                 }else{
                     currentGroup++;
                 }

                  stellarMap.setGroup(currentGroup,true);//摇动切换
            }
        });
        return stellarMap;
    }

    @Override
    public void onResume() {//界面可见的时候才启动 监听
        if(mShakeListener!=null){
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(mShakeListener!=null){//界面不可见关闭监听
            mShakeListener.pause();
        }
        super.onPause();
    }
}
