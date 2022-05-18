package uz.paynet.plumber.exception;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static Supplier<NotFoundException> throwNotFound(Class<?> clazz, Long id){
        return () -> {
            throw new NotFoundException(clazz.getSimpleName() + " not found by ID=" + id);
        };
    }
}
