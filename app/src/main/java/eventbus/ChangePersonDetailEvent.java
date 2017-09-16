package eventbus;

import android.graphics.Bitmap;

/**
 * Created by MyPC on 2017/1/5.
 */

public class ChangePersonDetailEvent {
    private String name;
    private String mail;
    private Bitmap mImageView;

    public String getFlag() {
        return flag;
    }

    private String flag;
    public ChangePersonDetailEvent(String name, String mail, Bitmap imageView, String flag){
        this.name=name;
        this.mail=mail;
        this.mImageView=imageView;
        this.flag=flag;
    }
    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public Bitmap getImageView() {
        return mImageView;
    }
}
