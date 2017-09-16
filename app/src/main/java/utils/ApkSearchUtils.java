package utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import domain.AppInfoData;

/**
 * Created by MyPC on 2016/12/12.
 */

public class ApkSearchUtils {

    private static int INSTALLED = 0; // 表示已经安装，且跟现在这个apk文件是一个版本
    private static int UNINSTALLED = 1; // 表示未安装
    private static int INSTALLED_UPDATE =2; // 表示已经安装，版本比现在这个版本要低，可以点击按钮更新
    private Context context;
    private List<AppInfoData> myFiles = new ArrayList<AppInfoData>();
    public List<AppInfoData> getMyFiles() {
        return myFiles;
    }
    public void setMyFiles(List<AppInfoData> myFiles) {
        this.myFiles = myFiles;
    }
    public ApkSearchUtils(Context context) {
        this.context = context;
    }
    /**
     *
     *            运用递归的思想，递归去找每个目录下面的apk文件
     * @return
     * @throws Exception
     */

    public List<AppInfoData> FindAllAPKFile(File file) throws Exception {
        // 手机上的文件,目前只判断SD卡上的APK文件

        // file = Environment.getDataDirectory();

        // SD卡上的文件目录

        if (file.isFile()) {
            String name_s = file.getName();
            AppInfoData myFile = new AppInfoData();
            String apk_path = null;
            // MimeTypeMap.getSingleton()
            if (name_s.toLowerCase().endsWith(".apk")) {
                apk_path = file.getAbsolutePath();// apk文件的绝对路劲
                // System.out.println("----" + file.getAbsolutePath() + "" +
                // name_s);
                long size=file.length();
                long time = file.lastModified();
                PackageManager pm = context.getPackageManager();
                PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path, PackageManager.GET_ACTIVITIES);
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                /**获取apk的图标 */
                appInfo.sourceDir = apk_path;
                appInfo.publicSourceDir = apk_path;
                Drawable apk_icon = appInfo.loadIcon(pm);
                myFile.setAppIcon(apk_icon);
                /** 得到包名 */
                String packageName = packageInfo.packageName;
                myFile.setPkagName(packageName);
                String appname = appInfo.loadLabel(pm).toString();
                myFile.setAppName(appname);
                /** apk的绝对路劲 */
                myFile.setApkPath(file.getAbsolutePath());
                /** apk的版本名称 String */
                String versionName = packageInfo.versionName;
                myFile.setVerison(versionName);
                /** apk的版本号码 int */
                int versionCode = packageInfo.versionCode;
                myFile.setVersonCode(versionCode);
                /**安装处理类型*/
                int type = doType(pm, packageName, versionCode);
                myFile.setInstalledType(type);
                Log.i("ok", "处理类型:"+String.valueOf(type)+"\n" + "------------------我是纯洁的分割线-------------------");

                /**文件大小*/
                String s = Formatter.formatFileSize(context, size);
                myFile.setApkSize(s);
                /**时间戳*/
                CharSequence format = DateFormat.format("yyyy/MM/dd HH:mm:ss", time);
                myFile.setTimeStamp((String) format);

                myFiles.add(myFile);
            }
            // String apk_app = name_s.substring(name_s.lastIndexOf("."));
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file_str : files) {
                    FindAllAPKFile(file_str);
                }
            }
        }
        return myFiles;
    }



     /*
      * 判断该应用是否在手机上已经安装过，有以下集中情况出现
      * 1.未安装，这个时候按钮应该是“安装”点击按钮进行安装
      * 2.已安装，按钮显示“已安装” 可以卸载该应用
      * 3.已安装，但是版本有更新，按钮显示“更新” 点击按钮就安装应用
      */
    /**
     091      * 判断该应用在手机中的安装情况
     092      * @param pm                   PackageManager
     093      * @param packageName  要判断应用的包名
     094      * @param versionCode     要判断应用的版本号
     095      */

    private int doType(PackageManager pm, String packageName, int versionCode) {
        List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pi : pakageinfos) {
            String pi_packageName = pi.packageName;
            int pi_versionCode = pi.versionCode;
            //如果这个包名在系统已经安装过的应用中存在
            if(packageName.endsWith(pi_packageName)){
                //Log.i("test","此应用安装过了");
                if(versionCode==pi_versionCode){
                    Log.i("test","已经安装，不用更新，可以卸载该应用");
                    return INSTALLED;
                }else if(versionCode>pi_versionCode){
                    Log.i("test","已经安装，有更新");
                    return INSTALLED_UPDATE;
                }
            }

        }
        Log.i("test","未安装该应用，可以安装");
        return UNINSTALLED;

    }


}
