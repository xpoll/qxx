package cn.blmdz.aide.file.aliyun;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.google.common.base.Throwables;

import cn.blmdz.aide.file.FileServer;
import cn.blmdz.aide.file.exception.FileException;
import cn.blmdz.aide.file.util.FUtil;

public class AliyunFileServer implements FileServer {
	private static final Logger log = LoggerFactory.getLogger(AliyunFileServer.class);
	private final String bucketName;
	private final OSSClient ossClient;

	public AliyunFileServer(String endpoint, String appKey, String appSecret, String bucketName) {
		this.bucketName = bucketName;
		this.ossClient = new OSSClient(endpoint, appKey, appSecret);
	}

	@Override
	public String write(String path, MultipartFile file) throws FileException {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getBytes().length);
			InputStream stream = file.getInputStream();

			try {
				this.ossClient.putObject(this.bucketName, FUtil.upPath(path), stream, metadata);
			} finally {
				if (stream != null) stream.close();
			}
			return path;
		} catch (Exception e) {
			log.error("failed to upload file(path={}) to oss, cause:{}", path, Throwables.getStackTraceAsString(e));
			throw new FileException(e);
		}
	}

	@Override
	public String write(String path, File file) throws FileException {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(Files.probeContentType(file.toPath()));
			metadata.setContentLength(file.length());
			InputStream stream = Files.newInputStream(file.toPath(), new OpenOption[0]);

			try {
				this.ossClient.putObject(this.bucketName, FUtil.upPath(path), stream, metadata);
			} finally {
				if (stream != null) stream.close();
			}

			return path;
		} catch (Exception e) {
			log.error("failed to upload file(path={}) to oss, cause:{}", path, Throwables.getStackTraceAsString(e));
			throw new FileException(e);
		}
	}

	@Override
	public String write(String path, InputStream inputStream) throws FileException {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(FUtil.contentType(path));
			metadata.setContentLength(FUtil.file(inputStream).getSize().intValue());
			this.ossClient.putObject(this.bucketName, FUtil.upPath(path), inputStream, metadata);
		} catch (Exception e) {
			log.error("failed to upload file(path={}) to oss, cause:{}", path, Throwables.getStackTraceAsString(e));
			throw new FileException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("Close file inputStream failed, path={}, error code={}", path, Throwables.getStackTraceAsString(e));
				}
			}
		}

		return path;
	}

	@Override
	public boolean delete(String path) throws FileException {
		try {
			this.ossClient.deleteObject(this.bucketName, FUtil.upPath(path));
			return true;
		} catch (Exception e) {
			log.error("failed to delete {} from oss, cause:{}", path, Throwables.getStackTraceAsString(e));
			throw new FileException(e);
		}
	}
}
