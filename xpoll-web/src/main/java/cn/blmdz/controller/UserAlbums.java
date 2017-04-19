package cn.blmdz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.model.Response;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.entity.QxxAlbum;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.service.AlbumService;

/**
 * 相册服务接口
 * 
 * @author lm
 */
@RestController
@RequestMapping("/api/user/album")
public class UserAlbums {

	@Autowired
	private MessageSources messageSources;

	@Autowired
	private AlbumService albumService;

	/**
	 * 获取
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<QxxAlbum>> files() {

		// TODO
		BaseUser user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new JsonResponseException(401, messageSources.get("user.not.login"));
		}

		return Response.ok(albumService.findAll());
	}

	/**
	 * 创建
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> create(@RequestBody QxxAlbum album) {

		// TODO
		albumService.create(album);

		return Response.ok(true);
	}

	/**
	 * 更改
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> update(@RequestBody QxxAlbum album) {

		// TODO
		albumService.update(album);

		return Response.ok(true);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Boolean> delete(@PathVariable Long id) {

		// TODO
		albumService.delete(id);

		return Response.ok(true);
	}
}
