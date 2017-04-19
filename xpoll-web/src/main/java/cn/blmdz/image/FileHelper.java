package cn.blmdz.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

import cn.blmdz.aide.file.ImageServer;
import cn.blmdz.aide.file.util.FUtil;
import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.entity.QxxImage;
import cn.blmdz.enums.FileType;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileHelper {

	private final static Set<String> ALLOWED_TYPES = ImmutableSet.of("jpeg", "jpg", "png", "gif");

	public final static Set<String> ALLOWED_CONTENT_TYPES = ImmutableSet.of("image/bmp", "image/png", "image/gif",
			"image/jpg", "image/jpeg", "image/pjpeg");
	@Autowired(required = false)
	private MessageSources messageSources;
	@Autowired
	private ImageServer imageServer;
	@Value("${image.max.size:2097152}")
	private Long imageMaxSize; // 默认2M
	@Value("${image.base.url}")
	private String imageBaseUrl;

	/**
	 * 上传图片文件
	 * 
	 * @param userId
	 *            用户编号
	 * @param realPath
	 *            相对路径
	 * @param group
	 *            用户族
	 * @param fileRealName
	 *            文件名称
	 * @param folderId
	 *            文件夹编号
	 * @param file
	 *            文件
	 * @return UploadDto
	 */
	public UploadDto upImage(Long userId, String realPath, String group, String fileRealName, Long folderId,
			MultipartFile file) {
		QxxImage image = new QxxImage();
		image.setGroup(group);
		image.setFileType(FileType.IMAGE.value());
		image.setCreateBy(userId);
		image.setName(fileRealName);
		image.setFolderId(folderId);

		String ext = Files.getFileExtension(fileRealName).toLowerCase();
		if (ALLOWED_TYPES.contains(ext)) {
			try {
				byte[] imageData = file.getBytes();
				// if size of the image is more than imgSizeMax,it will raise an
				// 500 error
				if (imageData.length > imageMaxSize) {
					log.debug("image size {} ,maxsize {} ,the upload image is to large", imageData.length,
							imageMaxSize);
					return new UploadDto(image,
							messageSources.get("image.size.exceed", imageMaxSize / (1024 * 1024), "mb"));
				}

				image.setSize((int) file.getSize());
				image.setExtra(imageSize(imageData));

				// 文件重命名(防止图片被复写掉)
				String filePath = imageServer.write(realPath + "/" + FileUtil.newFileName(fileRealName), file);
				image.setPath(filePath);

				// 若成功返回路径则代表上传成功
				boolean isSucceed = !Strings.isNullOrEmpty(filePath);
				if (!isSucceed) {
					log.error("write file(name={}) of user(id={}) to image server failed", fileRealName, userId);
					return new UploadDto(image, messageSources.get("user.image.upload.fail"));
				}

				// Response<Long> createRes = null; //
				// userFileService.createFile(image); TODO 创建记录
				// if(!createRes.isSuccess()){
				// log.error("Create image failed, upFile={}, error code={}",
				// image, createRes.getError());
				// throw new JsonResponseException(createRes.getError());
				// }

				// image.setId(createRes.getResult());
				image.setPath(FUtil.absolutePath(imageBaseUrl, filePath));
				return new UploadDto(image);
			} catch (Exception e) {
				log.error("failed to process upload image {},cause:{}", fileRealName,
						Throwables.getStackTraceAsString(e));
				return new UploadDto(image, messageSources.get("user.image.upload.fail"));
			}
		} else {
			return new UploadDto(image, messageSources.get("user.image.illegal.ext"));
		}
	}

	/**
	 * 获取图片的尺寸
	 * 
	 * @param imageData
	 *            图片数据
	 * @return 返回尺寸
	 */
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
