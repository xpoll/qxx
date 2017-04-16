package cn.blmdz.aide.file.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer size;
}
