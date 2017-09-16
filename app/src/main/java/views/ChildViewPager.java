package views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Administrator
 * @time 2016/8/31 11:41
 * @des  针对低版本的viewpager 的嵌套和滑动viewpager到终点的时候fragment（fragment在外层的viewpager里面）
 *       也会跟着切换（这里嵌套在listView中）事件的处理（请求父控件不拦截）自觉处理左右滑动
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX=ev.getRawX();
                float endY=ev.getRawY();

                float rangeX=endX-mDownX;
                float rangeY=endY-mDownY;
                if(Math.abs(rangeX)>Math.abs(rangeY)){//说明是横向移动，应自觉处理，上下移动给父控件处理
                    getParent().requestDisallowInterceptTouchEvent(true);

                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
