package test.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * ClassName: ResourceResolver
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class ResourceResolver {

    public static Object resolveObject(HashMap<String, String> currentMap,Class<?>clazz) {

        Object o=null;
        try {
             o = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        while (clazz!=Object.class){
            Field[] declaredFields =
                    clazz.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                    String property = declaredFields[i].toString();
                    property = property.substring(property.lastIndexOf(".") + 1);
                    try {
                        declaredFields[i].setAccessible(true);
                        String s1 = currentMap.get(property);
                        if(s1!=null){
                            if(declaredFields[i].getType()==double.class){
                                double s2=Double.valueOf(s1);
                                declaredFields[i].set(o,s2);
                            }else if(declaredFields[i].getType()==int.class){
                                int s3=Integer.valueOf(s1);
                                declaredFields[i].set(o,s3);
                            }else if(declaredFields[i].getType()==String.class){
                                declaredFields[i].set(o, s1);
                            }
                        }else{
                            if(declaredFields[i].getType()==double.class
                                    ||declaredFields[i].getType()==int.class){
                                declaredFields[i].set(o,0);
                            }else{
                                declaredFields[i].set(o,null);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
            }
                    clazz=clazz.getSuperclass();
        }
            return o;

    }
}
