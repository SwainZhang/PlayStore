package holder;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import base.BaseHolder;
import utils.DensityUtil;
import utils.MyConstant;
import utils.UIUtils;
import views.ChildViewPager;

/**
 * @author Administrator
 * @time 2016/8/31 10:50
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class PictureHolderView extends BaseHolder<List<String>>{
    private ChildViewPager mItemHomePicturePager;
    private LinearLayout mItemHomePictureContainerIndicator;
    List<String> pictures =new ArrayList<>();

    private void assignViews(View view) {
        mItemHomePicturePager = (ChildViewPager)view. findViewById(R.id.item_home_picture_pager);
        mItemHomePictureContainerIndicator = (LinearLayout)view.findViewById(R.id.item_home_picture_container_indicator);
    }

    @Override
    public View initHolderView() {

        View view=View.inflate(UIUtils.getContext(), R.layout.item_home_picture,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(List<String> itemData) {
       pictures=itemData;
        mItemHomePicturePager.setAdapter(new PictureAdapter());

        //给容器添加小点
        for (int i = 0; i < pictures.size(); i++) {
            View indicatorView=new View(UIUtils.getContext()) ;//小点
            indicatorView.setBackgroundResource(R.drawable.indicator_normal);
            // px/dp=density
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(UIUtils.getContext(),5.0f),DensityUtil.dip2px(UIUtils.getContext(),5.0f));
            params.leftMargin=DensityUtil.dip2px(UIUtils.getContext(),5.0f);
            params.bottomMargin=DensityUtil.dip2px(UIUtils.getContext(),5.0f);
            indicatorView.setLayoutParams(params);

            if(i==0){
                indicatorView.setBackgroundResource(R.drawable.indicator_selected);
            }
            //加入容器
            mItemHomePictureContainerIndicator.addView(indicatorView);
        }

        //监听滑动事件
        mItemHomePicturePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position=position%pictures.size();
                for (int i = 0; i < pictures.size(); i++) {
                    View childAt = mItemHomePictureContainerIndicator.getChildAt(i);
                    //还原背景
                    childAt.setBackgroundResource(R.drawable.indicator_normal);
                    if(i==position){//这个child被选中了
                        childAt.setBackgroundResource(R.drawable.indicator_selected);//滑动时的选中状态
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //为了让初始化的时候轮播图可以自左向右滑动
        int index=Integer.MAX_VALUE/2;
        int offset=index%pictures.size();

        //设置了这个后会跳到instantiateItem()方法里面,走 position=position%pictures.size()
        //也就是 position=index%pictures.size();但是这个时候的初始化的position不一定是0；所以要减去或者加上offset

        mItemHomePicturePager.setCurrentItem(index-offset);

        //自动轮播图；
        final AutoScrollTask scrollTask=new AutoScrollTask();
        scrollTask.startAutoDisplay();

        //触摸的时候停止自动轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                            scrollTask.endAutoDisplay();
                        break;

                    case MotionEvent.ACTION_UP:
                            scrollTask.startAutoDisplay();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }
    private Handler mHandler=new Handler();

    /**
     * @des 自动轮播的任务
     */
    class AutoScrollTask implements  Runnable{
        //开始自动轮播
        public void startAutoDisplay(){
           // mHandler.postDelayed(this,2000);
            UIUtils.postTaskDelay(this,2000);
        }

        //停止自动轮播
        public void endAutoDisplay(){
           UIUtils.removeTask(this);
        }
        @Override
        public void run() {
            int currentItem=mItemHomePicturePager.getCurrentItem();
            mItemHomePicturePager.setCurrentItem(currentItem+1);
            startAutoDisplay();
        }
    }
    /**
     * 设置数据给ViewPager
     */
class PictureAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position=position%pictures.size();//（无线轮播）余数总是会小于被除数

            ImageView view=new ImageView(UIUtils.getContext());
           // view.setImageResource(R.drawable.ic_default);
            view.setScaleType(ImageView.ScaleType.FIT_XY);

            String url= MyConstant.IMAGEBASEURL+pictures.get(position);
            //BitmapHelper.display(container,url);
            Picasso.with(UIUtils.getContext()).load(url).placeholder(R.drawable.ic_default).into(view);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }
}
