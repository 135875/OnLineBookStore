package test.book_moudle.bookstore;

import test.book_moudle.book.Book;
import test.book_moudle.book.BookCategory;
import test.book_moudle.io.BookResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: DefaultBookStore
 * Package: test.entity
 * Description:
 * 应对数据访问拦截
 * @author : 康熙
 * @version : v1.0
 */
public class DefaultBookStore extends AbstractConfigBookStore {
    //订单处理
    //日志处理
    public DefaultBookStore() {
        refresh();
    }
    private void OrderBooksByPrice(List<Book> books){
        Collections.sort(books);
    }
    //查询相关书籍
    @Override
    public List<Book> getBooks(String bookName) {
        bookName= BookResource.L_BOOK_MARKS+bookName+BookResource.R_BOOK_MARKS;
        List<Book> books = getBooksByName(bookName);
        OrderBooksByPrice(books);
        return books;
    }
    @Override
    public List<Book> getBooks(BookCategory bookCategory){
        ArrayList<Book> books = new ArrayList<>();
        HashMap<String, List<Book>> bookMap = getBooksByCategory(bookCategory);
        for (String s : bookMap.keySet()) {
            books.addAll(bookMap.get(s));
        }
        OrderBooksByPrice(books);
        return books;
    }
    @Override
    public void startUp(){
        preparedBooks();
        destoryResources();
    }
    @Override
    public void shutDown(){
        recycledBooks();
    }
}
