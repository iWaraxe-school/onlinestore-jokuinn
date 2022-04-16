package com.reut.store.http;

import com.reut.domain.categories.CategoriesENUM;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetProductsForCategoryHandler extends HttpStoreHandler {
    CategoriesENUM category;
    StoreHttpServer server;

    public GetProductsForCategoryHandler(CategoriesENUM category, StoreHttpServer server){

        this.category = category;
        this.server = server;
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        super.handle(httpExchange, server.getProductsForCategory(category, server.getCategories()));
    }
}
