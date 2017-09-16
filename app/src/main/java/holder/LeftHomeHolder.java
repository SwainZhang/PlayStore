package holder;

import android.view.LayoutInflater;
import android.view.View;

import com.emery.test.playstore.R;

import base.BaseHolder;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public class LeftHomeHolder extends BaseHolder {
    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .fragment_left_home, null);
        return inflate;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }
}
