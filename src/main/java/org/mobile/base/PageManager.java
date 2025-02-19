package org.mobile.base;

import org.mobile.utils.ConfigReader;

import static org.mobile.base.ThreadLocalManager.currentPageTL;

public class PageManager {

    public static void setPageInstance(String pageName, String path) {
        String pagesPath = ConfigReader.get("pages_path");
        path = normalizePath(path);

        try {
            Class<?> pageClass = Class.forName(pagesPath + path + pageName);
            Object instance = pageClass.getDeclaredConstructor().newInstance();
            if (instance instanceof BasePage page) {
                currentPageTL.set(page);
            } else {
                throw new AssertionError("Page is not an instance of BasePage, its an instance of [%s]".formatted(instance.getClass().getName()));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize page: " + pageName, e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate page class: " + pageName, e);
        }catch (NoClassDefFoundError error){
            throw new RuntimeException("Failed to load page class: " + pageName, error);
        }
    }

    private static String normalizePath(String path) {
        if (path == null || path.isEmpty()) {
            return ".";
        }

        if (!path.startsWith("/")) path = "/" + path;
        if (!path.endsWith("/")) path = path + "/";

        return path.replace("/", ".");
    }
}