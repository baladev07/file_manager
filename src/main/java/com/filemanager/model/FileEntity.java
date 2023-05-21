package com.filemanager.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class FileEntity extends BaseEntity{


    @Column(unique = true)
    private String fileName;



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_entity_seq")
    @SequenceGenerator(name = "my_entity_seq", allocationSize = 10, initialValue = 10000001)
    private long fileId;

    private long fileSize;

    private String fileFormat;

    private Long fileUploadedTime;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private DirectoryEntity directoryEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public String getFileNameWithFormat()
    {
        return this.fileName+"."+this.fileFormat;
    }

    public String getFilePath()
    {
        return this.directoryEntity.getAbsolutePath()+"/"+getFileNameWithFormat();
    }

}
