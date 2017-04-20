package cn.blmdz.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.model.Response;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.entity.QxxImage;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.model.UploadDto;
import cn.blmdz.service.ImageService;

@RestController
@RequestMapping("/api/user/files")
public class UserImages {

	@Autowired(required = false)
	private MessageSources messageSources;
	@Autowired
	private ImageService imageService;

	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UploadDto> upload(MultipartHttpServletRequest request) {
//		BaseUser user = UserUtil.getCurrentUser();
//		if (user == null) {
//			throw new JsonResponseException(401, messageSources.get("user.not.login"));
//		}
		Long aid = Strings.isNullOrEmpty(request.getParameter("albumId")) ? 0
				: Long.parseLong(request.getParameter("albumId"));

		if (aid != 0) {
			// 文件夹是否存在 TODO
		}
		Iterator<String> fileNameItr = request.getFileNames();
		List<UploadDto> result = Lists.newArrayList();
		while (fileNameItr.hasNext()) {
			MultipartFile file = request.getFile(fileNameItr.next());
			System.out.println(file.getOriginalFilename());

			// 文件夹 用户 名字 文件
			result.add(imageService.upload(1L, aid, file.getOriginalFilename(), file));
		}

		return result;
	}

	/**
	 * 文件移动
	 */
	@RequestMapping(value = "/{id}/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response<Boolean> move(@PathVariable Long id, @RequestParam Long aid) {
		// 移动

		// TODO
		imageService.move(id, aid);

		return Response.ok(true);
	}

	/**
	 * 分页获取
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<QxxImage>> files(/* mode */ Long aid) {

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
