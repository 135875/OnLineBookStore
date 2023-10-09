package test.user_moudle.users;

import test.user_moudle.user.User;

/**
 * ClassName: DefaultUserManager
 * Package: test.user_moudle.users
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class DefaultUserManager extends UserConfigManager{
    public DefaultUserManager() {
        refresh();
    }
    @Override
    public User  login(String userName,String password){
        User user = getUser(userName);
        if(user==null)
            return null;
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }


    public void startUp(){
        preparedUsers();
        destoryResources();
    }

    public void stopDown(){
        recycleUsers();
    }
}
