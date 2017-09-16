package skin;

import android.view.View;

/**
 * Created by MyPC on 2017/3/18.
 * <p>
 * 背景里面可以设置颜色，图片
 */

public class BackgroundSkin extends AbsSkinInterface {
    public BackgroundSkin(String attrName, String attrValueType, String attrValueName, int resId) {
        super(attrName, attrValueName, resId, attrValueType);
    }


    @Override
    protected void apply(View view) {
        if ("color".equals(attrValueType)) {
            view.setBackgroundColor(SkinManager.getInstance().getColor(resId));
        } else if ("drawable".equals(attrValueType)) {
            view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(resId));
        }
    }
}
