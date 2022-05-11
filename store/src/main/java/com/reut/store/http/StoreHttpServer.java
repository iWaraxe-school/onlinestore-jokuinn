package com.reut.store.http;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.populator.DBPopulator;
import com.reut.store.populator.Populator;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class StoreHttpServer {
    public static final int PORT = 8080;
    public static final String BASE_URL = "http://localhost:" + PORT;
    public static final String GET_CATEGORIES_URL = BASE_URL + "/categories";
    public static final String GET_PRODUCTS_FOR_CATEGORY_URL = BASE_URL + "/productsForCategory";
    public static final String CART_URL = BASE_URL + "/cart";
    private final List<Product> cartProducts = new ArrayList<>();
    private final List<Product> allProducts = new ArrayList<>();
    public Populator populator = new DBPopulator();
    public List<Category> categories = new ArrayList<>();

    public StoreHttpServer() {
        run();
    }

    public void run() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            List<Category> allCategories = new DBPopulator().getCategories();
            for (Category category : allCategories) {
                server.createContext(("/productsForCategory/" + category.getName()), new GetProductsForCategoryHandler(CategoriesENUM.valueOf(category.getName()), this));
            }
            server.createContext("/categories", new GetCategoriesHandler(this));
            server.createContext("/cart", new GetCartHandler(this));

            System.out.println("server started at " + PORT);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategories() {

        if (categories != null) {
            categories = populator.getCategories();
        }

        return categories;
    }

    public List<Product> getProductsForCategory(CategoriesENUM categoryName, List<Category> categories) {

        List<Product> products = populator.getProductsForCategory(categoryName);

        Category category = categories.stream().filter(x -> x.getName().equals(categoryName.name())).findFirst().orElse(null);
        assert category != null;
        category.addProducts(products);

        return products;
    }

    public Product addProductToCart(String productName, List<Product> cartProducts, List<Product> allProducts) {

        Product product = getSelectedProduct(productName, allProducts);
        cartProducts.add(product);

        return product;
    }

    private Product getSelectedProduct(String productName, List<Product> products) {
        Optional<Product> orderedProduct = getAllProducts(products).stream()
                .filter(x -> x.getName().equals(productName))
                .findFirst();

        return orderedProduct.orElse(null);
    }

    private List<Product> getAllProducts(List<Product> products) {
        for (Category category : this.categories) {
            products.addAll(category.getProductList());
        }

        return products;
    }
}
