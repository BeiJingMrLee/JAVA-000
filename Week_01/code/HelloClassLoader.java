package demo.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {
            Class<?> myClass = helloClassLoader.findClass("Hello");
            Object object = myClass.newInstance();
            Method hello = myClass.getMethod("hello");
            hello.invoke(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = tranByte("D:/Hello.xlass");
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        // 返回解析的类
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] tranByte(String filePath){
        File file = new File(filePath);
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int size = 0;
            // 从文件中读取
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out.toByteArray();
        }
    }
}
