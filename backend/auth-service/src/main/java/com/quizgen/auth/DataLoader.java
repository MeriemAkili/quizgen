package com.quizgen.auth;

import com.quizgen.auth.entity.User;
import com.quizgen.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println(">>> Base deja peuplee : " + userRepository.count() + " utilisateurs");
            return;
        }

        String[] prenoms = {"Alice", "Bob", "Claire", "David", "Emma", "Franck", "Grace", "Hugo", "Ines", "Jules",
                "Karim", "Lea", "Marc", "Nina", "Oscar", "Paul", "Quentin", "Romy", "Sami", "Tina",
                "Ugo", "Valerie", "Wassim", "Xavier", "Yasmine", "Zoe", "Adel", "Bilal", "Chloe", "Diane"};
        String[] noms = {"Martin", "Bernard", "Dubois", "Thomas", "Robert", "Richard", "Petit", "Durand", "Leroy", "Moreau",
                "Simon", "Laurent", "Lefebvre", "Michel", "Garcia", "David", "Bertrand", "Roux", "Vincent", "Fournier"};

        String encodedPwd = passwordEncoder.encode("password123");

        for (int i = 0; i < 30; i++) {
            User u = new User();
            u.setEmail("etudiant" + (i + 1) + "@quizgen.com");
            u.setPassword(encodedPwd);
            u.setFirstName(prenoms[i]);
            u.setLastName(noms[i % noms.length]);
            u.setRole(User.Role.STUDENT);
            userRepository.save(u);
        }

        String[] teacherNames = {"Prof Dupont", "Prof Martin", "Prof Leroy"};
        for (int i = 0; i < 3; i++) {
            User t = new User();
            t.setEmail("prof" + (i + 1) + "@quizgen.com");
            t.setPassword(encodedPwd);
            t.setFirstName(teacherNames[i].split(" ")[0]);
            t.setLastName(teacherNames[i].split(" ")[1]);
            t.setRole(User.Role.TEACHER);
            userRepository.save(t);
        }

        System.out.println(">>> 30 etudiants + 3 enseignants crees. Mot de passe : password123");
    }
}