package com.jiaojinda.services.demo.QRCode.handler;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jiaojinda.services.demo.QRCode.service.QRCodeService;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import org.apache.commons.lang3.math.NumberUtils;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.Map;

public class GenerateQRCodeHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getRequestReceiver().receiveFullString((exchangeCopy, message) -> {
            exchangeCopy.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

            Map<String, Deque<String>> queryParam = exchange.getQueryParameters();
            String text = queryParam.get("text") == null ? "" : queryParam.get("text").getFirst();
            String widthStr = queryParam.get("width") == null ? "" : queryParam.get("width").getFirst();
            String heightStr = queryParam.get("height") == null ? "" : queryParam.get("height").getFirst();
            String format = queryParam.get("format") == null ? "png" : queryParam.get("format").getFirst();
            String res = null;

            int width = NumberUtils.toInt(widthStr,100);    //二维码图片的宽
            int height = NumberUtils.toInt(heightStr,100);    //二维码图片的宽


            try {
                //生成二维码图片，并返回图片路径
                String pathName = QRCodeService.generateQRCode(text, width, height, format);
                System.out.println("生成二维码的图片路径：" + pathName);
                res = pathName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            exchangeCopy.setStatusCode(StatusCodes.OK);
            exchangeCopy.getResponseSender().send(res);
        }, Charset.forName("UTF-8"));
    }
}
