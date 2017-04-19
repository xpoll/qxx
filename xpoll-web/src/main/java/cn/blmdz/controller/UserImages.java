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
import cn.blmdz.entity.QxxImage;
import cn.blmdz.entity.QxxAlbum;
import cn.blmdz.enums.FileType;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.image.FileHelper;
import cn.blmdz.image.UploadDto;
import cn.blmdz.service.ImageService;
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

@Slf4j
@RestController
@RequestMapping("/api/user/files")
public class UserImages {

	@Autowired
	private FileHelper fileHelper;
	
	@Autowired(required = false)
	private MessageSources messageSources;
	@Autowired
	private ImageServer imageServer;
	@Autowired
	private ImageService imageService;
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

		if (albumId != 0) {
			// 文件夹是否存在 TODO
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
				result.add(fileHelper.upImage(userId, String.valueOf(albumId), group, fileRealName, albumId, file));
			}
		}

		return result;
	}


	/**
	 * 文件移动
	 */
	@RequestMapping(value = "/{id}/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response<Boolean> move(@PathVariable Long id, @RequestParam Long aid) {
		//移动

		// TODO
		imageService.move(id, aid);

		return Response.ok(true);
	}

	/**
	 * 分页获取
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<QxxImage>> files(/*mode*/ Long aid) {

		// TODO
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}

		return Response.ok(imageService.page(aid, 8, 1));
	}

	/**
	 * 创建
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> create(@RequestBody QxxImage image) {

		// TODO
		imageService.create(image);

		return Response.ok(true);
	}

	/**
	 * 更改
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> update(@RequestBody QxxImage image) {

		// TODO
		imageService.update(image.getId(), image.getName());

		return Response.ok(true);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> delete(@PathVariable Long id) {

		// TODO
		imageService.delete(id);

		return Response.ok(true);
	}

}
