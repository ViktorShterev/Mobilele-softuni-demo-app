//package org.softuni;
//
//import org.springframework.aop.MethodBeforeAdvice;
//import org.springframework.aop.framework.ProxyFactory;
//
//import java.lang.reflect.Method;
//
//public class TestAopProxy {
//    public static void main(String[] args) {
//
//        ProxyFactory proxyFactory = new ProxyFactory(new MyPojo());
//
//        proxyFactory.addAdvice(
//                (MethodBeforeAdvice) (method, args1, target) -> System.out.println("WITHIN ADVICE")
//        );
//
//        Pojo pojo = (Pojo) proxyFactory.getProxy();
//
//        ourCode(pojo);
//    }
//
//    private static void ourCode(Pojo pojo) {
//        pojo.foo();
//    }
//}
//
//class MyPojo implements Pojo {
//
//    @Override
//    public void foo() {
//        System.out.println("Inside MyPojo.foo() method");
//    }
//}
//
//interface Pojo {
//    void foo();
//}
