package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminRepo;
import com.MainProject.MedE.Store.*;
import com.MainProject.MedE.UserRegistration.UserRegistrationModel;
import com.MainProject.MedE.UserRegistration.UserRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

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

        userRegistrationRepo.save(userRegistrationModel1);

        return new ResponseEntity<>(userRegistrationModel1, HttpStatus.OK);
    }

    // USER LOGIN

    public ResponseEntity<?> userLogin(String email, String password) {
        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findByEmailAndPassword(email,password);
        if (optionalUserRegistrationModel.isPresent()){
            return new ResponseEntity<>("Login Success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not found ",HttpStatus.NOT_FOUND);
        }

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

    public ResponseEntity<?> adminLogin(String adminUserName, String password) {
        Optional<AdminModel>optionalAdminModel=adminRepo.findByAdminUserNameAndPassword(adminUserName,password);
        if (optionalAdminModel.isPresent()){
            return new ResponseEntity<>("Login Success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User name or password not match",HttpStatus.NOT_FOUND);
        }
    }

    // ADMIN VIEW ALL STORES

    public ResponseEntity<List<StoreDTO>> adminViewStores() {
        List<StoreDTO> storeDTOList =new ArrayList<>();
        List<StoreRegistrationModel> storeRegistrationModelList= storeRegistrationRepo.findAll();
        if(!storeRegistrationModelList.isEmpty()){
            for(StoreRegistrationModel urm : storeRegistrationModelList){

                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setStoreId(urm.getStore_id());
                storeDTO.setStoreName(urm.getStore_name());
                storeDTO.setLicenseNumber(urm.getLicenseNumber());
                storeDTO.setStatusId(urm.getStatus_id());
                storeDTO.setRegistrationDate(urm.getCreated_at());
                storeDTO.setStatusUpdateDate(urm.getStatusUpdate_at());

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
        storeRegistrationModel1.setStore_name(storeRegistrationModel.getStore_name());
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


    public ResponseEntity<?> storeLogin(String licenseNumber, String password) {
        Optional<StoreRegistrationModel>optionalStoreRegistrationModel=storeRegistrationRepo.findByLicenseNumberAndPassword(licenseNumber,password);
        if (optionalStoreRegistrationModel.isPresent()){
            return new ResponseEntity<>("Login Success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("License Number or password not match",HttpStatus.NOT_FOUND);
        }
    }

    // STORE ADD PRODUCT

    @Autowired
    private ProductRepo productRepo;


    public ResponseEntity<?> addProduct(ProductModel productModel, MultipartFile productImage) throws IOException {
        ProductModel productModel1 = new ProductModel();

        productModel1.setStoreId(productModel.getStoreId());
        productModel1.setProductName(productModel.getProductName());
        productModel1.setProductDesc(productModel.getProductDesc());
        productModel1.setProductImage(productImage.getBytes());

        productModel1.setStock(productModel.getStock());
        productModel1.setActualPrice(productModel.getActualPrice());
        productModel1.setOfferPercentage(productModel.getOfferPercentage());
        productModel1.calculateDiscountPrice();

        productRepo.save(productModel1);

        return new ResponseEntity<>(productModel1,HttpStatus.OK);
    }

    public ResponseEntity<?> productUpdate(Integer productId, Integer stock, double actualPrice, Integer offerPercentage) {

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
            return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> storeViewAllProduct(Integer storeId) {
        List<ProductModel> productModelList=productRepo.findAllByStoreId(storeId);
        if (!productModelList.isEmpty()){
            return new ResponseEntity<>(productModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
    }
}