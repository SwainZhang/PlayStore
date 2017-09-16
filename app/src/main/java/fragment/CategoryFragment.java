package fragment;

import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryListViewAdapter;
import base.BaseFragment;
import base.LoadingPager;
import domain.CategoryInfoBean;
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
public class CategoryFragment extends BaseFragment {
    private int flag = 0;
    private List<CategoryInfoBean> mData;//listview 的数据源

    private HomeBean bean;

    @Override
    public LoadingPager.LoadingDataResult initData() {

        LogUtils.sf("---CategoryFragment---");

        //真正加载数据

        String url = MyConstant.BASEURL + "category?index=0";
        JSONArray jsonrequest = null;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, jsonrequest, new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {


                LogUtils.sf("CategoryFragment" + response.toString());

                //这里用Gson解析，在adapter getView()中设置数据的时候就是跟据list中的category中的数量来显示item
                //的数量，但是list 中的categorybean只有两个，所以item 只有两个，但是categorybean中还有list,这样
                //就会造成最后的显示结果中只有2个真正的数据（分别是categorybean中的最一个entity）所以不能用Gson解析

                List<CategoryInfoBean> list=new ArrayList<>();
                 CategoryInfoBean categoryInfoBean =null;
                 CategoryInfoBean categoryTitleBean =null;
                for (int i = 0; i <response.length() ; i++) {

                    JSONObject jsonObject = response.optJSONObject(i);//得到arry中的一个bean
                    String title=jsonObject.optString("title");

                    categoryTitleBean =new CategoryInfoBean();//listview 中 title 的item

                    categoryTitleBean.setTitle(title);
                    categoryTitleBean.setTitle(true);

                    list.add(categoryTitleBean);//把title 单独做成一个同类型的item存入list来分类

                    JSONArray infos = jsonObject.optJSONArray("infos");

                    for (int j = 0; j < infos.length(); j++) {

                        JSONObject innerObject = infos.optJSONObject(j);

                        categoryInfoBean =new CategoryInfoBean();

                        categoryInfoBean.setName1(innerObject.optString("name1"));
                        categoryInfoBean.setName2(innerObject.optString("name2"));
                        categoryInfoBean.setName3(innerObject.optString("name3"));
                        categoryInfoBean.setUrl1(innerObject.optString("url1"));
                        categoryInfoBean.setUrl2(innerObject.optString("url2"));
                        categoryInfoBean.setUrl3(innerObject.optString("url3"));

                        list.add(categoryInfoBean);

                    }
                }

                for(CategoryInfoBean c:list){
                    System.out.println(c.toString());
                }



                if (list == null || list.size() == 0) {
                    flag = 1;//返回加载为空的状态
                }

                //拿到数据
                mData = list;


                LogUtils.sf(mData.size() + "");


                System.out.println("---CategoryFragment--bean.list.size()-" + list.size());

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

        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new CategoryListViewAdapter(mData, listView));
        return listView;

    }
}