package test.order_moudle.orders;

import test.global.Resource;
import test.order_moudle.io.OrderResource;
import test.order_moudle.io.OrderResourceLoader;
import test.order_moudle.order.Order;
import test.utils.ResourceResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: OrderConfigFactory
 * Package: test.order_moudle.orders
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class OrderConfigFactory extends OrderFactory{
    private String customeDIR;
    private List<Resource> configOrdersResources;

    /**
     * 暴露启动
     */
    public void refresh(){
        recycleOrders();
        preparedOrders();
        destoryResources();
    }
    protected void preparedOrders() {
        if(customeDIR==null){
            OrderResourceLoader.scanRescources(OrderResource.SCAN_LACATION+OrderResource.CONFIG_DIR);
        }else{
            OrderResourceLoader.scanRescources(OrderResource.SCAN_LACATION+customeDIR);
        }
        configOrdersResources= OrderResourceLoader.getCurrentResource();
        try {
            resolveResources(configOrdersResources);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected Order createOrder(String orderTag){
        HashMap<String, String> currentMap = new HashMap<>();
        Order order=null;
        String[] s1 = orderTag.split(" ");
        for (int j = 1; j < s1.length-1; j++) {
            String[] s2 = s1[j].split(":");
            currentMap.put(s2[0],s2[1]);
        }
        order=(Order) ResourceResolver.resolveObject(currentMap,Order.class);
        return order;
    }
    private void resolveResources(List<Resource> configResources) throws IOException {
        for (int i = 0; i < configResources.size(); i++) {
            Resource resource = configResources.get(i);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String s=null;
            while ((s=bufferedReader.readLine())!=null){
                s=s.trim();
                if(s.equals("")||!(s.startsWith(OrderResource.NORMAL_PREFIX))){
                    continue;
                }
                Order order= createOrder(s);
                registerObject(order);
            }
            bufferedReader.close();
        }
    }
    protected void destoryResources(){
        OrderResourceLoader.destoryResources();
        configOrdersResources=null;
    }
    /**
     * 暴露扫描
     * @param configLocation
     */
    public void setScanDIR(String configLocation){
        if(new File(OrderResource.SCAN_LACATION+configLocation).exists())
            customeDIR=configLocation;
        else{
            throw new RuntimeException("此目录路径不存在");
        }
    }
}
