package skin;

import android.view.View;

import java.util.List;

/**
 * Created by MyPC on 2017/3/18.
 * <p>
 * 封装了每一个需要换肤的控件，如 RelativeLayout,TextView,等控件
 */

public class SkinItem {
    //view 的类型，如textview
    public View view;
    //封装了每一个控件需要换肤的属性，如 background textColor
    public List<AbsSkinInterface> skinAttrs;

    public SkinItem(View view, List<AbsSkinInterface> skinAttrs) {
        this.view = view;
        this.skinAttrs = skinAttrs;
    }

    /**
     * 给控件换肤的方法
     */
    public void apply() {
        //遍历一个控件的所有需要换肤的属性，如background textColor,每一个属性都会有子类，并在其apply方法对这个属性进行相应的设置
        for (AbsSkinInterface skinAttr : skinAttrs) {
            skinAttr.apply(view);
        }
    }
}
