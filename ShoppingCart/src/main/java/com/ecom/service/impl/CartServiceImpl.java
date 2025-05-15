package com.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;

@Service
public class CartServiceImpl implements CartService
{
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) 
	{	
		UserDtls user = userRepository.findById(userId).get();
		Product product = productRepository.findById(productId).get();
		
		Cart carts = cartRepository.findByProductIdAndUserId(productId, userId);
		
		Cart c = null;
		
		if(ObjectUtils.isEmpty(carts))
		{
			c = new Cart();
			c.setUser(user);
			c.setProduct(product);
			c.setQuantity(1);
			c.setTotalPrice(1 * product.getDiscountPrice());
		}
		else
		{
			c = carts;
			c.setQuantity(c.getQuantity()+1);
			c.setTotalPrice(c.getQuantity() * c.getProduct().getDiscountPrice());
		}
		
		Cart save = cartRepository.save(c);
		
		return save;
	}

	@Override
	public List<Cart> getCartsByUser(Integer userId) 
	{
		List<Cart> list = cartRepository.findByUserId(userId);
		Double totalOrderPrice = 0.0;
		List<Cart> updateCart = new ArrayList<>();
		
		for(Cart c : list)
		{
			Double totalPrice = (c.getProduct().getDiscountPrice() * c.getQuantity());
			c.setTotalPrice(totalPrice);
			totalOrderPrice = totalOrderPrice + totalPrice;
			c.setTotalOrderPrice(totalOrderPrice);
			updateCart.add(c);
		}
		return updateCart;
	}

	@Override
	public Integer getCartCount(Integer userId) 
	{
		Integer byUserId = cartRepository.countByUserId(userId);
		return byUserId;
	}

	@Override
	public void updateCartQuantity(String sy, Integer cid) 
	{
		Cart cart = cartRepository.findById(cid).get();
		Integer update;
		if(sy.equalsIgnoreCase("de"))
		{
			update = cart.getQuantity() - 1;
			if(update<=0)
			{
				cartRepository.delete(cart);
			}
			else
			{
				cart.setQuantity(update);
				cartRepository.save(cart);
			}
		}
		else
		{
			update = cart.getQuantity() + 1;
			cart.setQuantity(update);
			cartRepository.save(cart);
		}
	}

}