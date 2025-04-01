package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminLoginDto;
import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminViewProductDTO;
import com.MainProject.MedE.Store.*;

import com.MainProject.MedE.UserRegistration.PrescriptionModel;
import com.MainProject.MedE.UserRegistration.UserLoginDto;
import com.MainProject.MedE.UserRegistration.UserRegistrationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.Origin;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> updateEmailMethod(@RequestParam Integer phoneNumber, @RequestParam String email){
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


    // USER SEARCH PRODUCT

    @GetMapping(path = "User/searchProduct")
   public ResponseEntity<?> searchProduct(@RequestParam String productName){
        return medEService.searchProduct(productName);
    }

    // USER ADD FEEDBACK

    @PostMapping(path = "User/addFeedBack")
    public ResponseEntity<?> addFeedBack(@RequestBody FeedBackModel feedBackModel){
        return medEService.addFeedBack(feedBackModel);
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


    @GetMapping(path = "Admin/AdminViewStoreProducts")
    public ResponseEntity<?>adminViewStoreProductsMethod(@RequestParam Integer storeId){
        try{
            return medEService.adminViewStoreProduct(storeId);
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
    @DeleteMapping(path = "Store/deleteProduct")
    public ResponseEntity<?>deleteProductMethod(@RequestParam Integer productId){
        try{
            return medEService.deleteProduct(productId);
        } catch ( Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>("delete failed ",HttpStatus.INTERNAL_SERVER_ERROR);
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

    // STORE LOCATION ADDING ( UPDATE STORE TABLE )

    @PutMapping(path = "Store/AddStoreLocation")
    public ResponseEntity<?>addStoreLocationMrthod(@RequestParam Integer store_Id,
                                             @RequestParam Double longitude,
                                             @RequestParam Double latitude){
        try{
            return medEService.addStoreLocation(store_Id,longitude,latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Server error",HttpStatus.INTERNAL_SERVER_ERROR);
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
}