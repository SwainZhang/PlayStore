package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.MyApplication;

public class ComUtils {
	private static boolean isClosed=true;
	public static int SCREEN_WIDTH;
	public static String PATH_UPLOADTEMP_CAM="";
	public static int SCREEN_HEIGHT;
	
	/**
	 * @return
	 */
	public static String getTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 
	 * @return
	 */
	public static Long getTime() {
		return new Date().getTime();
	}
	
	/**
	 * MD5
	 * 
	 * @param s
	 * @return
	 */
	public final static String getMD5(String s) {
		return getMD5(s.getBytes());
	}

	public final static String getMD5(byte[] bytes) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = bytes;
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static void jumpTo(Context context, Class<?> c) {
		Intent intent = new Intent();
		intent.setClass(context, c);
		context.startActivity(intent);
	}
	

	public static void jumpTo(Context context, Class<?> c,Bundle bundle){
		Intent intent = new Intent();
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		intent.setClass(context, c);
		context.startActivity(intent);
	}
	

    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
    
    public static int getScreenXDip(Context context){
    	final float scale = context.getResources().getDisplayMetrics().widthPixels;  
        return (int)scale; 
    }
    
    public static boolean isSupport(int apiNo){
        return Build.VERSION.SDK_INT >= apiNo;
    }
    
    public static int getAppVersionCode(Context context) {  
        int versionCode = 0;  
        try {  
            // ---get the package info---  
            PackageManager pm = context.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
            versionCode = pi.versionCode;
        } catch (Exception e) {  
            Log.e("VersionInfo", "Exception", e);  
        }  
        return versionCode;  
    } 

    
    public static String getAppVersionName(Context context) {  
        String versionName = "";  
        try {  
            // ---get the package info---  
            PackageManager pm = context.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {  
                return "";  
            }  
        } catch (Exception e) {  
            Log.e("VersionInfo", "Exception", e);  
        }  
        return versionName;  
    } 
    
    /**
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
    	return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    
    /**
	 * 
	 * @param path
	 * @return
	 */
	public static String getDir(String path) {
		String subString = path.substring(0, path.lastIndexOf('/'));
		return subString.substring(subString.lastIndexOf('/') + 1,
				subString.length());
	}
	
	/**
	 * �ж��Ƿ����sd��
	 * @return
	 */
	public static boolean isSdcardExit(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
	
	/**
	 */
	/*public static String dateAdd(long addNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(new Date(System.currentTimeMillis() + addNum * 24 * 60 * 60 * 1000));
	}*/
	
	public static Long dateAdd(long day) {
		return System.currentTimeMillis() + day * 24 * 60 * 60 * 1000;
	}

	public static int dateSub(long day1,long day2) {
		return (int)((day1 -day2)/(24 * 60 * 60 * 1000));
	}
	
	public static String formatDate(long time,String type){
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(time);
	}
	public static int getScreenWidth(){
		return SCREEN_WIDTH;
	}
	
	public static int getScreenHeight(){
		return SCREEN_HEIGHT;
	}
	
	public static int getWifiState(Context context){
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		int wifiState = 1;
		if(wifiManager != null){
			wifiState = wifiManager.getWifiState();
		}
		return wifiState;
	}
	
	
	public static ArrayList<String> convertStringToList(String type) {
		ArrayList<String> list;
		list = new ArrayList<String>();
		String resultStr = type;
		if(!resultStr.isEmpty()){
			for(String s:resultStr.split(",")){
				list.add(s);
			}
		}
		return list;
	}
	public static String convertListToString(ArrayList<String> list){
		String result="";
		for(String s:list){
			result+=s+",";
		}
		return result;
	}

	
	public static void hideLoadingFragment(FragmentManager fm,String tag) throws IllegalStateException{
		if(fm!=null){
			fm.popBackStack(tag,1);
		}else{
			Log.e("", "FragmentManager is null");
		}
	}



	/**
	 * 判断是否为当天
	 * @return
     */
	public static boolean isToday(Long date){
		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
		String time = format.format(new Date(date));
		Log.e("","isToday"+time);
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		Log.e("","isToday"+today);
		current.setTime(new Date(date));
		if(current.after(today)){
			Log.e("","isToday after"+true);
			return true;
		}else{
			Log.e("","isToday after"+false);
			return false;
		}
	}
	
	public static String getOrderTime(Long date){
		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
		String time = format.format(new Date(date));
		if(time==null ||"".equals(time)){
			return "";
		}
		String result = null;
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//����
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR)-1);
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(new Date(date));
		
