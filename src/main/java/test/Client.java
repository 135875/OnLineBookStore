package test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName: Client
 * Package: test
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class Client {
    public static final String ERROR ="error";
    private static boolean isLogin;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean start=true;
        ArrayList<String> str = new ArrayList<>();
        System.out.println("-=-=-=-=-客户端启动-=-=-=-=-");
        try {
            //输入
            Socket socket = new Socket("127.0.0.1", 8888);
            //登录流程
            while (!isLogin){
                receiveMessage(socket);//显示首页(登录页面)
                Login(socket);//发送登录信息
                isLogin = receiveMessage(socket);//接受验证结果
            }
            while (start){
                 receiveMessage(socket);//显示主页
                 //进入首页后
                    int i=scanner.nextInt();//选择选项
                    sendSelect(socket,i);
                    receiveMessage(socket);//得到相应选项的页面
                    switch (i){
                        case 1://查找书籍
                            String bookName = scanner.next();
                            //前进
                            str.add("bookName:"+bookName);
                            sendMessage(socket,str);//发送图书名字
                            boolean flag1 = receiveMessage(socket);//得到图书列表
                            if(flag1){//如果查到书籍则进入下一步选书
                                int i1 = scanner.nextInt();//选书的编号
                                sendSelect(socket,i1-1);//发0表示回到首页，其他则为书
                                if(i1==0)break;
                                receiveMessage(socket);//接受书籍详情
                                i1=scanner.nextInt();//1买 0返回首页
                                sendSelect(socket,i1);
                                if(i1==0) break;
                                receiveMessage(socket);//接收订单页面
                                str.clear();

                                String recipient = scanner.next();
                                String receiaddress = scanner.next();
                                String phone=scanner.next();

                                str.add("username:"+recipient);

                                str.add("addr:"+receiaddress);

                                str.add("phone:"+phone);

                                i1 = scanner.nextInt();
                                sendSelect(socket,i1);//确定下单状态
                                if(i1==0)break;
                                sendMessage(socket,str);//发送表单
                                receiveMessage(socket);//接收下单成功消息
                                }
                            scanner.nextInt();
                            str.clear();
                            //没有则返回主页面
                            break;
                        case 2://图书架
                            int i2 = scanner.nextInt();//选择哪类书籍
                            sendSelect(socket,i2);
                            boolean flag2 = receiveMessage(socket);//得到分类后的书籍书架或者错误信息
                            if(flag2) {
                                i2 = scanner.nextInt();//选书的编号
                                sendSelect(socket, i2 - 1);//发0表示回到首页，其他则为书
                                if (i2 == 0) break;
                                receiveMessage(socket);//接受书籍详情
                                i2 = scanner.nextInt();//1买 0返回首页
                                sendSelect(socket, i2);
                                if (i2 == 0) break;
                                receiveMessage(socket);//接收订单页面
                                str.clear();

                                String recipient = scanner.next();
                                String receiaddress = scanner.next();
                                String phone = scanner.next();

                                str.add("username:" + recipient);

                                str.add("addr:" + receiaddress);

                                str.add("phone:" + phone);

                                i2 = scanner.nextInt();
                                sendSelect(socket, i2);//确定下单状态
                                if (i2 == 0) break;
                                sendMessage(socket, str);//发送表单
                                receiveMessage(socket);//接收下单成功消息
                            }
                            scanner.nextInt();
                            str.clear();
                            break;
                        case 3://发布图书
                            int i3 = scanner.nextInt();
                            //默认输入不出错 在1和2
                            sendSelect(socket,i3);//发布哪类书
                            receiveMessage(socket);//接受表单页面
                            ArrayList<String> book3 = new ArrayList<>();
                            String s1 = scanner.next();
                            String s2 = scanner.next();
                            String s3 = scanner.next();
                            String s4 = scanner.next();
                            String s5 = scanner.next();
                            String s6 = scanner.next();
                            String s7 = scanner.next();
                            book3.add("bookName:"+s1);
                            book3.add("author:"+s2);
                            book3.add("owner:"+s3);
                            book3.add("price:"+s4);
                            book3.add("desc:"+s5);
                            book3.add("bookCategory:"+s6);
                            if(i3==1){
                                book3.add("url"+s7);
                            }else{
                                book3.add("severalTimesNew:"+s7);
                            }
                            sendMessage(socket,book3);
                            receiveMessage(socket);//接受发布书籍成功消息
                            scanner.nextInt();
                            str.clear();
                            break;
                        case 4://订单管理
                            int i4=scanner.nextInt();
                            break;
                        case 5://个人中心
                            int i5=scanner.nextInt();//选择要修改的内容
                            sendSelect(socket,i5);
                            if(i5==1){
                                receiveMessage(socket);//接收修改表单
                                ArrayList<String> user5 = new ArrayList<>();
                                String str1 = scanner.next();
                                String str2 = scanner.next();
                                String str3 = scanner.next();
                                user5.add("username:"+str1);
                                user5.add("addr:"+str2);
                                user5.add("phone:"+str3);
                                sendMessage(socket,user5);//发送修改信息
                                receiveMessage(socket);
                            }
                            break;
                        default://退出
                            start=false;
                            break;
                    }
                }

        } catch (Exception e) {
            System.exit(0);
        }
    }
    private static void Login(Socket socket) {
        ArrayList<String> message = new ArrayList<>();
        String username="username:";
        String password="password:";
        Scanner scanner = new Scanner(System.in);
        String s=null;
        s=scanner.next();
        username+=s;
        message.add(username);
        s=scanner.next();
        password+=s;
        message.add(password);
        sendMessage(socket,message);
    }

    private static void sendSelect(Socket socket,int i){
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            // 3、将低级的字节输出流包装成高级的打印流
            PrintStream ps = new PrintStream(os);
            ps.println(i);
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void sendMessage(Socket socket, List<String> message){
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            // 3、将低级的字节输出流包装成高级的打印流
            PrintStream ps = new PrintStream(os);
            for (String s : message) {
                ps.println(s);
            }
            ps.println("end");
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean receiveMessage(Socket socket){
        InputStream is = null;
        boolean isOn=true;
        try {
            is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ( (msg = br.readLine()) != null ) {
                if(msg.equals(ERROR)){
                    isOn=false;
                    break;
                }
                if(msg.equals("end"))
                    break;
                System.out.println(msg);
            }
            return isOn;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
