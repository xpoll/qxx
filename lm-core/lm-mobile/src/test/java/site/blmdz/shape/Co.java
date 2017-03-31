package site.blmdz.shape;

/**
 * @author lm
 * 常量
 */
public class Co {
	final static String Y = "@";
	final static String N = "*";
	final static String K = " ";
	final static String ENTER = "\n";

	final static Zb[][] TYPE ={
			{new Zb(0, 2), new Zb(0, 1), new Zb(0, 0), new Zb(1, 0)},
			{new Zb(0, 1), new Zb(0, 0), new Zb(1, 0), new Zb(2, 0)},
			{new Zb(0, 2), new Zb(0, 1), new Zb(1, 1), new Zb(1, 0)},
			{new Zb(1, 2), new Zb(0, 1), new Zb(1, 1), new Zb(0, 0)},
			{new Zb(0, 1), new Zb(1, 1), new Zb(0, 0), new Zb(1, 0)},
			{new Zb(0, 0), new Zb(1, 1), new Zb(1, 0), new Zb(2, 0)}
	};

	public static void main(String[] args) throws InterruptedException {
		/*
		 * 整体布局
		 */
		//Bj init
		Bj bj = new Bj();
		//Fk init
		Fk fk = new Fk();
		//布局内是否有方块
//		boolean fkhave = true;
		//显示当前方块形状
		System.out.println(fk.toString());
		//顺时针
		System.out.println(fk.cs(fk).toString());
		//逆时针
		System.out.println(fk.cn(fk).toString());
		//重新打印
		System.out.println(fk.toString());
		//添加方块x位置
		int tj = 4;
		fk = Su.tc(fk, tj);
		//显示方块改变后位置
		System.out.println(fk.toString(20, 20));
		while(true) {
			//显示此时的布局
			System.out.println(bj.toString());
			//步行一次
			fk = fk.wy(fk, 1);
			//布局内加入方块
			System.out.println(Su.th(bj, fk.getFk()).toString());
			Su.yc(bj, fk.getFk());
			Thread.sleep(500);
		}
	}
}








//quest

//while(true) {
//	//显示此时的布局
//	System.out.println(bj.toString());
//	//步行一次
//	fk = fk.wy(fk, 1);
//	//显示步行后样式
//	System.out.println(fk.toString(20, 20));
//	//布局内加入方块
//	System.out.println(Su.th(bj, fk.getFk()).toString());
//	Su.yc(bj, fk.getFk());
//	//此时布局应该不变  TODO 变了
//	System.out.println(bj.toString());
//	//步行一次
//	fk = fk.wy(fk, 1);
//	//布局内加入方块
//	System.out.println(Su.th(bj, fk.getFk()).toString());
//	//此时布局应该不变  TODO 变了
//	System.out.println(bj.toString());
//	Thread.sleep(1000);
//}
