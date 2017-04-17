package cn.blmdz.common.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.Getter;

public class InnerCookie implements Serializable {
    private static final long serialVersionUID = -5440120009211709421L;

    @Getter
    private final Set<FakeCookie> newCookies = new HashSet<FakeCookie>();
    @Getter
    private final Set<FakeCookie> delCookies = new HashSet<FakeCookie>();
    private final Map<String, String> cookies;

    public InnerCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String get(String name) {
        return cookies.get(name);
    }

    /**
     * 所有的 set 调用都不会影响本次请求的 cookie 值，将在下次请求时生效
     */
    public void set(String name, String value) {
        set(name, value, -1);
    }

    public void set(String name, String value, String domain) {
        set(name, value, domain, -1);
    }

    public void set(String name, String value, String domain, int age) {
        FakeCookie fakeCookie = new FakeCookie();
        fakeCookie.name = name;
        fakeCookie.value = value;
        fakeCookie.domain = domain;
        fakeCookie.age = age;
        set(fakeCookie);
    }

    /**
     * 所有的 set 调用都不会影响本次请求的 cookie 值，将在下次请求时生效
     */
    public void set(String name, String value, int age) {
        set(name, value, age, "/");
    }

    /**
     * 所有的 set 调用都不会影响本次请求的 cookie 值，将在下次请求时生效
     */
    public void set(String name, String value, int age, String path) {
        FakeCookie fakeCookie = new FakeCookie();
        fakeCookie.name = name;
        fakeCookie.value = value;
        fakeCookie.age = age;
        fakeCookie.path = path;
        set(fakeCookie);
    }

    public void set(FakeCookie fakeCookie) {
        if (fakeCookie.name == null) {
            throw new IllegalArgumentException("cookie name should not be null");
        }
        newCookies.add(fakeCookie);
    }

    public void remove(String name) {
        remove(name, "/");
    }

    public void remove(String name, String path) {
        remove(name, path, "");
    }

    public void removeInDomain(String name, String domain) {
        remove(name, "/", domain);
    }

    /**
     * 所有的 remove 调用都不会影响本次请求的 cookie 值，将在下次请求时生效
     */
    public void remove(String name, String path, String domain) {
        FakeCookie fakeCookie = new FakeCookie();
        fakeCookie.name = name;
        fakeCookie.path = path;
        fakeCookie.domain = domain;
        delCookies.add(fakeCookie);
    }

    public void merge(InnerCookie innerCookie) {
        newCookies.addAll(innerCookie.newCookies);
        delCookies.addAll(innerCookie.delCookies);
    }

    @Data
    public static class FakeCookie implements Serializable {
        private static final long serialVersionUID = 2337241241780428991L;
        private String name;
        private String value;
        private String domain = "";
        private String path = "/";
        private int age = -1;
        private boolean httpOnly = false;
    }
}
