package test.user_moudle.io;

import test.global.Resource;

import java.io.InputStream;

/**
 * ClassName: UserResource
 * Package: test.entity.io
 * Description:
 *
 * @author : 康熙
 * @version : v1.0
 */
public class UserResource extends Resource {
    public static final String USER_PREFIX="user_";
    public static  String CONFIG_DIR="/resources/users";
    public UserResource(InputStream inputStream) {
        super(inputStream);
    }
}
