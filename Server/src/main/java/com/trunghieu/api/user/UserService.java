package com.trunghieu.api.user;

import com.trunghieu.api.file.FileSystemStorageService;
import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.InfoUserRequestDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.exception.AuthenticationException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.Category;
import com.trunghieu.common.model.File;
import com.trunghieu.common.model.Role;
import com.trunghieu.common.model.User;
import com.trunghieu.common.repository.FileRepository;
import com.trunghieu.common.repository.PermissionRepository;
import com.trunghieu.common.repository.RoleRepository;
import com.trunghieu.common.repository.UserRepository;
import com.trunghieu.common.response.*;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 12/6/2020.
 * Class: UserService.java
 * By : Admin
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    FileSystemStorageService fileSystemStorageService;
    public PagedResponse<UserResponse> getAll(PaginationDto paginationDto){
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize(), Sort.Direction.DESC, "createdAt");
        Page<User> users = userRepository.findAllByOrderByIdDesc(pageable);
        if(users.getNumberOfElements() == 0){
            return new PagedResponse<>(Collections.emptyList(),users.getNumber(),users.getSize(),
                    users.getTotalElements(),users.getTotalPages(),users.isLast()
            );
        }
        List<UserResponse> userResponses = users.stream()
                .map(user->{
                    UserResponse userResponse = new UserResponse();
                    BeanUtils.copyProperties(user,userResponse);
                    if(user.getRoleId() != null){
                        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(
                                () -> new ResourceNotFoundException("ROLE","ID",user.getId())
                        );
                        List<PermissionResponse> permissionResponses = permissionRepository.findPermissionByUserId(role.getId());
                        RoleDetailResponse roleDetailResponse = new RoleDetailResponse();
                        roleDetailResponse.setId(role.getId());
                        roleDetailResponse.setDisplayName(role.getDisplayName());
                        roleDetailResponse.setName(role.getName());
                        roleDetailResponse.setPermissions(permissionResponses);
                        userResponse.setRole(roleDetailResponse);
                    }
                    if(user.getFileId() != null){
                        File file = fileRepository.findById(user.getFileId()).orElseThrow(
                                () -> new ResourceNotFoundException("File","ID",user.getFileId())
                        );
                        FileResponse fileResponse = new FileResponse();
                        fileResponse.setName(file.getNameFile());
                        fileResponse.setSize(file.getSizeFile());
                        fileResponse.setUri(file.getUrlFile());
                        fileResponse.setType(file.getTypeFile());
                        fileResponse.setCreatedAt(file.getCreatedAt());
                        fileResponse.setUpdatedAt(file.getUpdatedAt());
                        userResponse.setFile(fileResponse);
                    }
                    return userResponse;
                }).collect(Collectors.toList());
        return new PagedResponse<>(userResponses,users.getNumber(),users.getSize(),
                users.getTotalElements(),users.getTotalPages(),users.isLast());
    }
    public UserResponse getDetailUser(String userNameOrEmail){
        User user = userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail).orElseThrow(
                () -> new ResourceNotFoundException("UserName or Email")
        );
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user,userResponse);
        if(user.getRoleId() != null){
            Role role = roleRepository.findById(user.getRoleId()).orElseThrow(
                    () -> new ResourceNotFoundException("ROLE","ID",user.getId())
            );
            List<PermissionResponse> permissionResponses = permissionRepository.findPermissionByUserId(role.getId());
            RoleDetailResponse roleDetailResponse = new RoleDetailResponse();
            roleDetailResponse.setId(role.getId());
            roleDetailResponse.setDisplayName(role.getDisplayName());
            roleDetailResponse.setName(role.getName());
            roleDetailResponse.setPermissions(permissionResponses);
            userResponse.setRole(roleDetailResponse);
        }
        return userResponse;
    }
    public ApiStatusDto deleteUser(String userNameOrEmail){
        User user = userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail).orElseThrow(
                () -> new ResourceNotFoundException("User name or Email")
        );
        if(user.getRoleId() != null && user.getRole().isAdministrator()){
            throw new AuthenticationException("Không có quyền xoá Người dùng này !");
        }
        userRepository.delete(user);
        Map<String, String> rs = new HashMap<>();
        rs.put("username",user.getUsername());
        return new ApiStatusDto(true, MessageConstants.USER_DELETE_SUCCESS,rs);
    }
    public ApiStatusDto deleteUsers(List<String> userNameOrEmail){
        Map<String, List<String>> rs = new HashMap<>();
        List<String> userNameList = new ArrayList<>();
        userNameOrEmail.forEach(emailOrUserName -> {
            Optional<User> user = userRepository.findByUsernameOrEmail(emailOrUserName,emailOrUserName);
            if(user.isPresent()){
                if(user.get().getRoleId() == null){
                    userRepository.delete(user.get());
                    userNameList.add(user.get().getUsername());
                }else{
                    if(!user.get().getRole().isAdministrator()){
                        userRepository.delete(user.get());
                        userNameList.add(user.get().getUsername());
                    }
                }
            }
        });
        rs.put("username",userNameList);
        return new ApiStatusDto(true,MessageConstants.USER_DELETE_SUCCESS,rs);
    }
    public ApiStatusDto deleteUserByIds(List<Long> ids){
        Map<String, List<String>> rs = new HashMap<>();
        List<String> userNameList = new ArrayList<>();
        ids.forEach(id -> {
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                if(user.get().getRoleId() == null){
                    userRepository.delete(user.get());
                    userNameList.add(user.get().getUsername());
                }else{
                    if(!user.get().getRole().isAdministrator()){
                        userRepository.delete(user.get());
                        userNameList.add(user.get().getUsername());
                    }
                }
            }
        });
        rs.put("username",userNameList);
        return new ApiStatusDto(true,MessageConstants.USER_DELETE_SUCCESS,rs);
    }
    public ApiStatusDto updateUser(MultipartFile avatar ,String userNameOrEmail, InfoUserRequestDto infoUserRequestDto){
        User user = userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail).orElseThrow(
                () -> new ResourceNotFoundException("User","Username or Email",userNameOrEmail)
        );
        Role role = roleRepository.findById(infoUserRequestDto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role","ID",infoUserRequestDto.getRoleId())
        );
        if(role.isAdministrator()){
            throw new AuthenticationException("Không có quyền để set quyền này");
        }
        if(avatar != null){
            FileResponse fileResponse = fileSystemStorageService.uploadFile(avatar);
            user.setAvatar(fileResponse.getUri());
        }
        user.setName(infoUserRequestDto.getName());
        user.setDescription(infoUserRequestDto.getDescription());
        user.setStatus(infoUserRequestDto.status);
        user.setRoleId(role.getId());
        userRepository.save(user);
        Map<String, String> rs = new HashMap<>();
        rs.put("username",user.getUsername());
        return new ApiStatusDto(true,MessageConstants.USER_UPDATE_SUCCESS,rs);
    }
    public List<UserResponse> searchUser(String search){
        List<User> users = userRepository.findAllByNameContainingOrUsernameContainingOrEmailContaining(search,search,search);
        return users.stream().map(x -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setAvatar(x.getAvatar());
            userResponse.setId(x.getId());
            userResponse.setDescription(x.getDescription());
            userResponse.setEmail(x.getEmail());
            userResponse.setName(x.getName());
            userResponse.setStatus(x.getStatus());
            userResponse.setVerified(x.isVerified());
            userResponse.setUsername(x.getUsername());
            userResponse.setCreatedAt(x.getCreatedAt());
            userResponse.setUpdatedAt(x.getUpdatedAt());
            return userResponse;
        }).collect(Collectors.toList());
    }
    public List<UserResponse> searchUserNotIn(String search , List<Long> ids){
        return userRepository.findUserNotIn(search,ids);
    }
    public ApiStatusDto addUserToRole(Long roleId,Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","Id",userId)
        );
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("Role","ID",roleId)
        );
        user.setRoleId(role.getId());
        userRepository.save(user);
        Map<String, Long> rs = new HashMap<>();
        rs.put("id",user.getId());
        return new ApiStatusDto(true,"Thêm Role cho user thành công !",rs);
    }
}
