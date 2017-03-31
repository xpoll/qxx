package site.blmdz.shape;

import lombok.Getter;

/**
 * @author lm
 * 布局
 */
public class Bj {
	@Getter
	private int x;
	@Getter
	private int y;
	@Getter
	private Zb[] bj;
	public Bj(){
		this.x = 12;
		this.y = 22;
		this.bj = new Zb[x * y];
		int m = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if(i == 0
						|| i == (y - 1)
						|| j == 0
						|| j == (x - 1))
					bj[m++] = new Zb(j, i, false, false);
				else
					bj[m++] = new Zb(j, i, true, false);
			}
		}
	}
	public Bj(Zb[] bj) {
		this.x = 12;
		this.y = 22;
		this.bj = bj;
	}
	public Bj(int x, int y){
		this.x = x;
		this.y = y;
		this.bj = new Zb[x * y];
		int m = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
					bj[m++] = new Zb(j, i, false, false);
			}
		}
	}
	public Bj(int x, int y, Zb[] bj) {
		this.x = x;
		this.y = y;
		this.bj = bj;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
		.append("-----------------------").append(Co.ENTER)
		.append("--------       --------").append(Co.ENTER)
		.append("------   Start   ------").append(Co.ENTER)
		.append("--------       --by lm-").append(Co.ENTER)
		.append("-----------------------").append(Co.ENTER);
		for (int i = 0; i < bj.length; i++) {
			if(bj[i].isHave())
				sb.append(Co.Y).append(Co.K);
			if(!bj[i].isHave() && !bj[i].isMove())
				sb.append(Co.N).append(Co.K);
			if(!bj[i].isHave() && bj[i].isMove())
				sb.append(Co.K).append(Co.K);
			if ((i + 1) % x == 0)
				sb.append(Co.ENTER);
		}
		return sb.toString();
	}
	
	
}
