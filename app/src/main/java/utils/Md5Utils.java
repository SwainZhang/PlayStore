package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




/**
 * @author Administrator
 * @创建时间 2016-6-9下午6:31:06
 * @描述信息 MD5加密
 * @SVN提交者：$Author$
 * @提交时间： $Date$
 * @当前版本： $Rev$
 */
public class Md5Utils {
	
	
	/**
	 * 
	 * @param filePath     文件的路径
	 * @return    返回文件的MD5值
	 * @throws Exception 
	 */
	public static String getFileMD5(String path){
		StringBuilder mess = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[10240];
			int len = fis.read(buffer);
			while (len != -1) {
				md.update(buffer, 0, len);
			
				len = fis.read(buffer);
			}
			byte[] digest = md.digest();
			for (byte b : digest){
				
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				hexString = hexString.toUpperCase();
				mess.append(hexString);
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess + "";
	}
		

	public static String Md5(String str){
		StringBuilder	builder=new StringBuilder();
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes();//字字符串转成byte[]
			byte[] digest = mDigest.digest(bytes);//对byte[]进行MD5 加密
			builder = new StringBuilder();
			
			for(byte b:digest){
				int d =b & 0xff;
				String hexString=Integer.toHexString(d);//一个字符由两个字节组成
				if(hexString.length()==1){//说明高4位为0
                     hexString="0"+hexString;	
				}
				builder.append(hexString);//把每一位的16进制字符串拼接起来
				//System.out.println(hexString);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
}