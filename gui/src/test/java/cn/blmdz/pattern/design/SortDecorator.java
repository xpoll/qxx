package cn.blmdz.pattern.design;

import cn.blmdz.pattern.SchoolReport;

public class SortDecorator extends Decorator {

    public SortDecorator(SchoolReport report) {
        super(report);
    }

    public void reportSort() {
        System.out.println("我的排名38名...");
    }
    
    @Override
    public void report() {
        this.reportSort();
        super.report();
    }
}
