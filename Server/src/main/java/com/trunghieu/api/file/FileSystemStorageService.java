package com.trunghieu.api.file;

import com.trunghieu.common.exception.FileNotFoundException;
import com.trunghieu.common.exception.StorageException;
import com.trunghieu.common.model.File;
import com.trunghieu.common.repository.FileRepository;
import com.trunghieu.common.response.FileResponse;
import com.trunghieu.common.storage.StorageProperties;
import com.trunghieu.common.util.CommonUtils;
import com.trunghieu.common.util.constant.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created on 13/6/2020.
 * Class: FileSystemStorageService.java
 * By : Admin
 */
@Service
public class FileSystemStorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    @Autowired
    FileRepository fileRepository;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }


    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                String nameFileNew = CommonUtils.getExtensionByStringHandling(filename);
                Files.copy(inputStream, this.rootLocation.resolve(nameFileNew),
                        StandardCopyOption.REPLACE_EXISTING);

            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public List<FileResponse> uploadFiles(List<MultipartFile> files){
        List<FileResponse> fileResponses = new ArrayList<>();
        files.forEach(file -> {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if (!file.isEmpty()) {
                try {
                    InputStream inputStream = file.getInputStream();
                    String newNameFile = UUID.randomUUID().toString();
                    String ext = CommonUtils.getExtensionByStringHandling(filename);
                    Files.copy(inputStream, this.rootLocation.resolve(String.format("%s.%s",newNameFile,ext)),
                            StandardCopyOption.REPLACE_EXISTING);
                    FileResponse fileResponse = new FileResponse();
                    String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(APIConstants.PATH_FILE)
                            .path(String.format("%s.%s",newNameFile,ext))
                            .toUriString();
                    fileResponse.setName(newNameFile);
                    fileResponse.setType(file.getContentType());
                    fileResponse.setSize(file.getSize());
                    fileResponse.setUri(uri);
                    fileResponses.add(fileResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return fileResponses;
    }

    public FileResponse uploadFile(MultipartFile file){
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileResponse fileResponse = new FileResponse();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                String newNameFile = UUID.randomUUID().toString();
                String ext = CommonUtils.getExtensionByStringHandling(filename);
                Files.copy(inputStream, this.rootLocation.resolve(String.format("%s.%s",newNameFile,ext)),
                        StandardCopyOption.REPLACE_EXISTING);
                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(APIConstants.PATH_FILE)
                            .path(String.format("%s.%s",newNameFile,ext))
                            .toUriString();
                fileResponse.setName(newNameFile);
                fileResponse.setType(file.getContentType());
                fileResponse.setSize(file.getSize());
                fileResponse.setUri(uri);

            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return fileResponse;
    }


    /* Database */

    public FileResponse saveFile(MultipartFile file) {
        FileResponse fileResponse = new FileResponse();
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                String nameFileNew = CommonUtils.getExtensionByStringHandling(filename);
                Files.copy(inputStream, this.rootLocation.resolve(nameFileNew),
                        StandardCopyOption.REPLACE_EXISTING);
                File fileModel = new File();
                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(APIConstants.PATH_FILE)
                        .path(nameFileNew)
                        .toUriString();
                fileModel.setNameFile(nameFileNew);
                fileModel.setSizeFile(file.getSize());
                fileModel.setTypeFile(file.getContentType());
                fileModel.setUrlFile(url);
                fileRepository.save(fileModel);


                fileResponse.setName(fileModel.getNameFile());
                fileResponse.setSize(fileModel.getSizeFile());
                fileResponse.setUri(fileModel.getUrlFile());
                fileResponse.setType(fileModel.getTypeFile());

            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return fileResponse;
    }

    public boolean deleteFile(String filename){
        Path fileToDeletePath = load(filename);
        try {
            Files.delete(fileToDeletePath);
            return true;
        }catch (IOException ex){
            return false;
        }

    }



}
