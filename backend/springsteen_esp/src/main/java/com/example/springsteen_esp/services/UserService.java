package com.example.springsteen_esp.services;

import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.Song;
import com.example.springsteen_esp.models.UserConcertAttendance;
import com.example.springsteen_esp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Obtener todos los conciertos a los que ha asistido el usuario
    public List<Concert> getConcertsAttended(Long userId) {
        User user = get(userId);
        if (user == null) {
            return List.of(); // lista vacía si no existe usuario
        }

        List<Concert> concertsAttended = new ArrayList<>();
        for (UserConcertAttendance attendance : user.getAttendances()) {
            concertsAttended.add(attendance.getConcert());
        }
        return concertsAttended;
    }

    // Obtener todas las canciones que ha escuchado el usuario
    public List<Song> getSongsHeardByUser(Long userId) {
        User user = get(userId);
        if (user == null) {
            return List.of(); // lista vacía si no existe usuario
        }

        List<Song> songsHeard = new ArrayList<>();
        for (UserConcertAttendance attendance : user.getAttendances()) {
            Concert concert = attendance.getConcert();
            for (Song song : concert.getSongs()) {
                songsHeard.add(song);
            }
        }
        return songsHeard;
    }

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}