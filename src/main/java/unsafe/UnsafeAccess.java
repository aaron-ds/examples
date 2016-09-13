package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class UnsafeAccess {

    public static final Unsafe UNSAFE;

    static {
        Unsafe unsafe = null;
        try {
            PrivilegedExceptionAction<Unsafe> action = () -> {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                return (Unsafe)field.get(null);
            };
            unsafe = AccessController.doPrivileged(action);
        } catch (PrivilegedActionException e) {
            throw new RuntimeException("Couldn't get unsafe", e);
        }
        UNSAFE = unsafe;
    }
}
