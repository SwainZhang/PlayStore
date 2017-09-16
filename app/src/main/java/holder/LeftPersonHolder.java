package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.emery.test.playstore.ChangePicActivity;
import com.emery.test.playstore.R;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eventbus.ChangePersonDetailEvent;
import utils.commonUtils;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public class LeftPersonHolder extends BaseHolder {

    private Button mBt_changePic;
    private ImageView mImageView;

    @Override
    public View initHolderView() {
        EventBus.getDefault().register(this);
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .fragment_left_person, null);
        mImageView = (ImageView) inflate.findViewById(R.id.iv_chang_head);
        mBt_changePic = (Button) inflate.findViewById(R.id.bt_changeHeadPic);
        mBt_changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PhotoUtil photoUtil = new PhotoUtil();
//                photoUtil.showDialog();
                commonUtils.jumpTo(UIUtils.getContext(), ChangePicActivity.class);
            }
        });
        return inflate;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setHeadPic(ChangePersonDetailEvent event){
        if(event.getImageView()!=null){
          mImageView.setImageBitmap(event.getImageView());
        }
    }
}
