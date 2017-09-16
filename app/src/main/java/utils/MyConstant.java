package utils;

/**
 * @author Administrator
 * @param
 * @创建时间 2016-6-9下午5:53:34
 * @描述信息 TODO
 * @SVN提交者：$Author$
 * @提交时间： $Date$
 * @当前版本： $Rev$
 */
public interface MyConstant {
	 String SPFILE="config";
     int DEBUGLEVEL=LogUtils.LEVEL_ALL;
     String BASEURL="http://127.0.0.1:8090/";//手机服务器的地址
     String IMAGEBASEURL=BASEURL+"image?name=";
     String DOWNLOADBASEURL=BASEURL+"download";
}