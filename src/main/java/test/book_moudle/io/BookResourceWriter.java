package test.book_moudle.io;

import test.book_moudle.book.Book;
import test.book_moudle.book.EBook;
import test.book_moudle.book.PhysicalBook;
import test.global.ResourceWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BookResourceWriter
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class BookResourceWriter extends ResourceWriter {
    public static void write(File file, Book book){
        if(!file.exists()){
            throw new RuntimeException("文件不存在");
        }
        BufferedWriter bufferedWriter = null;
        try {
            if(fileMap.get(file.getName())==null){
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                fileMap.put(file.getName(),bufferedWriter);
            }else{
                bufferedWriter=fileMap.get(file.getName());
            }
            if(book.getClass()== EBook.class){
                bufferedWriter.write(BookResource.EBOOK_PREFIX+book+" desc:"+book.getDesc()+BookResource.NORMAL_SUFFIX);
            }else if(book.getClass()== PhysicalBook.class){
                bufferedWriter.write(BookResource.PHYBOOK_PREFIX+book+" desc:"+book.getDesc()+BookResource.NORMAL_SUFFIX);
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<File> scanFiles(String configLocation){
        List<File> files=new ArrayList<>();
        File file = new File(configLocation);
        if(!file.isDirectory()){
            throw new RuntimeException("不是目录");
        }
        File[] currentfiles = file.listFiles();
        for (int i = 0; i < currentfiles.length; i++) {
            String currentfileName = currentfiles[i].getName();
            if(currentfileName.endsWith(BookResource.NORMAL_FILE_SUFFIX)
                    &&currentfileName.startsWith(BookResource.BOOK_PREFIX)){
                files.add(currentfiles[i]);
            }
        }
        return files;
    }
}
