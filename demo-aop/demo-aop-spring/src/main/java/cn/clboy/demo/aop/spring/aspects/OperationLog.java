package cn.clboy.demo.aop.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面，即一个横跨多个核心逻辑的功能，或者称之为系统关注点
 */
@Aspect
@Component
public class OperationLog {

    /**
     * execution(修饰符 返回类型  包名.类名.方法名(参数) 异常)
     * <p>
     * 返回类型 方法名 参数必选项
     * <p>
     * 第一个”*“符号	表示返回值的类型任意
     * 包名后面的”..“	表示当前包及子包
     * 第二个”*“	表示类名，*即所有类
     * 括号表示参数，两个点表示任何参数类型
     * <p>
     * 表示service包下的所有类的所有方法
     */
    @Pointcut("execution(* cn.clboy.demo.aop.spring.service..*.*(..))")
    public void pointcut() {
    }

    /**
     * 前置通知 ：在目标方法执行之前执行.
     */
    @Before("pointcut()")
    public void before() {
        System.out.println("before：操作前");
    }


    /**
     * 环绕通知 ：在目标方法执行前和执行后执行
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around-before：记录操作日志");
        Object res = joinPoint.proceed();
        System.out.println("around-after：记录操作日志\r\n");
        return res;
    }

    /**
     * 正常返回通知：和@After不同的是，只有当目标代码正常返回时，才执行拦截器代码
     */
    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("afterReturning：操作完毕");
    }

    /**
     * 抛出异常通知：和@After不同的是，只有当目标代码抛出了异常时，才执行拦截器代码
     */
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing：操作出错！");
    }


    /**
     * 后置通知 ：在目标方法执行之后执行,无论目标代码是否抛异常，拦截器代码都会执行
     */
    @After("pointcut()")
    public void after() {
        System.out.println("after：记录操作结果");
    }

}