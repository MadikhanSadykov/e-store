package com.madikhan.estore.util;

public class UrlUtil {

    private UrlUtil(){

    }

    public static boolean isAjaxUrl(String url) {
        return url.startsWith("/more/");
    }

    public static boolean isAjaxJsonUrl(String url) {
        return url.startsWith("/more/");
    }

    public static boolean isAjaxHtmlUrl(String url) {
        return url.startsWith("/more/");
    }

    public static boolean isStaticUrl(String url) {
        return url.startsWith("/static/");
    }

    public static boolean isMediaUrl(String url) {
        return url.startsWith("/media/");
    }

}
