package MyBatis.mybatis9;

import MyBatis.mybatis9.proxy.Shopper;
import MyBatis.mybatis9.proxy.ShopperProxy;

import java.lang.reflect.Proxy;

/**
 * 探究Mybatis的动态代理机制
 * 在探究动态代理机制之前 我们要先聊聊上面是代理 其实顾名思义 就好比我开了个大篷 里面种有西瓜 那么西瓜成熟了是不是得去卖掉赚钱
 * 而我们的西瓜非常多 一个人肯定卖不过来 肯定就要去多找几个开水果摊的帮我们卖 这就是一种代理 实际上是由水果摊老板在帮我们卖瓜
 * 我们只告诉老板卖多少钱 而至于怎么卖的是由水果摊老板决定的
 *
 * 那么现在我们来尝试实现一下这样的类结构 首先定义一个接口用于规范行为:
 *      public interface Shopper {
 *          // 卖瓜行为
 *          void saleWatermelon(String customer);
 *      }
 * 然后需要实现一下卖瓜行为 也就是我们要告诉老板卖多少钱 这里就直接写成成功出售:
 *      public class ShopperImpl implements Shopper {
 *          @Override
 *          public void saleWatermelon(String customer) {
 *              System.out.println("成功出售西瓜给 ===>" + customer);
 *          }
 *      }
 * 最后老板代理后肯定要用自己的方式去出售这些西瓜 成交之后再按照我们告诉老板的价格进行出售:
 *      private final Shopper impl;
 *
 *     public ShopperProxy(Shopper impl) {
 *         this.impl = impl;
 *     }
 *
 *     // 代理卖瓜的行为
 *     @Override
 *     public void saleWatermelon(String customer) {
 *         //首先进行 代理商讨价还价行为
 *         System.out.println(customer + ": 哥们 这瓜多少钱一斤? ");
 *         System.out.println("老板: 两块钱一斤 ");
 *         System.out.println(customer + ": 你这瓜皮子是金子做的 还是瓜粒子是金子做的?");
 *         System.out.println("老板: 你瞅瞅现在哪有瓜啊 这都是大棚的瓜 你嫌贵不我还嫌贵呢");
 *         System.out.println(customer + ": 给我挑一个");
 *
 *         impl.saleWatermelon(customer); // 讨价还价成功 进行我们告诉代理商的卖瓜行为
 *     }
 * 现在我们来试试看:
 *      public static void main(String[] args) {
 *           Shopper impl = new ShopperImpl();
 *           Shopper proxy = new ShopperProxy((impl));
 *           proxy.saleWatermelon("小吵闹");
 *      }
 * 这样的操作称为静态代理 也就是我们需要提前知道接口的定义并进行实现才可以完成代理 而Mybatis这样的是无法预知代理接口的 我们就需要用到动态代理
 *
 * JDK提供的反射框架就为我们很好地解决了动态代理的问题 在这里相当于对javaSE阶段反射的内容进行一个补充
 *      Object target;
 *      public ShopperProxy(Object target) {
 *          this.target = target;
 *      }
 *
 *      @Override
 *      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 *          String customer = (String) args[0];
 *          System.out.println(customer + ": 哥们 这瓜多少钱一斤? ");
 *          System.out.println("老板: 两块钱一斤 ");
 *          System.out.println(customer + ": 你这瓜皮子是金子做的 还是瓜粒子是金子做的?");
 *          System.out.println("老板: 你瞅瞅现在哪有瓜啊 这都是大棚的瓜 你嫌贵不我还嫌贵呢");
 *          System.out.println(customer + ": 给我挑一个");
 *          return method.invoke(target, args);
 *      }
 * 通过实现InvocationHandler来成为一个动态代理 我们发现它提供了一个invoke方法 用于调用被代理对象的方法并完成我们的代理工作 现在就可以通过Proxy.newProxyInstance来生成一个动态代理类:
 *      public static void main(String[] args) {
 *          Shopper impl = new ShopperImpl();
 *          Shopper shopper = (Shopper) Proxy.newProxyInstance(impl.getClass().getClassLoader(),
 *                  impl.getClass().getInterfaces(), new ShopperProxy(impl));
 *          shopper.saleWatermelon("小吵闹");
 *          System.out.println(shopper.getClass());
 *      }
 *
 * 通过打印类型我们发现 就是我们之前看到的那种奇怪的类: class com.sun.proxy.$Proxy0 因此Mybatis其实也是这样的来实现的(肯定有人问了: Mybatis是直接代理接口啊 你这个还不是要把接口实现了吗)
 * 那我们来改改 现在我们不代理任何类了 直接做接口实现:
 *     @Override
 *     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 *         String customer = (String) args[0];
 *         System.out.println(customer + ": 哥们 这瓜多少钱一斤? ");
 *         System.out.println("老板: 两块钱一斤 ");
 *         System.out.println(customer + ": 你这瓜皮子是金子做的 还是瓜粒子是金子做的?");
 *         System.out.println("老板: 你瞅瞅现在哪有瓜啊 这都是大棚的瓜 你嫌贵不我还嫌贵呢");
 *         System.out.println(customer + ": 给我挑一个");
 *         return null;
 *     }
 *
 *     public static void main(String[] args) {
 *         Shopper shopper = (Shopper) Proxy.newProxyInstance(Shopper.class.getClassLoader(),
 *                 new Class[]{Shopper.class}, new ShopperProxy());
 *         shopper.saleWatermelon("小吵闹");
 *         System.out.println(shopper.getClass());
 *     }
 * 我们可以去看看Mybatis的源码
 *
 * Mybatis的学习差不多到这里为止了 不过同样类型的框架还有很多 Mybatis属于半自动框架 SQL语句依然需要我们自己编写 虽然存在一定的麻烦 但是会更加灵活
 * 而后面我们还会学习JPA 它是全自动的框架 你几乎见不到SQL的影子
 */
public class Test {

    static void test1(){
        /*Shopper impl = new ShopperImpl();
        Shopper proxy = new ShopperProxy((impl));
        proxy.saleWatermelon("小吵闹");*/
    }

    static void test2(){
        /*Shopper impl = new ShopperImpl();

        Shopper shopper = (Shopper) Proxy.newProxyInstance(impl.getClass().getClassLoader(),
                impl.getClass().getInterfaces(), new ShopperProxy(impl));
        shopper.saleWatermelon("小吵闹");
        System.out.println(shopper.getClass());*/
    }

    static void test3(){
        Shopper shopper = (Shopper) Proxy.newProxyInstance(Shopper.class.getClassLoader(),
                new Class[]{Shopper.class}, new ShopperProxy());
        shopper.saleWatermelon("小吵闹");
        System.out.println(shopper.getClass());
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
    }

}
