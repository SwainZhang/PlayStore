package manager;

/**
 * @author Administrator
 * @time 2016/9/6 13:17
 * @des 下载信息的配置
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class DownLoadInfo {
    public String downlOadUrl;
    public String dstDirPath;
    public String fileName;
    public int state=DownLoadAppManager.STATE_UNDOWNLOAD;
    public String packageName;
    public int maxProgress;
    public int currentProgress;
    public long fileSize;
    public Runnable task;
}
