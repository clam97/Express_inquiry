package org.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.Dao.*;
import org.apache.log4j.Logger;


import java.nio.charset.Charset;


import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HandleReqHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(HandleReqHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("===================1=======================");
        HttpPostRequestDecoder decoder = null;
        FullHttpRequest fuHR = (FullHttpRequest) msg;
        String url = fuHR.uri();
        logger.info("url-----------------"+url);
        Charset charset=Charset.forName("utf-8");
        ByteBuf byteBuf=fuHR.content();
        String data=byteBuf.toString(charset);
        String state="";
        switch (url){
            //请求验证码
            case "/random":
               state = ResighterDeal.reqRandom(data);
             break;
            //注册（接收注册页面的所有信息）
            case "/register":
                state = ResighterDeal.SuccessMessage(data);
              break;
            //登录
            case "/login":
                state =LoginDeal.login(data);
             break;
            //存储快递上门的个人信息
            case "/storemessage":
                state = StoreMassage.getMessage(data);
             break;
            //联系快递员并通知
            case "/call":
                state = CallMe.call(data);
             break;
            //输入查询/条形码查询
            case "/outSearch":
                state = OutSearch.outSearch(data);
              break;

            //OCR查询
            case "/ocrSearch":
                state =  OCRSearch.ocrSearch(data);
                break;
            //查看记录
            case "/searchjilu":
                state = Searchjilu.searchjilu(data);
                break;
            case "/detail":
                state = new Detail().detail(data);
                break;
            case "/changepassword":
                state = new UpdatePasswd().updatewdsend(data);
                break;
            case "/newpassword":
                state = new UpdatePasswd().newpassword(data);
                break;
            case "/picSearch":
                state = new OcrSearch().ocrSear(data);
                break;

        }


        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(state.getBytes("utf-8")));
        response.headers().set(CONTENT_TYPE, "text/json");
        response.headers().setInt(CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
        ctx.writeAndFlush(response);



    }
}
