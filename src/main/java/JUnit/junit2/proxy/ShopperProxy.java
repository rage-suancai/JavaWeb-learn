package JUnit.junit2.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class
ShopperProxy implements InvocationHandler {

    /*private final Shopper impl;

    public ShopperProxy(Shopper impl) {
        this.impl = impl;
    }

    // 代理卖瓜的行为
    @Override
    public void saleWatermelon(String customer) {
        //首先进行 代理商讨价还价行为
        System.out.println(customer + ": 哥们 这瓜多少钱一斤? ");
        System.out.println("老板: 两块钱一斤 ");
        System.out.println(customer + ": 你这瓜皮子是金子做的 还是瓜粒子是金子做的?");
        System.out.println("老板: 你瞅瞅现在哪有瓜啊 这都是大棚的瓜 你嫌贵不我还嫌贵呢");
        System.out.println(customer + ": 给我挑一个");

        impl.saleWatermelon(customer); // 讨价还价成功 进行我们告诉代理商的卖瓜行为
    }*/


    /*Object target;
    public ShopperProxy(Object target) {
        this.target = target;
    }*/

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String customer = (String) args[0];
        System.out.println(customer + ": 哥们 这瓜多少钱一斤? ");
        System.out.println("老板: 两块钱一斤 ");
        System.out.println(customer + ": 你这瓜皮子是金子做的 还是瓜粒子是金子做的?");
        System.out.println("老板: 你瞅瞅现在哪有瓜啊 这都是大棚的瓜 你嫌贵不我还嫌贵呢");
        System.out.println(customer + ": 给我挑一个");
        //return method.invoke(target, args);
        return null;
    }

}
