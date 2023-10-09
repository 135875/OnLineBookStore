package test.user_moudle.user;

import java.util.Objects;

/**
 * ClassName: User
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class User {
     private String username;

     private String password;

     private String phone;

     private String addr;

     private int status;

     private int grade;

     private String role;

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getPhone() {
          return phone;
     }

     public void setPhone(String phone) {
          this.phone = phone;
     }

     public String getAddr() {
          return addr;
     }

     public void setAddr(String addr) {
          this.addr = addr;
     }

     public int getStatus() {
          return status;
     }

     public void setStatus(int status) {
          this.status = status;
     }

     public int getGrade() {
          return grade;
     }

     public void setGrade(int grade) {
          this.grade = grade;
     }

     public String getRole() {
          return role;
     }

     public void setRole(String role) {
          this.role = role;
     }

     @Override
     public String toString() {
          return
                  "username:" + username +
                  " password:" + password  +
                  " phone:" + phone +
                  " addr:" + addr +
                  " status:" + status +
                  " grade:" + grade +
                  " role:" + role ;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          User user = (User) o;
          return status == user.status && grade == user.grade && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone) && Objects.equals(addr, user.addr) && Objects.equals(role, user.role);
     }

     @Override
     public int hashCode() {
          return Objects.hash(username, password, phone, addr, status, grade, role);
     }
}
