package test.order_moudle.io;

import test.global.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * ClassName: UserResourceLoader
 * Package: test.user_moudle.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class OrderResourceLoader extends ResourceLoader {
    public static void  scanRescources(String configLocation){
        File files = new File(configLocation);
        if(!files.isDirectory()){
            throw new RuntimeException("不是目录");
        }
        File[] currentfiles = files.listFiles();
        for (int i = 0; i < currentfiles.length; i++) {
            String currentfileName = currentfiles[i].getName();
            if(currentfileName.endsWith(OrderResource.NORMAL_FILE_SUFFIX)
                    &&currentfileName.startsWith(OrderResource.ORDER_PREFIX)){
                loadResources(currentfiles[i]);
            }
        }
    }
    public static void loadResources(File resource) {
        try {
            FileInputStream fileInputStream = new FileInputStream(resource);
            loadResource(new OrderResource(fileInputStream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
