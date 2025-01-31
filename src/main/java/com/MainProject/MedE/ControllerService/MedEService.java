package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Admin.AdminRepo;
import com.MainProject.MedE.Store.StatusModel;
import com.MainProject.MedE.Store.StatusRepo;
import com.MainProject.MedE.Store.StoreRegistrationModel;
import com.MainProject.MedE.Store.StoreRegistrationRepo;
import com.MainProject.MedE.UserRegistration.UserRegistrationModel;
import com.MainProject.MedE.UserRegistration.UserRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
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

    public ResponseEntity<?> forgotPassword(Integer user_id, String password) {
        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findById(user_id);
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

    public ResponseEntity<?> updateEmail(Integer user_id, String email) {
        Optional<UserRegistrationModel>optionalUserRegistrationModel=userRegistrationRepo.findById(user_id);
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


    //ADMIN LOGIN

    public ResponseEntity<?> adminLogin(String adminUserName, String password) {
        Optional<AdminModel>optionalAdminModel=adminRepo.findByAdminUserNameAndPassword(adminUserName,password);
        if (optionalAdminModel.isPresent()){
            return new ResponseEntity<>("Login Success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User name or password not match",HttpStatus.NOT_FOUND);
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
}