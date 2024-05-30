package tech.razymov.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {
    private String email;
    private String password;
    private String name;
    private String description;
    private String block1;
    private String block2;
    private String block3;
    private String block4;
    private String imgUrl;
}
