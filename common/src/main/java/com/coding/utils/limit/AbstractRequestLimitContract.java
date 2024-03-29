package com.coding.utils.limit;

import com.coding.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * @author guanweiming
 */
@Slf4j
@Aspect
@Component
public abstract class AbstractRequestLimitContract {

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {

//        try {
//            doLimit(HttpKit.getRequest(), joinPoint, limit);
//        } catch (RequestLimitException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("发生异常: ", e);
//            throw e;
//        }
    }

    /**
     * 请实现类自行实现相关方法
     *
     * @param request   HttpServletRequest对象
     * @param joinPoint JoinPoint
     * @param limit     limit对象
     * @throws RequestLimitException 超出频率限制
     */
//    protected abstract void doLimit(HttpServletRequest request, JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException;
}
