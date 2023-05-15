package com.filemanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.File;
import java.util.List;


@Entity
@Getter
@Setter
public class DirectoryEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_entity_seq")
    @SequenceGenerator(name = "my_entity_seq", allocationSize = 10, initialValue = 10000001)
    private int directoryId;

    private String directoryName;

    private String directoryPath;

    @ManyToOne
    @JoinColumn(name="parent_directory_id",updatable = false)
    private DirectoryEntity parentDirectory;

}
