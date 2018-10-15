package cn.blmdz.pattern.design;

import cn.blmdz.pattern.FourGradeSchoolReport;
import cn.blmdz.pattern.SchoolReport;

public class DecoratorTest {

    public static void main(String[] args) {

        String name = "张三";
        SchoolReport report = new FourGradeSchoolReport();
        report = new SortDecorator(report);
        report = new HighScoreDecorator(report);
        report.report();
        report.sign(name);
    
    }
}
