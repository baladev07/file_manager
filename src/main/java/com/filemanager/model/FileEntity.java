package com.filemanager.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class FileEntity extends BaseEntity{

    private String fileName;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int fileId;

    private int fileSize;

    private String fileFormat;


    @ManyToOne
    @JoinColumn(name = "directory_id")
    private DirectoryEntity directoryEntity;

}
