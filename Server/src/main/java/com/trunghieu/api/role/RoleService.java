package com.trunghieu.api.role;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.RoleRequestDto;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.Role;
import com.trunghieu.common.model.RoleHasPermission;
import com.trunghieu.common.model.User;
import com.trunghieu.common.repository.PermissionRepository;
import com.trunghieu.common.repository.RoleHasPermissionRepository;
import com.trunghieu.common.repository.RoleRepository;
import com.trunghieu.common.repository.UserRepository;
import com.trunghieu.common.response.PermissionResponse;
import com.trunghieu.common.response.RoleDetailResponse;
import com.trunghieu.common.response.RoleResponse;
import com.trunghieu.common.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2/6/2020.
 * Class: RoleService.java
 * By : Admin
 */
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleHasPermissionRepository roleHasPermission;
    public List<RoleResponse> getAllRole(){
        return roleRepository.findAllRoleCountPermissionUser();
    }
    public RoleDetailResponse getDetailRole(Long id){
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ROLE","ID",id)
        );
        List<PermissionResponse> permissionResponses = permissionRepository.findPermissionByUserId(role.getId());
        RoleDetailResponse roleDetailResponse = new RoleDetailResponse();
        roleDetailResponse.setId(role.getId());
        roleDetailResponse.setDisplayName(role.getDisplayName());
        roleDetailResponse.setName(role.getName());
        roleDetailResponse.setPermissions(permissionResponses);
        List<UserResponse> userResponses = userRepository.findByRoleId(role.getId()).stream().map(x ->{
            UserResponse userResponse = new UserResponse();
            userResponse.setAvatar(x.getAvatar());
            userResponse.setId(x.getId());
            userResponse.setDescription(x.getDescription());
            userResponse.setEmail(x.getEmail());
            userResponse.setName(x.getName());
            userResponse.setStatus(x.getStatus());
            userResponse.setVerified(x.isVerified());
            userResponse.setUsername(x.getUsername());
            return userResponse;
        }).collect(Collectors.toList());
        roleDetailResponse.setUserResponses(userResponses);
        roleDetailResponse.setStaff(role.isStaff());
        return roleDetailResponse;
    }
    public ApiStatusDto deleteRole(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("Role","Id",roleId)
        );
        roleHasPermission.findAllByRoleId(role.getId()).forEach(rs -> roleHasPermission.delete(rs));
        List<User> users = userRepository.findByRoleId(role.getId());
        if(!users.isEmpty()){
            users.forEach(user -> {
                user.setRoleId(null);
                userRepository.save(user);
            });
        }
        Map<String, Long> rs = new HashMap<>();
        rs.put("id",role.getId());
        roleRepository.delete(role);
        return new ApiStatusDto(true,"Xoá thành công !",rs);
    }
    public ApiStatusDto deleteMultipleRoles(List<Long> roleIds){
        Map<String , List<Long>> rs = new HashMap<>();
        List<Long> listRoles = new ArrayList<>();
        roleIds.forEach(roleId->{
            Role role = roleRepository.findById(roleId).orElseThrow(
                    () -> new ResourceNotFoundException("Role","Id",roleId)
            );
            roleHasPermission.findAllByRoleId(role.getId()).forEach(t -> roleHasPermission.delete(t));
            List<User> users = userRepository.findByRoleId(role.getId());
            if(!users.isEmpty()){
                users.forEach(user -> {
                    user.setRoleId(null);
                    userRepository.save(user);
                });
            }
            roleRepository.delete(role);
            listRoles.add(role.getId());
        });
        rs.put("id",listRoles);
        return new ApiStatusDto(true,"Xoá danh sách Role thành công !",rs);

    }
    public ApiStatusDto updateRole(Long roleId, RoleRequestDto roleRequestDto){
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("Role","Id",roleId)
        );
        role.setDisplayName(roleRequestDto.getDisplayName());
        role.setName(roleRequestDto.getName());
        role.setStaff(roleRequestDto.isStaff);
        roleRepository.save(role);
        return new ApiStatusDto(true,"Cập nhật thành công !",role);
    }
    public ApiStatusDto createRole(RoleRequestDto roleRequestDto){
        Role role = new Role();
        role.setName(roleRequestDto.getName());
        role.setDisplayName(roleRequestDto.getDisplayName());
        role.setStaff(roleRequestDto.isStaff);
        roleRepository.save(role);
        return new ApiStatusDto(true,"Tạo mới Role thành công !",role);
    }
    public ApiStatusDto deleteUserHasRole(Long roleId,Long userId){
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("Role","Id",roleId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","Id",roleId)
        );
        Map<String, Long> rs = new HashMap<>();
        rs.put("id",user.getId());
        user.setRoleId(null);
        userRepository.save(user);
        return new ApiStatusDto(true,"Xoá Role của user thành công" ,rs);
    }

    public List<RoleDetailResponse> getAllRoleDetails(){
        List<Role> roles = roleRepository.findAll();
        List<RoleDetailResponse> roleDetailResponses = new ArrayList<>();

        roles.forEach(role -> {
            RoleDetailResponse roleDetailResponse = new RoleDetailResponse();
            roleDetailResponse.setName(role.getName());
            roleDetailResponse.setDisplayName(role.getDisplayName());
            roleDetailResponse.setId(role.getId());
            roleDetailResponse.setStaff(role.isStaff());
            roleDetailResponse.setAdministrator(role.isAdministrator());
            List<PermissionResponse> permission = permissionRepository.findPermissionByUserId(role.getId());
            List<UserResponse> userResponseList = role.getUsers().stream().map(x ->{
                UserResponse userResponse = new UserResponse();
                userResponse.setAvatar(x.getAvatar());
                userResponse.setId(x.getId());
                userResponse.setDescription(x.getDescription());
                userResponse.setEmail(x.getEmail());
                userResponse.setName(x.getName());
                userResponse.setStatus(x.getStatus());
                userResponse.setVerified(x.isVerified());
                userResponse.setUsername(x.getUsername());
                return userResponse;
            }).collect(Collectors.toList());
            roleDetailResponse.setUserResponses(userResponseList);
            roleDetailResponse.setPermissions(permission);
            roleDetailResponses.add(roleDetailResponse);

        });
        return roleDetailResponses;
    }

}
