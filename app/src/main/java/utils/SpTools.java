package utils;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * @author Administrator
 * @创建时间 2016-6-9下午5:50:56
 * @描述信息 TODO
 * @SVN提交者：$Author$
 * @提交时间： $Date$
 * @当前版本： $Rev$
 */
public class SpTools {
	public static void putString(Context context,String key,String value){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);
		 
		 sp.edit().putString(key, value).commit();
		
	}
	public static String getString(Context context,String key,String defValue){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);
		
		 return sp.getString(key, defValue);
		
	}
	public static void putBoolean(Context context,String key,boolean value){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);
		 
		 sp.edit().putBoolean(key, value).commit();
		
	}
	public static Boolean getBoolean(Context context,String key,boolean defValue){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);
		
		 return sp.getBoolean(key, defValue);
		
	}
	public static void putInt(Context context,String key,int value){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);
		 
		 sp.edit().putInt(key, value).commit();
		
	}
	public static int getInt(Context context,String key,int defValue){
		SharedPreferences sp=context.getSharedPreferences(MyConstant.SPFILE, context.MODE_PRIVATE);

		return sp.getInt(key, defValue);
		
	}
	
}