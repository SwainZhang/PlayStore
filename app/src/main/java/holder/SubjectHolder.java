package holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import base.BaseHolder;
import domain.SubjectInfoBean;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/31 14:48
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SubjectHolder extends BaseHolder<SubjectInfoBean>  {
    private ImageView mItemSubjectIvIcon;
    private TextView mItemSubjectTvTitle;

    private void assignViews(View view) {
        mItemSubjectIvIcon = (ImageView) view.findViewById(R.id.item_subject_iv_icon);
        mItemSubjectTvTitle = (TextView)view. findViewById(R.id.item_subject_tv_title);
    }

    @Override
    public View initHolderView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_subject,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(SubjectInfoBean itemData) {
        Picasso.with(UIUtils.getContext()).load(MyConstant.IMAGEBASEURL+itemData.url).placeholder(R.drawable.ic_default).into(mItemSubjectIvIcon);
        mItemSubjectTvTitle.setTextColor(Color.BLACK);
        mItemSubjectTvTitle.setText(itemData.des);
    }


}
