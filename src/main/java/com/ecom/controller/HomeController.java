package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController
{
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;
	
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
	public String index(Model m)
	{
		List<Category> allActiveCategory = categoryService.getAllActiveCategory().stream().sorted((c1, c2)->c2.getId().compareTo(c1.getId())).limit(6).toList();
		List<Product> allActiveProducts = productService.getAllActiveProducts("").stream().sorted((p1, p2)->p2.getId().compareTo(p1.getId())).limit(8).toList();
		m.addAttribute("category", allActiveCategory);
		m.addAttribute("products", allActiveProducts);
		return "index";	
	}
	
	@GetMapping("/signin")
	public String login()
	{
		return "login";	
	}
	
	@GetMapping("/register")
	public String resgiter()
	{
		return "register";	
	}
	
	@GetMapping("/products")
	public String products(Model m, @RequestParam(value = "category", defaultValue = "") String category, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize, @RequestParam(defaultValue = "") String ch)
	{
		List<Category> categories = categoryService.getAllActiveCategory();
		m.addAttribute("categories", categories);
		m.addAttribute("paramValue", category);
		Page<Product> page = null;
		
		if(StringUtils.isEmpty(ch))
		{
			page = productService.getAllActiveProductPagination(pageNo, pageSize, category);
		}
		else
		{
			page = productService.searchActiveProductPagination(pageNo, pageSize, category, ch);
		}
		
		List<Product> products = page.getContent();
		m.addAttribute("products", products);
		m.addAttribute("productsSize", products.size());
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		/*List<Product> products = productService.getAllActiveProducts(category);
		m.addAttribute("products", products);*/
		return "product";	
	}
	
	@GetMapping("/product/{slug}")
	public String product(@PathVariable String slug, Model m)
	{
		Product product = productService.getProductBySlug(slug);
		if(product == null) {
			// Fallback: try to parse as ID for backward compatibility
			try {
				int id = Integer.parseInt(slug);
				product = productService.getProductById(id);
			} catch (NumberFormatException e) {
				// Not a number, product not found
			}
		}
		m.addAttribute("product", product);
		return "view_products";	
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException
	{
		Boolean existEmail = userService.existEmail(user.getEmail());
		
		if(existEmail)
		{
			session.setAttribute("errorMsg", "Email Already Exists!");
		}
		else
		{
			String image = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
			user.setProfileImage(image);
			UserDtls saveUser = userService.saveUser(user);
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
		}
		
		return "redirect:/register";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword()
	{
		return "forgot_password";
	}
	
	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException
	{
		UserDtls email2 = userService.getUserByEmail(email);
		if(ObjectUtils.isEmpty(email2))
		{
			session.setAttribute("succMsg", "Invalid Email");
		}
		else
		{
			String resetToken = UUID.randomUUID().toString();
			userService.updateUserResetToken(email, resetToken);
			
			String url = CommonUtil.generateUrl(req)+"/reset-password?token="+resetToken;
			
//			System.out.println("Reset URL: " + url);
			
			Boolean sendMail = commonUtil.sendMail(url, email);
			if(sendMail)
			{
				session.setAttribute("succMsg", "Please Check Your Mail, Reset Password link is Sent");
			}
			else
			{
				session.setAttribute("errorMsg", "Something Wrong on Server!! Email not send");
			}
		}
		return "redirect:/forgot-password";
	}
	
	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token, HttpSession session, Model m)
	{
		UserDtls token2 = userService.getUserByToken(token);
		if(token2 == null)
		{
			m.addAttribute("msg", "Your link is Invalid or Expired!");
			return "message";
		}
		m.addAttribute("token", token);
		return "reset_password";
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String password, HttpSession session, Model m)
	{
		UserDtls token2 = userService.getUserByToken(token);
		if(token2 == null)
		{
			m.addAttribute("msg", "Your link is Invalid or Expired!");
			return "message";
		}
		else
		{
			token2.setPassword(passwordEncoder.encode(password));
			token2.setResetToken(null);
			userService.updateUser(token2);	
			//session.setAttribute("succMsg", "Password Changed Successfully");
			m.addAttribute("msg", "Password Changed Successfully");
			return "message";
		}
	}
	
	@GetMapping("/search")
	public String searchProduct(@RequestParam String ch, Model m)
	{
		List<Product> searchProduct = productService.searchProduct(ch);
		m.addAttribute("products", searchProduct);
		List<Category> categories = categoryService.getAllActiveCategory();
		m.addAttribute("categories", categories);
		return "product";
	}
}