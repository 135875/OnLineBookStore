package test.book_moudle.book;

import java.util.Objects;

/**
 * ClassName: EBook
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class EBook extends Book {
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return super.toString()+
                " url:" + url
                ;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EBook eBook = (EBook) o;
        return Objects.equals(url, eBook.url);
    }
    @Override
    public int hashCode() {
        return Objects.hash(getBookName()+getAuthor()+getOwner()+url);
    }
}
