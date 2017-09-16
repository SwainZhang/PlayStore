package holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emery.test.playstore.AboutActivity;
import com.emery.test.playstore.CheckVersionActivity;
import com.emery.test.playstore.FeedbackActivity;
import com.emery.test.playstore.FocusActivity;
import com.emery.test.playstore.LeftHomeActivity;
import com.emery.test.playstore.LeftPersonActivity;
import com.emery.test.playstore.LeftSettingActivity;
import com.emery.test.playstore.LeftThemeActivity;
import com.emery.test.playstore.PackageManageActivity;
import com.emery.test.playstore.R;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import eventbus.ChangePersonDetailEvent;
import utils.commonUtils;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/11 13:26
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class LeftMenuHolder extends BaseHolder<Object> implements View.OnClickListener {
    private RelativeLayout mPhotoLayout;

    private RelativeLayout mHomeLayout;

    private RelativeLayout mSettingLayout;

    private RelativeLayout mThemeLayout;

    private RelativeLayout mScansLayout;

    private RelativeLayout mFeedbackLayout;

    private RelativeLayout mUpdatesLayout;

    private RelativeLayout mAboutLayout;

    private RelativeLayout mExitLayout;
    private TextView mMail;
    private TextView mName;
    private ImageView mIv_head;


    private void assignViews(View view) {
        EventBus.getDefault().register(this);
        mMail = (TextView) view.findViewById(R.id.user_email);
        mName = (TextView) view.findViewById(R.id.user_name);
        mIv_head = (ImageView) view.findViewById(R.id.image_photo);

        mPhotoLayout = (RelativeLayout) view.findViewById(R.id.photo_layout);
        mHomeLayout = (RelativeLayout) view.findViewById(R.id.home_layout);
        mSettingLayout = (RelativeLayout) view.findViewById(R.id.setting_layout);
        mThemeLayout = (RelativeLayout) view.findViewById(R.id.theme_layout);
        mScansLayout = (RelativeLayout) view.findViewById(R.id.scans_layout);
        mFeedbackLayout = (RelativeLayout) view.findViewById(R.id.feedback_layout);
        mUpdatesLayout = (RelativeLayout) view.findViewById(R.id.updates_layout);
        mAboutLayout = (RelativeLayout) view.findViewById(R.id.about_layout);
        mExitLayout = (RelativeLayout) view.findViewById(R.id.exit_layout);
    }
   @Subscribe(threadMode = ThreadMode.MainThread)
   public void personalChanged(ChangePersonDetailEvent event){
       if(event.getImageView()!=null&&event.getFlag().equals("head")){
           if(event.getImageView()!=null){
               mIv_head.setImageBitmap(event.getImageView());
           }
       }
       if(event.getMail()!=null&&event.getFlag().equals("mail")){
           mMail.setText(event.getMail());
       }
       if(event.getName()!=null&&event.getFlag().equals("name")){
           mName.setText(event.getName());
       }
   }
    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(Object itemData) {
        mPhotoLayout.setOnClickListener(this);
        mHomeLayout.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
        mThemeLayout.setOnClickListener(this);
        mScansLayout.setOnClickListener(this);
        mFeedbackLayout.setOnClickListener(this);
        mUpdatesLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);
        mExitLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.photo_layout:
                Toast.makeText(UIUtils.getContext(),"left unknow is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), LeftPersonActivity.class);
                break;
            case R.id.home_layout:
                Toast.makeText(UIUtils.getContext(),"left home is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), LeftHomeActivity.class);
                break;

            case R.id.setting_layout:
                Toast.makeText(UIUtils.getContext(),"left setting is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), LeftSettingActivity.class);
                break;
            case R.id.theme_layout:
                Toast.makeText(UIUtils.getContext(),"left theme is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), LeftThemeActivity.class);
                break;
            case R.id.scans_layout:
                Toast.makeText(UIUtils.getContext(),"left scans is clicked",Toast.LENGTH_SHORT).show();
                 commonUtils.jumpTo(UIUtils.getContext(), PackageManageActivity.class);
                break;
            case R.id.feedback_layout:
                Toast.makeText(UIUtils.getContext(),"left feedback is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), FeedbackActivity.class);
                break;
            case R.id.updates_layout:
                Toast.makeText(UIUtils.getContext(),"left updates is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), CheckVersionActivity.class);
                break;
            case R.id.about_layout:
                Toast.makeText(UIUtils.getContext(),"left about is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), AboutActivity.class);

                break;
            case R.id.exit_layout:
                Toast.makeText(UIUtils.getContext(),"left exit is clicked",Toast.LENGTH_SHORT).show();
                commonUtils.jumpTo(UIUtils.getContext(), FocusActivity.class);
                break;

            default:

                break;
        }
    }
}
