package skin;

import android.view.View;

/**
 * Created by MyPC on 2017/3/18.
 * <p>
 * 封装一个控件需要换肤的每一个属性，如TextView中的background ,textColor,textSize 都对应一个AbsSkinInterface
 */

public abstract class AbsSkinInterface {
    /**
     * background
     */
    protected String attrName;
    /**
     * @drawable/ic_louncher
     */
    protected String attrValueName;
    /**
     * id
     */
    protected int resId;
    /**
     * color/drawable
     */
    protected String attrValueType;

    public AbsSkinInterface(String attrName, String attrValueName, int resId, String
            attrValueType) {
        this.attrName = attrName;
        this.attrValueName = attrValueName;
        this.resId = resId;
        this.attrValueType = attrValueType;
    }

    protected abstract void apply(View view);
}
