package com.level.demo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.level.Constant;
import com.level.model.Word;
import com.level.util.CfgUtil;
import com.level.util.RegEx;
import com.level.util.ResourceUtil;

//Java8 学习笔记(一)——Lambda与Functional（函数式）接口
//作者：英勇青铜5
//链接：https://www.jianshu.com/p/0b4b59966276
//來源：简书
//简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。

//1.Lambda
//
//Lambda常见组成形式：参数列表——>函数体
//
//匿名内部类：
//
//new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello World!!!");
//            }
//        }).start();
//
//Lambada形式：
//
//new Thread(()->System.out.println("Hello World!!!")).start();
//
//()包括的就是参数列表，但这里无参
//System.out.println("Hello World!!!")是函数体
//
//1.1 参数列表
//
//没有参数时:一定要加上()
//
//new Thread(()->System.out.println("Hello World!!!")).start();
//
//有一个参数时:
//1.如果写了参数类型，就需要加()
//2.如果没有写参数类型，可以不加()
//Arrays.asList("qwe","asd","zxc").forEach((String s) -> System.out.println(s));  
//Arrays.asList("qwe","asd","zxc").forEach( s -> System.out.println(s));  
//有两个参数时：一定需要()；当参数需要加修饰符或者标签时，参数则需要加上完整的参数类型，否则，可以不加完整的参数类型。
//Arrays.asList("b","a","c","d").sort((final String s1,final String s2)->s1.compareTo(s2));
//1.2函数体
//
//Lambda的函数体和普通Java的方法差不多
//
//函数体只有一行，可以省略{}，需要返回值时，return也可以省略
//Arrays.asList("b","a","c","d").sort((s1,s2)->s1.compareTo(s2));
//多行时则需要{}，需要返回值时，return也不可以省略
//Arrays.asList("b","a","c","d").sort((s1,s2) ->{
//        int result = s1.compareTo(s2);
//        return result;
//});
//1.3 Lambda表达式中的变量
//
//参数
//Arrays.asList("b","a","c","d").sort((s1,s2)->s1.compareTo(s2));
//s1,s2就是Labmbda表达式的参数
//
//局部变量和自由变量
//private static void printHelloLambda(int times, String str) {
//    Runnable runnable = () -> {
//        for (int i = 0; i < times; i++) {
//            System.out.println(str);
//        }
//    };
//    new Thread(runnable).start();
//}
//运行结果就是打印times次string
//for()中的i就是局部变量
//
//int times, String str既不是参数，也不是局部变量，而是自由变量
//
//自由变量在Lambda表达式中不能修改。如果是采用内明内部类的形式，内部类想用使用方法的参数int times, String str，是需要加上final的。
//
//在Lambda表达式中的this代表创建Lambda表达式方法的this，表达式所在的类。
//
//Lambda表达式中的异常同普通方法一样，也是有两种处理方法。
//1.表达式函数体内进行try-catch处理
//2.接口方法进行throws。Lambda所在的方法进行throws是无效的。
//
//1.4 Lambda表达式方法引用
//
//Arrays.asList("b", "a", "c", "d").forEach(System.out :: println);
//String[] strings = { "acb", "abc", "cb", "bc" };
//List<String> list = Arrays.asList(strings);
//Collections.sort(list, String::compareTo);
//方法引用形式：
//类 ::静态方法
//对象::方法
//对象::静态方法
//
//1.5构造方法引用
//
//形式：类::new
//
//Arrays.asList()返回的是ArrayList，如果我们想要可以根据需要来确定返回List的类型，就可以使用构造方法引用。
//
//import java.util.List;
//@FunctionalInterface
//public interface ICreater<T extends List<?>> {
//    T create();
//}
//定义一个接口泛型为List的子类，接口内抽象方法无参，返回值为T
//
//public class LambdaDemo {
//    public static void main(String[] args) {
//        forEach();
//    }
//
//    private static void forEach() {
//    
//        LambdaDemo lambdaDemo = new LambdaDemo();
//        
//        List<String> list_2 =lambdaDemo.asList(LinkedList::new, strings);
//        Collections.sort(list_2, String::compareTo);
//        list.stream().forEach(System.out::println);
//    }
//
//    public <T> List<T> asList(ICreater<List<T>> creater, T... t) {
//        List<T> list = creater.create();
//        for (T a : t)
//            list.add(a);
//        return list;
//    }
//}
//构造方法引用，接口需要有一个无参的并且一定有返回值。
//
//2.Functional（函数式）接口
//
//语言设计者投入了大量精力来思考如何使现有的函数友好地支持lambda。最终采取的方法是：增加函数式接口的概念。函数式接口就是一个具有一个方法的普通接口。像这样的接口，可以被隐式转换为lambda表达式。java.lang.Runnable与java.util.concurrent.Callable是函数式接口最典型的两个例子。在实际使用过程中，函数式接口是容易出错的：如有某个人在接口定义中增加了另一个方法，这时，这个接口就不再是函数式的了，并且编译过程也会失败。为了克服函数式接口的这种脆弱性并且能够明确声明接口作为函数式接口的意图，Java 8增加了一种特殊的注解@FunctionalInterface（Java 8中所有类库的已有接口都添加了@FunctionalInterface注解）。
//Functional接口就是只有一个抽象方法的接口。
//注意是只有一个抽象方法，不是只有一个方法。也就是说Functional接口除了一个抽象方法外，还可以有默认方法和静态方法。
//
//2.1默认方法
//
//默认方法就是在接口中定义一个方法用default修饰
//
//定义一个水生生物接口IWaterAnimal：
//
//@FunctionalInterface
//public interface IWaterAnimal {
//     void run(String s);
//     default void breathe(){
//         System.out.println("可以在水中呼吸");
//     }
//}
//一个青蛙类Forg
//
//public class Frog implements IWaterAnimal {
//    
//    public static void main(String[] args) {
//        Forg forg = new Forg();
//        forg.breathe();
//        forg.run("我是青蛙，我会跳");
//    }
//
//    @Override
//    public void run(String s) {
//        System.out.println(s);
//    }
//}
//运行结果就是：
//可以在水中呼吸 我是青蛙，我会跳
//
//2.1解决接口中默认方法冲突
//
//青蛙不仅可以在水中，还可以在陆地，再定义一个陆地动物接口ILandAnimal
//
//@FunctionalInterface
//public interface ILandAnimal {
//    void run(String s);
//    default void breathe(){
//     System.out.println("可以在空气呼吸");
//    }
//}
//青蛙类Frog再去实现ILandAnimal接口，这时 IWaterAnimal和ILandAnimal中都有breathe()方法，此时的Forg类中则必须指明breathe()是实现的哪一个接口中的方法。
//
//public class Frog implements IWaterAnimal,ILandAnimal{
//    
//    public static void main(String[] args) {
//        Forg frog = new Forg();
//        forg.breathe();
//        forg.run("我是青蛙，我会跳");
//    }
//
//    @Override
//    public void run(String s) {
//        System.out.println(s);
//    }
//
//    @Override
//    public void breathe() {
//        ILandAnimal.super.breathe();
//    }
//}
//ILandAnimal.super.breathe();是指明了使用ILandAnimal接口中的breathe()方法，此时运行结果就变得不一样：
//可以在空气呼吸 我是青蛙，我会跳
//如果指明了IWaterAnimal.super.breathe();，运行结果就是：
//可以在水中呼吸 我是青蛙，我会跳
//
//当然也可以选择覆盖这个breathe()方法，在Forg类中修改方法breathe()
//
//@Override
//public void breathe() {
//   System.out.println("青蛙既可以在水中呼吸也可以在空气呼吸");
//}
//运行结果就是:
//青蛙既可以在水中呼吸也可以在空气呼吸 我是青蛙，我会跳
//当实现的接口中默认方法冲突时，要通过接口名.super.方法名的方式来指定方法。
//
//2.3父类方法与接口中默认方法相同
//
//青蛙有个大嘴巴，写一个BigMouth的类，然后Forg继承这个BigMouth类
//
//public class BigMouth {
//   public void openMouth(){
//       System.out.println("张开大嘴巴");
//   }
//}
//此时的Forg类：
//
//public class Forg extends BigMouth implements IWaterAnimal,ILandAnimal{
//    
//    public static void main(String[] args) {
//        Forg frog = new Forg();
//        frog.breathe();
//        frog.run("我是青蛙，我会跳");
//        frog.openMouth();
//    }
//
//    @Override
//    public void run(String s) {
//        System.out.println(s);
//    }
//
//    @Override
//    public void breathe() {
//        System.out.println("青蛙既可以在水中呼吸也可以在空气呼吸");
//    }
//}
//加入了第7行的方法后，运行结果
//青蛙既可以在水中呼吸也可以在空气呼吸 我是青蛙，我会跳 张开大嘴巴
//
//这时在IWaterAnimal水生动物接口中加入openMouth()方法
//
//@FunctionalInterface
//public interface IWaterAnimal {
//     void run(String s);
//     default void openMouth(){
//         System.out.println("水生生物张开大嘴巴");
//     }
//     default void breathe(){
//         System.out.println("可以在水中呼吸");
//     }
//}
//运行结果
//青蛙既可以在水中呼吸也可以在空气呼吸 我是青蛙，我会跳 张开大嘴巴
//根据运行结果，IWaterAnimal接口中的openMouth()方法并没有运行。
//当父类中的方法和接口的默认方法一样时，默认调用的父类中的方法。也就是说不要试图通过接口的默认方法来覆盖Object类的方法。
//--
//
//2.4 静态方法
//
//@FunctionalInterface
//public interface IWaterAnimal {
//     void run(String s);
//     static void swim(){
//         System.out.println("水生生物会游泳");
//     }
//     default void openMouth(){
//         System.out.println("水生生物张开大嘴巴");
//     }
//     default void breathe(){
//         System.out.println("可以在水中呼吸");
//     }
//}
//在Forg类中就可以直接通过IWaterAnimal.swim()来调用。和普通的Java类的静态方法相同。
//
//3.最后
//
//Lambda表达式简化了代码的书写，却增加了阅读代码的难度。我现在刚刚开始学使用，回头看写的方法，往往还得思考写的是啥。现在学习写Lambda表达式的思路就是，代码中哪些可以省略，省略后JVM能不能推测出来省略的是啥，怎么省略。
//



public class LambdaFunctionalDemo {

	public static void main(String[] args) {
		String cfg_augend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_augend");
		Map<String, String> augendMap = loadWordList(cfg_augend);
	}
	public static Map<String, String> loadWordList(String file) {
		System.out.println("parse word file: " + file);
		List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
		Map<String, String> wordMap = words.parallelStream()
				.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim())).map(RegEx::split2Word2)
				.distinct() // 剔重
				// .collect(Collectors.toSet());
				.collect(Collectors.toMap(Word::getSpelling, Word::getSentences));
		System.out.println("unique words count: " + wordMap.size());
		return wordMap;
	}
}
