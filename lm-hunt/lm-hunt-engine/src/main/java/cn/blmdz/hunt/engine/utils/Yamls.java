package cn.blmdz.hunt.engine.utils;

import org.yaml.snakeyaml.Yaml;

public class Yamls {
   private static final Yaml INSTANCE = new Yaml();

   public static Yaml getInstance() {
      return INSTANCE;
   }
}
