package cn.blmdz.pattern.nodesige;

import cn.blmdz.pattern.FourGradeSchoolReport;

public class SugarFourGradeSchoolReport extends FourGradeSchoolReport {

    public void reportHightScore() {
        System.out.println("最高成绩：语文：80；数学：80；体育：99；自然：80。");
    }
    
    public void reportSort() {
        System.out.println("我的排名38名...");
    }
    
    public void report() {
        this.reportHightScore();
        this.reportSort();
        super.report();
    }
}
