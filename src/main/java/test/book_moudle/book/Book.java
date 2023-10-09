package test.book_moudle.book;

import java.util.Objects;

/**
 * ClassName: Book
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class Book implements  Comparable<Book>  {
    private String bookName;

    private String owner;

    private String author;

    private double price;

    private String desc;
    private BookCategory bookCategory;
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public BookCategory getBookCategory() {
        return bookCategory;
    }
    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return "bookName:" + bookName +
                " owner:" + owner +
                " author:" + author +
                " price:" + price +
                " bookCategory:" + bookCategory
                ;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0 && Objects.equals(bookName, book.bookName) && Objects.equals(owner, book.owner) && Objects.equals(author, book.author) && Objects.equals(desc, book.desc) && bookCategory == book.bookCategory;
    }
    @Override
    public int hashCode() {
        return Objects.hash(bookName, owner, author, price, desc, bookCategory);
    }
    @Override
    public int compareTo(Book o) {
        return (int)(this.getPrice()-o.getPrice());
    }
}
