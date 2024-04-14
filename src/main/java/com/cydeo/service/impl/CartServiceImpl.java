package com.cydeo.service.impl;

import com.cydeo.model.Cart;
import com.cydeo.model.CartItem;
import com.cydeo.model.Product;
import com.cydeo.service.CartService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    public static Cart CART = new Cart(BigDecimal.ZERO, new ArrayList<>());

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addToCart(UUID productId, Integer quantity) {
        //todo find product based on productId
        Product product = productService.findProductById(productId);
        //todo initialise cart item using the found product
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        //todo add to cart
        List<Product> products = CART.getCartItemList().stream().map(CartItem::getProduct).toList();
        if (!products.contains(product)){
            //todo calculate cart total amount
            CART.setCartTotalAmount(CART.getCartTotalAmount().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));
            CART.getCartItemList().add(cartItem);
        }

        return CART;
    }

    @Override
    public boolean deleteFromCart(UUID productId) {
        //todo delete product object from cart using stream

        BigDecimal cartTotal = CART.getCartItemList().stream()
                .filter(cartItem -> cartItem.getProduct().getId().toString().equals(productId.toString()))
                .findAny().get().getTotalAmount();

        CART.setCartTotalAmount(CART.getCartTotalAmount().subtract(cartTotal));

        return CART.getCartItemList().
                removeIf(cartItem -> cartItem.getProduct().getId().toString().equals(productId.toString()));
    }
}
