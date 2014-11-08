package com.baidu.harry.rpc.core.handler;

import com.baidu.harry.rpc.util.ServiceContainer;
import org.apache.commons.lang3.StringUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class DefaultRouterHandler extends AbstractRouterHandler implements Runnable {

    private Socket socket;

    public DefaultRouterHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
             handler(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo. replace with netty
    @Override
    public void handler(Socket socket) throws Exception {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            try {
                String clazz = input.readUTF();
                Object service = ServiceContainer.getServices().get(getServiceName(clazz));
                String methodName = input.readUTF();
                //service是服务器端提供服务的对象，但是，要通过获取到的调用方法的名称，参数类型，以及参数来选择对象的方法，并调用。获得方法的名称
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();//获得参数的类型
                Object[] arguments = (Object[]) input.readObject();//获得参数
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                try {
                    Method method =
                            service.getClass().getMethod(methodName, parameterTypes);//通过反射机制获得方法
                    Object result = method.invoke(service, arguments);//通过反射机制获得类的方法，并调用这个方法
                    System.out.println("Server Get Result:" + result.toString());
                    output.writeObject(result);//将结果发送
                } catch (Throwable t) {
                    System.out.println("Server Go Error" + t.getMessage());
                    output.writeObject(t);
                } finally {
                    output.close();
                }
            } finally {
                input.close();
            }
        } finally {
            socket.close();
        }
    }

    public String getServiceName(String name) {
        if (StringUtils.isEmpty(name)) {

        }
        String[] temp = name.split("\\.");
        String simpleName = temp[temp.length - 1];
        String first = simpleName.substring(0, 1).toLowerCase();
        String rest = simpleName.substring(1, simpleName.length());
        return new StringBuffer(first).append(rest).toString();
    }
}
