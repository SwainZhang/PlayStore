package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emery.test.playstore.R;


/**
 * @author Administrator
 * @time 2016/9/10 9:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CircleProgressView extends LinearLayout {


    private TextView mTv_des;
    private ImageView mIvIcon;
    private int maxProgress = 100;
    private boolean progressEnable=true;
    private int curProgress = 0;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = View.inflate(context, R.layout.circle_progress_btn, this);
        mTv_des = (TextView) inflate.findViewById(R.id.circle_progress_des);
        mIvIcon = (ImageView) inflate.findViewById(R.id.circle_progress_icon);
    }

    /**
     * @param des 外界调用修改描述
     */
    public void setTv_des(String des) {
        mTv_des.setText(des);
    }

    public void setIvIcon(int resourceId) {
        mIvIcon.setImageResource(resourceId);
    }
    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
        invalidate();//刷新
    }
    public void setProgressEnable(boolean progressEnable) {
        this.progressEnable = progressEnable;
    }
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景，透明图片,如果不设背景是不会走onDraw方法的


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);//绘制具体的图片和内容
        if(progressEnable) {
            RectF recf = new RectF(mIvIcon.getLeft(), mIvIcon.getTop(), mIvIcon.getRight(), mIvIcon.getBottom());

            float start = -90;
            float range = (curProgress*360.0f / maxProgress);
            Paint paint = new Paint();
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);
            canvas.drawArc(recf, start, range, false, paint);//usrCenter就是弧形在圆的两条半径
        }
    }
}