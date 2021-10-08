package com.jiaojinda.services.demo.QRCode.handler;

import com.jiaojinda.services.demo.QRCode.service.QRCodeService;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import org.apache.commons.lang3.math.NumberUtils;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.Map;

public class ParseQRCodeHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getRequestReceiver().receiveFullString((exchangeCopy, message) -> {
            exchangeCopy.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");

            Map<String, Deque<String>> queryParam = exchange.getQueryParameters();
            String pathName = queryParam.get("pathName") == null ? "" : queryParam.get("pathName").getFirst();
            String res = null;

            try {
                //生成二维码图片，并返回图片路径
                String content = QRCodeService.parseQRCode(pathName);
                System.out.println("解析出二维码的图片的内容为：" + content);
                res = content;
            } catch (Exception e) {
                e.printStackTrace();
            }
            exchangeCopy.setStatusCode(StatusCodes.OK);
            exchangeCopy.getResponseSender().send(res);
        }, Charset.forName("UTF-8"));
    }
}
