package com.magnus.cafe.serviceImpI;

import com.magnus.cafe.POJO.User;
import com.magnus.cafe.contents.CafeContents;
import com.magnus.cafe.dao.UserDao;
import com.magnus.cafe.service.UserService;
import com.magnus.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import java.util.Map;
import java.util.Objects;
=======

import java.net.Authenticator;
import java.util.*;
>>>>>>> Stashed changes

@Slf4j
@Service
public class UserServiceImpI implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exist:", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeContents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password"))
        {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;

<<<<<<< Updated upstream
=======
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval. "+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("An error occurred:" ,ex);
        }

        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials. " + "\"}",
                HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>( new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                int userId = Integer.parseInt(requestMap.get("id"));
                Optional<User> optional = userDao.findById(userId);
                if (!optional.isEmpty()) {
                    User user = optional.get();

                    if (requestMap.containsKey("status")) {
                        userDao.updateStatus(requestMap.get("status"), userId);
                    }

                    if (requestMap.containsKey("email")) {
                        user.setEmail(requestMap.get("email"));
                        userDao.updateEmail(user.getEmail(), userId);
                    }

                    if (requestMap.containsKey("name")) {
                        user.setName(requestMap.get("name"));
                        userDao.updateName(user.getName(), userId);
                    }

                    if (requestMap.containsKey("contactNumber")) {
                        user.setContactNumber(requestMap.get("contactNumber"));
                        userDao.updateContactNumber(user.getContactNumber(), userId);
                    }

                    sendMailToAllAdmin(user.getStatus(), user.getEmail(), userDao.getAllAdmin());
                    return CafeUtils.getResponseEntity("User information updated successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("User ID does not exist", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeContents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    public ResponseEntity<String> checkToken() {

        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (userObj != null) {
                String enteredOldPassword = requestMap.get("oldPassword");
                if (passwordEncoder.matches(enteredOldPassword, userObj.getPassword())) {
                    String newEncodedPassword = passwordEncoder.encode(requestMap.get("newPassword"));
                    userObj.setPassword(newEncodedPassword);
                    userDao.save(userObj);
                    return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrect Previous Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "Credential by Cafe Management", user.getPassword());

                return CafeUtils.getResponseEntity("Check your mail for Credentials", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUsers(List<Integer> userIds) {
        try {
            if (jwtFilter.isAdmin()) {
                int updatedCount = userDao.deleteUsers(userIds);
                if (updatedCount > 0) {
                    return CafeUtils.getResponseEntity(updatedCount + " user(s) deleted.", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("No users found for deletion.", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeContents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved", "USER:-"+ user +"\n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin);

        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled", "USER:-"+ user +"\n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin);


        }
>>>>>>> Stashed changes

    }
}
