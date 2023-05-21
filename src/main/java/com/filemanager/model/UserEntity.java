package com.filemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filemanager.exception.BadRequestException;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;


@Entity
@Data
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_entity_seq")
    @SequenceGenerator(name = "my_entity_seq", allocationSize = 10, initialValue = 10000001)
    private int userId;


    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;


    @Column
    private String password;

    @OneToOne
    @JoinColumn(name = "directory_id")
    private DirectoryEntity directoryEntity;

    public void setPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);

    }

    public UserEntity(String username, String password, String email)
    {

        this.username = username;
        this.email = email;
        setPassword(password);

    }

    public UserEntity() {

    }


    public String getUserDirName()  {
        String firstFourChars = username.substring(0, 4);
        return firstFourChars + "_" + userId;
    }
}
