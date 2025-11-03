package com.example.springsteen_esp.services;

import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.Song;
import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.UserConcertAttendance;
import com.example.springsteen_esp.repositories.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<Concert> list() {
        return concertRepository.findAll();
    }

    public Concert get(Long id) {
        return concertRepository.findById(id).orElse(null);
    }

    public Concert save(Concert concert) {
        return concertRepository.save(concert);
    }

    public void delete(Long id) {
        concertRepository.deleteById(id);
    }

    // Agregar una canción a un concierto
    public Concert addSong(Long concertId, Song song) {
        Concert concert = get(concertId);
        if (concert != null) {
            concert.getSongs().add(song);
            song.setConcert(concert); // relación bidireccional
            return save(concert);
        }
        return null;
    }

    // Eliminar una canción de un concierto
    public Concert removeSong(Long concertId, Song song) {
        Concert concert = get(concertId);
        if (concert != null) {
            concert.getSongs().remove(song);
            song.setConcert(null);
            return save(concert);
        }
        return null;
    }

    // Obtener canciones ordenadas por posición
    public List<Song> getSongsOrdered(Long concertId) {
        Concert concert = get(concertId);
        if (concert == null) return List.of();

        List<Song> orderedSongs = new ArrayList<>(concert.getSongs());
        orderedSongs.sort(Comparator.comparingInt(Song::getPosition));
        return orderedSongs;
    }

    // Obtener todos los asistentes de un concierto
    public List<User> getAttendees(Long concertId) {
        Concert concert = get(concertId);
        if (concert == null) {
            return List.of();
        }

        List<User> attendees = new ArrayList<>();
        for (UserConcertAttendance attendance : concert.getAttendances()) {
            attendees.add(attendance.getUser());
        }
        return attendees;
    }

    // Contar asistentes al concierto
    public int countAttendees(Long concertId) {
        Concert concert = get(concertId);
        if (concert == null) return 0;
        return concert.getAttendances().size();
    }
}