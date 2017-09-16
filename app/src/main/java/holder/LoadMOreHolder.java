package holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emery.test.playstore.R;

import base.BaseHolder;
import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/28 21:56
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class LoadMOreHolder extends BaseHolder<Integer> {
    private LinearLayout mItemLoadmoreContainerLoading;
    private LinearLayout mItemLoadmoreContainerRetry;
    private TextView mItemLoadmoreTvRetry;
    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;

    private void assignViews(View view) {
        mItemLoadmoreContainerLoading = (LinearLayout) view.findViewById(R.id
                .item_loadmore_container_loading);
        mItemLoadmoreContainerRetry = (LinearLayout) view.findViewById(R.id
                .item_loadmore_container_retry);
        mItemLoadmoreTvRetry = (TextView) view.findViewById(R.id.item_loadmore_tv_retry);

        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
    }

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        assignViews(view);
        return view;
    }

    @Override
    public void refreshHolderView(Integer state) {
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (state) {
            case STATE_LOADING://正在加载更多
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_RETRY://加载更多失败
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case STATE_NONE:

                break;

            default:
                break;
        }

    }
}
