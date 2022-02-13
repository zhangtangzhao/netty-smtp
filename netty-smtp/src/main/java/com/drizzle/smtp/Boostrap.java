package com.drizzle.smtp;

public class Boostrap {

    public static void main(String[] args) {
        NettySmtpServer server = new NettySmtpServer();
        try {
            server.open();
        } catch (InterruptedException e) {
            e.printStackTrace();
            server.close();
        }
    }

}
