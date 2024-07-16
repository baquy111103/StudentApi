package com.example.demo.controller;

import org.springframework.ui.Model;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import com.example.demo.exception.CustomServiceException;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<List<User>> showUserList(){

        try{
            List<User> users= userService.listAll();
            if(users==null || users.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/addnewuser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addNewUser"; // Trả về tên của template HTML (addNewUser.html)
    }


    @PostMapping("/users/addnewuser")
    public ResponseEntity<String> addNewUser(@RequestBody  User user){
        System.out.println(user);
        try{
            userService.addUser(user);
            //201
            return ResponseEntity.ok("User added successfully");
        }catch (CustomServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/users/edit/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        logger.info("Request to get user by ID: {}", id);
        try {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                logger.info("User found: {}", user.get());
                return ResponseEntity.ok(user.get());
            }
            logger.warn("User not found with ID: {}", id);
            throw new CustomServiceException("User not found");
        } catch (CustomServiceException e) {
            logger.error("CustomServiceException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/users/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User userDetail) {
        logger.info("Request to update user with ID: {} and details: {}", id, userDetail);
        try {
            User updatedUser = userService.updateUser(id, userDetail);
            logger.info("User updated successfully: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (CustomServiceException e) {
            logger.error("CustomServiceException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        logger.info("Request to delete user with ID: {}", id);
        try {
            userService.deleteById(id);
            logger.info("User deleted successfully with ID: {}", id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (CustomServiceException e) {
            logger.error("CustomServiceException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam Map<String, String> params) {
        // Xử lý các tham số từ params
        String fullName = params.get("fullName");
        String gender = params.get("gender");
        String homeTown = params.get("homeTown");
        String className = params.get("className");
        String major = params.get("major");
        Float minAverageMark = params.containsKey("minAverageMark") ? Float.parseFloat(params.get("minAverageMark")) : null;
        Float maxAverageMark = params.containsKey("maxAverageMark") ? Float.parseFloat(params.get("maxAverageMark")) : null;
        String fromDate = params.get("fromDate");
        String toDate = params.get("toDate");
        String query = params.get("query");

        // Gọi tới service để tìm kiếm
        List<User> users = userService.searchUsers(fullName, gender, homeTown, className, major,
                minAverageMark, maxAverageMark, fromDate, toDate, query);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/happybirthday")
    public ResponseEntity<List<User>> getBirthdayWishes(){
        List<User> users = userService.findUsersByBirthdayToday();
        return ResponseEntity.ok(users);
    }
}