package test.global;

import java.io.InputStream;

/**
 * ClassName: Resource
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class Resource {
    public static final String NORMAL_FILE_SUFFIX=".txt";
    public static final String NORMAL_PREFIX="< ";
    public static final String NORMAL_SUFFIX=" >";
    public static final String SCAN_LACATION=System.getProperty("user.dir");
    private InputStream  inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    public Resource(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
