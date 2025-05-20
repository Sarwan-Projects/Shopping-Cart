package com.ecom.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecom.model.BillingAddress;
import com.ecom.model.Cart;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.service.OrderService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService
{
	@Autowired
	private ProductOrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public void saveOrder(Integer userId, OrderRequest orderRequest) 
	{
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		for(Cart c : carts)
		{
			ProductOrder order = new ProductOrder();
			order.setOrderId(UUID.randomUUID().toString());
			order.setOrderDate(LocalDate.now());
			order.setProduct(c.getProduct());
			order.setPrice(c.getProduct().getDiscountPrice());
			order.setQuantity(c.getQuantity());
			order.setUser(c.getUser());
			order.setStatus(OrderStatus.IN_PROGRESS.getName());
			order.setPaymentType(orderRequest.getPaymentType());
			
			BillingAddress ba = new BillingAddress();
			ba.setFirstName(orderRequest.getFirstName());
			ba.setLastName(orderRequest.getLastName());
			ba.setEmail(orderRequest.getEmail());
			ba.setMobile(orderRequest.getMobile());
			ba.setAddress(orderRequest.getAddress());
			ba.setCity(orderRequest.getCity());
			ba.setState(orderRequest.getState());
			ba.setPincode(orderRequest.getPincode());
			
			order.setBillingAddress(ba);
			
			ProductOrder save = orderRepository.save(order);
			try 
			{
				commonUtil.sendMailForProductOrder(save, "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cartRepository.deleteAll(carts);
	}

	@Override
	public List<ProductOrder> getOrderByUser(Integer userId) 
	{
		List<ProductOrder> orders = orderRepository.findByUserId(userId);
		return orders.stream()
                .filter(order -> !"CANCELLED".equalsIgnoreCase(order.getStatus()))
                .toList();
	}

	@Override
	public ProductOrder updateOrderStatus(Integer id, String status) 
	{
		Optional<ProductOrder> byId = orderRepository.findById(id);
		if(byId.isPresent())
		{
			ProductOrder order = byId.get();
			if ("CANCELLED".equalsIgnoreCase(status))
			{
				orderRepository.delete(order);
		        return null; // Indicate it's deleted
		    } 
			else 
			{
				order.setStatus(status);
		        return orderRepository.save(order);
		    }
		}
		return null;
	}

	@Override
	public List<ProductOrder> getAllOrders() 
	{	
		return orderRepository.findAll();
	}

	@Override
	public ProductOrder getOrderByOrderId(String orderId) 
	{
		return orderRepository.findByOrderId(orderId);
	}

	@Override
	public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) 
	{
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return orderRepository.findAll(pageable);
	}

}