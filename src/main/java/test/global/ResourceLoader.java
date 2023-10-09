package test.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ResourceLoader
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class ResourceLoader {
   private static List<Resource> currentResource=new ArrayList<>();
   protected static void loadResource(Resource resource){
      currentResource.add(resource);
   }
   public static List<Resource> getCurrentResource() {
      return currentResource;
   }
   public static void destoryResources(){
      destoryResources(currentResource);
   }
   public static void destoryResources(List<Resource> resources){
      if(resources==null|resources.size()==0)
         return;
      int i=0;
      while (resources.size()!=0){
         try {
            resources.get(i).getInputStream().close();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
         resources.remove(i);
      }
   }
}
