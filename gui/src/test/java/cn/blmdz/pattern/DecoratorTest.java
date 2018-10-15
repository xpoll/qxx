package cn.blmdz.pattern;

public class DecoratorTest {

    public static void main(String[] args) {

        String name = "张三";
        SchoolReport report = new FourGradeSchoolReport();
        report.report();
        report.sign(name);
    
    }
}
