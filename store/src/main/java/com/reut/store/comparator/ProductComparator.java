package com.reut.store.comparator;

import com.reut.domain.Product;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;

public class ProductComparator implements Comparator<Product> {

    public Map<String, String> sort;

    public ProductComparator(Map<String, String> sort){
        this.sort = sort;
    }

    @Override
    public int compare(Product a, Product b) {
        CompareToBuilder compareBuilder = new CompareToBuilder();

        for (Map.Entry<String, String> item : sort.entrySet()) {
            ProductSort sortOrder = ProductSort.valueOf(sort.get(item.getKey()));
            try {
                if (sortOrder == ProductSort.ASC) {
                    compareBuilder.append(this.getPropertyValue(a, item.getKey()), this.getPropertyValue(b, item.getKey()));
                } else {
                    compareBuilder.append(this.getPropertyValue(b, item.getKey()), this.getPropertyValue(a, item.getKey()));
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return 0;
            }
        }

        return compareBuilder.toComparison();
    }

    public String getPropertyValue(Product product, String property) throws Exception {

        try {
            Field field = product.getClass().getDeclaredField(property);
            field.setAccessible(true);
            return field.get(product).toString();
        }
        catch (NoSuchFieldException | IllegalAccessException ex){
            throw new Exception(ex);
        }
    }
}
