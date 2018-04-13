package karaoke.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Method;

@Data
@ToString
@EqualsAndHashCode
class CachedKey {
    private final Method method;
    private final Object[] arguments;
}
