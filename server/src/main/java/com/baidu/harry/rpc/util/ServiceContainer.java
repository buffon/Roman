package com.baidu.harry.rpc.util;

import com.baidu.harry.rpc.core.EndPoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: harry.chen
 * @since: 14-11-7 下午5:40
 */
public class ServiceContainer {

    private static Map<String, Object> services = new HashMap<String, Object>();

    public static Map<String, Object> getServices(){
        return services;
    }

    public static void scanClassPath(File file) {

        try {
            if (file.isFile()) {
                if (file.getName().endsWith(".class")) {
                    String path = file.getPath();
                    MyClassLoader myClassLoader = new MyClassLoader();
                    Class<?> clazz = myClassLoader.load(path);
                    EndPoint endPoint = (EndPoint) clazz.getAnnotation(EndPoint.class);
                    if (endPoint != null) {
                        String uri = endPoint.value();
                        Object action = clazz.newInstance();
                        services.put(uri, action);
                    }
                }
            } else {
                File[] files = file.listFiles();
                for (File child : files) {
                    scanClassPath(child);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class MyClassLoader extends ClassLoader {
        public MyClassLoader() {
            super();
        }

        public Class<?> load(String path) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                byte[] buf = new byte[fis.available()];
                int len = 0;
                int total = 0;
                int fileLength = buf.length;
                while (total < fileLength) {
                    len = fis.read(buf, total, fileLength - total);
                    total = total + len;
                }
                return super.defineClass(null, buf, 0, fileLength);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fis = null;
                }
            }
        }
    }
}
