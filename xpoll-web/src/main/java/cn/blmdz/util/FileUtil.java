//package cn.blmdz.util;
//
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//
//import cn.blmdz.enums.FileType;
//
//public class FileUtil {
//   private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
//
//   public static FileType fileType(String fileName) {
//      int index = fileName.lastIndexOf(".");
//      String fileType = fileName.substring(index).replace(".", "").toLowerCase();
//      byte var4 = -1;
//      switch(fileType.hashCode()) {
//      case 3643:
//         if(fileType.equals("rm")) {
//            var4 = 4;
//         }
//         break;
//      case 99640:
//         if(fileType.equals("doc")) {
//            var4 = 7;
//         }
//         break;
//      case 104461:
//         if(fileType.equals("ios")) {
//            var4 = 2;
//         }
//         break;
//      case 105441:
//         if(fileType.equals("jpg")) {
//            var4 = 11;
//         }
//         break;
//      case 111145:
//         if(fileType.equals("png")) {
//            var4 = 10;
//         }
//         break;
//      case 112675:
//         if(fileType.equals("rar")) {
//            var4 = 0;
//         }
//         break;
//      case 115312:
//         if(fileType.equals("txt")) {
//            var4 = 9;
//         }
//         break;
//      case 118783:
//         if(fileType.equals("xls")) {
//            var4 = 5;
//         }
//         break;
//      case 120609:
//         if(fileType.equals("zip")) {
//            var4 = 1;
//         }
//         break;
//      case 3088960:
//         if(fileType.equals("docx")) {
//            var4 = 8;
//         }
//         break;
//      case 3213227:
//         if(fileType.equals("html")) {
//            var4 = 3;
//         }
//         break;
//      case 3268712:
//         if(fileType.equals("jpeg")) {
//            var4 = 12;
//         }
//         break;
//      case 3682393:
//         if(fileType.equals("xlsx")) {
//            var4 = 6;
//         }
//      }
//
//      switch(var4) {
//      case 0:
//         return FileType.RAR;
//      case 1:
//         return FileType.ZIP;
//      case 2:
//         return FileType.IOS;
//      case 3:
//         return FileType.HTML;
//      case 4:
//         return FileType.RM;
//      case 5:
//         return FileType.XLS;
//      case 6:
//         return FileType.XLS;
//      case 7:
//         return FileType.DOC;
//      case 8:
//         return FileType.DOC;
//      case 9:
//         return FileType.TXT;
//      case 10:
//         return FileType.IMAGE;
//      case 11:
//         return FileType.IMAGE;
//      case 12:
//         return FileType.IMAGE;
//      default:
//         return FileType.OTHER;
//      }
//   }
//
//   public static String newFileName(String fileName) {
//      int index = fileName.lastIndexOf(".");
//      String fileType = fileName.substring(index).replace(".", "");
//      
//      return DateTime.now().toString(DATE_FORMAT) + (int)((Math.random() * 9.0D + 1.0D) * 10000.0D) + "." + fileType;
//   }
//}
