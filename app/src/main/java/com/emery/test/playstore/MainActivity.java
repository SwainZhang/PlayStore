package com.emery.test.playstore;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

import adapter.MainFragmentStateAdatper;
import base.BaseFragment;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eventbus.ChangeThemeEvent;
import factory.FragmentFactory;
import holder.LeftMenuHolder;
import skin.SkinActivity;
import skin.SkinManager;
import utils.LogUtils;
import utils.UIUtils;
import views.PagerSlidingTabStripExtends;


public class MainActivity extends /*ActionBarActivity*/ SkinActivity {
    private PagerSlidingTabStripExtends mTabs;
    private ActionBarDrawerToggle mToggle;
    private ViewPager mViewPager;
    private String[] mStr_title;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private MainFragmentStateAdatper mAdapter;
    File dir = new File("/storage/sdcard0/isFirst");
    private FrameLayout mLeftmenu;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionbar();
        initActionbarToggle();
        initData();
        initEvent();
        EventBus.getDefault().register(this);


    }


    private void initEvent() {
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) { //当fragment 被选中的时候加载数据

                //Fragment fragment = FragmentFactory.getFragment(position);
                // 这里拿到的fragment，不能拿到LoaderPager对象，即无法调用loadData();/


                BaseFragment fragment = FragmentFactory.getFragment(position);//使用这样的方法就可以拿到我们以
                // (k,v)存在容器的fragment

                fragment.getLoadingPager().setPosition(position);//为了控制不再重复加载数据
                fragment.getLoadingPager().loadData();
                LogUtils.sf("setOnPageChangeListener" + position + fragment.getClass() + "被选中了");
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mStr_title = UIUtils.getStringArr(R.array.main_titles);
        //MainFragmentAdapter adapter=new MainFragmentAdapter(getSupportFragmentManager(),
        // mStr_title);
        mAdapter = new MainFragmentStateAdatper(getSupportFragmentManager(), mStr_title);
        mViewPager.setAdapter(mAdapter);
        //必须先设置adapter再，跟ViewPager绑定，不然会报错
        mTabs.setViewPager(mViewPager);

        //左侧菜单
        LeftMenuHolder holder = new LeftMenuHolder();
        mLeftmenu.addView(holder.getHolderView());
        holder.setDataAndRefreshHolderView(null);


    }


    private void initView() {
        mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);

        mTabs.setIndicatorColor(UIUtils.getColor(R.color.tab_indicator_selected));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawlerlayout);
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mLeftmenu = (FrameLayout) findViewById(R.id.main_menu);
    }

    private void initActionbar() {
        // mActionBar = getSupportActionBar();

        mToolbar.setTitle("PlayStore");//设置Toolbar标题
        mToolbar.setTitleTextColor(Color.parseColor("#000000")); //设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setLogo(R.drawable.ic_launcher);//设置logo


       /* mActionBar= getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);//必不可少，不然logo不显示
        mActionBar.setDisplayUseLogoEnabled(true);//必不可少哦

        mActionBar.setLogo(R.drawable.ic_launcher);//设置logo
        mActionBar.setTitle("PlayStore");//设置文字title

        // 显示返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(true);*/
    }


    private void initActionbarToggle() {


        /*mToggle = new ActionBarDrawerToggle(//
                this, //
                mDrawerLayout, //
                R.drawable.ic_drawer_am, //
                R.string.open, //
                R.string.close//
        );

        // 同步状态的方法
        mToggle.syncState();
        // 设置mDrawerLayout拖动的监听
        mDrawerLayout.setDrawerListener(mToggle);*/


        //创建返回键，并实现打开关/闭监听
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R
                .string.close);


        mToggle.syncState();
        mDrawerLayout.setDrawerListener(mToggle);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //  mToggle.syncState();// 这个必须要，没有的话进去的默认是个箭头。。正常应该是三横杠的
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // mToggle控制打开关闭drawlayout
                mToggle.onOptionsItemSelected(item);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < 7; i++) {
            File file = new File(dir, i + "");
            if (file.exists()) {
                file.delete();
            }
        }
        EventBus.getDefault().unregister(this);
    }

    private long isPirmeTime;

    @Override
    public void onBackPressed() {
        if (SystemClock.currentThreadTimeMillis() - isPirmeTime > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次，退出PlayStore", Toast.LENGTH_SHORT).show();
            isPirmeTime = SystemClock.currentThreadTimeMillis();
            return;
        }
        //也就是说当连续点击了的时间小于2000ms才会finish;否则会执行上面的return
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void changeTheme(ChangeThemeEvent event) {
        int number = event.getNumber();
        String path = "";
        SkinManager instance = null;
        switch (number) {
            case 0:
                //加载不成功就是紫色，加载成功就是绿色
                path = new File(Environment.getExternalStorageDirectory(),
                        "skin0.apk").getAbsolutePath();

                instance = SkinManager.getInstance();
                instance.loadSkin(path);
                update();
                break;
            case 1:
                //加载不成功就是紫色，加载成功就是蓝色
                path = new File(Environment.getExternalStorageDirectory(),
                        "skin1.apk").getAbsolutePath();

                instance = SkinManager.getInstance();
                instance.loadSkin(path);
                update();
                break;
            case 2:
                //加载不成功就是紫色，加载成功就是红色
                path = new File(Environment.getExternalStorageDirectory(),
                        "skin2.apk").getAbsolutePath();

                instance = SkinManager.getInstance();
                instance.loadSkin(path);
                update();
                break;

            default:
                break;
        }
    }
}
