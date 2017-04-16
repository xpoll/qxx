package cn.blmdz.aide.file.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

import cn.blmdz.aide.file.exception.FileInfoException;
import cn.blmdz.aide.file.exception.ImageInfoException;

public class FUtil {
	public static ImageInfo image(MultipartFile file) throws ImageInfoException {
		try {
			return getImageInfo(file.getBytes());
		} catch (IOException e) {
			throw new ImageInfoException("Get image info failed.", e);
		}
	}

	public static ImageInfo image(File file) throws ImageInfoException {
		try {
			return getImageInfo(Files.toByteArray(file));
		} catch (IOException e) {
			throw new ImageInfoException("Get image info failed.", e);
		}
	}

	public static ImageInfo image(InputStream input) throws ImageInfoException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageInfo imageInfo;
			try {
				byte[] buffer = new byte[1024];

				int len;
				while ((len = input.read(buffer)) > -1) {
					outputStream.write(buffer, 0, len);
				}

				outputStream.flush();
				imageInfo = getImageInfo(outputStream.toByteArray());
			} finally {
				if (outputStream != null) outputStream.close();
			}

			return imageInfo;
		} catch (IOException e) {
			throw new ImageInfoException("Get image info failed.", e);
		}
	}

	public static FileInfo file(MultipartFile file) throws FileInfoException {
		try {
			return new FileInfo(Integer.valueOf(file.getBytes().length));
		} catch (IOException e) {
			throw new FileInfoException("Get file info failed.", e);
		}
	}

	public static FileInfo file(File file) throws ImageInfoException {
		try {
			return new FileInfo(Integer.valueOf(Files.toByteArray(file).length));
		} catch (IOException e) {
			throw new ImageInfoException("Get file info failed.", e);
		}
	}

	public static FileInfo file(InputStream input) throws ImageInfoException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			FileInfo fileInfo;
			try {
				byte[] buffer = new byte[1024];

				int len;
				while ((len = input.read(buffer)) > -1) {
					outputStream.write(buffer, 0, len);
				}

				outputStream.flush();
				fileInfo = new FileInfo(Integer.valueOf(outputStream.toByteArray().length));
			} finally {
				if (outputStream != null) outputStream.close();
			}

			return fileInfo;
		} catch (IOException e) {
			throw new ImageInfoException("Get file info failed.", e);
		}
	}

	public static String contentType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		return fileNameMap.getContentTypeFor(path);
	}

	public static String absolutePath(String url, String realPath) {
		return realPath.startsWith("/")
				? (url.endsWith("/") ? url + realPath.substring(1, realPath.length()) : url + realPath)
				: (url.endsWith("/") ? url + realPath : url + "/" + realPath);
	}

	public static String upPath(String path) {
		return path.startsWith("/") ? path.substring(1, path.length()) : path;
	}

	private static ImageInfo getImageInfo(byte[] fileData) throws IOException {
		ImageInfo imageInfo = new ImageInfo();
		InputStream inputStream = new ByteArrayInputStream(fileData);
		try {
			BufferedImage originalImage = ImageIO.read(inputStream);
			imageInfo.setWeight(Integer.valueOf(originalImage.getWidth()));
			imageInfo.setHeight(Integer.valueOf(originalImage.getHeight()));
			imageInfo.setSize(Integer.valueOf(fileData.length));
		} finally {
			if (inputStream != null) inputStream.close();
		}
		
		return imageInfo;
	}
}
