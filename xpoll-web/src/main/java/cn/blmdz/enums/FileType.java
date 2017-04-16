package cn.blmdz.enums;

import cn.blmdz.hunt.common.util.Arguments;

public enum FileType {
   RAR(1, "压缩包"),
   ZIP(2, "zip压缩包"),
   PDF(3, "pdf文档"),
   IOS(4, "镜像文件"),
   HTML(5, "网页"),
   RM(6, "视频文件"),
   XLS(7, "excel文件"),
   DOC(8, "word文件"),
   TXT(9, "记事本"),
   IMAGE(10, "图片"),
   OTHER(11, "其它");

   private final int value;
   private final String description;

   private FileType(int value, String description) {
      this.value = value;
      this.description = description;
   }

   public static FileType from(Integer value) {
      for(FileType t : values()) {
         if(Arguments.equalWith(Integer.valueOf(t.value), value)) {
            return t;
         }
      }
      return null;
   }

   public int value() {
      return this.value;
   }

   public String toString() {
      return this.description;
   }
}