package site.blmdz.shape;

/**
 * @author lm
 * Util
 */
public class Su {
	/**
	 * 填充
	 * @param fk
	 * @param m
	 * @return
	 */
	public static Fk tc(Fk fk, int m) {
		Zb[] zb = fk.getFk();
		Zb[] b = new Zb[4];
		for (int i = 0; i < zb.length; i++) {
			b[i] = new Zb(zb[i].getX() + m, zb[i].getY());
		}
		return new Fk(b);
	}

	/**
	 * for Fk.toString
	 * @param sbj
	 * @return
	 */
	public static String toStr(Bj sbj){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sbj.getBj().length; i++) {
			if(sbj.getBj()[i].isHave())
				sb.append(Co.Y).append(Co.K);
			if(!sbj.getBj()[i].isHave() && !sbj.getBj()[i].isMove())
				sb.append(Co.K).append(Co.K);
			if(!sbj.getBj()[i].isHave() && sbj.getBj()[i].isMove())
				sb.append(Co.K).append(Co.K);
			if ((i + 1) % sbj.getX() == 0)
				sb.append(Co.ENTER);
		}
		return sb.toString();
	}
	

	/**
	 * 布局坐标元素替换
	 * @param bj
	 * @param zb
	 * @return
	 */
	public static Bj th(Bj bj, Zb[] zb){
		Zb[] n = bj.getBj();
		for (int i = 0; i < zb.length; i++) {
			int x = zb[i].getX();
			int y = zb[i].getY();
			int m = y * bj.getX() + x;
			n[m] = zb[i];
		}
		return new Bj(bj.getX(), bj.getY(), n);
	}

	/**
	 * 布局坐标元素移除
	 * @param bj
	 * @param zb
	 * @return
	 */
	public static Bj yc(Bj bj, Zb[] zb){
		Zb[] n = bj.getBj();
		for (int i = 0; i < zb.length; i++) {
			int x = zb[i].getX();
			int y = zb[i].getY();
			int m = y * bj.getX() + x;
			n[m] = new Zb(x, y, true, false);
		}
		return new Bj(bj.getX(), bj.getY(), n);
	}
}
