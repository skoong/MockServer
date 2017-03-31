package api;

import java.lang.annotation.*;

/**
 * @author fkrauthan
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface API {
}
