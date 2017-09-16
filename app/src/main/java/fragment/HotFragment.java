package fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;

import base.BaseFragment;
import base.LoadingPager;
import domain.HomeBean;
import utils.DensityUtil;
import utils.LogUtils;
import utils.MyConstant;
import utils.UIUtils;
import views.FlowLayout;

/**
 * @author Administrator
 * @time 2016/8/25 10:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HotFragment extends BaseFragment {
    private int flag = 0;
    private List<String> mData;//listview 的数据源

    private HomeBean bean;
    @Override
    public LoadingPager.LoadingDataResult initData() {

        LogUtils.sf("---HotFragment---");

        //真正加载数据

        String url = MyConstant.BASEURL + "hot?index=0";
        JSONArray jsonrequest=null;
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, jsonrequest, new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {


                LogUtils.sf("HotFragment"+response.toString());
                Gson gson = new Gson();
                List<String> list = gson.fromJson(response.toString(), new TypeToken<List<String>>() {
                }.getType());//得到了bean 的数据


                if (list == null ||list.size() == 0) {
                    flag = 1;//返回加载为空的状态
                }

                //拿到数据
                mData = list;


                LogUtils.sf(mData.size() + "");


                System.out.println("---HotFragment--bean.list.size()-" + list.size());

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
           //返回成功的视图
        ScrollView scrollView=new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout=new FlowLayout(UIUtils.getContext());

        for (final  String  str : mData) {
            TextView  textView=new TextView(UIUtils.getContext());
            textView.setText(str);
            textView.setTextSize(25);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            //textView.setPadding(left,top,right,bottom);
            //GradientDrawable 可以设置渐变颜色
            GradientDrawable drawable=new GradientDrawable();
            Random random=new Random();
            //设置填充颜色
            int alpha=255;
            int red=random.nextInt(190)+30;
            int green=random.nextInt(190)+30;
            int blue=random.nextInt(190)+30;
            drawable.setColor(Color.argb(alpha,red,green,blue));//设置随机颜色
            drawable.setCornerRadius(DensityUtil.dip2px(UIUtils.getContext(),6.0f));

            //按下状态的图片
            GradientDrawable pressedDrawable=new GradientDrawable();
            pressedDrawable.setColor(Color.GRAY);
            pressedDrawable.setCornerRadius(DensityUtil.dip2px(UIUtils.getContext(),6.0f));

            //设置一个状态图片(Selector)
            StateListDrawable stateDrawable=new StateListDrawable();

            stateDrawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);
            stateDrawable.addState(new int[]{},drawable);//正常未按下的状态图片（注意状态顺序）

            textView.setBackgroundDrawable(stateDrawable);//GradientDrawable 设置给背景（代替了shape作为背景）
            //textView.setBackgroundResource(R.drawable.shape_hot_tv_bg);

            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), str, Toast.LENGTH_SHORT).show();
                }
            });
            flowLayout.addView(textView);
        }

        scrollView.addView(flowLayout);
        return scrollView;

    }
}
