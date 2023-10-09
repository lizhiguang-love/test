package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {
        //创建Socket对象，指定服务端的IP地址和端口号
        try {
            Socket socketClient = new Socket("127.0.0.1", 12345);
            //获取输入流和输出流，输入流和输出流是捅过socket对象来进行数据传输的
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream(), true);
            //从控制台读取用户输入
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                System.out.println("请输入要发送的信息（输入 'exit' 退出）：");
                message = reader.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    // 如果用户输入 'exit'，发送终止标志给服务端并退出循环
                    printWriter.println("exit");
                    break;
                }

                // 将用户输入的信息发送给服务端
                printWriter.println(message);

                // 接收服务端的响应并打印
                String response = bufferedReader.readLine();
                System.out.println("服务端响应：" + response);
            }

            // 关闭连接
            socketClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
