package test.book_moudle.book;

/**
 * ClassName: PhysicalBook
 * Package: test.entity
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class PhysicalBook extends Book {
    private double severalTimesNew;
    public double getSeveralTimesNew() {
        return severalTimesNew;
    }
    public void setSeveralTimesNew(double severalTimesNew) {
        this.severalTimesNew = severalTimesNew;
    }
    @Override
    public String toString() {
        return  super.toString()+
                " severalTimesNew:" + severalTimesNew
                ;
    }
}
