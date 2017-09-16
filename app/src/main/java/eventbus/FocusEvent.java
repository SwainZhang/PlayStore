package eventbus;

import domain.AppInfoBean;

/**
 * Created by MyPC on 2017/3/26.
 */

public class FocusEvent {
    private AppInfoBean bean;

    public FocusEvent(AppInfoBean bean) {
        this.bean = bean;
    }

    public AppInfoBean getBean() {
        return bean;
    }
}
