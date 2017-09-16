package holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.emery.test.playstore.R;

import base.BaseHolder;
import domain.AppInfoBean;
import utils.UIUtils;


/**
 *
 */
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements OnClickListener {
    private TextView mTvDes;
    private TextView mTvAuthor;
    private ImageView mIvArrow;
    private AppInfoBean mData;
    int   mTvDesMeasuredHeight;
    boolean isOpen=true;
    private void assignViews(View v) {
        mTvDes = (TextView) v.findViewById(R.id.app_detail_des_tv_des);
        mTvAuthor = (TextView)v. findViewById(R.id.app_detail_des_tv_author);
        mIvArrow = (ImageView) v.findViewById(R.id.app_detail_des_iv_arrow);
    }


    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);
        assignViews(view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        mData = data;

        mTvAuthor.setText(data.author);
        mTvDes.setText(data.des);

        mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mTvDesMeasuredHeight= mTvDes.getMeasuredHeight();
                // 默认折叠
                toggle(false);
                // 如果不移除,一会高度变成7行的时候.mTvDesMeasuredHeight就会变
                mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    public void onClick(View v) {
        toggle(true);
    }

    private void toggle(boolean isAnimation) {
        if (isOpen) {// 折叠

            /**
             mTvDes高度发生改变
             应有的高度-->7行的高度
             */
            int start = mTvDesMeasuredHeight;
            int end = getShortHeight(7, mData);
            if (isAnimation) {//有动画
               doAnimation(start, end);
            } else {//没有动画
                mTvDes.setHeight(end);//没有动画的时候设置为7行的高度
            }
        } else {// 展开
            int start = getShortHeight(7, mData);
            int end = mTvDesMeasuredHeight;
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mTvDes.setHeight(end);
            }
        }

        if (isAnimation) {// mTvDes正在折叠或者展开
            if (isOpen) {
                ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0).start();
            } else {
                ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
            }
        }
        isOpen = !isOpen;

    }

    public void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mTvDes, "height", start, end);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {// 动画开始
                // TODO

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {// 动画重复
                // TODO

            }

            @Override
            public void onAnimationEnd(Animator arg0) {// 动画结束
                ViewParent parent = mTvDes.getParent();
                while (true) {
                    parent = parent.getParent();//找scrollView
                    if (parent == null) {// 已经没有父亲
                        break;
                    }
                    if (parent instanceof ScrollView) {// 已经找到
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);//设置向上还是向下滚动
                        break;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {// 动画取消
                // TODO

            }
        });
    }
    /**
     * @param i 指定行高
     * @param data 指定textView的内容
     * @return
     */
    private int  getShortHeight(int i, AppInfoBean data) {
        //临时textView,只做测绘用
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setLines(7);
        tempTextView.setText(data.des);

        tempTextView.measure(0, 0);

        int measuredHeight = tempTextView.getMeasuredHeight();

        return measuredHeight;
    }

}
