package cn.blmdz.controller;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.blmdz.aide.file.FileServer;
import cn.blmdz.aide.file.ImageServer;
import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.model.Response;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.enums.FileType;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.image.FileHelper;
import cn.blmdz.image.UploadDto;
import cn.blmdz.model.UserFile;
import cn.blmdz.model.UserFolder;
import cn.blmdz.util.FileUtil;
//import io.terminus.common.exception.JsonResponseException;
//import io.terminus.common.model.BaseUser;
//import io.terminus.common.model.Response;
//import io.terminus.common.utils.JsonMapper;
//import io.terminus.lib.file.FileServer;
//import io.terminus.lib.file.ImageServer;
//import io.terminus.lib.file.util.FUtil;
//import io.terminus.pampas.common.UserUtil;
//import io.terminus.pampas.engine.MessageSources;
//import io.terminus.parana.file.dto.FileRealPath;
//import io.terminus.parana.file.enums.FileType;
//import io.terminus.parana.file.model.UserFile;
//import io.terminus.parana.file.model.UserFolder;
//import io.terminus.parana.file.service.UserFileService;
//import io.terminus.parana.file.service.UserFolderService;
//import io.terminus.parana.file.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user/files")
@Slf4j
public class UserFiles {

	@Autowired(required = false)
	private UserFileService userFileService;
	@Autowired(required = false)
	private UserFolderService userFolderService;
	@Autowired
	private FileHelper fileHelper;
	
	@Autowired(required = false)
	private MessageSources messageSources;
	@Autowired
	private ImageServer imageServer;
	@Autowired
	private FileServer fileServer;
	@Value("${image.max.size:2097152}")
	private Long imageMaxSize; // 默认2M
	@Value("${image.base.url}")
	private String imageBaseUrl;

	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UploadDto> upload(MultipartHttpServletRequest request) {
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}
		
		Long albumId = Strings.isNullOrEmpty(request.getParameter("albumId")) ? 0 : Long.parseLong(request.getParameter("albumId"));
		String group = request.getParameter("group");

		String realPath = "";
		if (albumId != 0) {
			Response<FileRealPath> pathRes = userFolderService.folderPath(folderId);
			realPath += pathRes.getResult().getRealPath();
		}

		Iterator<String> fileNameItr = request.getFileNames();
		List<UploadDto> result = Lists.newArrayList();
		while (fileNameItr.hasNext()) {
			String name = fileNameItr.next();
			MultipartFile file = request.getFile(name);
			
			if (!FileHelper.ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
				throw new JsonResponseException("user.image.type.not.valid");
			}
			try {
				if (ImageIO.read(file.getInputStream()) == null) {
					throw new JsonResponseException("user.image.type.not.valid");
				}
			} catch (IOException e) {
				throw new JsonResponseException("user.image.type.not.valid");
			}

			String fileRealName = file.getOriginalFilename();
			Long userId = user.getId();

			if (Objects.equals(FileUtil.fileType(fileRealName), FileType.IMAGE)) {
				// 图片处理
				result.add(fileHelper.upImage(userId, realPath, group, fileRealName, albumId, file));
			}
		}

		return result;
	}

	/**
	 * 文件修改(改名)
	 * 
	 * @param userFile
	 *            文件
	 * @return Boolean 更改是否成功
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean updateFile(@RequestBody UserFile userFile) {
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}

		try {
			checkArgument(notNull(userFile.getId()), "file.id.null");
			checkAuthorize(user.getId(), userFile.getId());

			Response<Boolean> result = userFileService.updateFile(userFile);
			if (!result.isSuccess()) {
				log.error("Update file failed, folder={}", userFile);
				throw new JsonResponseException(result.getError());
			}

			return result.getResult();
		} catch (IllegalArgumentException e) {
			log.error("Check argument failed, userFile={}", userFile);
			throw new JsonResponseException(e.getMessage());
		}
	}

	/**
	 * 文件移动
	 * 
	 * @param id
	 *            文件编号
	 * @param folderId
	 *            移动到的文件夹编号
	 * @return Boolean 返回文件移动结果
	 */
	@RequestMapping(value = "/{id}/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean moveFile(@PathVariable Long id, @RequestParam Long folderId) {
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}

		checkAuthorize(user.getId(), id);

		Response<Optional<UserFolder>> folderRes = userFolderService.findById(folderId);
		if (!folderRes.isSuccess() || !folderRes.getResult().isPresent()) {
			log.error("Don't exist folder with id={}, error code={}", folderId, folderRes.getError());
			throw new JsonResponseException(folderRes.getError());
		}

		if (Objects.equals(folderRes.getResult().get().getCreateBy(), user.getId())) {
			log.error("Can't delete folder={}, by userId={}", folderId, user.getId());
			throw new JsonResponseException("authorize.fail");
		}

		Response<Boolean> result = userFileService.moveFile(id, folderId);
		if (!result.isSuccess()) {
			log.error("Failed move file, fileId={}, folderId={}", id, folderId);
			throw new JsonResponseException(result.getError());
		}

		return result.getResult();
	}

	/**
	 * 删除文件
	 * 
	 * @param id
	 *            文件id
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@PathVariable Long id) {
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}

		Response<UserFile> fileR = userFileService.deleteFile(id);
		if (!fileR.isSuccess()) {
			log.warn("failed to find userImage by imageId {} when delete", id);
			return;
		}

		UserFile userFile = fileR.getResult();
		try {
			imageServer.delete(userFile.getPath());
		} catch (Exception e) {
			log.warn("Failed delete file:{}, error:{}", userFile, e);
		}
	}

	/**
	 * 用户是否可更改文件
	 * 
	 * @param userId
	 *            用户编号
	 * @param folderId
	 *            文件编号
	 */
	private void checkAuthorize(Long userId, Long folderId) {
		Response<Optional<UserFile>> fileRes = userFileService.findById(folderId);
		if (!fileRes.isSuccess() || !fileRes.getResult().isPresent()) {
			log.error("Don't exist folder with id={}, error code={}", folderId, fileRes.getError());
			throw new JsonResponseException(fileRes.getError());
		}

		if (Objects.equals(fileRes.getResult().get().getCreateBy(), userId)) {
			log.error("Can't delete folder={}, by userId={}", folderId, userId);
			throw new JsonResponseException("authorize.fail");
		}
	}
}
