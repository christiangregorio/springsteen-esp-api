package com.example.springsteen_esp.services;

import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.UserConcertAttendance;
import com.example.springsteen_esp.repositories.UserConcertAttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class UserConcertAttendanceService {

    private final UserConcertAttendanceRepository attendanceRepository;

    public UserConcertAttendanceService(UserConcertAttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // Marcar que un usuario ha asistido a un concierto
    public UserConcertAttendance markAttendance(User user, Concert concert) {
        UserConcertAttendance existing = attendanceRepository.findByUserAndConcert(user, concert);
        if (existing != null) {
            return existing; // ya estaba marcado
        }
        UserConcertAttendance attendance = new UserConcertAttendance(user, concert);
        return attendanceRepository.save(attendance);
    }

    // Quitar asistencia
    public void removeAttendance(User user, Concert concert) {
        UserConcertAttendance existing = attendanceRepository.findByUserAndConcert(user, concert);
        if (existing != null) {
            attendanceRepository.delete(existing);
        }
    }
}