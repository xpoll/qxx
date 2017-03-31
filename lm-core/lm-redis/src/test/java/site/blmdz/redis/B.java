package site.blmdz.redis;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.terminus.common.utils.Joiners;

public class B {

	public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
		list.add("abc");
		list.add("abc");
		list.add("abc");
		list.add("abc");
		list.add("abcd");
		System.out.println(list.toString());
		List<String> d = Lists.newCopyOnWriteArrayList(Sets.newLinkedHashSet(list));
		System.out.println(Joiners.COMMA.join(d));
		
	}
}