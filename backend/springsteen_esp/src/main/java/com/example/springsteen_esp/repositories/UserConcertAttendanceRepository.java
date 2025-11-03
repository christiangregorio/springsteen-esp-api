package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.UserConcertAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConcertAttendanceRepository extends JpaRepository<UserConcertAttendance, Long> {
}