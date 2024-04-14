package com.cydeo.controller;

import com.cydeo.model.CartItem;
import com.cydeo.service.CartService;
import com.cydeo.service.impl.CartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Model model){

        model.addAttribute("cartList", CartServiceImpl.CART.getCartItemList());
        model.addAttribute("cartTotalAmount", CartServiceImpl.CART.getCartTotalAmount());
        return "cart/show-cart";
    }

    @GetMapping("/addToCart/{productId}/{quantity}")
    public String addToCart(@PathVariable UUID productId, @PathVariable Integer quantity, Model model){

        cartService.addToCart(productId, quantity);

        model.addAttribute("cartList", CartServiceImpl.CART.getCartItemList());
        model.addAttribute("cartTotalAmount", CartServiceImpl.CART.getCartTotalAmount());

        return "cart/show-cart";
    }

    @GetMapping("/delete/{productId}")
    public String deleteFromCart(@PathVariable UUID productId, Model model){

        cartService.deleteFromCart(productId);

        model.addAttribute("cartList", CartServiceImpl.CART.getCartItemList());
        model.addAttribute("cartTotalAmount", CartServiceImpl.CART.getCartTotalAmount());

        return "cart/show-cart";
    }
}
