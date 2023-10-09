package test.order_moudle.io;

import test.global.Resource;

import java.io.InputStream;

/**
 * ClassName: OrderResource
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class OrderResource extends Resource {
    public static final String ORDER_PREFIX="order_";
    public static  String CONFIG_DIR="/resources/orders";
    public OrderResource(InputStream inputStream) {
        super(inputStream);
    }
}
