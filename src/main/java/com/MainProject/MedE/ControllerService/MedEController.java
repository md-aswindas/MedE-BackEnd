package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminLoginDto;
import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminViewProductDTO;
import com.MainProject.MedE.Store.*;

import com.MainProject.MedE.UserRegistration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/MedE")
public class MedEController {

    @Autowired
    private MedEService medEService;
//    private static final Logger logger = LoggerFactory.getLogger(MedEController.class);





    // U S E R




    // USER REGISTRATION

    @PostMapping(path = "User/userRegistration")
    public ResponseEntity<?> userRegistrationMethod(@RequestBody UserRegistrationModel userRegistrationModel){
        try{
            return medEService.userRegistration(userRegistrationModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Registration Failed !", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // USER LOGIN

                        //    @PostMapping(path = "User/userLogin")
                        //    public ResponseEntity<?> userLoginMethod(@RequestParam String email, @RequestParam String password){
                        //        try{
                        //            return medEService.userLogin(email,password);
                        //
                        //        } catch (Exception e) {
                        //            e.printStackTrace();
                        //        }
                        //        return new ResponseEntity<>("Login Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
                        //    }
                        //


    @PostMapping(path = "User/userLogin")
    public ResponseEntity<?>userLoginMethod(@RequestBody  UserLoginDto userLoginDto){

            return medEService.userLogin(userLoginDto);

    }

    // UPDATE PASSWORD ( FORGOT PASSWORD )

    @PutMapping(path = "User/forgotPassword")
    public ResponseEntity<?> forgotPasswordMethod(@RequestParam String email, @RequestParam String password){
        try{
        return medEService.forgotPassword(email,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went Wrong !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UPDATE EMAIL **MODIFY TO DISPLAY EMAIL**

    @PutMapping(path = "User/updateEmail")
    public ResponseEntity<?> updateEmailMethod(@RequestParam String phoneNumber, @RequestParam String email){
        try{
            return medEService.updateEmail(phoneNumber,email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went Wrong !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // USER UPLOAD PRESCRIPTION

    @PostMapping(path = "User/uploadPrescription")
    public ResponseEntity<?> uploadPrescriptionMethod(@RequestPart PrescriptionModel prescriptionModel, @RequestPart MultipartFile prescriptionImage){
        try{
            return medEService.uploadPrescription(prescriptionModel,prescriptionImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("upload failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // USER FETCH PRESCRIPTION

    @GetMapping("User/fetchPrescriptions")
    public ResponseEntity<?> fetchPrescriptions(@RequestParam Long userId) {
        try {
            return medEService.fetchUserPrescriptions(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch prescriptions");
        }
    }

    // USER DELETE PRESCRIPTION
    @DeleteMapping("User/deletePrescription")
    public ResponseEntity<?> deletePrescription(@RequestParam Long userId, @RequestParam Long prescriptionId) {
        try {
            String result = medEService.deletePrescriptionByUser(userId, prescriptionId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete prescription");
        }
    }

    // USER FETCH PROFILE

    @GetMapping(path = "User/profile")
    public ResponseEntity<?> profile( @RequestParam Integer userId){
        return medEService.fetchProfile(userId);
    }

    // USER SEARCH PRODUCT

    @GetMapping(path = "User/searchProduct")
   public ResponseEntity<?> searchProduct(@RequestParam String productName, @RequestParam Integer storeId){
        return medEService.searchProduct(productName,storeId);
    }

    // USER SEARCH STORE
    @GetMapping(path = "User/searchStore")
    public ResponseEntity<?> searchStore(@RequestParam String storeName){
        return medEService.searchStore(storeName);
    }

    // USER ADD FEEDBACK

    @PostMapping(path = "User/addFeedBack")
    public ResponseEntity<?> addFeedBack(@RequestBody FeedBackModel feedBackModel){
        return medEService.addFeedBack(feedBackModel);
    }

    // USER FIND NEARBY STORE

    @GetMapping("User/findNearbyStores")
    public ResponseEntity<?> findNearbyStores(@RequestParam Double latitude,
                                              @RequestParam Double longitude) {
        try {
            return medEService.findNearbyStores(latitude,longitude);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // USER ADD PRODUCT TO CART

    @PostMapping("User/addCart")
    public ResponseEntity<?> addProductCart(@RequestBody CartItemDTO cartItemDTO) {
        try {
            medEService.addProductCart(cartItemDTO);
            return new ResponseEntity<>("Product added to cart successfully", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("User/cart")
    public ResponseEntity<CartResponse> getUserCart(@RequestParam Long userId) {
        return ResponseEntity.ok(medEService.getCartForUser(userId));
    }


    // USER GET CART ITEMS

    @GetMapping("User/getCart")
    public ResponseEntity<?> getCart(@RequestParam Long userId) {
        try {
            return medEService.getCart(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // USER DELETE CART ITEM

    @DeleteMapping(path = "User/deleteCartProduct")
    public ResponseEntity<?> deleteCartProductMethod(@RequestParam Long itemId, @RequestParam Long userId) {
        try {
            return medEService.deleteCartProduct(itemId,userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Cart item delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // USER GET PRODUCT DETAILS

    @GetMapping(path = "User/getProductDetails")
    public ResponseEntity<?> getProductById(@RequestParam Integer productId, @RequestParam Integer storeId) {
        return  medEService.getProductDetails(productId,storeId);
    }

    // USER CHECK OUT

    @PostMapping("User/checkoutCart")
    public ResponseEntity<?> checkoutCart(@RequestParam Long userId,
                                          @RequestBody CheckoutRequestDto checkoutRequestDto) {
        try {
            return medEService.checkoutCart(userId, checkoutRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong during checkout.");
        }
    }


    // USER VIEW ORDERS

    @GetMapping("User/orders")
    public ResponseEntity<?> getUserOrders(@RequestParam Long userId) {
        try {
            List<OrderResponseDto> orders = medEService.getOrdersByUser(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch orders");
        }
    }


    // A D M I N



    // ADMIN REGISTRATION

    @PostMapping(path = "Admin/adminRegistration")
    public ResponseEntity<?>adminRegistrationMethod(@RequestBody AdminModel adminModel){
        try{
            return medEService.adminRegistration(adminModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // ADMIN LOGIN

                                        //    @PostMapping(path = "Admin/adminLogin")
                                        //    public ResponseEntity<?>adminLoginMethod(@RequestParam String adminUserName,@RequestParam String password){
                                        //        try{
                                        //            return medEService.adminLogin(adminUserName,password);
                                        //        } catch (Exception e) {
                                        //            e.printStackTrace();
                                        //        }
                                        //        return new ResponseEntity<>("Something Went Wrong ",HttpStatus.INTERNAL_SERVER_ERROR);
                                        //    }

    @PostMapping(path = "Admin/adminLogin")
    public ResponseEntity<?>adminLoginMethod(@RequestBody AdminLoginDto adminLoginDto){

        return medEService.adminLogin(adminLoginDto);

    }

    // ADMIN VIEW STORES

    @PostMapping(path = "Admin/adminViewStore")
    public ResponseEntity<List<StoreDTO>>adminViewStoreMethod(){
        return medEService.adminViewStores();
    }

    // ADMIN (STORE STATUS UPDATE)

    @PutMapping(path = "Admin/adminUpdateStoreStatus")
    public ResponseEntity<?>updateStoreStatusMethod(@RequestParam Integer store_id,@RequestParam Integer status_id){
        try{
            return medEService.updateStoreStatus(store_id,status_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Update Failed !",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // ADMIN (VIEW PRODUCTS OF SINGLE STORE)


//    @GetMapping(path = "Admin/AdminViewStoreProducts")
//    public ResponseEntity<?>adminViewStoreProductsMethod(@RequestParam Integer storeId){
//        try{
//            return medEService.adminViewStoreProduct(storeId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @GetMapping(path = "Admin/AdminViewStoreProducts")
    public ResponseEntity<?>adminViewStoreProductsMethod(@RequestParam Integer storeId, @RequestParam(required = false) String sort){
        try {
            if ("asc".equalsIgnoreCase(sort)) {
                return medEService.getProductsSortedAsc(storeId); // ✅ Return result
            } else if ("desc".equalsIgnoreCase(sort)) {
                return medEService.getProductsSortedDesc(storeId); // ✅ Return result
            } else {
                return medEService.adminViewStoreProduct(storeId); // ✅ Return result
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ADMIN ( VIEW PRODUCTS OF ALL STORES WITH STORE NAME )

    @GetMapping(path = "Admin/adminViewAllProduct")
    public ResponseEntity<List<AdminViewProductDTO>>adminViewProductDTOMethod(){
        return medEService.adminViewProductsWithName();
    }

    // EACH STORE PRODUCT (fetch  store products : )

//    @GetMapping(path = "Admin/adminViewStoreProduct")
//    public ResponseEntity<List<AdminViewProductDTO>>adminViewStoreProductDTOMethod(@RequestParam Integer storeId){
//        return medEService.adminViewStoreProductsWithName( storeId);
//    }

    @GetMapping("/Admin/adminViewStoreProduct")
    public ResponseEntity<?> adminViewStoreProductDTOMethod(@RequestParam Integer storeId,
                                                          @RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) Integer categoryId) {
        try {
            return medEService.adminViewStoreProductsWithName(storeId, sort, categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE CATEGORY

    @PostMapping(path = "Admin/adminCreateCategory")
    public ResponseEntity<?>createCategoryMethod(@RequestBody CategoryModel categoryModel){
        try{
            return medEService.createCategory(categoryModel);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ADD CATEGORY

    @PostMapping(path = "Admin/adminAddCategory")
    public ResponseEntity<?>addCategoryMethod(@RequestBody CategoryModel categoryModel){
        try{
            return medEService.addCategory(categoryModel);
        } catch (Exception e) {
            e.printStackTrace();
        } return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ADMIN FETCH ALL ADS

    @GetMapping(path= "Admin/fetchAds")
    public ResponseEntity<?>adminFetchAds(){
        try{
            return medEService.adminFetchAds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //ADMIN FETCH NEW STORE
    @GetMapping(path = "Admin/fetchPendingStores")
    public ResponseEntity<?> fetchPendingStores() {
        try {
            return medEService.fetchPendingStores();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ADMIN FETCH FEEDBACK

    @GetMapping(path = "Admin/fetchFeedback")
    public ResponseEntity<?> fetchFeedback() {
        try {
            return medEService.fetchAllFeedback();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //STORE FULL DETAIL

    @GetMapping("Admin/findTopStores")
    public ResponseEntity<?> findTopStores() {
        try {
            return medEService.findTopStores();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // STORE




    // STATUS TABLE ADD DATA

    @PostMapping(path = "/addStatus")
    public ResponseEntity<?>addStatusMethod(@RequestBody StatusModel statusModel){
        try{
            return medEService.addStatus(statusModel);
        } catch (Exception e) {
            e.printStackTrace();
        } return new ResponseEntity<>("Something Went Wrong ",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // STORE REGISTRATION

    @PostMapping(path = "Store/storeRegistration")
    public ResponseEntity<?>storeRegistrationMethod(@RequestPart StoreRegistrationModel storeRegistrationModel,
                                                    @RequestPart MultipartFile licenseImage){
        try{
            return medEService.storeRegistration(storeRegistrationModel,licenseImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Store Registration Failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // STORE LOGIN

//    @PostMapping(path = "Store/storeLogin")
//    public ResponseEntity<?> StoreLoginMethod(@RequestParam String licenseNumber, @RequestParam String password){
//        try{
//            return medEService.storeLogin(licenseNumber,password);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("Login Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @PostMapping(path = "Store/storeLogin")
    public ResponseEntity<?>storeLoginMethod(@RequestBody StoreLoginDto storeLoginDto){

        return medEService.storeLogin(storeLoginDto);

    }

    // STORE PRODUCT ADDING

    @PostMapping(path = "Store/addProduct")
    public ResponseEntity<?>addProductMethod(@RequestPart ProductModel productModel,
                                                    @RequestPart MultipartFile productImage){
        try{
            return medEService.addProduct(productModel,productImage);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Product Add Failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE UPDATE PRODUCT( STOCK , ACTUAL PRICE , OFFER PERCENTAGE ) ***create dto class and use @RequestBody***

    @PutMapping(path = "Store/updateProduct")
    public ResponseEntity<?>updateProductMethod(@RequestParam Integer productId,
                                          @RequestParam Integer stock,
                                          @RequestParam double actualPrice,
                                          @RequestParam Integer offerPercentage,@RequestParam Integer store_id){
        try{
            return medEService.productUpdate(productId,stock,actualPrice,offerPercentage,store_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Update Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  STORE DELETE PRODUCT
//    @DeleteMapping(path = "Store/deleteProduct")
//    public ResponseEntity<?>deleteProductMethod(@RequestParam Integer productId){
//        try{
//            return medEService.deleteProduct(productId);
//        } catch ( Exception e ){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("delete failed ",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

        @DeleteMapping(path = "Store/deleteProduct")
    public ResponseEntity<?>deleteProductMethod(@RequestParam Integer productId){
        try{
            medEService.deleteProductById(productId);
        } catch ( Exception e ){
            e.printStackTrace();
            return new ResponseEntity<>("delete failed ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

            return null;
        }

    // STORE VIEW ALL PRODUCTS

    @GetMapping(path = "Store/StoreViewAllProducts")
    public ResponseEntity<?>storeViewAllProductsMethod(@RequestParam Integer storeId){
        try{
            return medEService.storeViewAllProduct(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // STORE SEARCH PRODUCT

    @GetMapping(path = "Store/searchStoreProduct")
    public ResponseEntity<?>searchStoreProductMethod(@RequestParam Integer store_id,
                                                     @RequestParam String productName){
        try{
            return medEService.searchStoreProduct(store_id,productName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("search failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE LOADCATEGORIES

    @GetMapping(path = "Store/loadCategories")
    public ResponseEntity<?>loadCategoriesMethod(){
        return medEService.loadCategories();
    }

    // STORE LOAD PRODUCT

//    @GetMapping(path = "Store/loadProducts")
//    public ResponseEntity<?>loadProductsMethod(@RequestParam Integer productId){
//        try{
//            return medEService.loadProducts(productId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("load failed",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // FETCH IMAGE

    @GetMapping(path = "Store/fetchImage")
    public ResponseEntity<byte[]> fetchImageMethod(@RequestParam Integer productId){
        byte[] imageData = medEService.getProductImageById(productId);

        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(imageData);
    }

    // STORE FETCH FEEDBACK

    @GetMapping(path = "Store/fetchFeedBack")
    public ResponseEntity<?> fetchFeedBack(@RequestParam Integer Store_id){
        return medEService.fetchFeedback(Store_id);
    }

    // FETCH PRESCRIPTIONS

//    @GetMapping(path = "Store/fetchPrescription")
//    public ResponseEntity<?> fetchPrescription(@RequestParam Integer storeId){
//        return medEService.fetchPrescription(storeId);
//    }

    @GetMapping(path = "Store/fetchPrescription")
    public ResponseEntity<List<PrescriptionDTO>> fetchPrescription(@RequestParam Integer storeId){
        return medEService.fetchPrescription(storeId);
    }

    // STORE ADD ADVERTISEMENT

    @PostMapping(path = "Store/addAds")
    public ResponseEntity<?> addAds(@RequestBody AdsModel adsModel){
        try {
            return medEService.addAds(adsModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE FETCH PROFILE

    @GetMapping(path = "Store/profile")
    public ResponseEntity<List<StoreDTO>>storeProfile(@RequestParam Integer Store_id){
        return medEService.storeProfile(Store_id);
    }

    // STORE UPDATE PROFILE

    @PutMapping(path = "Store/updateProfile")
    public ResponseEntity<?>updateProfile(@RequestParam Integer store_id,
                                          @RequestParam String store_name,
                                          @RequestParam String password,
                                          @RequestParam String phone_number){
        return medEService.updateProfile(store_id,store_name,password,phone_number);
    }

    // STORE FETCH ADS

    @GetMapping(path = "Store/fetchAds")
    public ResponseEntity<?>fetchAds(@RequestParam Integer store_id){
        try{
            return medEService.fetchAds(store_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // STORE DELETE ADS

    @DeleteMapping(path = "Store/deleteAds")
    public ResponseEntity<?>deleteads(@RequestParam Integer adsId){
        try{
            return medEService.deleteAds(adsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PRESCRIPTION ACCEPT AND REJECT WITH MESSAGE
    @Autowired
    private SmsService smsService;

    // REJECT PRESCRIPTION

    @PutMapping("Store/rejectPrescription")
    public ResponseEntity<?> rejectPrescription(@RequestParam Integer prescriptionId, @RequestParam String rejectionReason, @RequestParam Integer storeId) {
        try{
            return medEService.rejectPrescription(prescriptionId, rejectionReason, storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ACCEPT PRESCRIPTION

    @PutMapping("Store/acceptPrescription")
    public ResponseEntity<?> acceptPrescription(@RequestParam Integer prescriptionId, @RequestParam Integer storeId) {
        try{
            return medEService.acceptPrescription(prescriptionId,storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE LOCATION ADDING ( UPDATE STORE TABLE )

    @PutMapping(path = "Store/AddStoreLocation")
    public ResponseEntity<?>addStoreLocationMethod(@RequestParam Integer store_Id,
                                                   @RequestParam Double longitude,
                                                   @RequestParam Double latitude,
                                                   @RequestParam String address){
        try{
            return medEService.addStoreLocation(store_Id,longitude,latitude,address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE VIEW ORDERS
    @GetMapping("Store/orders")
    public ResponseEntity<?> getOrdersByStore(@RequestParam Long storeId) {
        try {
            List<OrderResponseDto> orders = medEService.getOrdersByStore(storeId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch store orders");
        }
    }

    // STORE UPDATE STATUS
    @PutMapping(path = "Store/updateStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestParam Long orderId,
                                               @RequestParam Long status) {
        try {
            return medEService.updateOrderStatus(orderId, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Status Update Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // STORE GET ORDER WITH STATUS 2

    @GetMapping(path = "Store/getByStatus")
    public ResponseEntity<?> getOrdersByStoreAndStatus(@RequestParam Long storeId,
                                               @RequestParam Long status) {
        try {
            List<OrderResponseDto> orders = medEService.getOrdersByStoreAndStatus(storeId,status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to fetch orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}