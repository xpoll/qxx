//package cn.blmdz.image;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.Objects;
//import java.util.Set;
//
//import javax.imageio.ImageIO;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.google.common.base.Strings;
//import com.google.common.base.Throwables;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.ImmutableSet;
//import com.google.common.io.Files;
//
//import cn.blmdz.aide.file.FileServer;
//import cn.blmdz.aide.file.ImageServer;
//import cn.blmdz.aide.file.util.FUtil;
//import cn.blmdz.enums.FileType;
//import cn.blmdz.hunt.common.exception.JsonResponseException;
//import cn.blmdz.hunt.common.model.Response;
//import cn.blmdz.hunt.common.util.JsonMapper;
//import cn.blmdz.hunt.engine.MessageSources;
//import cn.blmdz.model.UserFile;
//import cn.blmdz.util.FileUtil;
////import io.terminus.common.exception.JsonResponseException;
////import io.terminus.common.model.Response;
////import io.terminus.common.utils.JsonMapper;
////import io.terminus.lib.file.FileServer;
////import io.terminus.lib.file.ImageServer;
////import io.terminus.lib.file.util.FUtil;
////import io.terminus.pampas.engine.MessageSources;
////import io.terminus.parana.file.enums.FileType;
////import io.terminus.parana.file.model.UserFile;
////import io.terminus.parana.file.service.UserFileService;
////import io.terminus.parana.file.util.FileUtil;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Mail: xiao@terminus.io <br>
// * Date: 2016-04-06 2:47 PM  <br>
// * Author: xiao
// */
//@Slf4j
//@Component
//public class FileHelper {
//
//    private final static Set<String> ALLOWED_TYPES = ImmutableSet.of("jpeg", "jpg", "png", "gif");
//
//    public final static Set<String> ALLOWED_CONTENT_TYPES =
//            ImmutableSet.of("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");
//
//    @Autowired(required = false)
//    private UserFileService userFileService;
//    @Autowired(required = false)
//    private MessageSources messageSources;
//    @Autowired
//    private ImageServer imageServer;
//    @Autowired
//    private FileServer fileServer;
//    @Value("${image.max.size:2097152}")
//    private Long imageMaxSize;         //默认2M
//    @Value("${image.base.url}")
//    private String imageBaseUrl;
//
//
//    /**
//     * 上传文件
//     * @param userId    用户id
//     * @param file      文件
//     * @return          上传文件信息
//     */
//    public UploadDto upload(Long userId, MultipartFile file) {
//
//        if(Objects.equals(FileUtil.fileType(file.getOriginalFilename()) , FileType.IMAGE)){
//            //图片处理
//            return upImage(userId, "", null, file.getOriginalFilename(), 0L, file);
//        }
//
//        //文件上传
//        return upFile(userId, "", null, file.getOriginalFilename(), 0L, file);
//    }
//
//
//
//
//    /**
//     * 上传图片文件
//     * @param userId        用户编号
//     * @param realPath      相对路径
//     * @param group         用户族
//     * @param fileRealName  文件名称
//     * @param folderId      文件夹编号
//     * @param file          文件
//     * @return  UploadDto
//     */
//    public UploadDto upImage(Long userId, String realPath, String group, String fileRealName, Long folderId, MultipartFile file){
//        UserFile image = new UserFile();
//        image.setGroup(group);
//        image.setFileType(FileType.IMAGE.value());
//        image.setCreateBy(userId);
//        image.setName(fileRealName);
//        image.setFolderId(folderId);
//
//        String ext = Files.getFileExtension(fileRealName).toLowerCase();
//        if (ALLOWED_TYPES.contains(ext)) {
//            try {
//                byte[] imageData = file.getBytes();
//                //if size of the image is more than imgSizeMax,it will raise an 500 error
//                if (imageData.length > imageMaxSize) {
//                    log.debug("image size {} ,maxsize {} ,the upload image is to large", imageData.length, imageMaxSize);
//                    return new UploadDto(image, messageSources.get("image.size.exceed", imageMaxSize / (1024 * 1024), "mb"));
//                }
//
//                image.setSize((int)file.getSize());
//                image.setExtra(imageSize(imageData));
//
//                //文件重命名(防止图片被复写掉)
//                String filePath = imageServer.write(realPath+"/"+ FileUtil.newFileName(fileRealName), file);
//                image.setPath(filePath);
//
//                //若成功返回路径则代表上传成功
//                boolean isSucceed = !Strings.isNullOrEmpty(filePath);
//                if (!isSucceed) {
//                    log.error("write file(name={}) of user(id={}) to image server failed", fileRealName, userId);
//                    return new UploadDto(image, messageSources.get("user.image.upload.fail"));
//                }
//
//                Response<Long> createRes = userFileService.createFile(image);
//                if(!createRes.isSuccess()){
//                    log.error("Create image failed, upFile={}, error code={}", image, createRes.getError());
//                    throw new JsonResponseException(createRes.getError());
//                }
//
//                image.setId(createRes.getResult());
//                image.setPath(FUtil.absolutePath(imageBaseUrl, filePath));
//                return new UploadDto(image);
//            } catch (Exception e) {
//                log.error("failed to process upload image {},cause:{}", fileRealName, Throwables.getStackTraceAsString(e));
//                return new UploadDto(image, messageSources.get("user.image.upload.fail"));
//            }
//        } else {
//            return new UploadDto(image, messageSources.get("user.image.illegal.ext"));
//        }
//    }
//
//    /**
//     * 上传文件
//     * @param userId        用户编号
//     * @param realPath      相对路径
//     * @param group         用户族
//     * @param fileRealName  文件名称
//     * @param folderId      文件夹编号
//     * @param file          文件
//     * @return  UploadDto
//     */
//    public UploadDto upFile(Long userId, String realPath, String group, String fileRealName, Long folderId, MultipartFile file){
//        UserFile upFile = new UserFile();
//        upFile.setGroup(group);
//        upFile.setFileType(FileUtil.fileType(fileRealName).value());
//        upFile.setCreateBy(userId);
//        upFile.setName(fileRealName);
//        upFile.setFolderId(folderId);
//
//        try {
//            byte[] fileData = file.getBytes();
//            if (fileData.length > imageMaxSize) {
//                log.debug("image size {} ,maxsize {} ,the upload file is to large", fileData.length, imageMaxSize);
//                return new UploadDto(upFile, messageSources.get("file.size.exceed", imageMaxSize / (1024 * 1024), "mb"));
//            }
//
//            upFile.setSize((int)file.getSize());
//
//            //文件重命名(防止文件被复写掉)
//            String filePath = fileServer.write(realPath+"/"+ FileUtil.newFileName(fileRealName), file);
//            upFile.setPath(filePath);
//
//            //若成功返回路径则代表上传成功
//            boolean isSucceed = !Strings.isNullOrEmpty(filePath);
//            if (!isSucceed) {
//                log.error("write file(name={}) of user(id={}) to file server failed", fileRealName, userId);
//                return new UploadDto(upFile, messageSources.get("user.file.upload.fail"));
//            }
//
//            Response<Long> createRes = userFileService.createFile(upFile);
//            if(!createRes.isSuccess()){
//                log.error("Create file failed, upFile={}, error code={}", upFile, createRes.getError());
//                throw new JsonResponseException(createRes.getError());
//            }
//
//            upFile.setId(createRes.getResult());
//            upFile.setPath(FUtil.absolutePath(imageBaseUrl, filePath));
//            return new UploadDto(upFile);
//        } catch (Exception e) {
//            log.error("failed to process upload file {},cause:{}", fileRealName, Throwables.getStackTraceAsString(e));
//            return new UploadDto(upFile, messageSources.get("user.file.upload.fail"));
//        }
//    }
//
//    /**
//     * 获取图片的尺寸
//     * @param imageData 图片数据
//     * @return  返回尺寸
//     */
//    private String imageSize(byte[] imageData){
//        try {
//            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));
//
//            Integer width = originalImage.getWidth();
//            Integer height = originalImage.getHeight();
//
//            return JsonMapper.nonEmptyMapper().toJson(ImmutableMap.of("width", width, "height", height));
//        }catch(IOException e){
//            log.error("Read image size failed, Error code={}", Throwables.getStackTraceAsString(e));
//            return "";
//        }
//    }
//
//
//
//}
