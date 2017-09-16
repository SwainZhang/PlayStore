package manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by MyPC on 2016/12/9.
 */

public class FragmentManagerHelper {
    public static  FragmentManagerHelper fragmentManager;
    public static FragmentManagerHelper getInstance(){
        if(fragmentManager==null){
            synchronized (FragmentManagerHelper.class){
                if(fragmentManager==null){
                    fragmentManager=new FragmentManagerHelper();
                }
            }
        }
        return fragmentManager;
    }
    public void add(FragmentManager fragmentManager, int container, Fragment fragment,String tag){
        fragmentManager.beginTransaction().add(container,fragment,tag);
    }
}
