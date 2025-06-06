package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminLoginDto;
import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminRepo;
import com.MainProject.MedE.Admin.AdminViewProductDTO;
import com.MainProject.MedE.Store.*;
import com.MainProject.MedE.UserRegistration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@Service
public class MedEService {
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;




    // USER


    // USER REGISTRATION

    public ResponseEntity<?> userRegistration(UserRegistrationModel userRegistrationModel) {
        UserRegistrationModel userRegistrationModel1 = new UserRegistrationModel();
        userRegistrationModel1.setName(userRegistrationModel.getName());
        userRegistrationModel1.setPassword(userRegistrationModel.getPassword());
        userRegistrationModel1.setEmail(userRegistrationModel.getEmail());
        userRegistrationModel1.setPhoneNumber(userRegistrationModel.getPhoneNumber());
        userRegistrationRepo.save(userRegistrationModel1);

        return new ResponseEntity<>(userRegistrationModel1, HttpStatus.OK);
    }

    // USER LOGIN

                        //    public ResponseEntity<?> userLogin(String email, String password) {
                        //        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findByEmailAndPassword(email,password);
                        //        if (optionalUserRegistrationModel.isPresent()){
                        //            return new ResponseEntity<>("Login Success",HttpStatus.OK);
                        //        }else{
                        //            return new ResponseEntity<>("Not found ",HttpStatus.NOT_FOUND);
                        //        }
                        //
                        //    }

    public ResponseEntity<?> userLogin(UserLoginDto userLoginDto) {
        Optional<UserRegistrationModel>userRegistrationModelOptional=userRegistrationRepo.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if(userRegistrationModelOptional.isPresent()){
            UserLoginDto userLoginDto1 = new UserLoginDto();
            userLoginDto1.setUser_id(userRegistrationModelOptional.get().getUser_id());
            userLoginDto1.setUserName(userRegistrationModelOptional.get().getName());
            return new ResponseEntity<>(userLoginDto1,HttpStatus.OK);
        }
        return new ResponseEntity<>("email and password not match",HttpStatus.NOT_FOUND);
    }

    // UPDATE PASSWORD

    public ResponseEntity<?> forgotPassword(String email, String password) {
        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findByEmail(email);
        if (optionalUserRegistrationModel.isPresent()){
            UserRegistrationModel userRegistrationModel = optionalUserRegistrationModel.get();
            userRegistrationModel.setPassword(password);

            userRegistrationRepo.save(userRegistrationModel);
            return new ResponseEntity<>("Password Updated",HttpStatus.OK);
        } else{
            return new ResponseEntity<>("User id not Found",HttpStatus.NOT_FOUND);
        }

    }

    //UPDATE EMAIL

    public ResponseEntity<?> updateEmail(Integer phoneNumber, String email) {
        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findByPhoneNumber(phoneNumber);
        if (optionalUserRegistrationModel.isPresent()){
            UserRegistrationModel userRegistrationModel = optionalUserRegistrationModel.get();
            userRegistrationModel.setEmail(email);

            userRegistrationRepo.save(userRegistrationModel);
            return new ResponseEntity<>("Email Updated",HttpStatus.OK);
        } else{
            return new ResponseEntity<>("User id not Found",HttpStatus.NOT_FOUND);
        }
    }

    // UPLOAD PRESCRIPTION  *** check store id if exist ***

    @Autowired
    private PrescriptionRepo prescriptionRepo;

