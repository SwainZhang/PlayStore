package holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emery.test.playstore.R;
import com.squareup.picasso.Picasso;

import base.BaseHolder;
import domain.CategoryInfoBean;
import utils.MyConstant;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/9/1 16:48
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CategoryHolder extends BaseHolder<CategoryInfoBean> {
    private LinearLayout mItemCategoryItem1;
    private ImageView mItemCategoryIcon1;
    private TextView mItemCategoryName1;
    private LinearLayout mItemCategoryItem2;
    private ImageView mItemCategoryIcon2;
    private TextView mItemCategoryName2;
    private LinearLayout mItemCategoryItem3;
    private ImageView mItemCategoryIcon3;
    private TextView mItemCategoryName3;

    private void assignViews(View view) {
        mItemCategoryItem1 = (LinearLayout) view.findViewById(R.id.item_category_item_1);
        mItemCategoryIcon1 = (ImageView)view. findViewById(R.id.item_category_icon_1);
        mItemCategoryName1 = (TextView) view.findViewById(R.id.item_category_name_1);
        mItemCategoryItem2 = (LinearLayout) view.findViewById(R.id.item_category_item_2);
        mItemCategoryIcon2 = (ImageView) view.findViewById(R.id.item_category_icon_2);
        mItemCategoryName2 = (TextView)view.findViewById(R.id.item_category_name_2);
        mItemCategoryItem3 = (LinearLayout)view. findViewById(R.id.item_category_item_3);
        mItemCategoryIcon3 = (ImageView)view. findViewById(R.id.item_category_icon_3);
        mItemCategoryName3 = (TextView)view. findViewById(R.id.item_category_name_3);
    }

    @Override
    public View initHolderView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_category_info,null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean itemData) {

        setData(itemData.getName1(),mItemCategoryIcon1,mItemCategoryName1,MyConstant.IMAGEBASEURL+itemData.getUrl1());
        setData(itemData.getName2(),mItemCategoryIcon2,mItemCategoryName2,MyConstant.IMAGEBASEURL+itemData.getUrl2());
        setData(itemData.getName3(),mItemCategoryIcon3,mItemCategoryName3,MyConstant.IMAGEBASEURL+itemData.getUrl3());

         }

    public void setData(final String name, ImageView mItemCategoryIcon, TextView mItemCategoryName, String url)
         {
             if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(url)){
                 ((ViewGroup)mItemCategoryIcon.getParent()).setVisibility(View.VISIBLE);

                 mItemCategoryName.setText(name);

                 Picasso.with(UIUtils.getContext()).load(url).
                         placeholder(R.drawable.ic_default).into(mItemCategoryIcon);

                 ((ViewGroup)mItemCategoryIcon.getParent()).setOnClickListener(new View.OnClickListener() {


                     @Override
                     public void onClick(View v) {
                         Toast.makeText(UIUtils.getContext(),name,Toast.LENGTH_LONG).show();
                     }
                 });

             }else{
                 ((ViewGroup)mItemCategoryIcon.getParent()).setVisibility(View.INVISIBLE);
             }
          }


}
