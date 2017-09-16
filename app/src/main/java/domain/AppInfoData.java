package domain;

import android.graphics.drawable.Drawable;

/**
 * Created by MyPC on 2016/12/12.
 */

public class AppInfoData {
    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getPkagName() {
        return pkagName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPkagName(String pkagName) {
        this.pkagName = pkagName;
    }

    private String verison;

    public String getVerison() {
        return verison;
    }

    private Drawable appIcon;
    private String appName;

    public void setInstalledType(int installedType) {
        this.installedType = installedType;
    }

    public int getInstalledType() {
        return installedType;

    }

    private String pkagName;
    private String apkPath;
    private int installedType;
    private String timeStamp;

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {

        return timeStamp;
    }

    public int getVersonCode() {
        return versonCode;
    }

    public void setVersonCode(int versonCode) {
        this.versonCode = versonCode;
    }

    private int versonCode;

    public void setVerison(String verison) {
        this.verison = verison;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkPath() {
        return apkPath;

    }
    private String apkSize;

    @Override
    public String toString() {
        return "AppInfoData{" +
                "verison='" + verison + '\'' +
                ", appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                ", pkagName='" + pkagName + '\'' +
                ", apkPath='" + apkPath + '\'' +
                ", installedType=" + installedType +
                ", timeStamp='" + timeStamp + '\'' +
                ", versonCode=" + versonCode +
                ", apkSize='" + apkSize + '\'' +
                '}';
    }
}
