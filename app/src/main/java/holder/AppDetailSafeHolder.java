package holder;

import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import base.BaseHolder;
import domain.AppInfoBean;
import utils.DensityUtil;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/2 16:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppDetailSafeHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {
    private ImageView    mAppDetailSafeIvArrow;
    private LinearLayout mAppDetailSafePicContainer;
    private LinearLayout mAppDetailSafeDesContainer;
    private boolean isOpen=true;
    private void assignViews(View view) {
        mAppDetailSafeIvArrow = (ImageView)view. findViewById(R.id.app_detail_safe_iv_arrow);
        mAppDetailSafePicContainer = (LinearLayout) view.findViewById(R.id.app_detail_safe_pic_container);
        mAppDetailSafeDesContainer = (LinearLayout) view.findViewById(R.id.app_detail_safe_des_container);
    }

    @Override
    public View initHolderView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe,null);
        view.setOnClickListener(this);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean itemData) {

        mAppDetailSafeIvArrow   .  setImageResource(R.drawable.arrow_down);
        for (int i = 0; i < itemData.safe.size(); i++) {

            ImageView imageView2=new ImageView(UIUtils.getContext());//描述的安全的标题图标
            Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL+itemData.safe.get(i).safeUrl).into(imageView2);

            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(DensityUtil.dip2px(UIUtils.getContext(),40),DensityUtil.dip2px(UIUtils.getContext(),40));
            imageView2.setLayoutParams(params);
            mAppDetailSafePicContainer.addView(imageView2);


            ImageView imageView=new ImageView(UIUtils.getContext());//安全的内容前置图片
            Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL+itemData.safe.get(i).safeDesUrl).into(imageView);

            TextView textView=new TextView(UIUtils.getContext());//描述内容
            textView.setText(itemData.safe.get(i).safeDes);
            //itemData.safe.get(i).safeDesColor
           // textView.setTextColor();

            System.out.println(itemData.safe.get(i).safeDes);
            LinearLayout linearLayout =new LinearLayout(UIUtils.getContext());
            linearLayout.setPadding(10,2,2,5);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            mAppDetailSafeDesContainer.addView(linearLayout);

        }




    }

    @Override
    public void onClick(View v) {
        //测量mAppDetailSafeDesContainerd的高度
        int widthMeasureSpec= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ;
        mAppDetailSafeDesContainer.measure(widthMeasureSpec,heightMeasureSpec);

        //取出mAppDetailSafeDesContainer的高度
        int measuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();
        int start=measuredHeight;
        int end=0;


        if(isOpen){
            //关闭 mAppDetailSafeDesContainer 的高度由本身变成0
           doAnimation(start,end);
            mAppDetailSafeIvArrow   .  setImageResource(R.drawable.arrow_down);


        }else{
            //打开 由0变成原来的高度int widthMeasureSpec;
           doAnimation(end,start);
            mAppDetailSafeIvArrow   .  setImageResource(R.drawable.arrow_up);
        }
        isOpen=!isOpen;
    }

    public void doAnimation(int start,int end) {

        ValueAnimator valueAnimator=ValueAnimator.ofInt(start,end);//为了拿到start 到 end 的渐变值
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height= (int) animation.getAnimatedValue();//拿到start 到 end  的这些个渐变值
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer
                        .getLayoutParams();
                params.height=height;
                //高度变化值设置给mAppDetailSafeDesContainer
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }
        });
    }
}
