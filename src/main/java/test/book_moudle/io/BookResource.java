package test.book_moudle.io;

import test.global.Resource;

import java.io.InputStream;

/**
 * ClassName: BookResource
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class BookResource extends Resource {
    public static final String L_BOOK_MARKS="《";
    public static final String R_BOOK_MARKS="》";
    public static final String BOOK_PREFIX="book_";
    //default
    public static final String CONFIG_DIR="/resources/books";
    public static final String EBOOK_PREFIX="<u ";
    public static final String PHYBOOK_PREFIX="<p ";
    public BookResource(InputStream inputStream) {
        super(inputStream);
    }
}
