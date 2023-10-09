package test.order_moudle.order;

import java.util.Objects;

/**
 * ClassName: Order
 * Package: test.order_moudle.order
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class Order {
    private String username;
    private String addr;
    private String phone;
    private String price;
    private String desc;
    private String time;

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "username:" + username +
                " addr:" + addr +
                " phone:" + phone +
                " price:" + price +
                " desc:" + desc +
                " time:" + time ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(username, order.username) && Objects.equals(addr, order.addr) && Objects.equals(phone, order.phone) && Objects.equals(price, order.price) && Objects.equals(desc, order.desc) && Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, addr, phone, price, desc, time);
    }
}
