package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import com.example.demo.exception.CustomServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    private void validateUser(User user) throws CustomServiceException {
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new CustomServiceException("User name cannot be null or empty");
        }
        if (user.getBirthday() == null) {
            throw new CustomServiceException("User birthday cannot be null");
        }
        if (user.getGender() == null || user.getGender().isEmpty()) {
            throw new CustomServiceException("User gender cannot be null or empty");
        }
        if (user.getMajor() == null || user.getMajor().isEmpty()) {
            throw new CustomServiceException("User major cannot be null or empty");
        }
        if (user.getHomeTown() == null || user.getHomeTown().isEmpty()) {
            throw new CustomServiceException("User home town cannot be null or empty");
        }
        if (user.getClassName() == null || user.getClassName().isEmpty()) {
            throw new CustomServiceException("User class name cannot be null or empty");
        }
        if (user.getAverageMark() == null) {
            user.setAverageMark(0.0F);
        }
    }
    public void addUser(User user) throws CustomServiceException {
        System.out.println("Validate user"+user);
        validateUser(user);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new CustomServiceException("Data integrity violation: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CustomServiceException("An error occurred while saving the user: " + e.getMessage(), e);
        }
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User updateUser(Integer id,User userDetail) throws CustomServiceException {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new CustomServiceException("User not found");
        }
        validateUser(userDetail);
        User user = userOptional.get();
        user.setFullName(userDetail.getFullName());
        user.setGender(userDetail.getGender());
        user.setMajor(userDetail.getMajor());
        user.setHomeTown(userDetail.getHomeTown());
        user.setAverageMark(userDetail.getAverageMark());
        user.setClassName(userDetail.getClassName());
        user.setBirthday(userDetail.getBirthday());
        return userRepository.save(user);
    }

    public void deleteById(Integer id) throws CustomServiceException {
        Long count = userRepository.countById(id);
        if(count==null || count==0) {
            throw new CustomServiceException("User not found");
        }
        userRepository.deleteById(id);
    }

    public List<User> searchUsers(String fullName, String gender, String homeTown,
                                  String className, String major, Float minAverageMark,
                                  Float maxAverageMark, String fromDate, String toDate,
                                  String query) {
        return userRepository.findByCriteria(fullName, gender, homeTown, className, major,
                minAverageMark, maxAverageMark, fromDate, toDate, query);
    }

    public List<User> findUsersByBirthdayToday() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentDayOfMonth = now.getDayOfMonth();
        return userRepository.findByBirthdayMonthAndDay(currentMonth, currentDayOfMonth);
    }

}
