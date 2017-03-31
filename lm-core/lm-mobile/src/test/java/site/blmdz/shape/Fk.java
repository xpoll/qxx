package site.blmdz.shape;

import com.google.common.base.Objects;

import lombok.Data;

/**
 * @author lm
 * 方块
 */
@Data
public class Fk {
	private boolean move;
	private Zb[] fk;
	
	/**
	 * 获取预定义种类
	 * @param i
	 */
	public Fk(int i) {
		this.fk = Co.TYPE[i];
		this.move = true;
	}
	/**
	 * 随机获取预定义种类
	 */
	public Fk() {
		this.fk = Co.TYPE[(int) (Math.random()*6)];
		this.move = true;
	}
	/**
	 * 自定义类型
	 * @param zk
	 */
	public Fk(Zb[] zk) {
		this.fk = zk;
		this.move = true;
	}

	/**
	 * 旋转 中心对称
	 * @param fk
	 * @return
	 */
	public Fk cq(Fk fk) {
		if(Objects.equal(Co.TYPE[4], fk.getFk())) {
			return fk;
		}
		Zb[] a = fk.getFk();
		Zb[] b = new Zb[4];
		for (int i = 0; i < a.length; i++) {
			b[i] = new Zb(a[i].getY(), a[i].getX());
		}
		return new Fk(b);
	}
	/**
	 * 逆旋转 90
	 * @param fk
	 * @return
	 */
	public Fk cn(Fk fk) {
		return cs(cs(cs(fk)));
	}
	/**
	 * 顺旋转 90
	 * @param fk
	 * @return
	 */
	public Fk cs(Fk fk) {
		if(Objects.equal(Co.TYPE[4], fk.getFk())) {
			return fk;
		}
		Zb[] a = fk.getFk();
		Zb[] b = new Zb[4];
		for (int i = 0; i < a.length; i++) {
			b[i] = new Zb(2 - a[i].getY(), a[i].getX());
		}
		return new Fk(b);
	}
	/**
	 * 位移
	 * @param fk
	 * @param m
	 * @return
	 */
	public Fk wy(Fk fk, int m) {
		Zb[] zb = fk.getFk();
		Zb[] b = new Zb[4];
		for (int i = 0; i < zb.length; i++) {
			b[i] = new Zb(zb[i].getX(), zb[i].getY() + m);
			if((zb[i].getY() + m) > 20)
				return fk;
		}
		return new Fk(b);
	}
	@Override
	public String toString(){
		return toString(3, 3);
	}
	public String toString(int x, int y){
		return Su.toStr(Su.th(new Bj(x, y), fk));
	}
}
