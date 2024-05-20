package com.learnhub.common.autoconfigure.mvc.aspects;

import com.learnhub.common.utils.ArrayUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.validate.Checker;
import com.learnhub.common.validate.annotations.ParamChecker;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

import java.util.List;

/**
 * CheckerAspect
 *
 * @author lm
 * @since 2024-05-15 17:35:52
 * @version 1.0
 */
public class CheckerAspect {
    @Before("@annotation(paramChecker)")
    public void before(JoinPoint joinPoint, ParamChecker paramChecker) {
        Object[] args = joinPoint.getArgs();
        if(ArrayUtils.isNotEmpty(args)){
            //遍历方法参数，参数是否实现了Checker接口
            for (Object arg : args){
                if(arg instanceof Checker) {
                    //调用check方法，校验业务逻辑
                    ((Checker<?>)arg).check();
                }else if(arg instanceof List){
                    //如果参数是一个集合也要校验
                    CollUtils.check((List) arg);
                }
            }
        }
    }
}
