package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Sets;

import lombok.Data;

@Data
public class Auths implements Serializable {
	private static final long serialVersionUID = 6554743011603841486L;
	
	private Set<ProtectedAuth> protectedList;
	private Set<WhiteAuth> whiteList;

	public void merge(Auths mergedAuths) {
		
		if (!CollectionUtils.isEmpty(mergedAuths.getProtectedList())) {
			if (this.protectedList == null) {
				this.protectedList = Sets.newHashSet();
			}
			this.protectedList.addAll(mergedAuths.getProtectedList());
		}
		
		if (!CollectionUtils.isEmpty(mergedAuths.getWhiteList())) {
			if (this.whiteList == null) {
				this.whiteList = Sets.newHashSet();
			}
			this.whiteList.addAll(mergedAuths.getWhiteList());
		}
	}
}
