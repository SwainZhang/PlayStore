package adapter;

import android.widget.AbsListView;

import java.util.List;

import base.AppItemAdapter;

/**
 * @author Administrator
 * @time 2016/8/30 13:42
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class AppListViewAdapter extends AppItemAdapter {

    public AppListViewAdapter(List dataResource, AbsListView listView) {
        super(dataResource, listView);

    }

    @Override
    public String getExtraRequestParmas() {
        return "app?index=";
    }

}
