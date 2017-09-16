package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emery.test.playstore.R;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import eventbus.FeedBackEvent;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/20.
 */

public class FeedBackHolder extends BaseHolder implements View.OnClickListener{

    private EditText mEdFeedback;
    private Button mBtFeedback;

    private void assignViews(View view) {
        mEdFeedback = (EditText) view.findViewById(R.id.ed_feedback);
        mBtFeedback = (Button) view.findViewById(R.id.bt_feedback);
        mBtFeedback.setOnClickListener(this);
    }


    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .activity_feedback,null);
        assignViews(inflate);
        return inflate;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_feedback:
                EventBus.getDefault().post(new FeedBackEvent());
                break;

            default:
                break;
        }
    }
}
