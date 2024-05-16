package tech.razymov.restfull.entity;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String block1;
    private String block2;
    private String block3;
    private String block4;
    private byte[] img;

    public User(String name, String description, String block1, String block2, String block3, String block4, byte[] img) {
        this.name = name;
        this.description = description;
        this.block1 = block1;
        this.block2 = block2;
        this.block3 = block3;
        this.block4 = block4;
        this.img = img;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlock1() {
        return block1;
    }

    public void setBlock1(String block1) {
        this.block1 = block1;
    }

    public String getBlock2() {
        return block2;
    }

    public void setBlock2(String block2) {
        this.block2 = block2;
    }

    public String getBlock3() {
        return block3;
    }

    public void setBlock3(String block3) {
        this.block3 = block3;
    }

    public String getBlock4() {
        return block4;
    }

    public void setBlock4(String block4) {
        this.block4 = block4;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(description, user.description) && Objects.equals(block1, user.block1) && Objects.equals(block2, user.block2) && Objects.equals(block3, user.block3) && Objects.equals(block4, user.block4) && Arrays.equals(img, user.img);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, block1, block2, block3, block4);
        result = 31 * result + Arrays.hashCode(img);
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
                ", img=" + Arrays.toString(img) +
                '}';
    }
}
