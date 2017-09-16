package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.emery.test.playstore.R;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import eventbus.ChangeThemeEvent;
import utils.UIUtils;

/**
 * Created by MyPC on 2017/3/25.
 */

public class LeftThemeHolder extends BaseHolder implements View.OnClickListener{

    private ImageButton mIb_green;
    private ImageButton mIb_blue;
    private ImageButton mIb_red;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chose_green:
                EventBus.getDefault().post(new ChangeThemeEvent(0));
                break;
            case R.id.chose_blue:
                EventBus.getDefault().post(new ChangeThemeEvent(1));
                break;
            case R.id.chose_red:
                EventBus.getDefault().post(new ChangeThemeEvent(2));
                break;

            default:
                break;
        }
    }

    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .activity_left_theme, null);
        mIb_green = (ImageButton) inflate.findViewById(R.id.chose_green);
        mIb_green.setOnClickListener(this);
        mIb_blue = (ImageButton) inflate.findViewById(R.id.chose_blue);
        mIb_blue.setOnClickListener(this);
        mIb_red = (ImageButton) inflate.findViewById(R.id.chose_red);
        mIb_red.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }
}
