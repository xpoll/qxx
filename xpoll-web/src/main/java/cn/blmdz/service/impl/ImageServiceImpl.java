package cn.blmdz.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

import cn.blmdz.aide.file.ImageServer;
import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.dao.ImageDao;
import cn.blmdz.entity.QxxImage;
import cn.blmdz.model.UploadDto;
import cn.blmdz.service.ImageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private ImageServer imageServer;
	@Value("${image.max.size:2097152}")
	private Long imageMaxSize; // 默认2M
	@Value("${image.base.url:http://xpoll.oss-cn-shanghai.aliyuncs.com}")
	private String imageBaseUrl;

	private final static Set<String> ALLOWED_TYPES = ImmutableSet.of("jpeg", "jpg", "png", "gif");
	private final static Set<String> ALLOWED_CONTENT_TYPES = ImmutableSet.of("image/bmp", "image/png", "image/gif",
			"image/jpg", "image/jpeg", "image/pjpeg");

	@Override
	public void create(QxxImage image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Long id, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<QxxImage> page(Long id, Integer size, Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Long id, Long aid) {
		// TODO Auto-generated method stub

	}

	@Override
	public UploadDto upload(Long id, Long aid, String name, MultipartFile file) {
		QxxImage image = new QxxImage();
		image.setAid(aid);
		image.setCdate(new Date());
		image.setUdate(new Date());
		image.setName(name);
		
		if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
			return new UploadDto(image, "user.image.type.not.valid");
		}
		try {
			if (ImageIO.read(file.getInputStream()) == null) {
				return new UploadDto(image, "user.image.type.not.valid");
			}
		} catch (IOException e) {
			return new UploadDto(image, "user.image.type.not.valid");
		}


		image.setPath("qxx/" + id + "/" + DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmssSSS"))
				+ (int) ((Math.random() * 9.0D + 1.0D) * 10000.0D) + "."
				+ name.substring(name.lastIndexOf(".")).replace(".", ""));

		String ext = Files.getFileExtension(name).toLowerCase();
		if (ALLOWED_TYPES.contains(ext)) {
			try {
				byte[] imageData = file.getBytes();
				if (imageData.length > imageMaxSize) {
					log.debug("image size {} ,maxsize {} ,the upload image is to large", imageData.length, imageMaxSize);
					return new UploadDto(image, "image.size.exceed");
				}

				image.setSize((int) file.getSize());
				image.setExtra(imageSize(imageData));

				String filePath = imageServer.write(image.getPath(), file);
				image.setPath(imageBaseUrl + "/" + filePath);
				// 若成功返回路径则代表上传成功
				boolean isSucceed = !Strings.isNullOrEmpty(filePath);
				if (!isSucceed) {
					log.error("write file(name={}) of user(id={}) to image server failed", name, id);
					return new UploadDto(image, "user.image.upload.fail");
				}
				imageDao.create(image);
				return new UploadDto(image);
			} catch (Exception e) {
				log.error("failed to process upload image {},cause:{}", name, Throwables.getStackTraceAsString(e));
				return new UploadDto(image, "user.image.upload.fail");
			}
		} else {
			return new UploadDto(image, "user.image.illegal.ext");
		}

	}

	private String imageSize(byte[] imageData) {
		try {
			BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

			Integer width = originalImage.getWidth();
			Integer height = originalImage.getHeight();

			return JsonMapper.nonEmptyMapper().toJson(ImmutableMap.of("width", width, "height", height));
		} catch (IOException e) {
			log.error("Read image size failed, Error code={}", Throwables.getStackTraceAsString(e));
			return "";
		}
	}
}
