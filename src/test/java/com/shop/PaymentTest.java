package com.shop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.shop.CreditCardType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest
{

    public static final Product bike = Product.builder().name("bike")
            .price(BigDecimal.valueOf(2000)).build();
    public static final Product steamGiftCard = Product.builder().name("steam gift card")
            .price(BigDecimal.valueOf(50)).build();
    public static final Product amazonGiftCard = Product.builder().name("amazon gift card")
            .price(BigDecimal.valueOf(100)).build();
    public static final Product bubbleGum = Product.builder().name("bubble gum")
            .price(BigDecimal.valueOf(0.55)).build();
    public static final Product car = Product.builder().name("toyota car")
            .price(BigDecimal.valueOf(10_000)).build();
    public static final Product window = Product.builder().name("window")
            .price(BigDecimal.valueOf(1500)).build();

    @Test
    void normalTest()
    {
        List<Product> products = Arrays.asList(steamGiftCard, amazonGiftCard, bubbleGum, car, window);
        testDiscount(products, NORMAL, new BigDecimal("11650.55"));
    }

    @Test
    void silverTest()
    {
        List<Product> products = Arrays.asList(bike, steamGiftCard, amazonGiftCard, bubbleGum);
        testDiscount(products, SILVER, new BigDecimal("1935.5"));
    }

    @Test
    void goldTest()
    {
        List<Product> products = Arrays.asList(steamGiftCard, bubbleGum);
        testDiscount(products, GOLD, new BigDecimal("40.44"));
    }

    private void testDiscount(List<Product> products, CreditCardType creditCardType, BigDecimal expected)
    {
        ShoppingCart shoppingCart = new ShoppingCartImpl(products);
        Payment payment = PaymentImpl.builder()
                .creditCardType(creditCardType)
                .shoppingCart(shoppingCart).build();

        BigDecimal result = payment.withDiscount();

        assertEquals(0, result.compareTo(expected));
    }
}