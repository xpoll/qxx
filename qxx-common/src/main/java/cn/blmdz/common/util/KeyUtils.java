package cn.blmdz.common.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public abstract class KeyUtils {

    public static <T> String entityCount(Class<T> entityClass) {
        return "count:" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName());
    }

    public static <T> String entityId(Class<T> entityClass, long id) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":table:" + id;
    }

    public static <T> String entityId(Class<T> entityClass, String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "id can not be null");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":table:" + id;
    }
    
    public static <T> String entityIndex(Class<T> entityClass, String index) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(index), "index can not be null");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":index:" + index;
    }

}
