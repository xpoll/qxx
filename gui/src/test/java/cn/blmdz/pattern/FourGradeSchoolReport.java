package cn.blmdz.pattern;

public class FourGradeSchoolReport extends SchoolReport {

    @Override
    public void report() {
        System.out.println("--------------成绩单--------------");
        System.out.println("语文：64；数学：61；体育：90；自然：72。");
        System.out.println("                                            家长签名");
        
    }

    @Override
    public void sign(String name) {
        System.out.println("                                    家长签名：" + name);
        
    }

}
