package holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import base.BaseHolder;
import domain.AppInfoBean;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/2 16:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {
    private ImageView mAppDetailInfoIvIcon;
    private TextView  mAppDetailInfoTvName;
    private RatingBar mAppDetailInfoRbStar;
    private TextView  mAppDetailInfoTvDownloadnum;
    private TextView  mAppDetailInfoTvVersion;
    private TextView  mAppDetailInfoTvTime;
    private TextView  mAppDetailInfoTvSize;

    private void assignViews(View view) {
        mAppDetailInfoIvIcon = (ImageView) view.findViewById(R.id.app_detail_info_iv_icon);
        mAppDetailInfoTvName = (TextView) view.findViewById(R.id.app_detail_info_tv_name);
        mAppDetailInfoRbStar = (RatingBar) view.findViewById(R.id.app_detail_info_rb_star);
        mAppDetailInfoTvDownloadnum = (TextView)view. findViewById(R.id.app_detail_info_tv_downloadnum);
        mAppDetailInfoTvVersion = (TextView) view.findViewById(R.id.app_detail_info_tv_version);
        mAppDetailInfoTvTime = (TextView) view.findViewById(R.id.app_detail_info_tv_time);
        mAppDetailInfoTvSize = (TextView) view.findViewById(R.id.app_detail_info_tv_size);
    }

    @Override
    public View initHolderView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean itemData) {

     Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL+itemData.iconUrl).placeholder(R.drawable.ic_default).into(mAppDetailInfoIvIcon);
        mAppDetailInfoTvName.setText(itemData.name);
        mAppDetailInfoRbStar.setRating(itemData.stars);

        String downloadNum = UIUtils.getString(R.string.downloadNum,itemData.downloadNum);
        mAppDetailInfoTvDownloadnum.setText(downloadNum);

        String version = UIUtils.getString(R.string.version,itemData.version);
        mAppDetailInfoTvVersion.setText(version);

        String date = UIUtils.getString(R.string.date,itemData.date);
        mAppDetailInfoTvTime.setText(date);

        String size = UIUtils.getString(R.string.size,Formatter.formatFileSize(UIUtils.getContext(),itemData.size));
        mAppDetailInfoTvSize.setText(size);

    }
}
