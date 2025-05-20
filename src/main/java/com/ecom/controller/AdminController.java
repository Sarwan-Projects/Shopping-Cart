package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@ModelAttribute
	public void getUserDetails(Principal p, Model m)
	{
		if(p!=null)
		{
			String name = p.getName();
			UserDtls user = userService.getUserByEmail(name);
			m.addAttribute("user", user);
			Integer cartCount = cartService.getCartCount(user.getId());
			m.addAttribute("cartCount", cartCount);
		}
		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}
	
	@GetMapping("/")
	public String index()
	{
		return "admin/index";
	}
	@GetMapping("/add-product")
	public String addProduct(Model m)
	{
		List<Category> category = categoryService.getAllCategory();
		m.addAttribute("categories", category);
		return "admin/add_product";
	}
	@GetMapping("/category")
	public String category(Model m, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize)
	{
		//m.addAttribute("categorys", categoryService.getAllCategory());
		Page<Category> page = categoryService.getAllCategoryPagination(pageNo, pageSize);
		List<Category> categorys = page.getContent();
		m.addAttribute("categorys", categorys);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		return "admin/category";
	}
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, @RequestParam("isActive") String isActiveStr,
			HttpSession session) throws IOException 
	{

		category.setisActive(Boolean.parseBoolean(isActiveStr));
		
		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImageName(imageName);

		Boolean existCategory = categoryService.existCategory(category.getName());

		if (existCategory) 
		{
			session.setAttribute("errorMsg", "Category Name already exists");
		} else 
		{

			Category saveCategory = categoryService.saveCategory(category);

			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return "redirect:/admin/category";
	}
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session)
	{
		boolean deleteCategory = categoryService.deleteCategory(id);
		if(deleteCategory)
		{
			session.setAttribute("succMsg", "Category Delete Success");
		}
		else
		{
			session.setAttribute("errorMsg", "Somthing Wrong on Server");
		}
		return "redirect:/admin/category";
	}
	
	@GetMapping("/loadEdit/{id}")
	public String loadEdit(@PathVariable int id, Model m)
	{
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "/admin/edit_category";
	}
	
	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException
	{
		Category old = categoryService.getCategoryById(category.getId());
		String image = file.isEmpty() ? old.getImageName() : file.getOriginalFilename();
		
		if(!ObjectUtils.isEmpty(old))
		{
			old.setName(category.getName());
			old.setisActive(category.getisActive());
			old.setImageName(image);
		}
		Category update = categoryService.saveCategory(old);
		if(!ObjectUtils.isEmpty(update))
		{
			if(!file.isEmpty())
			{
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			
			session.setAttribute("succMsg", "Category Update Success");
		}
		else
		{
			session.setAttribute("errorMsg", "Something Wrong on Server");
		}
		return "redirect:/admin/loadEdit/"+category.getId();
	}
	
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session) throws IOException
	{
		String name = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		product.setImage(name);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());
		
		Product product2 = productService.saveProduct(product);
		
		if(!ObjectUtils.isEmpty(product2))
		{
			File saveFile = new ClassPathResource("static/img").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
					+ image.getOriginalFilename());

			//System.out.println(path);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			session.setAttribute("succMsg", "Product Saved Successfully");
		}
		else
		{
			session.setAttribute("errorMsg", "Something Wrong On Server");
		}
		
		return "redirect:/admin/add-product";
	}
	
	@GetMapping("/products")
	public String viewProducts(Model m, @RequestParam(defaultValue = "") String ch, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize)
	{
		/*List<Product> products = null;
		if(ch != null && ch.length() > 0)
		{
			products = productService.searchProduct(ch);
		}
		else
		{
			products = productService.getAllProducts();
		}
		m.addAttribute("products", products);*/
		
		Page<Product> page = null;
		if(ch != null && ch.length() > 0)
		{
			page = productService.searchProductPagination(ch, pageNo, pageSize);
		}
		else
		{
			page = productService.getAllProductsPagination(pageNo, pageSize);
		}
		m.addAttribute("products", page.getContent());

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		
		return "admin/products";
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProducts(@PathVariable int id, HttpSession session)
	{
		Boolean boolean1 = productService.deleteProducts(id);
		
		if(boolean1)
		{
			session.setAttribute("succMsg", "Product Deleted Successfully");
		}
		else
		{
			session.setAttribute("errorMsg", "Something Wrong On Server");
		}
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/editProduct/{id}")
	public String editProducts(@PathVariable int id, Model m)
	{
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_product";
	}
	
	@PostMapping("/updateProduct")
	public String updateProducts(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session, Model m)
	{
		if(product.getDiscount()<0 || product.getDiscount()>100)
		{
			session.setAttribute("errorMsg", "Inavlid Discount");
		}
		else
		{
			Product updateProduct = productService.updateProduct(product, image);
			if(!ObjectUtils.isEmpty(updateProduct))
			{
				session.setAttribute("succMsg", "Product Updated Successfully");
			}
			else
			{
				session.setAttribute("errorMsg", "Something Wrong On Server");
			}
		}
		return "redirect:/admin/editProduct/"+product.getId();
	}
	
	@GetMapping("/users")
	public String getAllUsers(Model m, @RequestParam Integer type)
	{
		List<UserDtls> user = null;
		
		if(type==1)
		{
			user = userService.getUser("ROLE_USER");
		}
		else
		{
			user = userService.getUser("ROLE_ADMIN");
		}
		m.addAttribute("userType", type);
		m.addAttribute("users", user);
		return "/admin/users";
	}
	
	@GetMapping("/updateStatus")
	public String updateUserStatus(@RequestParam Boolean status,@RequestParam Integer id, @RequestParam Integer type, HttpSession session)
	{
		Boolean f = userService.updateAccountStatus(status, id);
		if(f)
		{
			session.setAttribute("succMsg", "Account Status Updated");
		}
		else
		{
			session.setAttribute("errorMsg", "Something Wrong On Server");
		}
		return "redirect:/admin/users?type="+type;
	}
	
	@GetMapping("/orders")
	public String getAllOrders(Model m, @RequestParam(defaultValue = "") String ch, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize)
	{
		/*List<ProductOrder> orders = orderService.getAllOrders();
		m.addAttribute("orders", orders);
		m.addAttribute("srch", false);*/
		
		Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
		m.addAttribute("orders", page.getContent());
		m.addAttribute("srch", false);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		
		return "/admin/orders";
	}
	
	@PostMapping("/update-order-status")
	public String orderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session)
	{
		OrderStatus[] values = OrderStatus.values();
		String status = null;
		
		for (OrderStatus orderStatus : values) 
		{
			if(orderStatus.getId().equals(st))
			{
				status = orderStatus.getName();
			}
		}
		
		ProductOrder order = orderService.updateOrderStatus(id, status);
		
		try 
		{
			commonUtil.sendMailForProductOrder(order, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!ObjectUtils.isEmpty(order))
		{
			session.setAttribute("succMsg", "Status Updated Successfully");
		}
		else
		{
			session.setAttribute("errorMsg", "Status not Updated");
		}
		
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/search-order")
	public String searchProduct(@RequestParam String orderId, Model m, HttpSession session, @RequestParam(defaultValue = "") String ch, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize)
	{
		if(orderId != null && orderId.length() > 0)
		{
			ProductOrder order = orderService.getOrderByOrderId(orderId.trim());
			if(ObjectUtils.isEmpty(order))
			{
				session.setAttribute("errorMsg", "Invalid Order Id");
				m.addAttribute("orderDtls", null);
			}
			else
			{
				m.addAttribute("orderDtls", order);
			}
			m.addAttribute("srch", true);
		}
		else
		{
			/*List<ProductOrder> orders = orderService.getAllOrders();
			m.addAttribute("orders", orders);
			m.addAttribute("srch", false);*/
			
			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			m.addAttribute("orders", page.getContent());
			m.addAttribute("srch", false);
			
			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());
		}
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/add-admin")
	public String addAdmin()
	{
		return "/admin/add_admin";
	}
	
	@PostMapping("/save-admin")
	public String saveAdmin(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException
	{
		String image = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(image);
		UserDtls saveUser = userService.saveAdmin(user);
		if(!ObjectUtils.isEmpty(saveUser))
		{
			if(!file.isEmpty())
			{
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
						+ file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg", "Registered Successfully");
		}
		else
		{
			session.setAttribute("errorMsg", "Something Wrong on Server");
		}
		
		return "redirect:/admin/add-admin";
	}
	
	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session)
	{
		UserDtls profile = userService.updateProfile(user, img);
		if(ObjectUtils.isEmpty(profile))
		{
			session.setAttribute("errorMsg", "Profile Not Updated");
		}
		else
		{
			session.setAttribute("succMsg", "Profile Updated");
		}
		
		return "redirect:/admin/profile";
	}
	
	@GetMapping("/profile")
	public String profile()
	{
		return "/admin/profile";
	}
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p, HttpSession session)
	{
		UserDtls loggedInUserDetails = commonUtil.getLoggedInUserDetails(p);
		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());
		if(matches)
		{
			String encode = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encode);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if(ObjectUtils.isEmpty(updateUser))
			{
				session.setAttribute("errorMsg", "Password Not Updated !! Error in Server");
			}
			else
			{
				session.setAttribute("succMsg", "Password Updated Successfully");
			}
		}
		else
		{
			session.setAttribute("errorMsg", "Incorrect Password");
		}
		
		return "redirect:/admin/profile";
	}
	
}