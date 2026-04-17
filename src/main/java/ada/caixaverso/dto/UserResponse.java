package ada.caixaverso.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserResponse(Long id, String name, String email, String password, String role) {
}
