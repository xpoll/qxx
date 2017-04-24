package cn.blmdz.recursive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 八皇后
 */
public class Test01 {
	static Set<Integer> row = new HashSet<>();
	static Set<Integer> col = new HashSet<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) {
		List<Coors> list = Lists.newArrayList();
		for (int i = 1; i < 9; i++) {
			int c = retCol();
			int r = retRow();
			System.out.println("(" + c + ", " + r + ")");
			list.add(new Coors(c, r));
		}
		for (int i = 1; i < 9; i++) {
			print(list, i);
		}
		System.out.println(sb.toString());
	}

	private static int retCol(){
		int i = (int)(Math.random() * 8) + 1;
		if (col.contains(i)) return retCol(); // +- 1 更快
		col.add(i);
		return i;
	}
	
	private static int retRow(){
		int i = (int)(Math.random() * 8) + 1;
		if (row.contains(i)) return retRow(); // +- 1 更快
		row.add(i);
		return i;
	}
	private static StringBuilder print(List<Coors> list, int row) {
		for (int i = 1; i < 9; i++) {
			sb.append(get(list, new Coors(row, i))).append(" ");
		}
		sb.append("\n");
		return sb;
	}
	
	private static String get(List<Coors> list, Coors coor) {
		for (Coors coors : list) {
			if (coors.getX() == coor.getX() && coors.getY() == coor.getY()) {
				return "0";
			}
		}
		return "#";
	}
}
@Data
@AllArgsConstructor
class Coors {
	private int x;
	private int y;
}