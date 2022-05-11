package com.reut.store.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetCartHandler extends HttpStoreHandler {
    StoreHttpServer server;

    public GetCartHandler(StoreHttpServer server){
        this.server = server;
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        super.handle(httpExchange, server.getCartProducts());
    }
}
