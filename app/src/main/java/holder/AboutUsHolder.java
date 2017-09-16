package holder;

import android.view.LayoutInflater;
import android.view.View;

import com.emery.test.playstore.R;

import base.BaseHolder;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/20.
 */

public class AboutUsHolder extends BaseHolder {
    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.activity_about,
                null);

        return inflate;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }
}
