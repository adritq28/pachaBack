package com.pacha.proyecto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pacha.proyecto.service.db.EmailService;
import com.pacha.proyecto.service.db.UsuarioServiceJpa;

@RestController
@RequestMapping("/email")
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioServiceJpa usuarioService;

    @PostMapping("/send")
    public String sendRecoveryCode(@RequestParam String email) {
        try {
            System.out.println("email: " + email);
            emailService.sendRecoveryCode(email);
            return "Código de recuperación enviado correctamente";
        } catch (Exception e) {
            return "Error al enviar el código: " + e.getMessage();
        }
    }

    @PostMapping("/validate-code")
    public String validateRecoveryCode(@RequestParam String email, @RequestParam String code) {
        if (emailService.validateRecoveryCode(email, code)) {
            System.out.println("codigooo " + code);
            return "Código válido. Puedes restablecer tu contraseña";
        } else {
            System.out.println("entro a falsoooo");
            return "Código inválido. Intenta nuevamente";
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("correoElectronico");
        String newPassword = payload.get("newPassword");

        try {
            boolean success = usuarioService.changeUserPassword(email, newPassword);

            if (success) {
                return ResponseEntity.ok("Contraseña cambiada correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo cambiar la contraseña");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

}
