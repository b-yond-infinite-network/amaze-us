package karaoke.cache;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Lock(LockType.READ)
@Startup
public class CachedHolder {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private CacheManager jcacheManager;
    private Map<Object, Cache<CachedKey, Object>> caches = Collections.synchronizedMap(new HashMap<>());
    private Configuration<CachedKey, Object> config;

    @PostConstruct
    public void init() {
        CachingProvider jcacheProvider = Caching.getCachingProvider();
        this.jcacheManager = jcacheProvider.getCacheManager();
        this.config = new MutableConfiguration<CachedKey, Object>()
                .setTypes(CachedKey.class, Object.class)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 1)));
    }

    public void createCache(Object beanInstance) {
        this.caches.put(beanInstance, jcacheManager.createCache(
                "${beanInstance.class}_${counter.incrementAndGet()}",
                this.config
        ));
    }

    public void removeCache(Object beanInstance) {
        this.caches.remove(beanInstance);
    }

    public Object get(Object beanInstance, Method method, Object[] arguments) throws ExceptionCache {
        Cache<CachedKey, Object> methodCache = this.caches.get(beanInstance);
        CachedKey key = new CachedKey(method, arguments);
        if (methodCache.containsKey(key)) {
            logger.log(Level.INFO, "Using cache entry for {0}#{1}({2})", new Object[]{
                    beanInstance,
                    method.getName(),
                    Arrays.asList(arguments)
            });
            return methodCache.get(key);
        }
        throw new ExceptionCache("cache entry not found");
    }

    public void set(Object beanInstance, Method method, Object[] arguments, Object value) {
        Cache<CachedKey, Object> methodCache = this.caches.get(beanInstance);
        CachedKey key = new CachedKey(method, arguments);
        if (methodCache.putIfAbsent(key, value)) {
            logger.log(Level.INFO, "New cache entry for {0}#{1}({2})", new Object[]{
                    beanInstance,
                    method.getName(),
                    Arrays.asList(arguments)
            });
        }
    }

}