		if(current.after(today)){
			result = time.split(" ")[1];
		}else if(current.before(today) && current.after(yesterday)){
			int index = time.indexOf("/")+1;
			result = time.substring(index, index+5);
		}else{
			result =  time.substring(0, 8);
		}
		return result;
	}



	/*public static boolean verForm(String num) {
		String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
		if (!num.matches(reg)) {
			System.out.println("Format Error!");
			return false;
		}
		return true;
	}*/

	/*public static boolean verify(char[] id) {
		int sum = 0;
		int w[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		char[] ch = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		for (int i = 0; i < id.length - 1; i++) {
			sum += (id[i] - '0') * w[i];
		}
		int c = sum % 11;
		char code = ch[c];
		char last = id[id.length-1];
		last = last == 'x' ? 'X' : last;
		return last == code;
	}*/



	public static String filterUnNumber(String str) {
		// 只允数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		//替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
	}

	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return str != null && !"".equals(str);
	}
	
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str);
	}
	
	
	/**
	 * @param obj
	 * @param split
	 * @return
	 */
	public static String join(String[] obj, String split) {
		int length = obj.length;
		if(obj == null || length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String str = obj[i];
			if(isNotBlank(str)) {
				sb.append(str);
			}
			if((i < length - 1)&&isNotBlank(str)){
				sb.append(split);
			}
		}
		return sb.toString();
	}
	
	public static void goCamera(Activity activity,int requestCode){
		File photoFile = new File(PATH_UPLOADTEMP_CAM + System.currentTimeMillis() +"_cam.jpg");
		if (!photoFile.getParentFile().exists()) {
		    photoFile.getParentFile().mkdirs();
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		Intent intent2 = activity.getIntent();
		if(null==intent2) intent2 = new Intent();
		intent2.putExtra("path", photoFile.getPath());


		activity.startActivityForResult(intent,requestCode);
	}
	
	 /**
     * @param context
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    /**
     * @param priceStr
     * @return
     */
    public static String formatPrice(String priceStr) {
    	try {
			BigDecimal price = new BigDecimal(priceStr);
			price = price.divide(new BigDecimal(100));
			return price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();  
		} catch (Exception e) {
			return "--";
		}
    }

    public static String int2float(String priceStr) {
        try {
            BigDecimal formatPrice = new BigDecimal(priceStr);
            return formatPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            return "--";
        }
    }
	
	public static List<String> getSortStringList(List<String> list){
    	List<Integer>listIntegers=new ArrayList<Integer>();
    	for (int i = 0; i < list.size(); i++) {
    		listIntegers.add(Integer.parseInt(list.get(i)));
		}
    	list.clear();
    	for(int i=0;i<listIntegers.size()-1;i++) {
    		 for(int j=1;j<listIntegers.size()-i;j++) {
    		 Integer a;
    		 if((listIntegers.get(j-1)).compareTo(listIntegers.get(j))<0) { //�Ƚ���������Ĵ�С
        		 
        		 a=listIntegers.get(j-1);
        		 listIntegers.set((j-1),listIntegers.get(j));
        		 listIntegers.set(j,a);
        		 }
    		 }
    	}
    	for (int i = 0; i < listIntegers.size(); i++) {
    		list.add(listIntegers.get(i)+"");
		}
		return list;
    }
	
	
	public static  boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }

	/**
	 * ��Assets�ж�ȡͼƬ
	 */
	public static Bitmap getbmFromAssetsFile(Resources res, String fileName) {
		if (res == null)
			return null;
		Bitmap bm = null;
		AssetManager am = res.getAssets();
		try {
			InputStream is = am.open(fileName);
			bm = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bm;
	}

	public static void hiddenSoftKeyboard(EditText view){
		if(view.getWindowToken()!=null){
			((InputMethodManager) view.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
  public static void syso(String title,String content){
	  if(!isClosed) {
		  String head = "-----------------------------" + title + "-----------------------------\n";

		  String rear = "\n--------------------------------------------------------------------\n";
		  System.out.println(head + content + rear);
	  }
  }
  public static void showToast(String content) {
	  if (!isClosed) {
		  Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
	  }
  }
	public static Context getApplicationContext(){
		return MyApplication.getContext();
	}

	/**
	 　　* 验证手机号码
	 　　* @param mobiles
	 　　* @return  [0-9]{5,9}
	 　　*/
	public static boolean isMobileNO(String mobiles) {
		String value = "手机号";
		String regExp = "^\\d{11}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}
}
