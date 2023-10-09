package test.user_moudle.users;

import test.book_moudle.book.Registry;
import test.user_moudle.io.UserResource;
import test.user_moudle.io.UserResourceWriter;
import test.user_moudle.user.User;

import java.io.File;
import java.util.*;

/**
 * ClassName: UserManager
 * Package: test.book_moudle.bookstore
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class UserManager implements Registry<User> {

    private HashMap<String,User> accessAdmin=new HashMap<>();

    private HashMap<String,User> accessUsers =new HashMap<>();

    private Set<String> telephoneBook =new HashSet<>();

    public UserManager(){
    }
    @Override
    public boolean registerObject(User user) {
        if(user == null){
            return false;
        }
        if(!checkUser(user)){
            return false;
        }
        String role = user.getRole();
        if(telephoneBook.contains(user.getPhone())){
            return false;
        }
        if(accessUsers.get(user.getUsername())!=null||
                accessAdmin.get(user.getUsername())!=null){
            return false;
        }
        if(role.equals("user")){
            accessUsers.put(user.getUsername(),user);
            telephoneBook.add(user.getPhone());
        }else{
            accessAdmin.put(user.getUsername(),user);
            telephoneBook.add(user.getPhone());
        }
        return true;
    }


    protected void recycleUsers(){
        ArrayList<User> users = new ArrayList<>();
        for (User user : accessAdmin.values()) {
            users.add(user);
        }
        for (User user : accessUsers.values()) {
            users.add(user);
        }

        if(users.size()==0){
            return;
        }
        List<File> files =
                UserResourceWriter.scanFiles(UserResource.SCAN_LACATION + UserResource.CONFIG_DIR);
        int i,j,flag=0;
        for(i=0;i<files.size();i++){
            for(j=0;j<users.size()/files.size();j++,flag++){
                UserResourceWriter.write(files.get(i),users.get(flag));
            }
        }
        while (flag!=users.size()){
            UserResourceWriter.write(files.get(i-1),users.get(flag));
            flag++;
        }
        UserResourceWriter.destoryFileMap();
        destroyUsers();
    }
    private void destroyUsers() {
        accessUsers.clear();
        accessAdmin.clear();
        telephoneBook.clear();
    }


    protected List<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        for (User user : accessUsers.values()) {
            users.add(user);
        }
        for(User user: accessAdmin.values()){
            users.add(user);
        }
        return users;
    }
    protected User getUser(String userName){
        return accessUsers.get(userName)==null?
                (accessAdmin.get(userName)==null?
                        null:accessAdmin.get(userName)):accessUsers.get(userName);
    }
    //封禁某人
    public boolean removeUser(User user){
        user.setStatus(1);
        return true;
    }
    //提升某人
    public boolean updateUser(User user){
        if(user.getRole().equals("user")){
            user.setRole("admin");
        }else{
            user.setRole("user");
        }
        return true;
    }

    protected  boolean checkUser(User user){
        return user.getUsername()==null?false:
                 (user.getPassword()==null?false:
                        (user.getPhone()==null?false:
                                (user.getAddr()==null?false:true)));
    }

    public abstract User  login(String userName,String password);

    public abstract void startUp();

    public abstract void stopDown();
}
