package tech.razymov.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.razymov.restfull.entity.User;
@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String description;
    private String block1;
    private String block2;
    private String block3;
    private String block4;
    private String img;
    private String uniqUrl;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.description = user.getDescription();
        this.block1 = user.getBlock1();
        this.block2 = user.getBlock2();
        this.block3 = user.getBlock3();
        this.block4 = user.getBlock4();
        this.uniqUrl = user.getUniqUrl();
        this.img = user.getImg();
    }
}
