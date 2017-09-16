package factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import utils.UIUtils;

/**
 * @author Administrator
 * @time 2016/8/31 10:42
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ListViewFactory  {
    public static ListView getListView(){
        ListView listView=new ListView(UIUtils.getContext());
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return  listView;
    }
}
