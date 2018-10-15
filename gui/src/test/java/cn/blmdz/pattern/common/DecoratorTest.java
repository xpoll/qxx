package cn.blmdz.pattern.common;

public class DecoratorTest {

    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new ConcreteDecorator(component);
        component.operate();
    }
}
