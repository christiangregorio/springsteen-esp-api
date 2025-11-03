package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.UserConcertAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConcertAttendanceRepository extends JpaRepository<UserConcertAttendance, Long> {

    UserConcertAttendance findByUserAndConcert(User user, Concert concert);

    List<UserConcertAttendance> findByConcert(Concert concert);

    int countByConcert(Concert concert);

    List<UserConcertAttendance> findByUser(User user);
}