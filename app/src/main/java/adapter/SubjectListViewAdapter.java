package adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.emery.test.playstore.SubjectDetailActivity;

import java.util.List;

import base.BaseHolder;
import base.SuperBaseAdapter;
import domain.SubjectInfoBean;
import holder.SubjectHolder;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/31 14:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SubjectListViewAdapter extends SuperBaseAdapter<SubjectInfoBean> {

    public SubjectListViewAdapter(List<SubjectInfoBean> dataResource, AbsListView listView) {
        super(dataResource, listView);
    }

    @Override
    public BaseHolder<SubjectInfoBean> getSpecialHolder(int position) {
        return new SubjectHolder();
    }

    @Override
    public boolean hasMoreData() {
        return false;
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(UIUtils.getContext(),SubjectDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}
