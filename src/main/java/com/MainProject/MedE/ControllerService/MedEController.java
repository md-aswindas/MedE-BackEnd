package com.MainProject.MedE.ControllerService;

import com.MainProject.MedE.Admin.AdminModel;
import com.MainProject.MedE.Store.StatusModel;
import com.MainProject.MedE.Store.StoreRegistrationModel;
import com.MainProject.MedE.UserRegistration.UserRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> forgotPasswordMethod(@RequestParam Integer user_id, @RequestParam String password){
        try{
        return medEService.forgotPassword(user_id,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went Wrong !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UPDATE EMAIL

    @PutMapping(path = "User/updateEmail")
    public ResponseEntity<?> updateEmailMethod(@RequestParam Integer user_id, @RequestParam String email){
        try{
            return medEService.updateEmail(user_id,email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went Wrong !",HttpStatus.INTERNAL_SERVER_ERROR);
    }





                                // A D M I N



    // ADMIN REGISTREATION

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


    // ADMIN (STORE STATUS UPDATE)



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

}