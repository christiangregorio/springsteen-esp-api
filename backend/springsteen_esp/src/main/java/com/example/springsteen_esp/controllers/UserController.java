package com.example.springsteen_esp.controllers;

import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.Song;
import com.example.springsteen_esp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios y sus conciertos")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve una lista con todos los usuarios registrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
            }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.list();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca un usuario por su identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Buscar usuario por nombre de usuario",
            description = "Devuelve los datos del usuario con el nombre indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Buscar usuario por email",
            description = "Devuelve los datos del usuario asociado a un email específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario creado correctamente")
            }
    )
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Modifica los datos de un usuario ya existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.get(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        User savedUser = userService.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(
            summary = "Eliminar un usuario",
            description = "Borra completamente un usuario del sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener conciertos asistidos por un usuario",
            description = "Devuelve la lista de conciertos a los que un usuario ha asistido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de conciertos obtenida"),
                    @ApiResponse(responseCode = "204", description = "El usuario no tiene conciertos registrados")
            }
    )
    @GetMapping("/{id}/concerts")
    public ResponseEntity<List<Concert>> getConcertsAttended(@PathVariable Long id) {
        List<Concert> concerts = userService.getConcertsAttended(id);
        if (concerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(concerts);
    }

    @Operation(
            summary = "Obtener canciones escuchadas por un usuario",
            description = "Lista todas las canciones que el usuario ha escuchado en los conciertos a los que asistió.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de canciones obtenida"),
                    @ApiResponse(responseCode = "204", description = "El usuario no tiene canciones registradas")
            }
    )
    @GetMapping("/{id}/songs")
    public ResponseEntity<List<Song>> getSongsHeardByUser(@PathVariable Long id) {
        List<Song> songs = userService.getSongsHeardByUser(id);
        if (songs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(songs);
    }


    // LOGIN Y REGISTER
    // _________________

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Permite crear una nueva cuenta de usuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Ya existe un usuario con ese email")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userService.getByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        User createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Verifica las credenciales de un usuario mediante su email y contraseña.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
                    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        boolean isAuthenticated = userService.login(user.getEmail(), user.getPassword());

        if (isAuthenticated) {
            User existingUser = userService.getByEmail(user.getEmail());

            response.put("message", "login ok");
            response.put("email", user.getEmail());
            if (existingUser != null) {
                response.put("id", existingUser.getId());
            }
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "usuario o contraseña incorrectos");
            return ResponseEntity.status(401).body(response);
        }
    }
}