package cn.blmdz;

import java.util.ArrayList;
import java.util.List;

public class Jiazi {
    
//    十天干：甲（jiǎ）、乙（yǐ）、丙（bǐng）、丁（dīng）、戊（wù）、己（jǐ）、庚（gēng）、辛（xīn）、壬（rén）、癸（guǐ）；
//    十二地支：子（zǐ）、丑（chǒu）、寅（yín）、卯（mǎo）、辰（chén）、巳（sì）、午（wǔ）、未（wèi）、申（shēn）、酉（yǒu）、戌（xū）、亥（hài）。

    // 十天干
    public final static String[] TIAN_GAN = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    // 十二地支
    public final static String[] DI_ZHI = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    // 十二属相
    public final static String[] SHU_XING = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    // 天干对应五行
    public final static String[] WU_XING = {"海中金", "炉中火", "大林木", "路旁土", "剑锋金", "山头火", "洞下水", "城墙土", "白腊金", "杨柳木", "泉中水", "屋上土", "霹雷火", "松柏木", "常流水", "沙中金", "山下火", "平地木", "壁上土", "金箔金", "佛灯火", "天河水", "大驿土", "钗钏金", "桑松木", "大溪水", "沙中土", "天上火", "石榴木", "大海水"};
    
    // 《史记》、《汉书》中均有部分记载
    
    // 十天干含义
    public final static String[] TIAN_GAN_MEAN = {
            "甲是拆的意思，指万物剖符甲而出也。",
            "乙是轧的意思，指万物出生，抽轧而出。",
            "丙是炳的意思，指万物炳然著见。",
            "丁是强的意思，指万物丁壮。",
            "戊是茂的意思，指万物茂盛。",
            "己是纪的意思，指万物有形可纪识。",
            "庚是更的意思，指万物收敛有实。",
            "辛是新的意思，指万物初新皆收成。",
            "壬是任的意思，指阳气任养万物之下。",
            "癸是揆的意思，指万物可揆度。",
    };
    // 十二地支含义
    public final static String[] DI_ZHI_MEAN = {
            "子是兹的意思，指万物兹萌于既动之阳气下。",
            "丑是纽，阳气在上未降。",
            "寅是移，引的意思，指万物始生寅然也。",
            "卯是茂，言万物茂也。",
            "辰是震的意思，物经震动而长。",
            "巳是起，指阳气之盛。",
            "午是仵的意思，指万物盛大枝柯密布。",
            "未是味，万物皆成有滋味也。",
            "申是身的意思，指万物的身体都已成就。",
            "酉是老的意思，万物之老也。",
            "戌是灭的意思，万物尽灭。",
            "亥是核的意思，万物收藏。",
    };
    

    
    public static void main(String[] args) {
        test(1993);
    }
    
    
    
    public static void test(Integer year) {
        int i = 0;
        int j = 0;
        List<String> list = new ArrayList<>();
        boolean turn = true;
        while (turn) {
            String cache = TIAN_GAN[i] + DI_ZHI[j];
            if (!list.isEmpty() && cache.equals(list.get(0))) {
                turn = false;
            } else {
                list.add(TIAN_GAN[i] + DI_ZHI[j]);
                i ++;
                j ++;
                i = (i < TIAN_GAN.length ? i : 0);
                j = (j < DI_ZHI.length ? j : 0);
            }
        }
        
        for (int k = 0; k < list.size(); k++) {
            System.out.print(String.format("%02d %s", (k + 1), list.get(k)));
            if (k != 0 && (k + 1) % TIAN_GAN.length == 0) System.out.println();
            else System.out.print("  ");
        }
        
        int tmp1 = (year - 4) % list.size() + 1;
        int tmp2 = (year - 4) % SHU_XING.length + 1;
        int tmp3 = (year - 4) % TIAN_GAN.length + 1;
        int tmp4 = (year - 4) % DI_ZHI.length + 1;
        
        System.out.println();
        
        System.out.println(String.format("%s年是%s年, 配%s, %s[%s]", year, list.get(tmp1 - 1), WU_XING[(tmp1 - 1)/2], DI_ZHI[tmp2 - 1], SHU_XING[tmp2 - 1]));
        System.out.println("《史记》、《汉书》中均有部分记载");
        System.out.println(TIAN_GAN_MEAN[tmp3 - 1]);
        System.out.println(DI_ZHI_MEAN[tmp4 - 1]);
    }
}
