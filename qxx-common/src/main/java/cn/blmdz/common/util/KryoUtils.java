package cn.blmdz.common.util;

import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.google.common.collect.Lists;

public class KryoUtils {
	private static final List<Class<?>> classList = Lists.newArrayList();
	private static final List<Serializer<?>> serializerList = Lists.newArrayList();
	private static final List<Integer> idList = Lists.newArrayList();
	private static final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			int size = KryoUtils.idList.size();

			for (int i = 0; i < size; ++i) {
				kryo.register(KryoUtils.classList.get(i), KryoUtils.serializerList.get(i),
						KryoUtils.idList.get(i).intValue());
			}

			kryo.setReferences(false);
			return kryo;
		}
	};

	public static synchronized void registerClass(Class<?> className, Serializer<?> serializer, int id) {
		classList.add(className);
		serializerList.add(serializer);
		idList.add(Integer.valueOf(id));
	}

	public static Kryo getKryo() {
		return kryos.get();
	}
}