    public ResponseEntity<?> uploadPrescription(PrescriptionModel prescriptionModel, MultipartFile prescriptionImage) throws IOException {
        Optional<UserRegistrationModel>userRegistrationModelOptional=userRegistrationRepo.findById(prescriptionModel.getUser_id());
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(prescriptionModel.getStoreId());
        if (storeRegistrationModelOptional.isPresent()){
            if(userRegistrationModelOptional.isPresent()) {
                PrescriptionModel prescriptionModel1 = new PrescriptionModel();
                prescriptionModel1.setUser_id(prescriptionModel.getUser_id());
                prescriptionModel1.setStoreId(prescriptionModel.getStoreId());
                prescriptionModel1.setPrescriptionImage(prescriptionImage.getBytes());

                prescriptionRepo.save(prescriptionModel1);
                return new ResponseEntity<>(prescriptionModel1, HttpStatus.OK);
            }
            return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("store not found",HttpStatus.NOT_FOUND);
    }

    //USER FETCH PRESCRIPTION
    public ResponseEntity<?> fetchUserPrescriptions(Long userId) {
        List<PrescriptionModel> prescriptions = prescriptionRepo.findByUserId(userId);
        return ResponseEntity.ok(prescriptions);
    }

    //USER DELETE PRESCRIPTION
    @Transactional
    public String deletePrescriptionByUser(Long userId, Long prescriptionId) {
        Optional<PrescriptionModel> optionalPrescription = prescriptionRepo.findById(prescriptionId.intValue());

        if (optionalPrescription.isEmpty()) {
            throw new RuntimeException("Prescription not found");
        }

        PrescriptionModel prescription = optionalPrescription.get();

        if (!(prescription.getUser_id().longValue() == userId)) {
            throw new RuntimeException("Unauthorized: Prescription does not belong to this user");
        }

        prescriptionRepo.deleteById(prescriptionId.intValue());
        return "Prescription deleted successfully";
    }

    // FETCH PROFILE
    public ResponseEntity<?> fetchProfile(Integer userId) {
        Optional<UserRegistrationModel> userOptional = userRegistrationRepo.findByUserId(userId);
        try {

            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user details");
        }
    }



    // SEARCH PRODUCT

    public ResponseEntity<?> searchProduct(String productName, Integer storeId) {


        List<ProductModel> products = productRepo.findByProductNameContainingIgnoreCaseAndStoreId(productName,storeId);
        if (products.isEmpty()){
            return new ResponseEntity<>("not found ",HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(products,HttpStatus.OK);
    }

    // SEARCH STORE

    public ResponseEntity<?> searchStore(String storeName) {
        List<StoreRegistrationModel> stores = storeRegistrationRepo.findByStoreNameContainingIgnoreCase(storeName);
        if (stores.isEmpty()){
            return new ResponseEntity<>("not found ",HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(stores,HttpStatus.OK);
    }

    // ADD FEEDBACK

    @Autowired FeedBackRepo feedBackRepo;

//    public ResponseEntity<?> addFeedBack(FeedBackModel feedBackModel) {
//
//        Optional<UserRegistrationModel>userRegistrationModelOptional=userRegistrationRepo.findById(feedBackModel.getUser_id());
//        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(feedBackModel.getStore_id());
//        if (userRegistrationModelOptional.isPresent() && storeRegistrationModelOptional.isPresent()){
//            Optional<FeedBackModel>feedBackModelOptional=feedBackRepo.findById(feedBackModel.getUser_id());
//
//            if (!feedBackModelOptional.isPresent()) {
//                FeedBackModel feedBackModel1 = new FeedBackModel();
//                feedBackModel1.setStore_id(feedBackModel.getStore_id());
//                feedBackModel1.setComment(feedBackModel.getComment());
//                feedBackModel1.setRating(feedBackModel.getRating());
//                feedBackModel1.setUser_id(feedBackModel1.getUser_id());
//                feedBackRepo.save(feedBackModel1);
//            } else{
//                return new ResponseEntity<>("feedback already added",HttpStatus.NOT_ACCEPTABLE);
//            }
//        }
//
//        return new ResponseEntity<>("Feedback Added",HttpStatus.OK);
//    }

    public ResponseEntity<?> addFeedBack(FeedBackModel feedBackModel) {
        // Check if user and store exist
        Optional<UserRegistrationModel> userOptional = userRegistrationRepo.findById(feedBackModel.getUser_id());
        Optional<StoreRegistrationModel> storeOptional = storeRegistrationRepo.findById(feedBackModel.getStore_id());

        if (userOptional.isPresent() && storeOptional.isPresent()) {
            // Check if the user already gave feedback to the same store
            Optional<FeedBackModel> existingFeedback = feedBackRepo.findByUserAndStore(
                    feedBackModel.getUser_id(),
                    feedBackModel.getStore_id()
            );

            if (existingFeedback.isPresent()) {
                return new ResponseEntity<>("Feedback already added for this store", HttpStatus.NOT_ACCEPTABLE);
            }

            // Allow feedback submission
            FeedBackModel newFeedback = new FeedBackModel();
            newFeedback.setStore_id(feedBackModel.getStore_id());
            newFeedback.setUser_id(feedBackModel.getUser_id());
            newFeedback.setComment(feedBackModel.getComment());
            newFeedback.setRating(feedBackModel.getRating());

            feedBackRepo.save(newFeedback);

            return new ResponseEntity<>("Feedback Added Successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid User or Store", HttpStatus.BAD_REQUEST);
    }


    // USER FIND NEARBY STORE

//    public ResponseEntity<?> findNearbyStores(Double latitude, Double longitude) {
//        List<StoreRegistrationModel> allStores = storeRegistrationRepo.findAll();
//        List<StoreRegistrationModel> nearbyStores = new ArrayList<>();
//
//        double fixedRadius = 5.0;
//        for(StoreRegistrationModel store: allStores){
//
//            if (store.getLatitude() == null || store.getLongitude() == null) {
//                continue;
//            }
//
//            double distance = calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude());
//
//            if (distance <= fixedRadius) {
//                nearbyStores.add(store);
//            }
//        }
//        return new ResponseEntity<>(nearbyStores,HttpStatus.OK);
//    }

    // FIND NEARBY STORE

    public ResponseEntity<?> findNearbyStores(Double latitude, Double longitude) {
        List<StoreRegistrationModel> allStores = storeRegistrationRepo.findAll();
        List<nearbyStoreDto> nearbyStores = new ArrayList<>();

        double fixedRadius = 5.0;
        for(StoreRegistrationModel store: allStores){

            if (store.getLatitude() == null || store.getLongitude() == null) {
                continue;
            }

            double distance = calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude());

            if (distance <= fixedRadius) {
                Double avgRating = feedBackRepo.findAverageRatingByStoreId(store.getStore_id());
                if (avgRating == null) {
                    avgRating = 0.0;
                }
                nearbyStoreDto dto = new nearbyStoreDto();
                dto.setAverageRating(avgRating);
                dto.setLatitude(store.getLatitude());
                dto.setLongitude(store.getLongitude());
                dto.setAddress(store.getAddress());
                dto.setStoreName(store.getStoreName());
                dto.setPhoneNumber(store.getPhone_number());
                dto.setStoreId(store.getStore_id());
                dto.setLicenseNumber(store.getLicenseNumber());

                nearbyStores.add(dto);
            }
        }
        return new ResponseEntity<>(nearbyStores,HttpStatus.OK);
    }

    // distance calculate method

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // ADD TO CART

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;


    public MedEService(ProductRepo productRepo, CartItemRepo cartItemRepo) {
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
    }

    // ADD PRODUCT TO CART (NOW USING)

    public void addProductCart(CartItemDTO cartItemDTO) {
        Long userId = cartItemDTO.getUserId();

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }

        if (!userRegistrationRepo.existsById(userId.intValue())) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        // Step 1: Get the storeId of the product being added
        ProductModel product = productRepo.findById(cartItemDTO.getProductId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist."));
        Long newProductStoreId = Long.valueOf(product.getStoreId());

        CartModel cart = cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    CartModel newCart = new CartModel();
                    newCart.setUserId(userId);
                    return cartRepo.save(newCart);
                });

        // Step 2: Check if cart contains products from a different store
        if (!cart.getCartItems().isEmpty()) {
            CartItem firstItem = cart.getCartItems().get(0); // get any item
            ProductModel existingProduct = productRepo.findById(firstItem.getProductId().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Existing product not found in cart."));
            Long existingStoreId = Long.valueOf(existingProduct.getStoreId());

            if (!existingStoreId.equals(newProductStoreId)) {
                throw new IllegalArgumentException("Cart already contains products from a different store.");
            }
        }

        // Step 3: Add or update the item
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(cartItemDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(cartItemDTO.getProductId());
            newItem.setQuantity(cartItemDTO.getQuantity());
            newItem.setCart(cart);
            cart.getCartItems().add(newItem);
        }

        cartRepo.save(cart); // cascade saves items
    }


    private String convertImageToBase64(byte[] imageData) {
        if (imageData == null || imageData.length == 0) return null;
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
    }


    // UPDATED VIEW CART (NOW USING)


    public CartResponse getCartForUser(Long userId) {
        CartModel cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found."));

        List<CartProductDTO> cartItems = new ArrayList<>();
        double totalPrice = 0;
        double totalDiscount = 0;

        for (CartItem item : cart.getCartItems()) {
            ProductModel product = productRepo.findById(item.getProductId().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found."));

            CategoryModel category = categoryRepo.findById(product.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            CartProductDTO dto = new CartProductDTO();

            dto.setItemId(item.getItemId());
            dto.setProductId(product.getProductId().longValue());
            dto.setName(product.getProductName());
            dto.setDescription(product.getProductDesc());
            dto.setCategoryId(product.getCategoryId());
            dto.setCategory(category.getCategoryName());
            dto.setActualPrice(product.getActualPrice());
            dto.setDiscountPrice(product.getDiscountPrice());
            dto.setDiscountPercentage(product.getOfferPercentage());
            dto.setQuantity(item.getQuantity());
            dto.setImageBase64(convertImageToBase64(product.getProductImage()));

            totalPrice += product.getDiscountPrice() * item.getQuantity();
            totalDiscount += (product.getActualPrice() - product.getDiscountPrice()) * item.getQuantity();

            cartItems.add(dto);
        }

        ProductModel product = productRepo.findById(cart.getCartItems().get(0).getProductId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));

        StoreRegistrationModel store = storeRegistrationRepo.findById(product.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found."));

        String storeName = store.getStoreName();  // or store.getStore_id() if you need the ID


        CartResponse response = new CartResponse();
        response.setItems(cartItems);
        response.setTotalPrice(totalPrice);
        response.setTotalDiscount(totalDiscount);
        response.setStoreName(store.getStoreName());

        return response;
    }



    // GET CART ITEM

    public ResponseEntity<?> getCart(Long userId) {
        Optional<CartModel> optionalCart = cartRepo.findByUserId(userId);

        if (!optionalCart.isPresent()) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        CartModel cart = optionalCart.get();
        List<CartItem> cartItems = cart.getCartItems();

        // Extract productIds from cart items
        List<Long> productIds = cartItems.stream()
                .map(CartItem::getProductId)
                .distinct()
                .collect(Collectors.toList());

        // Fetch all products by these IDs in one query
        List<ProductModel> products = productRepo.findAllByProductIdIn(productIds);

        // Map productId -> ProductModel for quick lookup
        Map<Long, ProductModel> productMap = products.stream()
                .collect(Collectors.toMap(p -> p.getProductId().longValue(), p -> p));

        // Build list of CartItemWithProductDTO
        List<CartItemWithProductDTO> itemsWithProduct = cartItems.stream()
                .map(item -> new CartItemWithProductDTO(item, productMap.get(item.getProductId())))
                .collect(Collectors.toList());

        CartResponseDTO responseDTO = new CartResponseDTO(cart, itemsWithProduct);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // USER DELETE CART ITEM

    public ResponseEntity<?> deleteCartProduct(Long itemId, Long userId) {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(itemId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            if (cartItem.getCart() != null && cartItem.getCart().getUserId().equals(userId)) {
                cartItemRepo.deleteById(itemId);
                return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized or mismatched user", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Cart item not found", HttpStatus.NOT_FOUND);
        }
    }

    // USER GET PRODUCT DETAILS

    public ResponseEntity<?> getProductDetails(Integer productId, Integer storeId) {
        Optional<ProductModel> productOpt = productRepo.findByProductIdAndStoreId(productId, storeId);

        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for given store");
        }

        ProductModel product = productOpt.get();
        ProductDetailsDto dto = new ProductDetailsDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setActualPrice(product.getActualPrice());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setOfferPercentage(product.getOfferPercentage());
        dto.setStockCount(product.getStock());
        dto.setProductDescription(product.getProductDesc());
        dto.setExpiryDate(product.getExpiryDate());
        dto.setProductImage(product.getProductImage());

        // 🔍 Manually fetch store name
        storeRegistrationRepo.findById(storeId).ifPresent(store -> dto.setStoreName(store.getStoreName()));

        // 🔍 Manually fetch category name using categoryId from product
        Integer categoryId = product.getCategoryId(); // Assuming you store categoryId directly
        categoryRepo.findById(categoryId).ifPresent(category -> dto.setCategoryName(category.getCategoryName()));

        return ResponseEntity.ok(dto);
    }


    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public ResponseEntity<?> checkoutCart(Long userId, CheckoutRequestDto checkoutRequest) {
        // 1. Validate user
        Optional<UserRegistrationModel> userOpt = userRegistrationRepo.findById(userId.intValue());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // 2. Fetch cart
        CartModel cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // 3. Validate store
        ProductModel firstProduct = productRepo.findById(cart.getCartItems().get(0).getProductId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Integer storeId = firstProduct.getStoreId();

        // 4. Prepare order
        OrderModel order = new OrderModel();
        order.setUserId(userId);
        order.setStoreId(storeId);
        order.setCustomerName(checkoutRequest.getCustomerName());
        order.setPhoneNumber(checkoutRequest.getPhoneNumber());
        order.setAddress(checkoutRequest.getAddress());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        order.setOrderDate(LocalDateTime.now());

        double totalPrice = 0;
        double totalDiscount = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            ProductModel product = productRepo.findById(item.getProductId().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getProductId().longValue());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getDiscountPrice());
            orderItem.setOrder(order); // link to order

            orderItems.add(orderItem);

            totalPrice += product.getDiscountPrice() * item.getQuantity();
            totalDiscount += (product.getActualPrice() - product.getDiscountPrice()) * item.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setTotalDiscount(totalDiscount);

        // 5. Save order (and cascade items)
        orderRepo.save(order);

        // 6. Clear cart
        cart.getCartItems().clear();
        cartRepo.save(cart);

        // 7. Respond with success
        return ResponseEntity.ok(Map.of(
                "message", "Order placed successfully",
                "orderId", order.getOrderId()
        ));
    }


    // VIEW USER ORDERS

    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        List<OrderModel> orders = orderRepo.findByUserId(userId);
        List<OrderResponseDto> result = new ArrayList<>();

        for (OrderModel order : orders) {
            StoreRegistrationModel store = storeRegistrationRepo.findById(order.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));

            OrderResponseDto dto = new OrderResponseDto();
            dto.setOrderId(order.getOrderId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStoreName(store.getStoreName());
            dto.setCustomerName(order.getCustomerName());
            dto.setPhoneNumber(order.getPhoneNumber());
            dto.setAddress(order.getAddress());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setTotalDiscount(order.getTotalDiscount());

            List<OrderItemDto> itemDtos = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                ProductModel product = productRepo.findById(item.getProductId().intValue())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                CategoryModel category = categoryRepo.findById(product.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                OrderItemDto itemDto = new OrderItemDto();
                itemDto.setProductId(product.getProductId().longValue());
                itemDto.setProductName(product.getProductName());
                itemDto.setDescription(product.getProductDesc());
                itemDto.setCategoryName(category.getCategoryName());
                itemDto.setPrice(item.getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setImageBase64(convertImageToBase64(product.getProductImage()));

                itemDtos.add(itemDto);
            }

            dto.setItems(itemDtos);
            result.add(dto);
        }

        return result;
    }




//    public void checkoutCart(Integer userId) {
//        CartModel cart = cartRepo.findByUserId(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        if (cart.getCartItems().isEmpty()) {
//            throw new RuntimeException("Cart is empty");
//        }
//
//        OrderModel order = new OrderModel();
//        order.setUserId(userId);
//        order.setOrderDate(LocalDateTime.now());
//
//        List<OrderItem> items = new ArrayList<>();
//        for (CartItem cartItem : cart.getCartItems()) {
//            ProductModel product = productRepo.findById(cartItem.getProductId().intValue())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            OrderItem item = new OrderItem();
//            item.setProductId(product.getProductId());
//            item.setStoreId(product.getStoreId().longValue());
//            item.setQuantity(cartItem.getQuantity());
//            item.setPrice(product.getDiscountPrice() * cartItem.getQuantity());
//            item.setOrder(order);
//
//            items.add(item);
//        }
//
//        order.setOrderItems(items);
//        orderModelRepo.save(order);
//
//        cart.getCartItems().clear();
//        cartRepo.save(cart);
//    }
//
//
//    @Autowired
//    private OrderItemRepo orderItemRepo;
//
//    public List<OrderItem> getOrdersForStore(Integer storeId) {
//        return orderItemRepo.findByStoreId(storeId.intValue());
//    }


    // ADMIN




    @Autowired
    private AdminRepo adminRepo;



    // ADMIN REGISTRATION

    public ResponseEntity<?> adminRegistration(AdminModel adminModel) {
        AdminModel adminModel1= new AdminModel();
        adminModel1.setPassword(adminModel.getPassword());
        adminModel1.setAdminUserName(adminModel.getAdminUserName());
        adminRepo.save(adminModel1);
        return new ResponseEntity<>(adminModel1,HttpStatus.OK);
    }


    // ADMIN LOGIN

                            //    public ResponseEntity<?> adminLogin(String adminUserName, String password) {
                            //        Optional<AdminModel>optionalAdminModel=adminRepo.findByAdminUserNameAndPassword(adminUserName,password);
                            //        if (optionalAdminModel.isPresent()){
                            //            return new ResponseEntity<>("Login Success",HttpStatus.OK);
                            //        }else{
                            //            return new ResponseEntity<>("User name or password not match",HttpStatus.NOT_FOUND);
                            //        }
                            //    }

    public ResponseEntity<?> adminLogin(AdminLoginDto adminLoginDto) {
        Optional<AdminModel>optionalAdminModel=adminRepo.findByAdminUserNameAndPassword(adminLoginDto.getAdminUserName(), adminLoginDto.getPassword());
        if (optionalAdminModel.isPresent()) {
            return new ResponseEntity<>("Login Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("username and password not match",HttpStatus.NOT_FOUND);
    }

    // ADMIN VIEW ALL STORES

    public ResponseEntity<List<StoreDTO>> adminViewStores() {
        List<StoreDTO> storeDTOList =new ArrayList<>();
        List<StoreRegistrationModel> storeRegistrationModelList= storeRegistrationRepo.findAll();
        if(!storeRegistrationModelList.isEmpty()){
            for(StoreRegistrationModel urm : storeRegistrationModelList){

                StoreDTO storeDTO = new StoreDTO();
                
                storeDTO.setStoreId(urm.getStore_id());
                storeDTO.setStoreName(urm.getStoreName());
                storeDTO.setLicenseNumber(urm.getLicenseNumber());
                storeDTO.setStatusId(urm.getStatus_id());
                storeDTO.setRegistrationDate(urm.getCreated_at());
                storeDTO.setStatusUpdateDate(urm.getStatusUpdate_at());
                storeDTO.setPhoneNumber(urm.getPhone_number());
                storeDTO.setStorePassword(urm.getPassword());

                Optional<StatusModel> statusModelOptional=statusRepo.findById(urm.getStatus_id());
                if(statusModelOptional.isPresent()){
                    StatusModel statusModel = statusModelOptional.get();
                    storeDTO.setStatusName(statusModel.getStatus_name());
                }
                storeDTOList.add(storeDTO);

            }
            return new ResponseEntity<>(storeDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(storeDTOList,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //ADMIN UPDATE STORE STATUS


    public ResponseEntity<?> updateStoreStatus(Integer store_id, Integer status_id) {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(store_id);
        if (storeRegistrationModelOptional.isPresent()){
            StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
            storeRegistrationModel.setStatus_id(status_id);
            storeRegistrationRepo.save(storeRegistrationModel);
            return new ResponseEntity<>(storeRegistrationModel,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
        }

    }

    // ADMIN (VIEW PRODUCTS OF SINGLE STORE)

    public ResponseEntity<?> adminViewStoreProduct(Integer storeId) {
        List<ProductModel> productModelList=productRepo.findAllByStoreId(storeId);
        if (!productModelList.isEmpty()){
            return new ResponseEntity<>(productModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
    }

    // Ascending order by actual price
    public ResponseEntity<?> getProductsSortedAsc(Integer storeId) {
        List<ProductModel> sortedList = productRepo.findByStoreIdOrderByDiscountPriceAsc(storeId);
        if (!sortedList.isEmpty()) {
            return new ResponseEntity<>(sortedList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No products found for sorting", HttpStatus.NOT_FOUND);
    }

    // Descending order by actual price
    public ResponseEntity<?> getProductsSortedDesc(Integer storeId) {
        List<ProductModel> sortedList = productRepo.findByStoreIdOrderByDiscountPriceDesc(storeId);
        if (!sortedList.isEmpty()) {
            return new ResponseEntity<>(sortedList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No products found for sorting", HttpStatus.NOT_FOUND);
    }

    // ADMIN VIEW ALL PRODUCTS WITH STORE NAME

    public ResponseEntity<List<AdminViewProductDTO>> adminViewProductsWithName() {
        List<AdminViewProductDTO> adminViewProductDTOList = new ArrayList<>();
        List<ProductModel> productModelList = productRepo.findAll();

        if(!productModelList.isEmpty()){
            for (ProductModel pdm : productModelList){
                AdminViewProductDTO adminViewProductDTO = new AdminViewProductDTO();
                adminViewProductDTO.setStoreId(pdm.getStoreId());
                adminViewProductDTO.setProductId(pdm.getProductId());
                adminViewProductDTO.setProductName(pdm.getProductName());
                adminViewProductDTO.setActualPrice(pdm.getActualPrice());
                adminViewProductDTO.setOfferPercentage(pdm.getOfferPercentage());
                adminViewProductDTO.setFinalDiscountPrice(pdm.getDiscountPrice());
                adminViewProductDTO.setStockCount(pdm.getStock());
                adminViewProductDTO.setExpiryDate(pdm.getExpiryDate());
                adminViewProductDTO.setProductDescription(pdm.getProductDesc());
                adminViewProductDTO.setProductImage(pdm.getProductImage());

                Optional<StoreRegistrationModel> storeRegistrationModelOptional = storeRegistrationRepo.findById(pdm.getStoreId());
                Optional<CategoryModel> categoryModelOptional = categoryRepo.findById(pdm.getCategoryId());

                if(storeRegistrationModelOptional.isPresent()){
                    StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
                    adminViewProductDTO.setStoreName(storeRegistrationModel.getStoreName());
                }
                if (categoryModelOptional.isPresent()){
                    CategoryModel categoryModel = categoryModelOptional.get();
                    adminViewProductDTO.setCategoryName(categoryModel.getCategoryName());
                }

                adminViewProductDTOList.add(adminViewProductDTO);
            }
            return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.NOT_FOUND);
    }

    // VIEW STORE PRODUCT

//    public ResponseEntity<List<AdminViewProductDTO>> adminViewStoreProductsWithName(Integer storeId) {
//        List<AdminViewProductDTO> adminViewProductDTOList = new ArrayList<>();
//        List<ProductModel> productModelList = productRepo.findAllByStoreId(storeId);
//
//        if(!productModelList.isEmpty()){
//            for (ProductModel pdm : productModelList){
//                AdminViewProductDTO adminViewProductDTO = new AdminViewProductDTO();
//                adminViewProductDTO.setStoreId(pdm.getStoreId());
//                adminViewProductDTO.setProductId(pdm.getProductId());
//                adminViewProductDTO.setProductName(pdm.getProductName());
//                adminViewProductDTO.setActualPrice(pdm.getActualPrice());
//                adminViewProductDTO.setOfferPercentage(pdm.getOfferPercentage());
//                adminViewProductDTO.setFinalDiscountPrice(pdm.getDiscountPrice());
//                adminViewProductDTO.setStockCount(pdm.getStock());
//                adminViewProductDTO.setExpiryDate(pdm.getExpiryDate());
//                adminViewProductDTO.setProductDescription(pdm.getProductDesc());
//                adminViewProductDTO.setProductImage(pdm.getProductImage());
//
//                Optional<StoreRegistrationModel> storeRegistrationModelOptional = storeRegistrationRepo.findById(pdm.getStoreId());
//                Optional<CategoryModel> categoryModelOptional = categoryRepo.findById(pdm.getCategoryId());
//
//                if(storeRegistrationModelOptional.isPresent()){
//                    StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
//                    adminViewProductDTO.setStoreName(storeRegistrationModel.getStoreName());
//                }
//                if (categoryModelOptional.isPresent()){
//                    CategoryModel categoryModel = categoryModelOptional.get();
//                    adminViewProductDTO.setCategoryName(categoryModel.getCategoryName());
//                }
//
//                adminViewProductDTOList.add(adminViewProductDTO);
//            }
//            return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.NOT_FOUND);
//    }



    public ResponseEntity<List<AdminViewProductDTO>> adminViewStoreProductsWithName(Integer storeId, String sort, Integer categoryId) {
        List<AdminViewProductDTO> adminViewProductDTOList = new ArrayList<>();
        List<ProductModel> productModelList = productRepo.findAllByStoreId(storeId);

        if (categoryId != null) {
            productModelList = productModelList.stream()
                    .filter(product -> product.getCategoryId().equals(categoryId))
                    .collect(Collectors.toList());
        }

        if (!productModelList.isEmpty()) {
            for (ProductModel pdm : productModelList) {
                AdminViewProductDTO adminViewProductDTO = new AdminViewProductDTO();
                adminViewProductDTO.setStoreId(pdm.getStoreId());
                adminViewProductDTO.setProductId(pdm.getProductId());
                adminViewProductDTO.setProductName(pdm.getProductName());
                adminViewProductDTO.setActualPrice(pdm.getActualPrice());
                adminViewProductDTO.setOfferPercentage(pdm.getOfferPercentage());
                adminViewProductDTO.setFinalDiscountPrice(pdm.getDiscountPrice());
                adminViewProductDTO.setStockCount(pdm.getStock());
                adminViewProductDTO.setExpiryDate(pdm.getExpiryDate());
                adminViewProductDTO.setProductDescription(pdm.getProductDesc());
                adminViewProductDTO.setProductImage(pdm.getProductImage());


                storeRegistrationRepo.findById(pdm.getStoreId()).ifPresent(store ->
                        adminViewProductDTO.setStoreName(store.getStoreName())
                );

                categoryRepo.findById(pdm.getCategoryId()).ifPresent(category ->
                        adminViewProductDTO.setCategoryName(category.getCategoryName())
                );

                adminViewProductDTOList.add(adminViewProductDTO);
            }

            // ✅ Sorting logic
            if ("asc".equalsIgnoreCase(sort)) {
                adminViewProductDTOList.sort(Comparator.comparing(AdminViewProductDTO::getFinalDiscountPrice));
            } else if ("desc".equalsIgnoreCase(sort)) {
                adminViewProductDTOList.sort(Comparator.comparing(AdminViewProductDTO::getFinalDiscountPrice).reversed());
            }

            return new ResponseEntity<>(adminViewProductDTOList, HttpStatus.OK);
        }

        return new ResponseEntity<>(adminViewProductDTOList, HttpStatus.NOT_FOUND);
    }



    // ADMIN CREATE CATEGORY

    @Autowired
    private CategoryRepo categoryRepo;

    public ResponseEntity<?> createCategory(CategoryModel categoryModel) {
        System.out.println("Received CategoryModel: " + categoryModel);
        System.out.println("Category Name: " + categoryModel.getCategoryName());

        CategoryModel categoryModel1 = new CategoryModel();
        categoryModel1.setCategoryName(categoryModel.getCategoryName());

        categoryRepo.save(categoryModel1);
        System.out.println("Saved CategoryModel: " + categoryModel1);
        return new ResponseEntity<>(categoryModel1,HttpStatus.CREATED);
    }

    // ADMIN ADD CATEGORY

    public ResponseEntity<?> addCategory(CategoryModel categoryModel) {
        CategoryModel categoryModel1 = new CategoryModel();
        categoryModel1.setCategoryName(categoryModel.getCategoryName());
        categoryRepo.save(categoryModel1);
        return new ResponseEntity<>("Category Added Successfully",HttpStatus.CREATED);
    }

    // ADMIN FETCH ALL ADS

    public ResponseEntity<?> adminFetchAds() {
        List<AdsModel> adsModelList=adsRepo.findAll();
        if (!adsModelList.isEmpty()){
            return new ResponseEntity<>(adsModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> fetchPendingStores() {
        List<StoreRegistrationModel> pendingStores = storeRegistrationRepo.findByStatusId(1);
        return new ResponseEntity<>(pendingStores, HttpStatus.OK);
    }

    //ADMIN FETCH FEEDBACK
    public ResponseEntity<?> fetchAllFeedback() {
        List<FeedBackModel> feedbackList = feedBackRepo.findAll();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }





    // STORE



    @Autowired
    private StatusRepo statusRepo;

    // ADD STATUS

    public ResponseEntity<?> addStatus(StatusModel statusModel) {
        StatusModel statusModel1= new StatusModel();
        statusModel1.setStatus_name(statusModel.getStatus_name());
        statusRepo.save(statusModel1);
        return new ResponseEntity<>("Add Status Success ",HttpStatus.OK);
    }


    @Autowired
    private StoreRegistrationRepo storeRegistrationRepo;


    // STORE REGISTRATION


    public ResponseEntity<?> storeRegistration(StoreRegistrationModel storeRegistrationModel, MultipartFile licenseImage) throws IOException {
        StoreRegistrationModel storeRegistrationModel1 = new StoreRegistrationModel();
        storeRegistrationModel1.setStoreName(storeRegistrationModel.getStoreName());
        storeRegistrationModel1.setLicenseNumber(storeRegistrationModel.getLicenseNumber());
        storeRegistrationModel1.setPhone_number(storeRegistrationModel.getPhone_number());

        storeRegistrationModel1.setPassword(storeRegistrationModel.getPassword());
        storeRegistrationModel1.setCreated_at(LocalDate.now());
        // file upload(multipart)
        storeRegistrationModel1.setLicense_image(licenseImage.getBytes());

        storeRegistrationRepo.save(storeRegistrationModel1);
        return new ResponseEntity<>(storeRegistrationModel1,HttpStatus.OK);


    }

    // STORE LOGIN


//    public ResponseEntity<?> storeLogin(String licenseNumber, String password) {
//        Optional<StoreRegistrationModel>optionalStoreRegistrationModel=storeRegistrationRepo.findByLicenseNumberAndPassword(licenseNumber,password);
//        if (optionalStoreRegistrationModel.isPresent()){
//            return new ResponseEntity<>("Login Success",HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("License Number or password not match",HttpStatus.NOT_FOUND);
//        }
//    }


    public ResponseEntity<?> storeLogin(StoreLoginDto storeLoginDto) {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findByLicenseNumberAndPassword(storeLoginDto.getLicenseNumber(),storeLoginDto.getPassword());

        if(storeRegistrationModelOptional.isPresent()){
            StoreLoginDto storeLoginDto1 = new StoreLoginDto();
//            storeLoginDto1.setLicenseNumber(storeRegistrationModelOptional.get().getLicenseNumber());
            storeLoginDto1.setStore_id(storeRegistrationModelOptional.get().getStore_id());
            return new ResponseEntity<>(storeLoginDto1,HttpStatus.OK);
        }
        return new ResponseEntity<>("licnseNumber and password not match",HttpStatus.NOT_FOUND);
    }



    // STORE ADD PRODUCT *** check store id if exist & add exp date***

    @Autowired
    private ProductRepo productRepo;


    public ResponseEntity<?> addProduct(ProductModel productModel, MultipartFile productImage) throws IOException {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(productModel.getStoreId());
        if(storeRegistrationModelOptional.isPresent()) {

            ProductModel productModel1 = new ProductModel();

            productModel1.setStoreId(productModel.getStoreId());
            productModel1.setProductName(productModel.getProductName());
            productModel1.setProductDesc(productModel.getProductDesc());
            productModel1.setProductImage(productImage.getBytes());

            productModel1.setCategoryId(productModel.getCategoryId());
            productModel1.setExpiryDate(productModel.getExpiryDate());
            productModel1.setStock(productModel.getStock());
            productModel1.setActualPrice(productModel.getActualPrice());
            productModel1.setOfferPercentage(productModel.getOfferPercentage());
            productModel1.calculateDiscountPrice();

            productRepo.save(productModel1);

            return new ResponseEntity<>(productModel1, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }

    // STORE PRODUCT UPDATE *** check storeid if exist ***

    public ResponseEntity<?> productUpdate(Integer productId, Integer stock, double actualPrice, Integer offerPercentage, Integer store_id) {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(store_id);
        if(storeRegistrationModelOptional.isPresent()){

            Optional<ProductModel>productModelOptional=productRepo.findById(productId);
            if (productModelOptional.isPresent()){
                ProductModel productModel = productModelOptional.get();
                productModel.setStock(stock);
                productModel.setActualPrice(actualPrice);
                productModel.setOfferPercentage(offerPercentage);
                productModel.calculateDiscountPrice();
                productRepo.save(productModel);
                return new ResponseEntity<>(productModel,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(" ProductId Not Found",HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("store Id not found",HttpStatus.NOT_FOUND);

    }

    // STORE VIEW ALL PRODUCTS

    public ResponseEntity<?> storeViewAllProduct(Integer storeId) {
        List<ProductModel> productModelList=productRepo.findAllByStoreId(storeId);
        if (!productModelList.isEmpty()){
            return new ResponseEntity<>(productModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
    }

    // STORE DELETE PRODUCT

//    public ResponseEntity<?> deleteProduct(Integer productId) {
//        Optional<ProductModel> productModelOptional = productRepo.findById(productId);
//
//        if (productModelOptional.isPresent()){
//            ProductModel product = productModelOptional.get();
//            productRepo.delete(product);
//            return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);
//        }
//        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
//    }

    @Transactional
    public void deleteProductById(Integer productId) {
        // 1. Check if product exists
        ProductModel product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // 2. Find all cart items referencing the product
        List<CartItem> cartItems = cartItemRepo.findByProductId(productId.longValue());

        // 3. Delete all those cart items
        if (!cartItems.isEmpty()) {
            cartItemRepo.deleteAll(cartItems);
        }

        // 4. Delete the product
        productRepo.delete(product);
    }

    // STORE SEARCH PRODUCT

    public ResponseEntity<?> searchStoreProduct(Integer storeId, String productName) {
        Optional<StoreRegistrationModel> storeModelOptional = storeRegistrationRepo.findById(storeId);

        if (storeModelOptional.isPresent()){
            List<ProductModel> products = productRepo.findByStoreIdAndProductNameContainingIgnoreCase(storeId,productName);
            if (products.isEmpty()){
                return new ResponseEntity<>("not found ",HttpStatus.NOT_FOUND);
            }return new ResponseEntity<>(products,HttpStatus.OK);
        }
        return new ResponseEntity<>("store not found",HttpStatus.NOT_FOUND);
    }

    // STORE LOAD CATEGORIES

    public ResponseEntity<?> loadCategories() {
        List<CategoryModel> categoryModelOptional = categoryRepo.findAll();
        if (!categoryModelOptional.isEmpty()){
            return new ResponseEntity<>(categoryModelOptional,HttpStatus.OK);
        }
        return new ResponseEntity<>("no category",HttpStatus.NOT_FOUND);
    }


    public byte[] getProductImageById(Integer productId) {

            Optional<ProductModel> product = productRepo.findById(productId);

            return product.map(ProductModel::getProductImage).orElse(null);
        }

//  STORE FETCH FEEDBACK

    public ResponseEntity<?> fetchFeedback(Integer store_id) {
        List<FeedBackModel> feedbackList = feedBackRepo.findByStore(store_id);
        if (!feedbackList.isEmpty()) {
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No feedback found for this store", HttpStatus.NOT_FOUND);
    }



    // STORE FETCH PRESCRIPTION

//    public ResponseEntity<?> fetchPrescription(Integer storeId) {
//        Optional<PrescriptionModel> prescriptionModelList = prescriptionRepo.findById(storeId);
//        if (!prescriptionModelList.isEmpty()){
//            return new ResponseEntity<>(prescriptionModelList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>("no Prescription found for this store",HttpStatus.NOT_FOUND);
//    }

    public ResponseEntity<List<PrescriptionDTO>> fetchPrescription(Integer storeId) {
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        List<PrescriptionModel> prescriptionModelList = prescriptionRepo.findByStoreId(storeId);
        for (PrescriptionModel prescription : prescriptionModelList) {
            Optional<UserRegistrationModel> userOptional = userRegistrationRepo.findById(prescription.getUser_id());

            if (userOptional.isPresent()) {
                UserRegistrationModel user = userOptional.get();

                PrescriptionDTO dto = new PrescriptionDTO();
                dto.setUserId(user.getUser_id());
                dto.setUserName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setPhoneNumber(user.getPhoneNumber());
                dto.setPrescriptionImage(prescription.getPrescriptionImage());
                dto.setPrescriptionId(prescription.getPrescriptionId());
                prescriptionDTOList.add(dto);
            }

        }
        if (prescriptionDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prescriptionDTOList, HttpStatus.OK);
    }

    // STORE ADD ADVERTISEMENT

    @Autowired
    private AdsRepo adsRepo;

    public ResponseEntity<?> addAds(AdsModel adsModel) {
        Optional<StoreRegistrationModel>adsModelOptional=storeRegistrationRepo.findById(adsModel.getStoreId());

        if(adsModelOptional.isPresent()) {
            AdsModel adsModel1 = new AdsModel();

            adsModel1.setOfferName(adsModel.getOfferName());
            adsModel1.setStoreId(adsModel.getStoreId());
            adsModel1.setOfferPercentage(adsModel.getOfferPercentage());
            adsModel1.setStartDate(adsModel.getStartDate());
            adsModel1.setEndDate(adsModel.getEndDate());
            adsModel1.setConditions(adsModel.getConditions());

            adsRepo.save(adsModel1);

            return new ResponseEntity<>(adsModel1, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }

    // STORE FETCH PROFILE

    public ResponseEntity<List<StoreDTO>> storeProfile(Integer storeId) {

        Optional<StoreRegistrationModel> storeRegistrationModelList= storeRegistrationRepo.findById(storeId);
        List<StoreDTO> storeDTOList = new ArrayList<>();
        if(storeRegistrationModelList.isPresent()){
                StoreRegistrationModel urm = storeRegistrationModelList.get();;

                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setStoreId(urm.getStore_id());
                storeDTO.setStoreName(urm.getStoreName());
                storeDTO.setLicenseNumber(urm.getLicenseNumber());
                storeDTO.setStatusId(urm.getStatus_id());
                storeDTO.setRegistrationDate(urm.getCreated_at());
                storeDTO.setStatusUpdateDate(urm.getStatusUpdate_at());
                storeDTO.setPhoneNumber(urm.getPhone_number());
                storeDTO.setStorePassword(urm.getPassword());
                storeDTO.setAddress(urm.getAddress());

                Optional<StatusModel> statusModelOptional=statusRepo.findById(urm.getStatus_id());
                if(statusModelOptional.isPresent()){
                    StatusModel statusModel = statusModelOptional.get();
                    storeDTO.setStatusName(statusModel.getStatus_name());
                }
                storeDTOList.add(storeDTO);


            return new ResponseEntity<>(storeDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(storeDTOList,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE UPDATE PROFILE

    public ResponseEntity<?> updateProfile(Integer storeId, String storeName, String password, Long phoneNumber) {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(storeId);
        if (storeRegistrationModelOptional.isPresent()){
            StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
            storeRegistrationModel.setStoreName(storeName);
            storeRegistrationModel.setPassword(password);
            storeRegistrationModel.setPhone_number(phoneNumber);

            storeRegistrationRepo.save(storeRegistrationModel);
            return new ResponseEntity<>(storeRegistrationModel,HttpStatus.OK);
        }
        return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);
    }

    // STORE FETCH ADS

    public ResponseEntity<?> fetchAds(Integer storeId) {
        List<AdsModel> adsModelList=adsRepo.findAllByStoreId(storeId);
        if (!adsModelList.isEmpty()){
            return new ResponseEntity<>(adsModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
    }



    // STORE DELETE ADS

    public ResponseEntity<?> deleteAds(Integer adsId) {
        Optional<AdsModel>optionalAdsModel=adsRepo.findById(adsId);
        if (optionalAdsModel.isPresent()){
            AdsModel adsModel = optionalAdsModel.get();
            adsRepo.delete(adsModel);
            return new ResponseEntity<>("Delete success",HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }

    // STORE REJECT PRESCRIPTION

//    public ResponseEntity<?> rejectPrescription(Integer prescriptionId) {
//        Optional<PrescriptionModel>optionalPrescriptionModel=prescriptionRepo.findById(prescriptionId);
//        if(optionalPrescriptionModel.isPresent()){
//            PrescriptionModel prescriptionModel = optionalPrescriptionModel.get();
//            prescriptionRepo.delete(prescriptionModel);
//            return new ResponseEntity<>("prescription Rejected",HttpStatus.OK);
//        }
//        return new ResponseEntity<>("prescription not found",HttpStatus.NOT_FOUND);
//    }

@Autowired
private SmsService smsService;  // To send SMS notifications

    // Reject Prescription and send SMS to user

    @Transactional
    public ResponseEntity<?> rejectPrescription(Integer prescriptionId, String rejectionReason, Integer storeId) {
        // Find the prescription by its ID
        Optional<StoreRegistrationModel> storeRegistrationModel = storeRegistrationRepo.findById(storeId);
        if (storeRegistrationModel.isPresent()) {
            PrescriptionModel prescription = prescriptionRepo.findById(prescriptionId)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));

            // Update the prescription status and set the rejection reason
            prescription.setStatus("Rejected");
            prescription.setRejectionReason(rejectionReason);

            // Save the updated prescription
            PrescriptionModel updatedPrescription = prescriptionRepo.save(prescription);

            // Send an SMS to the user informing them of the rejection

            String message = "Dear user, your prescription has been rejected due to: " + rejectionReason + ".";
            smsService.sendSms("+919567954754", message);  // for India

            return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
        }
        return new ResponseEntity<>("no prescription for store",HttpStatus.NOT_FOUND);
    }

    // ACCEPT PRESCRIPTION

    public ResponseEntity<?> acceptPrescription(Integer prescriptionId, Integer storeId) {
        Optional<StoreRegistrationModel> storeRegistrationModel = storeRegistrationRepo.findById(storeId);
        if (storeRegistrationModel.isPresent()) {
            PrescriptionModel prescription = prescriptionRepo.findById(prescriptionId)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));
            prescription.setStatus("Accepted");
            PrescriptionModel updatedPrescription = prescriptionRepo.save(prescription);
            String message = "Dear user, your prescription is Accepted ";
            smsService.sendSms("+919567954754", message);  // for India

            return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
        }
        return new ResponseEntity<>("no prescription for store",HttpStatus.NOT_FOUND);
    }

    // STORE ADD LOCATION ( UPDATE STORE TABLE )

    public ResponseEntity<?> addStoreLocation(Integer storeId, Double longitude, Double latitude, String address) {
        Optional<StoreRegistrationModel> storeOptional = storeRegistrationRepo.findById(storeId);

        if (storeOptional.isPresent()){
            StoreRegistrationModel store = storeOptional.get();
            store.setLatitude(latitude);
            store.setLongitude(longitude);
            store.setAddress(address);
            storeRegistrationRepo.save(store);
            return new ResponseEntity<>("Location Added successfully ",HttpStatus.OK);
        }
        return new ResponseEntity<>("location Adding Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // STORE VIEW ORDERS

    public List<OrderResponseDto> getOrdersByStore(Long storeId) {
        List<OrderModel> orders = orderRepo.findByStoreId(storeId);
        List<OrderResponseDto> result = new ArrayList<>();

        for (OrderModel order : orders) {
            OrderResponseDto dto = new OrderResponseDto();
            dto.setOrderId(order.getOrderId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStoreName(storeRegistrationRepo.findById(storeId.intValue()).map(StoreRegistrationModel::getStoreName).orElse("Unknown Store"));
            dto.setCustomerName(order.getCustomerName());
            dto.setPhoneNumber(order.getPhoneNumber());
            dto.setAddress(order.getAddress());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setTotalDiscount(order.getTotalDiscount());

            List<OrderItemDto> itemDtos = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                ProductModel product = productRepo.findById(item.getProductId().intValue())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                CategoryModel category = categoryRepo.findById(product.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                OrderItemDto itemDto = new OrderItemDto();
                itemDto.setProductId(product.getProductId().longValue());
                itemDto.setProductName(product.getProductName());
                itemDto.setDescription(product.getProductDesc());
                itemDto.setCategoryName(category.getCategoryName());
                itemDto.setPrice(item.getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setImageBase64(convertImageToBase64(product.getProductImage()));

                itemDtos.add(itemDto);
            }

            dto.setItems(itemDtos);
            result.add(dto);
        }

        return result;
    }



}