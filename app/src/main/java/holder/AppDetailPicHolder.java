package holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import base.BaseHolder;
import domain.AppInfoBean;
import utils.DensityUtil;
import utils.MyConstant;
import utils.UIUtils;
import views.RatioLayout;

/**
 * @author Administrator
 * @time 2016/9/2 16:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppDetailPicHolder extends BaseHolder<AppInfoBean> {
    private LinearLayout mAppDetailPicIvContainer;

    private void assignViews(View view) {
        mAppDetailPicIvContainer = (LinearLayout) view.findViewById(R.id.app_detail_pic_iv_container);
    }

    @Override
    public View initHolderView() {
       View view= View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean itemData) {
        for (int i = 0; i <itemData.screen.size() ; i++) {

            ImageView iv=new ImageView(UIUtils.getContext());

            //屏幕的宽度
            int widthPixels = UIUtils.getResource().getDisplayMetrics().widthPixels;

            //得到容器的宽度
            int containerWidth=widthPixels-mAppDetailPicIvContainer.getPaddingLeft()-mAppDetailPicIvContainer.getPaddingRight();

            //得到imageView的宽度  （已知图片的宽高比，和控件的宽度计算控件的高度）
            int child= (int) (containerWidth/3+.5f);

            //容器动态计算
            RatioLayout ratioLayout=new RatioLayout(UIUtils.getContext());
            //实际上是通过控给ratioLayout来控制imageView的大小
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(child, ViewGroup.LayoutParams.WRAP_CONTENT);
            ratioLayout.setLayoutParams(params);

            //（已知图片的宽高比，和控件的宽度计算控件的高度）
            ratioLayout.setPicRatio(150/250);
            ratioLayout.setRelative(RatioLayout.RELATIVE_WIDTH);
            if(i>0){
                params.leftMargin= DensityUtil.dip2px(UIUtils.getContext(),1);//设置给ratiolayout相当于给imgeview
            }
            ratioLayout.addView(iv);

            Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL+itemData.screen.get(i)).placeholder(R.drawable.ic_default).into(iv);
            mAppDetailPicIvContainer.addView(ratioLayout);
        }

    }
}
