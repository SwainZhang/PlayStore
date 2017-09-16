package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.emery.test.playstore.R;


/**
 * @author Administrator
 * @time 2016/8/31 15:34
 * @des 克服不同规格的图片或者其他控件在显示的时候在另一控件中被拉伸的情况（父容器的宽高比等于孩子的宽高比）
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class RatioLayout extends FrameLayout {
    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    private float mPicRatio=  0f;//图片的宽高比

      public static  final int RELATIVE_WIDTH=0;
      public static  final int RELATIVE_HEIGHT=1;

    public void setRelative(int relative) {
        mRelative = relative;
    }

    private int mRelative=RELATIVE_WIDTH;//相对于height或者width来计算（默认width) 这样的好处是如：width=10dp,height=10dp，都是exactly类型，
      // 那么我们的判断就只会走第一个也就是根据宽度计算高度，我们们设了这个属性后就可以避免都是exactly的时候不能根据height计算这种情况发生

    public RatioLayout(Context context) {
        this(context,null);
    }
    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //取出我们定义的所有属性的一个数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        //取出我们需要的属性
        mPicRatio= typedArray.getFloat(R.styleable.RatioLayout_picRatio, 0);//设置图片的宽高比
        mRelative=typedArray.getInt(R.styleable.RatioLayout_relative,0);//设置相对于款或者高来作为已知条件来计算另一个值
        //回收
        typedArray.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //控件宽度固定，已知图片宽高比，计算控件高度
        int parentWidthMode=MeasureSpec.getMode(widthMeasureSpec);//relative_width

        //控件的宽度固定，已知图片的宽高比，计算控件的宽度
        int parentHeightMode=MeasureSpec.getMode(heightMeasureSpec);//relative_height

        if(parentWidthMode==MeasureSpec.EXACTLY&&mPicRatio!=0&&mRelative==RELATIVE_WIDTH){//指定大小的模式 如：10dp ,match parent 都是， warp content 不是
            //得到parent容器的宽度
            int parentWidth=MeasureSpec.getSize(widthMeasureSpec);
            //得到child的宽度
            int childWidth=parentWidth-getPaddingLeft()-getPaddingRight();
            //控件宽度/控件的高度=mPicRatio（图片的宽高比，有已知的图片得出）

            //计算child的高度
            int childHeight=(int)(childWidth/mPicRatio+.5f);

            //计算父容器的高度 (这就是我们的最终目的)
            int parentHeight=childHeight+getPaddingTop()+getPaddingBottom();

            //主动测绘child，固定child的大小
            int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);

            //设置自己的测试结果
            setMeasuredDimension(parentWidth,parentHeight);
        }else if(parentHeightMode==MeasureSpec.EXACTLY && mPicRatio!=0&&mRelative==RELATIVE_HEIGHT) {//控件高度固定，已知图片宽高比，计算控件的宽度
           //得到parent容器的高度
            int parentHeight=MeasureSpec.getSize(heightMeasureSpec);
            //得到child的高度
            int childHeight=parentHeight-getPaddingTop()-getPaddingBottom();
            //得到孩子的宽度
            int childWidth=(int)(childHeight*mPicRatio+.5f);
            //得到parent的宽度
            int parentWidth= childWidth+getPaddingLeft()+getPaddingRight();

            //固定child的大小
            int childWidthMeasureSpec=MeasureSpec.getSize(childWidth);
            int childHeightMeasureSpec=MeasureSpec.getSize(childHeight);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);

            // //设置自己的测试结果
            setMeasuredDimension(parentWidth,parentHeight);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);//有系统测量
        }
    }


}
