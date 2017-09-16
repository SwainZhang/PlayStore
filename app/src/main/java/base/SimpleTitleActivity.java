package base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.emery.test.playstore.R;

import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public abstract class SimpleTitleActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initView();

    }

    private void init() {

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

    public abstract LoadingPager.LoadingDataResult onLoadData();

    //成功视图(整个activity的布局，但是里面的各个部分的内容都是一个holder）

    public abstract View onSuccessView();

}
