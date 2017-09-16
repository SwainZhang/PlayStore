package com.emery.test.playstore;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import base.LoadingPager;
import domain.AppInfoBean;
import holder.AppDetailDesHolder;
import holder.AppDetailDownLoadHolder;
import holder.AppDetailInfoHolder;
import holder.AppDetailPicHolder;
import holder.AppDetailSafeHolder;
import manager.DownLoadAppManager;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/2 13:11
 * @des 点击listview的item后应用详情界面
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppDetailActivity extends AppCompatActivity {

    private String mPackageName;
    private AppInfoBean mBean;
    private FrameLayout mAppDetailBottom;
    private FrameLayout mAppDetailInfo;
    private FrameLayout mAppDetailSafe;
    private FrameLayout mAppDetailPic;
    private FrameLayout mAppDetailDes;
    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private AppDetailDownLoadHolder mAppDetailDownLoadHolder;

    private void assignViews(View view) {

        mAppDetailBottom = (FrameLayout)view. findViewById(R.id.app_detail_bottom);
        mAppDetailInfo = (FrameLayout)view.  findViewById(R.id.app_detail_info);
        mAppDetailSafe = (FrameLayout)view.  findViewById(R.id.app_detail_safe);
        mAppDetailPic = (FrameLayout)view.  findViewById(R.id.app_detail_pic);
        mAppDetailDes = (FrameLayout)view.  findViewById(R.id.app_detail_des);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initView();

    }

    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    private void initView() {
        //loadingpager管理视图

        LoadingPager loadingPager=new LoadingPager(UIUtils.getContext()) {
            @Override
            public LoadingDataResult initData() {

                return onLoadData();
            }

            @Override
            public View initSuccessView() {

                return onSuccessView();
            }
        };
        //触发加载数据
        loadingPager.loadData();//这样才会去调用onLoadData()，initSuccessView()真正加载

        setContentView(loadingPager);

        initActionBar();
    }


    private void initActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setLogo(R.drawable.ic_launcher);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6666ff")));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private LoadingPager.LoadingDataResult onLoadData() {
        //访问网络拉取数据
        String url= MyConstant.BASEURL+"detail?packageName="+mPackageName;
        JSONObject jsonRequest=null;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                LogUtils.sf("AppDetailActivity"+response.toString());
                Gson gson=new Gson();
                mBean = gson.fromJson(response.toString(), AppInfoBean.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               LogUtils.sf(error.getMessage());
            }
        });
        RequestQueue queue= Volley.newRequestQueue(UIUtils.getContext());
        queue.add(request);
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

     //成功视图(整个activity的布局，但是里面的各个部分的内容都是一个holder）

    private View onSuccessView() {

        View view= View.inflate(UIUtils.getContext(),R.layout.activity_detail,null);
        assignViews(view);//拿到child

        //信息部分内容
        AppDetailInfoHolder appDetailInfoHolder=new AppDetailInfoHolder();
        //将每个holder 的内容添加到容器中
        mAppDetailInfo.addView(appDetailInfoHolder.getHolderView());
        //设置数据给holder
        appDetailInfoHolder.setDataAndRefreshHolderView(mBean);

        //安全部分内容
        AppDetailSafeHolder  appDetailSafeHolder=new AppDetailSafeHolder();
        mAppDetailSafe.addView(appDetailSafeHolder.getHolderView());
        appDetailSafeHolder.setDataAndRefreshHolderView(mBean);

        //截图部分内容
        AppDetailPicHolder appDetailPicHolder =new AppDetailPicHolder();
        mAppDetailPic.addView(appDetailPicHolder.getHolderView());
        appDetailPicHolder.setDataAndRefreshHolderView(mBean);

        //描述部分内容
        AppDetailDesHolder appDetailDesHolder=new AppDetailDesHolder();
        mAppDetailDes.addView(appDetailDesHolder.getHolderView());
        appDetailDesHolder.setDataAndRefreshHolderView(mBean);

        //下载部分内容
        mAppDetailDownLoadHolder = new AppDetailDownLoadHolder();
        mAppDetailBottom.addView(mAppDetailDownLoadHolder.getHolderView());
        mAppDetailDownLoadHolder.setDataAndRefreshHolderView(mBean);
       //添加内容观察者（需要实时监听所以应该在方法的生命周期之外）holder初始化的时候加入
        DownLoadAppManager.getInstance().addObserver(mAppDetailDownLoadHolder);
        return view;
    }

    @Override
    protected void onPause() {
        //移除观察者
        if(mAppDetailDownLoadHolder!=null){
            DownLoadAppManager.getInstance().deleteObserver(mAppDetailDownLoadHolder);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(mAppDetailDownLoadHolder!=null){
            //添加内容观察者
            //开启监听的时候手动获取最新的downloadinfo
            mAppDetailDownLoadHolder.addObserverAndRerefresh();
        }

        super.onResume();
    }
}