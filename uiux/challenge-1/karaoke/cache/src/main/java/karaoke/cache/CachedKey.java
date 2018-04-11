package karaoke.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class CachedKey {
    final Method method;
    final Object[] arguments;

    public CachedKey(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachedKey cachedKey = (CachedKey) o;
        return Objects.equals(method, cachedKey.method) &&
                Arrays.equals(arguments, cachedKey.arguments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }

    @Override
    public String toString() {
        return "CachedKey{" + "method=" + method + ", arguments=" + Arrays.toString(arguments) + '}';
    }
}
