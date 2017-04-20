package cn.blmdz.model;

import java.io.Serializable;

import cn.blmdz.entity.QxxImage;
import cn.blmdz.entity.QxxAlbum;
import lombok.Data;

@Data
public class UploadDto2 implements Serializable {

    private static final long serialVersionUID = -38331060124340967L;

    /**
     * 类型(文件夹|文件)
     */
    private Integer type;

    /**
     * 文件信息
     */
    private UserFile image;

    /**
     * 文件夹信息
     */
    private UserFolder album;

    private String error;

    public UploadDto2(){}

    public UploadDto2(UserFile image){
        this.type = Type.FILE.toNumber();
        this.image = image;
    }

    public UploadDto2(UserFolder album){
        this.type = Type.FOLDER.toNumber();
        this.album = album;
    }

    public UploadDto2(UserFile image, String error){
        this.type = Type.FILE.toNumber();
        this.image = image;
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
