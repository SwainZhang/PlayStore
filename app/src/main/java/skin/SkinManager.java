package skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MyPC on 2017/3/17.
 */

public class SkinManager {
    /**
     * 包含第三方apk的资源Resource
     */
    private Resources skinResource;
    private static final SkinManager ourInstance = new SkinManager();
    private static Context mContext;
    private String skinPackageName;

    public void init(Context context) {
        mContext = context;

    }

    public static SkinManager getInstance() {
        return ourInstance;
    }

    public void loadSkin(String path) {
        PackageManager packageManager = mContext.getPackageManager();
        //拿到内存卡的pkg信息
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path,
                PackageManager.GET_ACTIVITIES);
        //外置apk的保名
        skinPackageName = packageArchiveInfo.packageName;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            Resources resources = mContext.getResources();
            skinResource = new Resources(assetManager, resources.getDisplayMetrics(), resources
                    .getConfiguration());

        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (InvocationTargetException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        if (skinResource == null) {
            return mContext.getResources().getColor(resId);
        }
        System.out.println("getColor()");
        //如 colorAccent
        String resourceEntryName = mContext.getResources().getResourceEntryName(resId);
        //根据colorAccent获取插件apk的颜色id
        int trueResId = skinResource.getIdentifier(resourceEntryName, "color", skinPackageName);
        //获取颜色
        int color = skinResource.getColor(trueResId);
        return color;
    }

    public Drawable getDrawable(int resId) {
        if(skinResource==null){
            return mContext.getResources().getDrawable(resId);
        }
        String resourceEntryName = mContext.getResources().getResourceEntryName(resId);
        //资源apk中名字为resourceEntryName的id
        int truResId = skinResource.getIdentifier(resourceEntryName, "drawable",
                skinPackageName);
        Drawable drawable = skinResource.getDrawable(truResId);
        return drawable ;
    }
}
