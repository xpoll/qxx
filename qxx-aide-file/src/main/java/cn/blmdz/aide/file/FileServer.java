package cn.blmdz.aide.file;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.blmdz.aide.file.exception.FileException;

public interface FileServer {
	String write(String path, MultipartFile file) throws FileException;

	String write(String path, File file) throws FileException;

	String write(String path, InputStream inputStream) throws FileException;

	boolean delete(String path) throws FileException;
}
