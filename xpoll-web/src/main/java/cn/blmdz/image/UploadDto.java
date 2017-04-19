package cn.blmdz.image;

import java.io.Serializable;

import cn.blmdz.entity.QxxImage;
import cn.blmdz.entity.QxxAlbum;
import lombok.Data;

@Data
public class UploadDto implements Serializable {

    private static final long serialVersionUID = -38331060124340967L;

    /**
     * 类型(文件夹|文件)
     */
    private Integer type;

    /**
     * 文件信息
     */
    private QxxImage userFile;

    /**
     * 文件夹信息
     */
    private QxxAlbum userFolder;

    private String error;

    public UploadDto(){}

    public UploadDto(QxxImage userFile){
        this.type = Type.FILE.toNumber();
        this.userFile = userFile;
    }

    public UploadDto(QxxAlbum userFolder){
        this.type = Type.FOLDER.toNumber();
        this.userFolder = userFolder;
    }

    public UploadDto(QxxImage userFile, String error){
        this.type = Type.FILE.toNumber();
        this.userFile = userFile;
        this.error = error;
    }

    public static enum Type {
        FOLDER(1, "文件夹"),
        FILE(2, "文件");

        private final Integer value;

        private final String display;

        private Type(Integer value, String display) {
            this.value = value;
            this.display = display;
        }

        public Integer toNumber() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.display;
        }
    }
}
