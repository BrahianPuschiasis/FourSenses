package com.example.proyectointegrador.controller;

import java.util.*;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.exception.BadRequestException;
import com.example.proyectointegrador.exception.ResoucerNotFoundException;
import com.example.proyectointegrador.service.ComidaService;
import com.example.proyectointegrador.service.UserDetail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.proyectointegrador.dto.LoginDto;
import com.example.proyectointegrador.dto.SignUpDto;
import com.example.proyectointegrador.entity.Role;
import com.example.proyectointegrador.entity.User;
import com.example.proyectointegrador.repository.RoleRepository;
import com.example.proyectointegrador.repository.UserRepository;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetail userDetail;

    @Autowired
    public HomeController(UserDetail userDetail) {
        this.userDetail = userDetail;
    }



    @PostMapping("/login")
    public ResponseEntity<User> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = loginDto.getEmail();
        User usuario = userRepository.findByEmail(email); // Encuentra el usuario por el email
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        // reviso si el usuario existe en la BD
     //   if(userRepository.existsByUserName(signUpDto.getUsername())){
   //         return new ResponseEntity<>("El nombre de usuario ya existe!", HttpStatus.BAD_REQUEST);
    //    }



        // reviso si el email existe en la BD
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("El email ya existe!", HttpStatus.BAD_REQUEST);
        }



        // Buscar o crear y guardar el rol "ROLE_ADMIN"
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role newAdminRole = new Role();
            newAdminRole.setName("ROLE_ADMIN");
            roleRepository.save(newAdminRole);
            return newAdminRole;
        });


        // Buscar o crear y guardar el rol "ROLE_USER"
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role newUserRole = new Role();
            newUserRole.setName("ROLE_USER");
            roleRepository.save(newUserRole);
            return newUserRole;
        });


        // Buscar o crear y guardar el usuario "admin"
        User adminUser = userRepository.findByEmail("admin@gmail.com");

        if (adminUser == null) {
            adminUser = new User();
            adminUser.setName("admin");
            adminUser.setSurName("admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("12345678"));
            Role admin = roleRepository.findByName("ROLE_ADMIN").get();
            adminUser.setRoles(Collections.singleton(admin));
            userRepository.save(adminUser);
        }




        // si no se cumple lo de arriba, creo al usuario, por defecto con rol USER osea usuario normal
        User user = new User();
        user.setName(signUpDto.getName());
        user.setSurName(signUpDto.getSurName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("Usuario registrado con exito!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(userDetail.listarUsuarios());
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<User> buscarUsuarioEmail(@PathVariable String email) throws ResoucerNotFoundException {
        boolean existeUsuario = userRepository.existsByEmail(email);

        if (existeUsuario) {
            User user = userRepository.findByEmail(email);
            return ResponseEntity.ok(user);
        } else {
            throw new ResoucerNotFoundException("Usuario no encontrado " + email);
        }
    }


    @PutMapping
    @Transactional
    public ResponseEntity<User> actualizarUser(@RequestBody User user) throws BadRequestException {
        Optional<User> userBuscado = userDetail.buscarUserPorId(user.getId());

        if (userBuscado.isPresent()) {
            User userActual = userBuscado.get();

            Integer currentRoleId = userActual.getRoles().iterator().next().getId();

            Role role = new Role();
            if (currentRoleId == 1) {
                role.setId(2);
            } else if (currentRoleId == 2) {
                role.setId(1);
            }

            Set<Role> roles = new HashSet<>();
            roles.add(role);

            userActual.setRoles(roles); // Actualiza los roles
            userDetail.actualizarUsuario(userActual);
            return ResponseEntity.ok(userActual); // Devuelve el usuario actualizado
        } else {
            throw new BadRequestException("Usuario no encontrado " + user.getEmail());
        }
    }





}
