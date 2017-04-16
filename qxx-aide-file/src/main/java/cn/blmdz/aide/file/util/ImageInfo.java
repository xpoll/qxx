package cn.blmdz.aide.file.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImageInfo extends FileInfo {
	private static final long serialVersionUID = 1L;
	private Integer weight;
	private Integer height;
}
