package cn.blmdz.pattern.design;

import cn.blmdz.pattern.SchoolReport;

public abstract class Decorator extends SchoolReport {
    private SchoolReport report;
    
    public Decorator(SchoolReport report) {
        this.report = report;
    }
    
    public void report() {
        report.report();
    }
    
    public void sign(String name) {
        report.sign(name);
    }
    
}
