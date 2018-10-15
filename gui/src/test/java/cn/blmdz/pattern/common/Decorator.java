package cn.blmdz.pattern.common;

/**
 * 抽象装饰
 * @author yongzongyang
 * @date 2017年10月16日
 */
public abstract class Decorator extends Component {
    
    private Component component;

    // 通过构造传递被修饰者
    public Decorator(Component component) {
        this.component = component;
    }
    
    // 委托被修饰者执行
    @Override
    public void operate() {
        component.operate();
    }
}
