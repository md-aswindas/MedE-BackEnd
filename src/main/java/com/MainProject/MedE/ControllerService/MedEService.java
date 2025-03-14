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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            return new ResponseEntity<>("login success",HttpStatus.OK);
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
        if(userRegistrationModelOptional.isPresent()) {
            PrescriptionModel prescriptionModel1 = new PrescriptionModel();
            prescriptionModel1.setUser_id(prescriptionModel.getUser_id());
            prescriptionModel1.setPrescriptionImage(prescriptionImage.getBytes());

            prescriptionRepo.save(prescriptionModel1);
            return new ResponseEntity<>(prescriptionModel1, HttpStatus.OK);
        }
        return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);
    }

    // SEARCH PRODUCT

    public ResponseEntity<?> searchProduct(String productName) {
        List<ProductModel> products = productRepo.findByProductNameContainingIgnoreCase(productName);
        if (products.isEmpty()){
            return new ResponseEntity<>("not found ",HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>("item found",HttpStatus.FOUND);
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

    // ADMIN (VIEW PRODUCTS OF SINGLE STORE)

    public ResponseEntity<?> adminViewStoreProduct(Integer storeId) {
        List<ProductModel> productModelList=productRepo.findAllByStoreId(storeId);
        if (!productModelList.isEmpty()){
            return new ResponseEntity<>(productModelList,HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
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

                Optional<StoreRegistrationModel> storeRegistrationModelOptional = storeRegistrationRepo.findById(pdm.getStoreId());

                if(storeRegistrationModelOptional.isPresent()){
                    StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
                    adminViewProductDTO.setStoreName(storeRegistrationModel.getStore_name());
                }
                adminViewProductDTOList.add(adminViewProductDTO);
            }
            return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(adminViewProductDTOList,HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("login success",HttpStatus.OK);
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

//    public Optional<byte[]> getImageByProductId(Integer productId) {
//        Optional<byte[]> imageData = productRepo.findImageByProductId(productId);
//
//        if (imageData.isPresent() && imageData.get().length > 0) {
//            return imageData;
//        } else {
//            return Optional.empty(); // Return empty if no image exists
//        }
//    }


    // STORE ADD LOCATION ( UPDATE STORE TABLE )

    public ResponseEntity<?> addStoreLocation(Integer storeId, Double longitude, Double latitude) {
        Optional<StoreRegistrationModel> storeOptional = storeRegistrationRepo.findById(storeId);

        if (storeOptional.isPresent()){
            StoreRegistrationModel store = storeOptional.get();
            store.setLatitude(latitude);
            store.setLongitude(longitude);
            storeRegistrationRepo.save(store);
            return new ResponseEntity<>("Location Added successfully ",HttpStatus.OK);
        }
        return new ResponseEntity<>("location Adding Failed !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE DELETE PRODUCT

    public ResponseEntity<?> deleteProduct(Integer productId) {
        Optional<ProductModel> productModelOptional = productRepo.findById(productId);

        if (productModelOptional.isPresent()){
            ProductModel product = productModelOptional.get();
            productRepo.delete(product);
            return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
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

}