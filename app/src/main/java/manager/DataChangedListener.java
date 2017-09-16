package manager;

/**
 * Created by MyPC on 2016/12/12.
 */

public interface DataChangedListener<T> {
    void onDataChanged(T content);
}
