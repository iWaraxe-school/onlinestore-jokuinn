package com.reut.store.populator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.http.StoreHttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
public class HttpPopulator implements Populator {

    public static final String DEFAULT_USERNAME = "Username";
    public static final String DEFAULT_PASSWORD = "Password";

    StoreHttpServer server;
    HttpClient client;
    ObjectMapper objectMapper = new ObjectMapper();

    public HttpPopulator() {
        server = new StoreHttpServer();
        createClient();
    }

    @Override
    public List<Category> getCategories() {
        try {
            HttpGet request = new HttpGet(StoreHttpServer.GET_CATEGORIES_URL);
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return objectMapper.readValue(result, new TypeReference<List<Category>>() {
            });

        } catch (IOException e) {
            log.error("Error when getting all categories via HTTP : " + e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public List<Product> getProductsForCategory(CategoriesENUM category) {
        try {
            HttpGet request = new HttpGet(String.format(StoreHttpServer.GET_PRODUCTS_FOR_CATEGORY_URL + "/%s", category.name()));
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return objectMapper.readValue(result, new TypeReference<List<Product>>() {
            });

        } catch (IOException e) {
            log.error(String.format("Error when getting products for category '%S' via HTTP : ", category.name()) + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void addToCart(String productName) throws Exception {

        HttpPost httppost = new HttpPost(StoreHttpServer.CART_URL);

        try {
            httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(server.addProductToCart(productName, server.getCartProducts(), server.getAllProducts()))));

        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            log.error("Error when adding product to cart via HTTP : " + e.getLocalizedMessage());
            throw new Exception();
        }
    }

    public List<Product> getProductsFromCart() throws Exception {

        try {
            HttpGet request = new HttpGet(StoreHttpServer.CART_URL);
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return objectMapper.readValue(result, new TypeReference<List<Product>>() {
            });

        } catch (IOException e) {
            log.error("Error when getting all products from the cart via HTTP : " + e.getLocalizedMessage());
            throw new Exception();
        }
    }

    private void createClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        provider.setCredentials(AuthScope.ANY, credentials);

        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
    }
}
