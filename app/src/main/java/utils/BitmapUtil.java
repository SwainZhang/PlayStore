package utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhouyeliang
 * @Date 2015-11-3
 */
public class BitmapUtil {
	/**
	 * 
	 * @param bitmap
	 * @param maxlong
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, float maxlong) {
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) maxlong) / width;
		float scaleHeight = ((float) maxlong) / height;
		float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
		if (scale > 1)
			return bitmap;
		matrix.postScale(scale, scale);
		Bitmap resulttBm = Bitmap.createBitmap(bitmap, 0, 0, (int) width,(int) height, matrix, true);
		if (resulttBm == null)
			return bitmap;
		return resulttBm;
	}
	
	/**
	 * storeInSD(bitmap, "/01/imgs/test.jpg");
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void storeInSD(Bitmap bitmap, String path) {
		String pre = Environment.getExternalStorageDirectory().getPath();
		if(!path.startsWith(pre))path = pre + path;
		Log.i( "_storeInSD",
				path + "::path__"
						+ path.substring(0, path.lastIndexOf('/') + 1)
						+ "__||__" + path.substring(path.lastIndexOf('/') + 1));
		File file = new File(path.substring(0, path.lastIndexOf('/') + 1));
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("here u go");
		}
		File imageFile = new File(file,
				path.substring(path.lastIndexOf('/') + 1));
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param filePath
	 *            "/qfc/01/we.jpg"
	 * @return
	 */
	public static Bitmap getBitmapFromSDCard(String filePath) {

		if (filePath == null)
			filePath = Environment.getExternalStorageDirectory() + filePath;
		File mfile = new File(filePath);
		if (mfile.exists()) {// �����ļ�����
			Bitmap bm = BitmapFactory.decodeFile(filePath);
			Log.i("SD", "exists");
			return bm;
		}
		return null;
	}
	
	/**
	 * �Ӹ��·������ͼƬ����ָ���Ƿ��Զ���ת����
	 * */
	public static Bitmap tryRotateBitmap(Bitmap bm, String imgpath) {// FIXME
	// Bitmap bm = loadBitmap(imgpath);
		if (bm == null)
			return null;
		int digree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(imgpath);
		} catch (IOException e) {
			e.printStackTrace();
			exif = null;
		}
		if (exif != null) {
			// ��ȡͼƬ���������Ϣ
			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);
			// ������ת�Ƕ�
			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				digree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				digree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				digree = 270;
				break;
			default:
				digree = 0;
				break;
			}
		}
		if (digree != 0) {
			// ��תͼƬ
			Matrix m = new Matrix();
			m.postRotate(digree);
			float widthScale = CommonUtils.getScreenWidth() / (float) bm.getWidth();
			float heightScale = CommonUtils.getScreenHeight() / (float) bm.getHeight();
			float scale = Math.min(1.0f, widthScale < heightScale ? widthScale
					: heightScale);// ���С����Ϊͼ̫��bm������
			m.preScale(scale, scale);
			Bitmap bm2 = null;
			try {
				bm2 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, false);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				// ���oom ����δ��תͼ
				return bm;
			}
			if (null != bm2)
				bm.recycle();
			return bm2;
		} else {
			return null;
		}
	}
	
	@SuppressLint("NewApi")
	public static long getBitmapsize(Bitmap bitmap) {
		if(null==bitmap) return 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();

	}
	
	public static Bitmap decodeSampledBitmapFromFile(String filename,  
            int reqWidth, int reqHeight) {  
  
        // First decode with inJustDecodeBounds=true to check dimensions  
        final BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(filename, options);  
  
        // Calculate inSampleSize  
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
  
        // Decode bitmap with inSampleSize set  
        options.inJustDecodeBounds = false;  
        return BitmapFactory.decodeFile(filename, options);  
    }
	
	public static int calculateInSampleSize(BitmapFactory.Options options,  
            int reqWidth, int reqHeight) {  
        // Raw height and width of image  
        final int height = options.outHeight;  
        final int width = options.outWidth;  
        int inSampleSize = 1;  
  
        if (height > reqHeight || width > reqWidth) {  
            if (width > height) {  
                inSampleSize = Math.round((float) height / (float) reqHeight);  
            } else {  
                inSampleSize = Math.round((float) width / (float) reqWidth);  
            }  
        }
        return inSampleSize;  
    }
	
}
