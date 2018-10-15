package cn.blmdz.pattern.common;

/**
 * 具体构件
 * @author yongzongyang
 * @date 2017年10月16日
 */
public class ConcreteComponent extends Component {

    // 具体方法
    @Override
    public void operate() {
        System.out.println("ConcreteComponent#operate() ... do something.");
    }
}
