package holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import base.BaseHolder;
import domain.CategoryInfoBean;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/1 19:54
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {
    private String title;
    private TextView mTextView;

    @Override
    public View initHolderView() {
        mTextView = new TextView(UIUtils.getContext());
        mTextView.setPadding(5,5,5,5);
        mTextView.setTextSize(16);
        mTextView.setTextColor(Color.argb(100,0,0,0));
        return mTextView;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean itemData) {
        mTextView.setText(itemData.getTitle());
    }
}
