package test.order_moudle.orders;

/**
 * ClassName: DefaultOrderFactory
 * Package: test.order_moudle.orders
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class DefaultOrderFactory extends OrderConfigFactory{
    public DefaultOrderFactory() {
        refresh();
    }
    public void startUp(){
        preparedOrders();
        destoryResources();
    }
    public void stopDown(){
        recycleOrders();
    }
}
