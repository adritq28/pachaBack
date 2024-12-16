package com.pacha.proyecto.service.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> recoveryCodes = new HashMap<>();

    public void sendRecoveryCode(String email) {
        String code = generateRecoveryCode();
        recoveryCodes.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperación de Contraseña");
        message.setText("Tu código de recuperación es: " + code);
        message.setFrom("pachayatinahelvetas@gmail.com");

        mailSender.send(message);
    }

    public boolean validateRecoveryCode(String email, String code) {
        String storedCode = recoveryCodes.get(email);
        System.out.println("codigo correo: " + storedCode + " " + code);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateRecoveryCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
