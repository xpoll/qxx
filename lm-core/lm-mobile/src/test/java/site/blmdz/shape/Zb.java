package site.blmdz.shape;

import lombok.Data;

/**
 * @author lm
 * 坐标
 */
@Data
public class Zb {

	private int x,y;
	
	private boolean move = true;
	
	private boolean have = true;

	public Zb(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Zb(int x, int y, boolean move, boolean have) {
		this.x = x;
		this.y = y;
		this.move = move;
		this.have= have;
	}
	public boolean compare(Zb a, Zb b) {
		if(a.getX() == b.getX() && a.getY() == b.getY())
			return true;
		return false;
	}
}
