package test;


import test.book_moudle.book.Book;
import test.book_moudle.book.BookCategory;
import test.book_moudle.book.EBook;
import test.book_moudle.book.PhysicalBook;
import test.book_moudle.bookstore.BookStore;
import test.book_moudle.bookstore.DefaultBookStore;
import test.book_moudle.io.BookResource;
import test.order_moudle.order.Order;
import test.order_moudle.orders.DefaultOrderFactory;
import test.order_moudle.orders.OrderFactory;
import test.user_moudle.user.User;
import test.user_moudle.users.DefaultUserManager;
import test.user_moudle.users.UserManager;
import test.utils.Drawer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: UsedBookTradingPlatform
 * Package: test
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class UsedBookTradingPlatform {
    public static final String ERROR ="error";
    private static BookStore bookStore=new DefaultBookStore();

    private static UserManager userManager=new DefaultUserManager();

    private static OrderFactory orderFactory=new DefaultOrderFactory();

    public static void main(String[] args) {
        System.out.println("-=-=-=-=-服务端启动-=-=-=-=-");
        HashMap<String, String> msg = new HashMap<>();
        User user=null;
        boolean start=true;
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            int count=0;
            String state;
            //登录流程
            while (true){
                timeDelay();
                sendMessage(socket, Drawer.drawLoginPage());//发送登录页面
                receiveMessage(socket,msg);//接受登录信息
                user= userManager.login(msg.get("username"), msg.get("password"));
                //用户验证
                if(user!=null){
                    timeDelay();
                    if(count<=1)state="优";
                    else if(count<=3)state="良";
                    else state="差";
                    System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+msg.get("username")+"登录服务器");
                    sendMessage(socket,Drawer.drawLoginSuccess());
                    break;
                }else{
                    timeDelay();
                    count++;
                    sendMessage(socket,Drawer.drawLoginFailure()+ERROR);
                }
            }
            //
            timeDelay();
            //实际逻辑
            while (start){
                timeDelay();
                sendMessage(socket,Drawer.drawIndexPage());//发送首页
                //选项
                int i=0;
                i = receiveSelect(socket);//接收选项
                switch (i){
                    case 1://查找书籍
                        timeDelay();
                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"正在查找书籍");
                        sendMessage(socket,Drawer.findBookPage());//发送查找书籍的页面
                        msg.clear();

                        receiveMessage(socket,msg);//得到图书名字
                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找书籍《"+msg.get("bookName")+"》");
                        List<Book> books = bookStore.getBooks(msg.get("bookName"));
                        if(books.size()==0){
                            timeDelay();
                            sendMessage(socket,Drawer.drawMessage("没有您要找的书籍")+ERROR);
                        }
                        else{
                            timeDelay();
                            sendMessage(socket,Drawer.drawList(books));//发送图书列表

                            int i1 = receiveSelect(socket);//接收要购买的图书编号
                            if(i1>=0){
                                timeDelay();//查看书籍;
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找书籍《"+msg.get("bookName")+"》详情");
                                sendMessage(socket,Drawer.drawBook(books.get(i1)));//发送书籍详情
                                Book book = books.get(i1);
                                i1 = receiveSelect(socket);//购买意愿
                                if(i1==1){//买
                                    //发送订单页面
                                    timeDelay();
                                    sendMessage(socket,Drawer.drawMessage("请仔细填写订单!信息接收人、接收地址、以及电话号码:")+"\n信息接收人:\n接收地址:\n电话号码:\n");
                                    i1 = receiveSelect(socket);//确定状态
                                    if(i1==1){//要购买
                                        msg.clear();
                                        receiveMessage(socket,msg);
                                        Order order=new Order();
                                        order.setUsername(msg.get("username"));
                                        order.setAddr(msg.get("addr"));
                                        order.setPhone(msg.get("phone"));
                                        order.setDesc(book.getBookName());
                                        order.setPrice(String.valueOf(book.getPrice()));
                                        orderFactory.registerObject(order);
                                        //发送成功信息
                                        timeDelay();
                                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"已购买书籍《"+msg.get("bookName")+"》");
                                        sendMessage(socket,Drawer.drawMessage(book.getBookName()+"购买成功"));
                                        bookStore.removeBook(book);
                                    }
                                }//不买则返回主页面
                            }
                        }
                        break;
                    case 2://图书架
                        timeDelay();
                        sendMessage(socket,Drawer.drawBookShelf());//发送要进入的页面
                        int i2=receiveSelect(socket);//接受客户端选哪类书籍
                        List<Book> books2=null;
                        boolean flag2=true;
                        switch (i2) {
                            case 1:
                                books2 = bookStore.getBooks(BookCategory.科学);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找"+BookCategory.科学+"类书籍");
                                timeDelay();
                                sendMessage(socket, Drawer.drawList(books2));
                                break;
                            case 2:
                                books2 = bookStore.getBooks(BookCategory.文学);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找"+BookCategory.文学+"类书籍");
                                timeDelay();
                                sendMessage(socket, Drawer.drawList(books2));
                                break;
                            case 3:
                                books2   = bookStore.getBooks(BookCategory.历史);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找"+BookCategory.历史+"类书籍");
                                timeDelay();
                                sendMessage(socket, Drawer.drawList(books2));
                                break;
                            case 4:
                                books2  = bookStore.getBooks(BookCategory.学习资料);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找"+BookCategory.学习资料+"类书籍");
                                timeDelay();
                                sendMessage(socket, Drawer.drawList(books2));
                                break;
                            case 5:
                                books2  = bookStore.getBooks(BookCategory.其他);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找"+BookCategory.其他+"类书籍");
                                timeDelay();
                                sendMessage(socket, Drawer.drawList(books2));
                                break;
                            default://没接收的
                                flag2 = false;
                                timeDelay();
                                sendMessage(socket, Drawer.drawMessage("错误选项！即将返回首页")+ERROR);
                                break;
                        }
                        if(flag2){//说明没有发生错误选项
                             i2 = receiveSelect(socket);//接收要购买的图书编号
                            if(i2>=0){
                                timeDelay();//查看书籍;
                                sendMessage(socket,Drawer.drawBook(books2.get(i2)));//发送书籍详情
                                Book book = books2.get(i2);
                                System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"要查找书籍《"+book.getBookName()+"》详情");
                                i2 = receiveSelect(socket);//购买意愿
                                if(i2==1){//买
                                    //发送订单页面
                                    timeDelay();
                                    sendMessage(socket,Drawer.drawMessage("请仔细填写订单!信息接收人、接收地址、以及电话号码:")+"\n信息接收人:\n接收地址:\n电话号码:\n");
                                    i2 = receiveSelect(socket);//确定状态
                                    if(i2==1){//要购买
                                        msg.clear();
                                        receiveMessage(socket,msg);
                                        Order order=new Order();
                                        order.setUsername(msg.get("username"));
                                        order.setAddr(msg.get("addr"));
                                        order.setPhone(msg.get("phone"));
                                        order.setDesc(book.getBookName());
                                        order.setPrice(String.valueOf(book.getPrice()));
                                        orderFactory.registerObject(order);
                                        //发送成功信息
                                        timeDelay();
                                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"已购买书籍《"+book.getBookName()+"》");
                                        sendMessage(socket,Drawer.drawMessage(book.getBookName()+"购买成功"));
                                        bookStore.removeBook(book);
                                    }
                                }//不买则返回主页面
                            }
                        }
                        break;
                    case 3://发布图书
                        timeDelay();
                        sendMessage(socket,Drawer.drawMessage2("用户"+user.getUsername()+"您好，您是要售卖您的二手电子图书(按1)还是二手实体书(按2)"));//发布图书页面
                        int i3 = receiveSelect(socket);
                        msg.clear();
                        boolean flag3=false;
                        switch (i3){
                            case 1:
                                timeDelay();
                                sendMessage(socket,Drawer.drawInputEBook());//发送表单
                                receiveMessage(socket,msg);//接收填报信息
                                EBook book=new EBook();
                                book.setBookName(BookResource.L_BOOK_MARKS+msg.get("bookName")+BookResource.R_BOOK_MARKS);
                                book.setAuthor(msg.get("author"));
                                book.setOwner(msg.get("owner"));
                                book.setPrice(Double.parseDouble(msg.get("price")));
                                book.setDesc(msg.get("desc"));
                                book.setBookCategory(returnCategory(msg.get("bookCategory")));
                                book.setUrl(msg.get("url"));
                                flag3 = bookStore.registerObject(book);
                                break;
                            case 2:
                                timeDelay();
                                sendMessage(socket,Drawer.drawInputPhysBook());
                                receiveMessage(socket,msg);
                                PhysicalBook book1=new PhysicalBook();
                                book1.setBookName(BookResource.L_BOOK_MARKS+msg.get("bookName")+BookResource.R_BOOK_MARKS);
                                book1.setAuthor(msg.get("author"));
                                book1.setOwner(msg.get("owner"));
                                book1.setPrice(Double.parseDouble(msg.get("price")));
                                book1.setDesc(msg.get("desc"));
                                book1.setBookCategory(returnCategory(msg.get("bookCategory")));
                                book1.setSeveralTimesNew(Double.parseDouble(msg.get("severalTimesNew")));
                                flag3 = bookStore.registerObject(book1);
                                break;
                        }
                        if(flag3) {
                            timeDelay();
                            sendMessage(socket,Drawer.drawMessage("成功"));
                        } else{
                            timeDelay();
                            sendMessage(socket,Drawer.drawMessage("失败")+ERROR);
                        }break;
                    case 4:
                        List<Order> orders = orderFactory.getOrder(user.getUsername());
                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"正查找订单");
                        timeDelay();
                        sendMessage(socket,Drawer.drawOrderPage(orders));
                        break;
                    case 5:
                        timeDelay();
                        sendMessage(socket,Drawer.drawIndividualCenter(user));
                        System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"正查看个人信息");
                        int i5 = receiveSelect(socket);
                        if(i5==1){
                            timeDelay();
                            sendMessage(socket,Drawer.drawMessage("请仔细填写你要修改的内容!用户名、地址、以及电话:")+"\n用户名:\n地址:\n电话:\n");
                            msg.clear();
                            receiveMessage(socket,msg);
                            user.setPhone(msg.get("phone"));
                            user.setAddr(msg.get("addr"));
                            user.setUsername(msg.get("username"));
                            System.out.println(new Date()+"【 安全状态："+state+"】: 用户"+user.getUsername()+"正修改个人信息");
                            timeDelay();
                            sendMessage(socket,Drawer.drawMessage("修改成功"));

                        }
                        break;
                    default:
                        bookStore.shutDown();
                        userManager.stopDown();
                        orderFactory.stopDown();
                        start=false;
                        timeDelay();
                        System.out.println(new Date()+"用户"+user.getUsername()+"退出登录");
                        sendMessage(socket,Drawer.drawLogoutPage());break;

                }
            }
        } catch (Exception e) { // 捕获异常！
            System.exit(0);
        }finally {
            System.out.println(new Date()+"用户"+user.getUsername()+"登录异常");
        }
    }
    private static BookCategory returnCategory(String bookCategory){
        switch (bookCategory){
            case "文学": return BookCategory.文学;
            case "历史": return BookCategory.历史;
            case "科学": return BookCategory.科学;
            case "学习资料": return BookCategory.学习资料;
            default: return BookCategory.其他;
        }
    }
    private static int receiveSelect(Socket socket){
        InputStream is = null;
        try {
            is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
           if((msg = br.readLine()) != null){
               return Integer.parseInt(msg);
           }
           return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }    private static void receiveMessage(Socket socket, HashMap<String,String> message){
        InputStream is = null;
        try {
            is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ( (msg = br.readLine()) != null ) {
                if(msg.equals("end"))
                    break;
                String[] s = msg.split(":");
                message.put(s[0],s[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static void sendMessage(Socket socket,String message){
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            // 3、将低级的字节输出流包装成高级的打印流
            PrintStream ps = new PrintStream(os);
            ps.println(message);
            ps.println("end");
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void timeDelay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
