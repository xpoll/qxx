package cn.blmdz.session.serialize;

import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.io.BaseEncoding;

import cn.blmdz.session.service.Serialize;
import cn.blmdz.session.util.KryoUtils;

/**
 * Kryo序列化
 * @author lm
 * @date 2016年11月4日 下午10:36:40
 */
public class KryoSerialize implements Serialize {
	
	@Override
	public String serialize(Object o) {
		Kryo kryo = KryoUtils.getKryo();
		Output output = new ByteBufferOutput(4096, 40960);
		kryo.writeClassAndObject(output, o);
		return BaseEncoding.base16().encode(output.getBuffer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> deserialize(String o) {
		Kryo kryo = KryoUtils.getKryo();
		Input input = new ByteBufferInput(BaseEncoding.base16().decode(o));
		return (Map<String, Object>) kryo.readClassAndObject(input);
	}

}
