package test.book_moudle.bookstore;

import test.book_moudle.book.Book;
import test.book_moudle.book.BookCategory;
import test.book_moudle.book.EBook;
import test.book_moudle.book.PhysicalBook;
import test.book_moudle.io.BookResource;
import test.book_moudle.io.BookResourceLoader;
import test.global.Resource;
import test.utils.ResourceResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: AbstractConfigBookStore
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public abstract class AbstractConfigBookStore extends BookStore{
    //自定义扫描路径
    private String customeDIR;
    private List<Resource> configBooksResources;
    /**
     * 暴露启动
     */
    public void refresh(){
        recycledBooks();
        preparedBooks();
        destoryResources();
    }
    protected void preparedBooks() {
        if(customeDIR==null){
            BookResourceLoader.scanRescources(BookResource.SCAN_LACATION+BookResource.CONFIG_DIR);
        }else{
            BookResourceLoader.scanRescources(BookResource.SCAN_LACATION+customeDIR);
        }
        configBooksResources= BookResourceLoader.getCurrentResource();
        //解析资源文件,把书籍上架
        try {
            resolveResources(configBooksResources);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Book createBook(String bookTag){
        HashMap<String, String> currentMap = new HashMap<>();
        Book book=null;
        String[] s1 = bookTag.split(" ");
        for (int j = 1; j < s1.length-1; j++) {
            String[] s2 = s1[j].split(":");
            if(s2.length==2)
              currentMap.put(s2[0],s2[1]);
            else
                currentMap.put(s2[0],null);
        }
        if(bookTag.startsWith(BookResource.EBOOK_PREFIX)){
            book =(EBook) ResourceResolver.resolveObject(currentMap, EBook.class);
        }else if(bookTag.startsWith(BookResource.PHYBOOK_PREFIX)){
            book=(PhysicalBook) ResourceResolver.resolveObject(currentMap,PhysicalBook.class);
        }
        String bookCategory = currentMap.get("bookCategory");
        switch (bookCategory){
            case "文学":book.setBookCategory(BookCategory.文学);break;
            case "科学":book.setBookCategory(BookCategory.科学);break;
            case "历史":book.setBookCategory(BookCategory.历史);break;
            case "学习资料":book.setBookCategory(BookCategory.学习资料);break;
            default: book.setBookCategory(BookCategory.其他);break;
        }
        return book;
    }
    private void resolveResources(List<Resource> configResources) throws IOException {
        for (int i = 0; i < configResources.size(); i++) {
            Resource resource = configResources.get(i);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String s=null;
            while ((s=bufferedReader.readLine())!=null){
                s=s.trim();
                if(s.equals("")||!(s.startsWith(BookResource.PHYBOOK_PREFIX)
                        ||s.startsWith(BookResource.EBOOK_PREFIX))){
                    continue;
                }
                Book book = createBook(s);
                registerObject(book);
            }
            bufferedReader.close();
        }
    }
    protected void destoryResources(){
        BookResourceLoader.destoryResources();
        configBooksResources=null;
    }
    /**
     * 暴露扫描
     * @param configLocation
     */
    public void setScanDIR(String configLocation){
        if(new File(BookResource.SCAN_LACATION+configLocation).exists())
             customeDIR=configLocation;
        else{
            throw new RuntimeException("此目录路径不存在");
        }
    }
}
