package holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.emery.test.playstore.R;

import java.io.File;

import base.BaseHolder;
import de.greenrobot.event.EventBus;
import domain.AppInfoData;
import eventbus.ApkDeletEvent;
import utils.UIUtils;

/**
 * Created by MyPC on 2016/12/9.
 */

public class PackageManageHolder extends BaseHolder<AppInfoData>implements View.OnClickListener {
    private BaseAdapter mAdapter;
    private int mPosition;

    public  PackageManageHolder(BaseAdapter adapter,int position){

        mAdapter = adapter;
        mPosition = position;
    }
    private ImageView mIvApkManager;
    private TextView mTvApkNameManager;
    private TextView mTvApkModifyManager;
    private TextView mTvApkSizeManager;
    private ImageButton mIbDeleteManager;
    private AppInfoData itemData;

    private void assignViews(View inflate) {
        mIvApkManager = (ImageView) inflate.findViewById(R.id.iv_apk_icon_manager);
        mTvApkNameManager = (TextView) inflate.findViewById(R.id.tv_apk_name_manager);
        mTvApkModifyManager = (TextView)inflate. findViewById(R.id.tv_apk_modify_manager);
        mTvApkSizeManager = (TextView) inflate.findViewById(R.id.tv_apk_size_manager);
        mIbDeleteManager = (ImageButton) inflate.findViewById(R.id.ib_delete_manager);
        mIbDeleteManager.setOnClickListener(this);
    }


    @Override
    public View initHolderView() {
        View inflate = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout
                .item_apk_manage,null);
         assignViews(inflate);
        return inflate;
    }

    @Override
    public void refreshHolderView(AppInfoData itemData) {
        mIvApkManager.setImageDrawable(itemData.getAppIcon());
        mTvApkNameManager.setText(itemData.getAppName());
        mTvApkModifyManager.setText(itemData.getTimeStamp());
        mTvApkSizeManager.setText(itemData.getApkSize());
        this.itemData=itemData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_delete_manager:
                final File file=new File(itemData.getApkPath());
                 if(file.exists()){
                     EventBus.getDefault().post(new ApkDeletEvent(itemData.getApkPath(),mPosition));
                 }
                break;

            default:
                break;
        }
    }
}
