package com.drizzle.smtp;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettySmtpHandler extends SimpleChannelInboundHandler<String> {

    private static  boolean message = false;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("220 Netty SMTP Serve\n");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        String response="";
        boolean close = false;

        if(s.isEmpty()){
            response = "Please conetent.\n";
        }
        else if(s.equals("QUIT")){
            response = "221 Bye.\n";
            close = true;
        }else if(s.equals("DATA")){
            response="354 Enter mail, end with '.' on a line by itself\n";
            message = true;
        }else if(s.equals(".")){
            response = "250 Message sent\n";
            message = false;
        }else{
            if(!message){
                response = "250 OK.\n";
            }
        }

        ChannelFuture future = channelHandlerContext.write(response);
        channelHandlerContext.flush();

        if(close){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
