package test.user_moudle.users;

import test.global.Resource;
import test.user_moudle.io.UserResource;
import test.user_moudle.io.UserResourceLoader;
import test.user_moudle.user.User;
import test.utils.ResourceResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: UserConfigManager
 * Package: test.user_moudle.users
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class UserConfigManager extends UserManager{
    private String customeDIR;
    private List<Resource> configUsersResources;

    /**
     * 暴露启动
     */
    public void refresh(){
        recycleUsers();
        preparedUsers();
        destoryResources();
    }



    protected void preparedUsers() {
        if(customeDIR==null){
            UserResourceLoader.scanRescources(UserResource.SCAN_LACATION+UserResource.CONFIG_DIR);
        }else{
            UserResourceLoader.scanRescources(UserResource.SCAN_LACATION+customeDIR);
        }
        configUsersResources= UserResourceLoader.getCurrentResource();
        try {
            resolveResources(configUsersResources);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected User createUser(String userTag){
        HashMap<String, String> currentMap = new HashMap<>();
        User user=null;
        String[] s1 = userTag.split(" ");
        for (int j = 1; j < s1.length-1; j++) {
            String[] s2 = s1[j].split(":");
            currentMap.put(s2[0],s2[1]);
        }
        user=(User) ResourceResolver.resolveObject(currentMap,User.class);
        return user;
    }
    private void resolveResources(List<Resource> configResources) throws IOException {
        for (int i = 0; i < configResources.size(); i++) {
            Resource resource = configResources.get(i);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String s=null;
            while ((s=bufferedReader.readLine())!=null){
                s=s.trim();
                if(s.equals("")||!(s.startsWith(UserResource.NORMAL_PREFIX))){
                    continue;
                }
                User user = createUser(s);
                registerObject(user);
            }
            bufferedReader.close();
        }
    }
    protected void destoryResources(){
        UserResourceLoader.destoryResources();
        configUsersResources=null;
    }

    /**
     * 暴露扫描
     * @param configLocation
     */
    public void setScanDIR(String configLocation){
        if(new File(UserResource.SCAN_LACATION+configLocation).exists())
            customeDIR=configLocation;
        else{
            throw new RuntimeException("此目录路径不存在");
        }
    }
}
