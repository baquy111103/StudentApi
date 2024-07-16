package com.example.demo.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer>  {
    public long countById(Integer id);
    @Query(value = "SELECT * FROM users WHERE MONTH(birthday) = :month AND DAY(birthday) = :day", nativeQuery = true)
    List<User> findByBirthdayMonthAndDay(@Param("month") int month, @Param("day") int day);

    @Query("SELECT u FROM User u WHERE " +
            "(:fullName IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
            "(:gender IS NULL OR LOWER(u.gender) = LOWER(:gender)) AND " +
            "(:homeTown IS NULL OR LOWER(u.homeTown) LIKE LOWER(CONCAT('%', :homeTown, '%'))) AND " +
            "(:className IS NULL OR LOWER(u.className) LIKE LOWER(CONCAT('%', :className, '%'))) AND " +
            "(:major IS NULL OR LOWER(u.major) LIKE LOWER(CONCAT('%', :major, '%'))) AND " +
            "(:minAverageMark IS NULL OR u.averageMark >= :minAverageMark) AND " +
            "(:maxAverageMark IS NULL OR u.averageMark <= :maxAverageMark) AND " +
            "(:fromDate IS NULL OR u.birthday >= :fromDate) AND " +
            "(:toDate IS NULL OR u.birthday <= :toDate) AND " +
            "(COALESCE(:query, '') = '' OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.gender) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.homeTown) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.className) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.major) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<User> findByCriteria(@Param("fullName") String fullName,
                              @Param("gender") String gender,
                              @Param("homeTown") String homeTown,
                              @Param("className") String className,
                              @Param("major") String major,
                              @Param("minAverageMark") Float minAverageMark,
                              @Param("maxAverageMark") Float maxAverageMark,
                              @Param("fromDate") String fromDate,
                              @Param("toDate") String toDate,
                              @Param("query") String query);
}
