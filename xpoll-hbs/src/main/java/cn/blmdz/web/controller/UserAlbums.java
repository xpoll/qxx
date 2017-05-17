package cn.blmdz.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.common.model.Response;
import cn.blmdz.web.entity.QxxAlbum;
import cn.blmdz.web.model.UploadDto2;
import cn.blmdz.web.model.UserFile;
import cn.blmdz.web.model.UserFolder;
import cn.blmdz.web.service.AlbumService;

/**
 * 相册服务接口
 * 
 * @author lm
 */
@RestController
@RequestMapping("/api/user/folder")
public class UserAlbums {

//	@Autowired
//	private MessageSources messageSources;

	@Autowired
	private AlbumService albumService;

//	/**
//	 * 获取
//	 */
//	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public Response<List<QxxAlbum>> files() {
//
//		// TODO
////		BaseUser user = UserUtil.getCurrentUser();
////		if (user == null) {
////			throw new JsonResponseException(401, messageSources.get("user.not.login"));
////		}
//
//		return Response.ok(albumService.findAll());
//	}
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> files(@RequestParam(defaultValue = "0") Long folderId,
                                    @RequestParam(required = false) Integer fileType,
                                    @RequestParam(required = false) String fileName,
                                    @RequestParam(required = false) String group,
                                    @RequestParam(value = "p", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "size", defaultValue = "10")Integer pageSize){
//      BaseUser user = UserUtil.getCurrentUser();
//      if (user == null) {
//          throw new JsonResponseException(401, messageSources.get("user.not.login"));
//      }

//      Response<FilePagingDto> resp = userFolderService.pagingFiles(user.getId(), fileType, group, fileName, folderId, pageNo, pageSize);
//      if (!resp.isSuccess()){
//          throw new JsonResponseException("获取失败");
//      }
//      Paging<UserFileDto> tempUserImages = resp.getResult().getFileDtoPaging();
//      Paging<UploadDto> userFiles = new Paging<UploadDto>(tempUserImages.getTotal(), Lists.transform(tempUserImages.getData(), new Function<UserFileDto, UploadDto>() {
//          @Override
//          public UploadDto apply(UserFileDto userFileDto) {
//              UploadDto uploadDto = new UploadDto();
//
//              if(userFileDto.getFolder() != null){
//                  uploadDto.setUserFolder(userFileDto.getFolder());
//              }
//
//              if(userFileDto.getUserFile() != null){
//                  userFileDto.getUserFile().setPath(baseUrl + userFileDto.getUserFile().getPath());
//                  uploadDto.setUserFile(userFileDto.getUserFile());
//              }
//
//              return uploadDto;
//          }
//      }));
      UserFolder album = new UserFolder();
      album.setId(1L);
      album.setPid(0L);
      album.setFolder("we");
      album.setHasChildren(true);
      album.setLevel(1);
      UserFile image = new UserFile();
      image.setFileType(10);
      image.setFolderId(0L);
      image.setId(1L);
      image.setSize(1944);
      image.setExtra("{\"width\":64,\"height\":64}");
      image.setName("sdf.png");
      image.setPath("http://xpoll.oss-cn-shanghai.aliyuncs.com/qxx/1/2017042021102232372836.png");
      List<UploadDto2> list = Lists.newArrayList();
      UploadDto2 d = new UploadDto2();
      d.setUserFile(image);
      d.setUserFolder(album);
      list.add(d);
      list.add(new UploadDto2(album));
      
      Map<String, Object> cs = Maps.newHashMap();
      cs.put("data", list);
      cs.put("total", 20);
      
      // 为前段组件包一层
      Map<String, Object> result = Maps.newHashMap();
      result.put("data", cs);
      result.put("realPath" , "");
      return result;
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
