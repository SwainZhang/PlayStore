package manager;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MyPC on 2016/12/12.
 */

public class FileManager {
    public static  FileManager fileManager;
    public static FileManager getInstance(){
        if(fileManager==null){
            synchronized (FileManager.class){
                if(fileManager==null){
                    fileManager=new FileManager();
                }
            }
        }
        return  fileManager;
    }

        public  void getApkFiles(final  ArrayList<String> files, final DataChangedListener<ArrayList<String>> listener){

            new Thread(new Runnable() {

                @Override
                public void run() {

                    while (true){
                        File file=new File(Environment.getExternalStorageDirectory(),".apk");
                        if(file.exists()){
                            files.add(file.getPath());
                        }else{
                            listener.onDataChanged(files);
                            break;
                        }

                    }

                }
            }).start();



    }
    public  ArrayList<String> getApkFiles(){

        ArrayList<String> files=new ArrayList<>();
        while (true){
                    File file=new File(Environment.getExternalStorageDirectory(),".apk");
                    if(file.exists()){
                        files.add(file.getPath());
                    }else{
                        break;
                    }

                }
           return files;
            }




}
