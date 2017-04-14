package cn.blmdz.image;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.common.exception.JsonResponseException;
import cn.blmdz.hunt.common.model.BaseUser;
import cn.blmdz.hunt.common.model.Response;
import cn.blmdz.hunt.common.util.UserUtil;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.model.UserFolder;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户文件夹服务接口
 * Mail:v@terminus.io
 * author:Michael Zhao
 * Date:2016-03-03.
 */
@Controller
@RequestMapping("/api/user/folder")
@Slf4j
public class Folders {
    @Autowired(required = false)
    private MessageSources messageSources;

    @Autowired(required = false)
    private UserFolderService userFolderService;

    @Value("${image.base.url}")
    private String baseUrl;

    /**
     * 分页获取用户上传的文件&文件夹
     * @param pageNo 页号
     * @param pageSize 分页大小
     * @return 用户上传的图片
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> files(@RequestParam(defaultValue = "0") Long folderId,
                                      @RequestParam(required = false) Integer fileType,
                                      @RequestParam(required = false) String fileName,
                                      @RequestParam(required = false) String group,
                                      @RequestParam(value = "p", defaultValue = "1") Integer pageNo,
                                      @RequestParam(value = "size", defaultValue = "10")Integer pageSize){
        BaseUser user = UserUtil.getCurrentUser();
        if (user == null) {
            throw new JsonResponseException(401, messageSources.get("user.not.login"));
        }

        Response<FilePagingDto> resp = userFolderService.pagingFiles(user.getId(), fileType, group, fileName, folderId, pageNo, pageSize);
        if (!resp.isSuccess()){
            log.error("Query files failed, userId={} folderId={} fileType={} fileName={}", user.getId(), folderId, fileType, fileName);
            throw new JsonResponseException(messageSources.get(resp.getError()));
        }
        Paging<UserFileDto> tempUserImages = resp.getResult().getFileDtoPaging();
        Paging<UploadDto> userFiles = new Paging<UploadDto>(tempUserImages.getTotal(), Lists.transform(tempUserImages.getData(), new Function<UserFileDto, UploadDto>() {
            @Override
            public UploadDto apply(UserFileDto userFileDto) {
                UploadDto uploadDto = new UploadDto();

                if(userFileDto.getFolder() != null){
                    uploadDto.setUserFolder(userFileDto.getFolder());
                }

                if(userFileDto.getUserFile() != null){
                    userFileDto.getUserFile().setPath(baseUrl + userFileDto.getUserFile().getPath());
                    uploadDto.setUserFile(userFileDto.getUserFile());
                }

                return uploadDto;
            }
        }));
        // 为前段组件包一层
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", userFiles);
        result.put("realPath" , resp.getResult().getFileRealPath());
        return result;
    }

    /**
     * 创建文件夹
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UploadDto createFolder(@RequestBody UserFolder userFolder) {
        BaseUser user = UserUtil.getCurrentUser();
        if (user == null) {
            throw new JsonResponseException(401, messageSources.get("user.not.login"));
        }

        userFolder.setCreateBy(user.getId());

        Response<Long> result = userFolderService.createFolder(userFolder);
        if(!result.isSuccess()){
            log.error("Create folder failed, folder={}", userFolder);
            throw new JsonResponseException(result.getError());
        }

        userFolder.setId(result.getResult());
        UploadDto uploadDto = new UploadDto();
        uploadDto.setUserFolder(userFolder);

        return uploadDto;
    }

    /**
     * 文件夹修改(改名)
     * @param userFolder    文件夹
     * @return  Boolean
     * 更改是否成功
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean updateFolder(@RequestBody UserFolder userFolder) {
        BaseUser user = UserUtil.getCurrentUser();
        if (user == null) {
            throw new JsonResponseException(401, messageSources.get("user.not.login"));
        }

        try {
            checkArgument(notNull(userFolder.getId()), "folder.id.null");
            checkAuthorize(user.getId(), userFolder.getId());

            Response<Boolean> result = userFolderService.updateFolder(userFolder);
            if (!result.isSuccess()) {
                log.error("Update folder failed, folder={}", userFolder);
                throw new JsonResponseException(result.getError());
            }

            return result.getResult();
        }catch (IllegalArgumentException e){
            log.error("Check argument failed, userFolder={}", userFolder);
            throw new JsonResponseException(e.getMessage());
        }
    }

    /**
     * 文件夹移动
     * @param id    文件夹编号
     * @param pid   移动到的文件夹编号
     * @return  Boolean
     * 返回文件夹移动结果
     */
    @RequestMapping(value = "/{id}/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean moveFolder(@PathVariable Long id, @RequestParam Long pid) {
        BaseUser user = UserUtil.getCurrentUser();
        if (user == null) {
            throw new JsonResponseException(401, messageSources.get("user.not.login"));
        }

        checkAuthorize(user.getId(), id);

        checkAuthorize(user.getId(), pid);

        Response<Boolean> result = userFolderService.moveFolder(id, pid);
        if(!result.isSuccess()){
            log.error("Failed move folder, fid={}, pid={}", id, pid);
            throw new JsonResponseException(result.getError());
        }

        return result.getResult();
    }

    /**
     * 删除文件夹
     * @param id 文件夹id
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteFolder(@PathVariable Long id) {
        BaseUser user = UserUtil.getCurrentUser();
        if (user == null) {
            throw new JsonResponseException(401, messageSources.get("user.not.login"));
        }
        checkAuthorize(user.getId(), id);

        Response<UserFolder> deleteRes = userFolderService.deleteFolder(id);
        if (!deleteRes.isSuccess()) {
            log.error("Failed to delete by folderId {} when delete", id);
            throw new JsonResponseException(deleteRes.getError());
        }
    }

    /**
     * 用户是否可更改文件夹
     * @param userId    用户编号
     * @param folderId  文件夹编号
     */
    private void checkAuthorize(Long userId, Long folderId){
        Response<Optional<UserFolder>> folderRes = userFolderService.findById(folderId);
        if(!folderRes.isSuccess() || !folderRes.getResult().isPresent()){
            log.error("Don't exist folder with id={}, error code={}", folderId, folderRes.getError());
            throw new JsonResponseException(folderRes.getError());
        }

        if(!Objects.equals(folderRes.getResult().get().getCreateBy() , userId)){
            log.error("Can't delete folder={}, by userId={}", folderId, userId);
            throw new JsonResponseException("authorize.fail");
        }
    }
}
