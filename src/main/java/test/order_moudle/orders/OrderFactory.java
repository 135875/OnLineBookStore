package test.order_moudle.orders;

import test.book_moudle.book.Registry;
import test.order_moudle.io.OrderResource;
import test.order_moudle.io.OrderResourceWriter;
import test.order_moudle.order.Order;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: OrderFactory
 * Package: test.order_moudle.orders
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class OrderFactory implements Registry<Order> {
    private HashMap<String,List<Order>> accessOrders =new HashMap<>();

    public OrderFactory() {
    }

    @Override
    public boolean registerObject(Order order) {
        if(order == null){
            return false;
        }
        if(!checkOrder(order)){
            return false;
        }
        order.setTime(getLocalDate());
        if(accessOrders.get(order.getUsername())!=null){
            accessOrders.get(order.getUsername()).add(order);
        }else{
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order);
            accessOrders.put(order.getUsername(),orders);
        }
        return true;
    }
    private String getLocalDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    protected void recycleOrders(){
        ArrayList<Order> orders = new ArrayList<>();

        for (List<Order> orderList : accessOrders.values()) {
            orders.addAll(orderList);
        }
        if(orders.size()==0){
            return;
        }
        List<File> files =
                OrderResourceWriter.scanFiles(OrderResource.SCAN_LACATION + OrderResource.CONFIG_DIR);
        int i,j,flag=0;
        for(i=0;i<files.size();i++){
            for(j=0;j<orders.size()/files.size();j++,flag++){
                OrderResourceWriter.write(files.get(i),orders.get(flag));
            }
        }
        while (flag!=orders.size()){
            OrderResourceWriter.write(files.get(i-1),orders.get(flag));
            flag++;
        }
        OrderResourceWriter.destoryFileMap();
        destroyOrders();
    }
    private void destroyOrders() {
        accessOrders.clear();
    }


    public List<Order> getOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        for (List<Order> orderList : accessOrders.values()) {
            orders.addAll(orderList);
        }
        return orders;
    }
    public List<Order> getOrder(String userName){
        return accessOrders.get(userName)==null?new ArrayList<>(): accessOrders.get(userName);
    }

    public boolean removeOrder(Order order){
        List<Order> orderList = accessOrders.get(order.getUsername());
        if(orderList.contains(order))
            orderList.remove(order);
        return true;
    }

    protected  boolean checkOrder(Order order){
        return order.getUsername()==null?false:
                (order.getPhone()==null?false:
                        (order.getAddr()==null?false:
                                (order.getPrice()==null?false:
                                        (order.getDesc()==null?false:true))));
    }
    public abstract void startUp();
    public abstract void stopDown();
}
