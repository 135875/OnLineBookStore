package test.global;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName: ResourceWriter
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class ResourceWriter {
       protected static HashMap<String, BufferedWriter>fileMap=new HashMap<>();
       public static void destoryFileMap() {
              if (fileMap.isEmpty()) {
                     return;
              }
              Set<String> strings = fileMap.keySet();
              Iterator<String> iterator = strings.iterator();
              while (iterator.hasNext()) {
                     String next = iterator.next();
                     BufferedWriter bufferedWriter = fileMap.get(next);
                     try {
                            bufferedWriter.close();
                     } catch (IOException e) {
                            throw new RuntimeException(e);
                     }
              }
              fileMap.clear();
       }
}