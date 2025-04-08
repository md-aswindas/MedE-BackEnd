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

    // SEARCH PRODUCT

    public ResponseEntity<?> searchProduct(String productName) {
        List<ProductModel> products = productRepo.findByProductNameContainingIgnoreCase(productName);
        if (products.isEmpty()){
            return new ResponseEntity<>("not found ",HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>("item found",HttpStatus.FOUND);
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

                Optional<StoreRegistrationModel> storeRegistrationModelOptional = storeRegistrationRepo.findById(pdm.getStoreId());
                Optional<CategoryModel> categoryModelOptional = categoryRepo.findById(pdm.getCategoryId());

                if(storeRegistrationModelOptional.isPresent()){
                    StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
                    adminViewProductDTO.setStoreName(storeRegistrationModel.getStore_name());
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
                storeDTO.setStoreName(urm.getStore_name());
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


            return new ResponseEntity<>(storeDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(storeDTOList,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // STORE UPDATE PROFILE

    public ResponseEntity<?> updateProfile(Integer storeId, String storeName, String password, Long phoneNumber) {
        Optional<StoreRegistrationModel>storeRegistrationModelOptional=storeRegistrationRepo.findById(storeId);
        if (storeRegistrationModelOptional.isPresent()){
            StoreRegistrationModel storeRegistrationModel = storeRegistrationModelOptional.get();
            storeRegistrationModel.setStore_name(storeName);
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

    public ResponseEntity<?> rejectPrescription(Integer prescriptionId) {
        Optional<PrescriptionModel>optionalPrescriptionModel=prescriptionRepo.findById(prescriptionId);
        if(optionalPrescriptionModel.isPresent()){
            PrescriptionModel prescriptionModel = optionalPrescriptionModel.get();
            prescriptionRepo.delete(prescriptionModel);
            return new ResponseEntity<>("prescription Rejected",HttpStatus.OK);
        }
        return new ResponseEntity<>("prescription not found",HttpStatus.NOT_FOUND);
    }
}