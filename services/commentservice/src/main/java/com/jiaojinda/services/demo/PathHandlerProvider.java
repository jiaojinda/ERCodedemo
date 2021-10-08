package com.jiaojinda.services.demo;

import com.jiaojinda.services.demo.QRCode.handler.GenerateQRCodeHandler;
import com.jiaojinda.services.demo.QRCode.handler.ParseQRCodeHandler;
import com.networknt.server.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.util.Methods;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
                .add(Methods.GET, "/generate", new GenerateQRCodeHandler())
                .add(Methods.GET, "/parse", new ParseQRCodeHandler())
                ;
    }
}
