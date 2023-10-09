package test.book_moudle.bookstore;

import test.book_moudle.book.Book;
import test.book_moudle.book.BookCategory;
import test.book_moudle.book.PhysicalBook;
import test.book_moudle.book.Registry;
import test.book_moudle.io.BookResource;
import test.book_moudle.io.BookResourceWriter;

import java.io.File;
import java.util.*;

/**
 * ClassName: BookStore
 * Package: test.entity.book
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class BookStore implements Registry<Book> {
    private final List<HashMap<String,List<Book>>> accessBooks=new ArrayList<>();
    private final HashMap<String,List<Book>> accessLiteratureBooks=new HashMap<>();
    private final HashMap<String,List<Book>> accessScienceBooks=new HashMap<>();
    private final HashMap<String,List<Book>> accessHistoryBooks=new HashMap<>();
    private final HashMap<String,List<Book>> accessLearningMaterialBooks=new HashMap<>();
    private final HashMap<String,List<Book>> accessOtherBooks=new HashMap<>();
    /**
     * 电子书
     */
    private final Set<Book> accessEBooks=new HashSet<>();
    protected BookStore(){
        accessBooks.add(accessLearningMaterialBooks);
        accessBooks.add(accessScienceBooks);
        accessBooks.add(accessLiteratureBooks);
        accessBooks.add(accessOtherBooks);
        accessBooks.add(accessHistoryBooks);
    }
    /**
     * 注册书籍
     * @param book
     * @return
     */
    @Override
    public boolean registerObject(Book book) {
        if(book==null) {
            return false;
        }
        if(!checkBook(book)){
            return false;
        }
        if(book.getClass()==PhysicalBook.class){
           PhysicalBook book1=(PhysicalBook)book;
               BookCategory bookCategory = book1.getBookCategory();
               switch (bookCategory) {
                   case 文学: registerBook(book1,accessLiteratureBooks);break;
                   case 科学: registerBook(book1,accessScienceBooks);break;
                   case 历史: registerBook(book1,accessHistoryBooks);break;
                   case 学习资料: registerBook(book1,accessLearningMaterialBooks);break;
                   default: registerBook(book1,accessOtherBooks);break;
               }
               return true;
        }else{
            int start=accessEBooks.size();
             accessEBooks.add(book);
            int end = accessEBooks.size();
            return start==end?false:true;
        }
    }

    /**
     * 注册书籍具体执行
     * @param book
     * @param books
     */
    private synchronized void registerBook(PhysicalBook book,HashMap<String,List<Book>> books){
        String bookTag=book.getBookName();
        List<Book>bookInstances =books.get(bookTag);
        if(bookInstances==null){
            List<Book>books1=new ArrayList<>();
            books1.add(book);
            books.put(bookTag,books1);
        }else{
           bookInstances.add(book);
        }
    }
    /**
     * 回收书籍
     */
    protected void recycledBooks(){
        //收拾书籍
        List<Book> books = new ArrayList<>();
        for (HashMap<String, List<Book>> accessBook : accessBooks) {
            Collection<List<Book>> values = accessBook.values();
            for (List<Book> value : values) {
                books.addAll(value);
            }
        }
        books.addAll(accessEBooks);
        if(books.size()==0){
            return;
        }

        List<File> files =
                BookResourceWriter.scanFiles(BookResource.SCAN_LACATION + BookResource.CONFIG_DIR);

        //开始回收
        int i,j,flag=0;
        for(i=0;i<files.size();i++){
            for(j=0;j<books.size()/files.size();j++,flag++){
                BookResourceWriter.write(files.get(i),books.get(flag));
            }
        }
        while (flag!=books.size()){
            BookResourceWriter.write(files.get(i-1),books.get(flag));
            flag++;
        }
        BookResourceWriter.destoryFileMap();
        destroyBooks();
    }

    /**
     * 销毁书籍
     */
    private void destroyBooks() {
        for (HashMap<String, List<Book>> accessBook : accessBooks) {
            accessBook.clear();
        }
        accessEBooks.clear();
    }

    /**
     * 根据名字找书
     * @param bookName
     * @return
     */
    protected List<Book> getBooksByName(String bookName){
        ArrayList<Book> books = new ArrayList<>();
        for (HashMap<String, List<Book>> accessBook : accessBooks) {
            List<Book> bookList = accessBook.get(bookName);
            if(bookList!=null){
                books.addAll(bookList);
            }
        }
        for (Book eBook : accessEBooks) {
            if(eBook.getBookName().equals(bookName))
                books.add(eBook);
        }
        return books;
    }

    /**
     * 根据类别找实体书
     * @param bookCategory
     * @return
     */
    private   HashMap<String,List<Book>> getPhysicalBooksByCategory(BookCategory bookCategory){
        switch (bookCategory){
            case 文学: return accessLiteratureBooks;
            case 科学: return accessScienceBooks;
            case 历史: return accessHistoryBooks;
            case 学习资料: return accessLearningMaterialBooks;
            default:return accessOtherBooks;
        }
    }

    /**
     * 根据类别找书
     * @param bookCategory
     * @return
     */
    protected HashMap<String, List<Book>> getBooksByCategory(BookCategory bookCategory) {
        //实体书里已分类好了
        HashMap<String, List<Book>> physicalBooksByCategory = getPhysicalBooksByCategory(bookCategory);
        //在电子书里找
        for (Book accessEBook : accessEBooks) {
            if (accessEBook.getBookCategory() == bookCategory) {
                //查看书名分支
                List<Book> books = physicalBooksByCategory.get(accessEBook.getBookName());
                if (books == null) {
                    //没有那一栏
                    ArrayList<Book> bookList = new ArrayList<>();
                    bookList.add(accessEBook);
                    physicalBooksByCategory.put(accessEBook.getBookName(), bookList);
                } else {
                    //直接添加到那一栏
                    books.add(accessEBook);
                }
            }
        }
        return physicalBooksByCategory;
    }
    /**
     * 购买书籍
     * @param book
     * @return
     */
    public boolean removeBook(Book book){
       if(book.getClass()==PhysicalBook.class){
           for (HashMap<String, List<Book>> accessBook : accessBooks) {
               List<Book> books = accessBook.get(book.getBookName());
               boolean isExist=false;
               if(books!=null&&books.contains(book)){
                  isExist=true;
               }
               if(isExist){
                   books.remove(book);
                   return true;
               }
           }
           return false;
       }else{
           boolean isExist = accessEBooks.contains(book);
           if(isExist){
               accessEBooks.remove(book);
               return true;
           }
           return false;
       }
    }
    protected boolean checkBook(Book book) {
        return book.getBookName()==null? false:
                (book.getBookCategory()==null?false:
                        (book.getOwner()==null?false:
                                (book.getPrice()<=0?false:true)));
    }
    public abstract List<Book> getBooks(String bookName);
    public abstract  void startUp();
    public abstract  void shutDown();
    public abstract List<Book> getBooks(BookCategory bookCategory);
}
