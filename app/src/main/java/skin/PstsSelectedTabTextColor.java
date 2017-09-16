package skin;

import android.view.View;

import views.PagerSlidingTabStripExtends;

/**
 * Created by MyPC on 2017/3/25.
 */

public class PstsSelectedTabTextColor extends AbsSkinInterface{
    public PstsSelectedTabTextColor(String attrName, String attrValueType, String attrValueName, int resId) {
        super(attrName, attrValueName, resId, attrValueType);
    }

    @Override
    protected void apply(View view) {
        if(view instanceof PagerSlidingTabStripExtends){
            PagerSlidingTabStripExtends slidingTabStripExtends= (PagerSlidingTabStripExtends) view;
             slidingTabStripExtends.setSelectedTabTextColor(SkinManager.getInstance().getColor(resId));
        }
    }
}
