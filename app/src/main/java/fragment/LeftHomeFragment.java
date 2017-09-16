package fragment;


import android.os.Bundle;

import com.emery.test.playstore.R;


/**
 * Created by MyPC on 2016/12/9.
 */

public class LeftHomeFragment extends LeftBaseFragment{


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_left_home;
    }

    public static LeftHomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LeftHomeFragment fragment = new LeftHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
