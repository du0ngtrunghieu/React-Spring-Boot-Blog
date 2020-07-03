package com.trunghieu.api.file;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.exception.FileManagerException;
import com.trunghieu.common.exception.FileNotFoundException;
import com.trunghieu.common.exception.StorageException;
import com.trunghieu.common.response.FileViewResponse;
import com.trunghieu.common.storage.StorageProperties;
import com.trunghieu.common.util.CommonUtils;
import com.trunghieu.common.util.constant.APIConstants;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 25/6/2020.
 * Class: FileService.java
 * By : Admin
 */

@Service
public class FileService {

    private final Path rootLocation;

    @Autowired
    public FileService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    List<String> imageExt = Arrays.asList(".png", ".jpeg", ".jpg", ".gif");
    public List<FileViewResponse> getAll(){
        List<FileViewResponse> files = new ArrayList<>();
        try{
            Stream<Path> directoryStream = Files.walk(this.rootLocation, 100);
            directoryStream.forEach(p ->{
                BasicFileAttributes attrs = null;
                try {
                    attrs = Files.readAttributes(p, BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileViewResponse fileViewResponse = new FileViewResponse();
                assert attrs != null;
                if(attrs.isDirectory()){

                    fileViewResponse.setId(p.getFileName().toString());
                    fileViewResponse.setName(p.getFileName().toString());
                    fileViewResponse.setIsDir(true);
                    fileViewResponse.setExt(null);
                    fileViewResponse.setSize(null);
                    fileViewResponse.setParentId(p.getParent() != null ? p.getParent().getFileName().toString(): null);
                    fileViewResponse.setChildrenIds(null);
                    fileViewResponse.setModDate(attrs.lastModifiedTime().toInstant());
                    fileViewResponse.setPathReal(p.toFile().getPath().replace("\\","/"));
                }else{
                    fileViewResponse.setId(p.getFileName().toString());
                    fileViewResponse.setName(p.getFileName().toString());
                    fileViewResponse.setIsDir(false);
                    fileViewResponse.setSize(attrs.size());
                    fileViewResponse.setExt("." +p.getFileName().toString().substring(p.getFileName().toString().lastIndexOf(".") + 1));
                    fileViewResponse.setParentId(p.getParent().getFileName().toString());
                    fileViewResponse.setModDate(attrs.lastModifiedTime().toInstant());
                    if (imageExt.stream().anyMatch(fileViewResponse.getExt()::equalsIgnoreCase)) {

                        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path(APIConstants.PATH_MEDIA)
                                    .toUriString();
                        String rootPath = p.toFile().getPath().replace("\\"+p.getFileName(),"").replace("\\","/");
                        fileViewResponse.setThumbnailUrl(uri+rootPath.replace("/","%2F")+"/"+p.getFileName());
                    }
                    fileViewResponse.setPathReal(p.toFile().toPath().toString().replace("\\"+p.getFileName(),"").replace("\\","/"));
                    fileViewResponse.setIsSymlink(attrs.isSymbolicLink());
                }

                files.add(fileViewResponse);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<FileViewResponse> temp = new ArrayList<>(files);
        return files.stream().map(x -> {
            FileViewResponse fileViewResponse1 = new FileViewResponse();
            BeanUtils.copyProperties(x,fileViewResponse1);
            fileViewResponse1.setChildrenIds(temp.stream()
                    .filter(p -> x.getId().equals(p.getParentId()))
                    .map(FileViewResponse::getName)
                    .collect(Collectors.toList())
            );

            return fileViewResponse1;
        }).collect(Collectors.toList());
    }
    public Resource getFile(String path){
        Path file = Paths.get(path);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read path: " + path);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read path: " + path, e);
        }
    }
    public FileViewResponse uploadFile(MultipartFile file, String path,String parent){
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileViewResponse fileViewResponse = new FileViewResponse();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path newPath = Paths.get(path);
                String newNameFile = UUID.randomUUID().toString();
                String ext = CommonUtils.getExtensionByStringHandling(filename);
                Files.copy(inputStream, newPath.resolve(String.format("%s.%s",newNameFile,ext)),
                        StandardCopyOption.REPLACE_EXISTING);
                fileViewResponse.setId(String.format("%s.%s",newNameFile,ext));
                fileViewResponse.setName(String.format("%s.%s",newNameFile,ext));
                fileViewResponse.setIsDir(false);
                fileViewResponse.setSize(file.getSize());

                fileViewResponse.setExt("."+ext);
                fileViewResponse.setParentId(null);
                fileViewResponse.setModDate(Instant.now());
                fileViewResponse.setParentId(parent);
                if (imageExt.stream().anyMatch(fileViewResponse.getExt()::equalsIgnoreCase)) {
                    String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(APIConstants.PATH_MEDIA)
                            .toUriString();
                    fileViewResponse.setThumbnailUrl(uri+path.replace("/","%2F")+"/"+String.format("%s.%s",newNameFile,ext));
                }
                fileViewResponse.setPathReal(path);
            }
            return fileViewResponse;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

    }

    public FileViewResponse createFolder(String path){
        FileViewResponse fileViewResponse = new FileViewResponse();
        try {
            File f = new File(path);
            if(f.exists() && f.isDirectory()){
                throw new FileManagerException("Error in create-folder action, folder already exists");
            }
            Path newPath = Paths.get(path);
            Path p = Files.createDirectories(newPath);
            fileViewResponse.setId(p.getFileName().toString());
            fileViewResponse.setName(p.getFileName().toString());
            fileViewResponse.setIsDir(true);
            fileViewResponse.setExt(null);
            fileViewResponse.setSize(null);
            fileViewResponse.setParentId(p.getParent() != null ? p.getParent().getFileName().toString(): null);
            fileViewResponse.setChildrenIds(new ArrayList<>());
            fileViewResponse.setModDate(Instant.now());
            fileViewResponse.setPathReal(p.toFile().getPath());
            return fileViewResponse;
        }catch (FileAlreadyExistsException ex) {
            throw new FileManagerException("Error in create-folder action, folder already exists", ex);
        } catch (IOException e) {
            throw new FileManagerException("Error in create-folder action", e);
        }
    }

    public ApiStatusDto deleteFile(String path){
        Map<String, String> rs = new HashMap<>();
        try {
            File file = new File(path);
            if(!file.delete()){
                throw new FileManagerException("Error in delete file action, file not found !");
            }else{
                rs.put("id" , file.getName());
            }
            return new ApiStatusDto(true,"Delete file thành công !",rs);
        }catch (Exception ex){
            throw new FileManagerException("Error in delete folder action", ex);
        }

    }
    public ApiStatusDto deleteFolder(String path){
       try {
           Files.walk(Paths.get(path))
                   .sorted(Comparator.reverseOrder())
                   .map(Path::toFile)
                   .forEach(File::delete);
           return new ApiStatusDto(true,"Delete folder thành công !",null);
       }catch (Exception ex){
           throw new FileManagerException("Error in delete folder action", ex);
       }

    }
    public List<FileViewResponse> uploadMultipleFile(MultipartFile[] files, String path,String parent){
        List<FileViewResponse> fileViewResponses = new ArrayList<>();

        Arrays.stream(files).forEach(file->{
            FileViewResponse fileViewResponse = this.uploadFile(file,path,parent);
            fileViewResponses.add(fileViewResponse);
        });
        return fileViewResponses;
    }
    public ApiStatusDto deleteMultileFile(List<String> paths){
        Map<String,List<String>> rs = new HashMap<>();
        List<String> name = new ArrayList<>();
        try {
            paths.forEach(path ->{
                File file = new File(path);
                if(!file.delete()){
                    throw new FileManagerException("Error in delete file action, file not found !");
                }else{
                    name.add(file.getName());
                }
            });
            rs.put("id",name);
            return new ApiStatusDto(true,"Delete file thành công !",rs);
        }catch (Exception ex){
            throw new FileManagerException("Error in delete folder action", ex);
        }
    }
}
