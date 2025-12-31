package com.ecom.config;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.repository.CategoryRepo;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Admin if not exists
        if (userRepository.findByEmail("admin@ecom.com") == null) {
            UserDtls admin = new UserDtls();
            admin.setName("Admin User");
            admin.setEmail("admin@ecom.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setMobile("9876543210");
            admin.setAddress("123 Admin Street");
            admin.setCity("Mumbai");
            admin.setState("Maharashtra");
            admin.setPincode("400001");
            admin.setRole("ROLE_ADMIN");
            admin.setIsEnable(true);
            admin.setProfileImage("admin.png");
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin@ecom.com / admin123");
        }

        // Create Test User if not exists
        if (userRepository.findByEmail("user@ecom.com") == null) {
            UserDtls user = new UserDtls();
            user.setName("Test User");
            user.setEmail("user@ecom.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setMobile("9876543211");
            user.setAddress("456 User Lane");
            user.setCity("Delhi");
            user.setState("Delhi");
            user.setPincode("110001");
            user.setRole("ROLE_USER");
            user.setIsEnable(true);
            user.setProfileImage("user.png");
            userRepository.save(user);
            System.out.println("✅ Test user created: user@ecom.com / user123");
        }

        // Create Categories if empty
        if (categoryRepo.count() == 0) {
            String[] categories = {"Electronics", "Fashion", "Home & Kitchen", "Books", "Sports", "Beauty"};
            String[] images = {"electronics.png", "fashion.png", "home.png", "books.png", "sports.png", "beauty.png"};
            
            for (int i = 0; i < categories.length; i++) {
                Category cat = new Category();
                cat.setName(categories[i]);
                cat.setImageName("default.png");
                cat.setIsActive(true);
                categoryRepo.save(cat);
            }
            System.out.println("✅ 6 Categories created");
        }

        // Create Products if empty
        if (productRepository.count() == 0) {
            String[][] products = {
                {"Smartphone Pro", "Electronics", "Latest smartphone with amazing features", "25000", "10", "15"},
                {"Laptop Ultra", "Electronics", "High performance laptop for professionals", "65000", "8", "12"},
                {"Wireless Earbuds", "Electronics", "Premium sound quality earbuds", "3500", "20", "20"},
                {"Smart Watch", "Electronics", "Fitness tracking smartwatch", "8000", "15", "18"},
                {"Bluetooth Speaker", "Electronics", "Portable wireless speaker", "2500", "25", "15"},
                {"Men's T-Shirt", "Fashion", "Comfortable cotton t-shirt", "599", "50", "25"},
                {"Women's Dress", "Fashion", "Elegant party dress", "1999", "30", "30"},
                {"Denim Jeans", "Fashion", "Classic blue denim jeans", "1299", "40", "20"},
                {"Sneakers", "Fashion", "Stylish running sneakers", "2499", "35", "22"},
                {"Sunglasses", "Fashion", "UV protection sunglasses", "899", "45", "28"},
                {"Cookware Set", "Home & Kitchen", "Non-stick cookware set", "3500", "20", "15"},
                {"Blender", "Home & Kitchen", "High-speed blender", "2200", "18", "18"},
                {"Bedsheet Set", "Home & Kitchen", "Premium cotton bedsheet", "1500", "30", "25"},
                {"Wall Clock", "Home & Kitchen", "Modern decorative clock", "800", "40", "20"},
                {"Table Lamp", "Home & Kitchen", "LED study lamp", "650", "35", "22"},
                {"Fiction Novel", "Books", "Bestselling fiction book", "350", "100", "10"},
                {"Self Help Book", "Books", "Motivational guide", "450", "80", "15"},
                {"Cookbook", "Books", "Delicious recipes collection", "550", "60", "12"},
                {"Biography", "Books", "Inspiring life story", "400", "70", "18"},
                {"Children's Book", "Books", "Fun stories for kids", "250", "90", "20"},
                {"Yoga Mat", "Sports", "Anti-slip yoga mat", "800", "50", "25"},
                {"Dumbbells Set", "Sports", "Home workout dumbbells", "1500", "30", "20"},
                {"Football", "Sports", "Professional football", "900", "40", "15"},
                {"Badminton Racket", "Sports", "Lightweight racket set", "1200", "35", "22"},
                {"Skipping Rope", "Sports", "Fitness jump rope", "300", "60", "30"},
                {"Face Cream", "Beauty", "Moisturizing face cream", "450", "50", "20"},
                {"Lipstick Set", "Beauty", "Long-lasting lipstick", "650", "40", "25"},
                {"Perfume", "Beauty", "Premium fragrance", "1800", "25", "18"},
                {"Hair Serum", "Beauty", "Nourishing hair serum", "550", "35", "22"},
                {"Makeup Kit", "Beauty", "Complete makeup set", "2500", "20", "15"}
            };

            for (String[] p : products) {
                Product product = new Product();
                product.setTitle(p[0]);
                product.setCategory(p[1]);
                product.setDescription(p[2]);
                product.setPrice(Double.parseDouble(p[3]));
                product.setStock(Integer.parseInt(p[4]));
                product.setDiscount(Integer.parseInt(p[5]));
                product.setDiscountPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                product.setIsActive(true);
                product.setImage("default.png");
                productRepository.save(product);
            }
            System.out.println("✅ 30 Products created (5 per category)");
        }
    }
}
