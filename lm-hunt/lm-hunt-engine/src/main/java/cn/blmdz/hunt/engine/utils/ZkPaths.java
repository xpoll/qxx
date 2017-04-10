package cn.blmdz.hunt.engine.utils;

public class ZkPaths {
   public static String rootPath() {
      return "/pampas/groups";
   }

   public static String groupPath(String group) {
      return rootPath() + "/" + group;
   }

   public static String cellPath(String group, String cell) {
      return groupPath(group) + "/" + cell;
   }
}
