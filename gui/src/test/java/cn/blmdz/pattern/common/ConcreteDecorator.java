package cn.blmdz.pattern.common;

/**
 * 具体装饰
 * @author yongzongyang
 * @date 2017年10月16日
 */
public class ConcreteDecorator extends Decorator {

    // 定义被修饰者
    public ConcreteDecorator(Component component) {
        super(component);
    }
    
    // 定义自己的修饰方法
    public void decorator() {
        System.out.println("ConcreteDecorator#decorator() ... decorator something.");
    }
    
    // 重写父类的operate方法
    @Override
    public void operate() {
        this.decorator();
        super.operate();
    }
    
}
