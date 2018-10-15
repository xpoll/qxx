package cn.blmdz.pattern.nodesige;

import cn.blmdz.pattern.SchoolReport;

public class DecoratorTest {

    public static void main(String[] args) {
        String name = "张三";
        SchoolReport report = new SugarFourGradeSchoolReport();
        report.report();
        report.sign(name);
    }
}
