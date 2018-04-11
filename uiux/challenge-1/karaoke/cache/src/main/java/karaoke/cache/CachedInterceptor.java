package karaoke.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Cached
public class CachedInterceptor {
    @Inject
    private CachedHolder holder;

    @PostConstruct
    public void initCache(InvocationContext ctx) {
        holder.createCache(ctx.getTarget());
        try {
            ctx.proceed();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @PreDestroy
    public void cleanCache(InvocationContext ctx) {
        try {
            holder.removeCache(ctx.getTarget());
        } catch (IllegalStateException ignore) {
            // holder is already undeployed.
        }
    }

    @AroundInvoke
    public Object invoke(InvocationContext ctx) throws Exception {
        if (ctx.getMethod().getAnnotation(Cached.class) == null) {
            return ctx.proceed();
        }
        Object result;
        try {
            result = holder.get(ctx.getTarget(), ctx.getMethod(), ctx.getParameters());
        } catch (ExceptionCache ignore) {
            result = ctx.proceed();
            holder.set(ctx.getTarget(), ctx.getMethod(), ctx.getParameters(), result);
        }
        return result;
    }

}