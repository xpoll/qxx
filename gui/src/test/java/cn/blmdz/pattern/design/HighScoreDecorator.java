package cn.blmdz.pattern.design;

import cn.blmdz.pattern.SchoolReport;

public class HighScoreDecorator extends Decorator {

    public HighScoreDecorator(SchoolReport report) {
        super(report);
    }
    
    public void reportHighScore() {
        System.out.println("最高成绩：语文：80；数学：80；体育：99；自然：80。");
    }
    
    @Override
    public void report() {
        this.reportHighScore();
        super.report();
    }
}
