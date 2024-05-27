package tech.razymov.restfull.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String block1;
    private String block2;
    private String block3;
    private String block4;
    private String img;

    public User() {}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(description, user.description) && Objects.equals(block1, user.block1) && Objects.equals(block2, user.block2) && Objects.equals(block3, user.block3) && Objects.equals(block4, user.block4) && Objects.equals(img, user.img);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, block1, block2, block3, block4, img);
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", block1='" + block1 + '\'' +
                ", block2='" + block2 + '\'' +
                ", block3='" + block3 + '\'' +
                ", block4='" + block4 + '\'' +
                ", img=" + img +
                '}';
    }
}
