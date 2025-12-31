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

import java.util.UUID;

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
    
    private String generateSlug(String title) {
        String baseSlug = title.toLowerCase()
            .replaceAll("[^a-z0-9\\s-]", "")
            .replaceAll("\\s+", "-")
            .replaceAll("-+", "-")
            .trim();
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return baseSlug + "-" + uniqueId;
    }

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
            String[][] categories = {
                {"Electronics", "Electronics.jpg"},
                {"Fashion", "Fashion.jpg"},
                {"Home & Kitchen", "Home Kitchen.jpg"},
                {"Books", "Books.jpg"},
                {"Sports", "Sports.jpg"},
                {"Beauty", "Beauty.jpg"}
            };
            
            for (String[] cat : categories) {
                Category category = new Category();
                category.setName(cat[0]);
                category.setImageName(cat[1]);
                category.setisActive(true);
                categoryRepo.save(category);
            }
            System.out.println("✅ 6 Categories created");
        }

        // Check if products need slug update (for existing data)
        boolean needsSlugUpdate = productRepository.findAll().stream()
            .anyMatch(p -> p.getSlug() == null || p.getSlug().isEmpty());
        
        if (needsSlugUpdate && productRepository.count() > 0) {
            // Update existing products with slugs
            productRepository.findAll().forEach(p -> {
                if (p.getSlug() == null || p.getSlug().isEmpty()) {
                    p.setSlug(generateSlug(p.getTitle()));
                    productRepository.save(p);
                }
            });
            System.out.println("✅ Updated existing products with slugs");
        }

        // Create Products if empty
        if (productRepository.count() == 0) {
            // Format: {title, category, description, price, stock, discount, image}
            String[][] products = {
                {"Smartphone Pro", "Electronics", "Latest smartphone with amazing features and high-resolution display", "25000", "10", "15", "Smartphone Pro.jpg"},
                {"Laptop Ultra", "Electronics", "High performance laptop for professionals with 16GB RAM", "65000", "8", "12", "Laptop Ultra.jpg"},
                {"Wireless Earbuds", "Electronics", "Premium sound quality earbuds with noise cancellation", "3500", "20", "20", "Wireless earbuds.jpg"},
                {"Smart Watch", "Electronics", "Fitness tracking smartwatch with heart rate monitor", "8000", "15", "18", "Smart Watch.jpg"},
                {"Bluetooth Speaker", "Electronics", "Portable wireless speaker with deep bass", "2500", "25", "15", "Bluetooth Speaker.jpg"},
                {"Power Bank", "Electronics", "20000mAh fast charging portable charger", "1500", "30", "20", "Powerbank.jpg"},
                {"Men's T-Shirt", "Fashion", "Comfortable cotton t-shirt for everyday wear", "599", "50", "25", "Mens Tshirt.jpg"},
                {"Women's Dress", "Fashion", "Elegant party dress for special occasions", "1999", "30", "30", "Womens dress.jpg"},
                {"Denim Jeans", "Fashion", "Classic blue denim jeans with perfect fit", "1299", "40", "20", "Denim Jeans.jpg"},
                {"Sneakers", "Fashion", "Stylish running sneakers with cushioned sole", "2499", "35", "22", "Sneakers.jpg"},
                {"Sunglasses", "Fashion", "UV protection sunglasses with polarized lenses", "899", "45", "28", "Sunglasses.jpg"},
                {"Leather Wallet", "Fashion", "Premium genuine leather bifold wallet", "799", "45", "15", "Leather Wallet.jpg"},
                {"Cookware Set", "Home & Kitchen", "Non-stick cookware set with 5 pieces", "3500", "20", "15", "Cookware Set.jpg"},
                {"Blender", "Home & Kitchen", "High-speed blender for smoothies and shakes", "2200", "18", "18", "Blender.jpg"},
                {"Bedsheet Set", "Home & Kitchen", "Premium cotton bedsheet with pillow covers", "1500", "30", "25", "Bedsheet set.jpg"},
                {"Wall Clock", "Home & Kitchen", "Modern decorative wall clock for home", "800", "40", "20", "Wall Clock.jpg"},
                {"Table Lamp", "Home & Kitchen", "LED study lamp with adjustable brightness", "650", "35", "22", "Table Lamp.jpg"},
                {"Air Fryer", "Home & Kitchen", "Digital air fryer with 4.5L capacity", "4500", "15", "25", "Air fryer.jpg"},
                {"Fiction Novel", "Books", "Bestselling fiction book by renowned author", "350", "100", "10", "Fictional Novel.jpg"},
                {"Self Help Book", "Books", "Motivational guide for personal growth", "450", "80", "15", "Self Help book.jpg"},
                {"Cookbook", "Books", "Delicious recipes collection from around the world", "550", "60", "12", "Cookbook.jpg"},
                {"Biography", "Books", "Inspiring life story of a legendary personality", "400", "70", "18", "Biography.jpg"},
                {"Children's Book", "Books", "Fun stories for kids with colorful illustrations", "250", "90", "20", "Children book.jpg"},
                {"Travel Guide", "Books", "Complete guide to world destinations", "650", "55", "12", "Travel Guide.jpg"},
                {"Yoga Mat", "Sports", "Anti-slip yoga mat for comfortable workout", "800", "50", "25", "Yoga mat.jpg"},
                {"Dumbbells Set", "Sports", "Home workout dumbbells set with stand", "1500", "30", "20", "Dumbells sets.jpg"},
                {"Football", "Sports", "Professional football for matches and practice", "900", "40", "15", "Football.jpg"},
                {"Badminton Racket", "Sports", "Lightweight racket set with shuttlecocks", "1200", "35", "22", "Badminton Racket.jpg"},
                {"Skipping Rope", "Sports", "Fitness jump rope with counter", "300", "60", "30", "Skipping rope.jpg"},
                {"Tennis Ball Set", "Sports", "Pack of 6 professional tennis balls", "450", "70", "18", "Tennis Ball set.jpg"},
                {"Face Cream", "Beauty", "Moisturizing face cream for glowing skin", "450", "50", "20", "Face Cream.jpg"},
                {"Lipstick Set", "Beauty", "Long-lasting lipstick in 6 shades", "650", "40", "25", "Lipstick set.jpg"},
                {"Perfume", "Beauty", "Premium fragrance for men and women", "1800", "25", "18", "Perfume.jpg"},
                {"Hair Serum", "Beauty", "Nourishing hair serum for silky hair", "550", "35", "22", "Hair serum.jpg"},
                {"Makeup Kit", "Beauty", "Complete makeup set with all essentials", "2500", "20", "15", "Makeup kit.jpg"},
                {"Nail Polish Set", "Beauty", "12 vibrant colors nail polish kit", "350", "60", "22", "Nail Polish Set.jpg"}
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
                product.setImage(p[6]);
                product.setSlug(generateSlug(p[0]));
                productRepository.save(product);
            }
            System.out.println("✅ 36 Products created (6 per category)");
        }
    }
}
