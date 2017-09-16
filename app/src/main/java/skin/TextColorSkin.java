package skin;

import android.view.View;
import android.widget.TextView;

/**
 * Created by MyPC on 2017/3/18.
 */

public class TextColorSkin extends AbsSkinInterface {
    public TextColorSkin(String attrName, String attrValueType, String attrValueName, int resId) {
        super(attrName, attrValueName, resId, attrValueType);
    }

    @Override
    protected void apply(View view) {
        if (view instanceof TextView) {
            //选择默认还是设置皮肤样式
            TextView textView = (TextView) view;
            textView.setTextColor(SkinManager.getInstance().getColor(resId));
        }
    }
}
