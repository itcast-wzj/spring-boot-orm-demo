package com.wzj.dynamic.mutil.datasource.compoent;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Created by wzj on 2023/3/26 20:31
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 实现: 方法和类上都有@DS注解, 优先走方法的@DS注解
     * service包下的所有类所有方法都会被这里拦截, 所以可能方法没有使用DS注解, 对应的类上可能也没有使用DS注解, 所以要做非空判断!!!
     */
    @Pointcut("execution(* com.wzj.dynamic.mutil.datasource.service.*.*(..))")
    public void pointCut1(){}

    /**
     * TODO 这个@annotation只能获取方法上的注解, 所以被这里拦截了方法上就一定有Ds注解,所以就一定有值, 所以不要做非空判断!!!
     * 不能实现: 方法和类上都有@DS注解, 优先走方法的@DS注解
     */
    @Pointcut("@annotation(com.wzj.dynamic.mutil.datasource.compoent.Ds)")
    public void pointCut2(){}

    @SneakyThrows
    @Around("pointCut1()")
    public Object round(ProceedingJoinPoint joinPoint){
        /**
         * 方法和类上都有@DS注解, 优先走方法的
         */
        try {
            String routeingKey = getRouteingKey(joinPoint);
            DynamicDataSourceContextHolder.set(routeingKey);
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.remove();
        }
    }

    private String getRouteingKey(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if(signature instanceof MethodSignature){
            Ds methodDsAnnotation = ((MethodSignature) signature).getMethod().getAnnotation(Ds.class);
            if(methodDsAnnotation != null){
                return methodDsAnnotation.value();
            }

            Ds classDsAnnotation = joinPoint.getTarget().getClass().getAnnotation(Ds.class);
            if(classDsAnnotation != null){
                return classDsAnnotation.value();
            }
        }
        return "";
    }
}
