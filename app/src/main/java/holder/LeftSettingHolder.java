package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emery.test.playstore.R;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import eventbus.ChangePersonDetailEvent;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public class LeftSettingHolder extends BaseHolder implements View.OnClickListener{
    private Button mBt_mail;
    private Button mBt_name;
    private Button mCl_cache;
    private EditText mEd_change;
    private Button mConfirm;

    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .fragment_left_setting, null);
        mBt_mail = (Button) inflate.findViewById(R.id.change_mail);
        mBt_mail.setOnClickListener(this);
        mBt_name = (Button) inflate.findViewById(R.id.change_name);
        mBt_name.setOnClickListener(this);
        mCl_cache = (Button) inflate.findViewById(R.id.clear_cache);
        mCl_cache.setOnClickListener(this);
        mCl_cache.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
        mConfirm = (Button) inflate.findViewById(R.id.confirm);
        mConfirm.setOnClickListener(this);
        mEd_change = (EditText) inflate.findViewById(R.id.ed_change);



        return inflate ;
    }

    @Override
    public void refreshHolderView(Object itemData) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_mail:
                setVisiable();
                mEd_change.setHint("请输入新的邮箱");
                break;
            case R.id.change_name:
                setVisiable();
                mEd_change.setHint("请输入新的名称");
                break;
            case R.id.clear_cache:
                mCl_cache.setText("清理缓存");

                break;
            case R.id.confirm:
                if (mEd_change.getHint().equals("请输入新的名称")) {

                    EventBus.getDefault().post(new ChangePersonDetailEvent(mEd_change.getText().toString().trim(), null, null, "name"));
                } else if (mEd_change.getHint().equals("请输入新的邮箱")) {
                    EventBus.getDefault().post(new ChangePersonDetailEvent(null, mEd_change.getText().toString().trim(), null, "mail"));

                }
                setGone();

                Toast.makeText(UIUtils.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
    private void setVisiable(){

        mEd_change.setVisibility(View.VISIBLE);
        mConfirm.setVisibility(View.VISIBLE);
    }
    private void setGone(){
        mEd_change.setVisibility(View.GONE);
        mConfirm.setVisibility(View.GONE);
    }


}
