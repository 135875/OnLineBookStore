package test.book_moudle.book;

/**
 * ClassName: Registry
 * Package: test.entity.book
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public interface Registry<T> {
    boolean registerObject(T object);
}
