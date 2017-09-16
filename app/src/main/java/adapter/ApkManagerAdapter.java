package adapter;

import android.widget.AbsListView;

import java.util.List;

import base.BaseHolder;
import base.SuperBaseAdapter;
import domain.AppInfoData;
import holder.PackageManageHolder;

/**
 * Created by MyPC on 2016/12/20.
 */

public class ApkManagerAdapter extends SuperBaseAdapter<AppInfoData>{
    List<AppInfoData> data;
    public ApkManagerAdapter(List<AppInfoData> dataResource, AbsListView listView) {

        super(dataResource, listView);
        this.data=dataResource;
    }

    @Override
    public BaseHolder<AppInfoData> getSpecialHolder(int position) {
        PackageManageHolder packageManageHolder = new PackageManageHolder(this,position);
        return packageManageHolder;
    }

    @Override
    public boolean hasMoreData() {
        return false;
    }
}
