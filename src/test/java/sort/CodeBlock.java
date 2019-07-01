package sort;

public class CodeBlock {
    static{
        System.out.println("1.静态代码块");
    }
    
    {
        System.out.println("3.构造代码块");
    }
    public CodeBlock(){
        System.out.println("4.无参构造函数");
    }
     
    public void sayHello(){
        {
            System.out.println("5.普通代码块");
        }
    }
     
    public static void main(String[] args) {
        System.out.println("2.执行了main方法");
         
        new CodeBlock().sayHello();
        System.out.println("---------------");
        new CodeBlock().sayHello();
    }
}
