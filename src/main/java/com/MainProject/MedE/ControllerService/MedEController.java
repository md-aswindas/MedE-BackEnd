package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminViewProductDTO;
import com.MainProject.MedE.Store.ProductModel;
import com.MainProject.MedE.Store.StatusModel;
import com.MainProject.MedE.Store.StoreDTO;

import com.MainProject.MedE.Store.StoreRegistrationModel;
import com.MainProject.MedE.UserRegistration.PrescriptionModel;
import com.MainProject.MedE.UserRegistration.UserRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/MedE")
public class MedEController {

    @Autowired
    private MedEService medEService;




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

    @PostMapping(path = "User/userLogin")
    public ResponseEntity<?> userLoginMethod(@RequestParam String email, @RequestParam String password){
        try{
            return medEService.userLogin(email,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Login Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping(path = "Admin/adminLogin")
    public ResponseEntity<?>adminLoginMethod(@RequestParam String adminUserName,@RequestParam String password){
        try{
            return medEService.adminLogin(adminUserName,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong ",HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping(path = "Admin/adminViewAllProduct")
    public ResponseEntity<List<AdminViewProductDTO>>adminViewProductDTOMethod(){
        return medEService.adminViewProductsWithName();
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

    @PostMapping(path = "Store/storeLogin")
    public ResponseEntity<?> StoreLoginMethod(@RequestParam String licenseNumber, @RequestParam String password){
        try{
            return medEService.storeLogin(licenseNumber,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Login Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
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

    // STORE UPDATE PRODUCT( STOCK , ACTUAL PRICE , OFFER PERCENTAGE )

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

}