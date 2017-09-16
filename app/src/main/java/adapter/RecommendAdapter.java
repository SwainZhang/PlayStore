package adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import utils.UIUtils;
import views.flyin.StellarMap;

/**
 * @author Administrator
 * @time 2016/9/2 10:34
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class RecommendAdapter implements StellarMap.Adapter {

    private List<String> mData;//飞入飞出 的数据源
    private static final int PERPAGER_SIZE=15;
    public RecommendAdapter(List<String> data){
        this.mData=data;
    }
    @Override
    public int getGroupCount() {//有多少组
        int groupSize=mData.size()/PERPAGER_SIZE;
        if((mData.size() % PERPAGER_SIZE )>0){//不能整除就向上取整
           groupSize++;
        }
        return groupSize;
    }

    @Override
    public int getCount(int group) {//每组有多少条数据（0-15）

        if(group==getGroupCount()-1){//来到了最后一组

            if((mData.size() % PERPAGER_SIZE )>0){
                return mData.size()%PERPAGER_SIZE;//最后有余数就返回余数
            }
        }
        return PERPAGER_SIZE;
    }

    @Override
    public View getView(int group, int position, View convertView) {
        // group 代表第几组  position 代表组中的第几个
        TextView textView=new TextView(UIUtils.getContext());
        int index=group*PERPAGER_SIZE+position;
        textView.setText(mData.get(index));

        Random random=new Random();
        //字体随机
        textView.setTextSize(random.nextInt(6)+15);

        //颜色随机
        int alpha=255;
        int red=random.nextInt(180)+30;//30-210
        int green=random.nextInt(180)+30;
        int blue=random.nextInt(180)+30;
        textView.setTextColor(Color.argb(alpha,red,green,blue));
        return textView;
    }

    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }


    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        return 0;
    }
}
