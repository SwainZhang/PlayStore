package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.emery.test.playstore.R;

import base.BaseHolder;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/20.
 */

public class CheckVersionHolder extends BaseHolder implements View.OnClickListener {

    private ImageView mIvCheck;
    private Button mBtCheck;

    private void assignViews(View view) {
        mIvCheck = (ImageView) view.findViewById(R.id.iv_check);
        mBtCheck = (Button)view. findViewById(R.id.bt_check);
        mBtCheck.setOnClickListener(this);
    }

    @Override
    public View initHolderView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .activity_check_version, null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_check:
                Toast.makeText(UIUtils.getContext(),"已经是最新版本",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
