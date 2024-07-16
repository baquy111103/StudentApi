package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false, length = 30)
    private String className;

    @Column(nullable = false, length = 30)
    private String major;

    @Column(nullable = false, length = 50)
    private String homeTown;

    @Column(nullable = false, length = 10)
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    private Float averageMark;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Float averageMark) {
        this.averageMark = averageMark;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id:" + id +
                " Họ tên:" + fullName +
                " Ngày sinh:" + birthday +
                " Tên lớp:" + className +
                " Tên ngành:" + major +
                " Địa chỉ:" + homeTown +
                " Giới tính:" + gender +
                " Điểm:" + averageMark +
                "}";
    }
}
