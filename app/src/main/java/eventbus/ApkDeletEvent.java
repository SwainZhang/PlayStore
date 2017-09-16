package eventbus;

/**
 * Created by MyPC on 2016/12/20.
 */

public class ApkDeletEvent {
    private String mPath;
    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public String getPath() {
        return mPath;
    }

    public ApkDeletEvent(String path,int position){

        mPath = path;
        mPosition = position;
    }
}
