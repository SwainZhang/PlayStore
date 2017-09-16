package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 2017/3/26.
 */

public class AppFocusBeanManager {
    private static AppFocusBeanManager instance=new AppFocusBeanManager();
    public List<AppInfoBean> focusBeans=new ArrayList<>();
   public static AppFocusBeanManager getInstance(){
       return instance;
   }
    public List<AppInfoBean> getFocusBean() {
        return focusBeans;
    }

    public void setFocusBean(AppInfoBean focusBean) {
        focusBeans.add(focusBean);
    }
}
