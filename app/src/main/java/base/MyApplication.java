/**
 * 
 */
package base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

import utils.ComUtils;

/**
 * @author zhouyeliang
 *
 */
public abstract class MyApplication extends Application {
	
	private static MyApplication yApp;
	
	public static ArrayList<Activity> activitys = new ArrayList<Activity>();

	public static String deviceId = null;
	
	private void initScreenData(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		float SCREEN_WIDTH = dm.widthPixels;
		float SCREEN_HEIGHT= dm.heightPixels;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		deviceId = ComUtils.getDeviceId(this);
		yApp = this;
		initScreenData();

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
	}

	public static MyApplication app() {
		return yApp;
	}
	
	public static void addActivity(Activity activity) {
        if (activitys != null && !activitys.contains(activity)) {
                activitys.add(activity);
        }
    }
	
	
	public static void removeActivity(Activity activity) {
        if (activitys != null && activitys.contains(activity)) {
                activitys.remove(activity);
        }
    }
	
	public static boolean isActivityExist(Class clazz) {
		for(Activity ac:activitys){
			if(ac.getClass().getName().equals(clazz.getName())){
				return true;
			};
		}
        return false;
    }

	public static void exit() {
		Iterator<Activity> it = activitys.iterator();
		while(it.hasNext()){
			Activity ac = it.next();
			ac.finish();
			it.remove();
		}
		System.exit(0);
	}

    public static Context getContext(){
		return  yApp.getApplicationContext();
	}

	public abstract int getThemeColor();
}
