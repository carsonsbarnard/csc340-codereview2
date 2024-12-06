package com.bookstorestaticwebsite.StaticBookStoreWebsite.common;

import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CommonConfig {

    public static final float TAX_RATE = 0.07f;
    public static final float SHIPPING_FEE = 7.5f;
    public static final List<String> STATUS_LIST = Arrays.asList("Processing", "Shipping", "Shipped", "Completed", "Cancelled");

}
