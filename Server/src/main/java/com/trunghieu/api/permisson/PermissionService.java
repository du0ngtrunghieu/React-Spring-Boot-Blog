package com.trunghieu.api.permisson;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.PermissionRequestDto;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.*;
import com.trunghieu.common.repository.*;
import com.trunghieu.common.response.PermissionResponse;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 2/6/2020.
 * Class: PermissionService.java
 * By : Admin
 */
@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionTypeRepository permissionTypeRepository;
    @Autowired
    ModuleTypeRepository moduleTypeRepository;
    @Autowired
    RoleHasPermissionRepository roleHasPermissionRepository;
    @Autowired
    RoleRepository roleRepository;
    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }
    public List<PermissionType> getAllPermissionType(){
        return permissionTypeRepository.findAll();
    }
    public List<ModuleType> getAllModuleType(){
        return moduleTypeRepository.findAll();
    }

    public ApiStatusDto createPermission(PermissionRequestDto permissionRequestDto){
        PermissionType permissionType = permissionTypeRepository.findById(permissionRequestDto.getPermissionTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("PermissionType","ID",permissionRequestDto.getPermissionTypeId())
        );
        ModuleType moduleType = moduleTypeRepository.findById(permissionRequestDto.getModuleTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("ModuleType","ID",permissionRequestDto.getModuleTypeId())
        );
        Permission permission = new Permission();
        permission.setActive(permissionRequestDto.isActive());
        permission.setDescription(permissionRequestDto.getDescription());
        permission.setModuleId(moduleType.getId());
        permission.setPermissionTypeId(permissionType.getId());
        permission.setName(permissionRequestDto.getName());
        Permission rs =  permissionRepository.save(permission);
        return new ApiStatusDto(true, MessageConstants.PERMISSION_REGISTER_SUCCESS,rs);
    }
    public List<Permission> getAllPermission(){
        return permissionRepository.findAll();
    }
    public ApiStatusDto updatePermission(Long id,PermissionRequestDto permissionRequestDto){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Permission permission = permissionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Permission","ID",id)
        );
        PermissionType permissionType = permissionTypeRepository.findById(permissionRequestDto.getPermissionTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("PermissionType","ID",permissionRequestDto.getPermissionTypeId())
        );
        ModuleType moduleType = moduleTypeRepository.findById(permissionRequestDto.getModuleTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("ModuleType","ID",permissionRequestDto.getModuleTypeId())
        );

        permission.setActive(permissionRequestDto.isActive());
        permission.setDescription(permissionRequestDto.getDescription());
        permission.setModuleId(moduleType.getId());
        permission.setPermissionTypeId(permissionType.getId());
        permission.setName(permissionRequestDto.getName());
        Permission rs =  permissionRepository.save(permission);

        return new ApiStatusDto(true, MessageConstants.PERMISSION_UPDATE_SUCCESS,rs);
    }
    public ApiStatusDto deletePermission(Long id){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Permission permission = permissionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Permission","ID",id)
        );
        permissionRepository.deleteById(id);
        return new ApiStatusDto(true, MessageConstants.PERMISSION_DELETE_SUCCESS,permission);
    }




    /* ROLE HAS PERMISSION */

    public ApiStatusDto deleteRoleHasPermission(Long idRole,Long idPermission){
        if(idPermission == null || idRole == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        RoleHasPermission roleHasPermission = roleHasPermissionRepository.findByPermissionIdAndRoleId(idPermission,idRole)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Role has Permission","ID",idPermission+"-"+idRole)
                );
        roleHasPermissionRepository.delete(roleHasPermission);
        return new ApiStatusDto(true, MessageConstants.ROLE_PERMISSION_DELETE_SUCCESS, roleHasPermission);

    }
    // Thêm RoleHasPermission

    public ApiStatusDto createRoleHasPermission(Long idRole,Long idPermission){
        if(idPermission == null || idRole == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        RoleHasPermission rs;
        Optional<RoleHasPermission> roleHasPermission = roleHasPermissionRepository.findByPermissionIdAndRoleId(idPermission,idRole);
        if(roleHasPermission.isPresent()){
            throw new BadRequestException("Role has Perrmission đã tồn tại !");
        }else{
            RoleHasPermission roleHasPermission1 = new RoleHasPermission();
            roleHasPermission1.setPermissionId(idPermission);
            roleHasPermission1.setRoleId(idRole);
            rs = roleHasPermissionRepository.save(roleHasPermission1);
        }
        return new ApiStatusDto(true, MessageConstants.ROLE_PERMISSION_REGISTER_SUCCESS, rs);
    }
    public List<PermissionResponse> searchNameOrDescription(String search){
        List<Permission> permissionList = permissionRepository.findAllByNameContainingOrDescriptionContaining(search,search);
        return permissionList.stream().map(permission -> {
            PermissionResponse permissionResponse = new PermissionResponse();
            permissionResponse.setId(permission.getId());
            permissionResponse.setActive(permission.isActive());
            permissionResponse.setName(permission.getName());
            permissionResponse.setDescription(permission.getDescription());
            permissionResponse.setModuleId(permission.getModuleId());
            permissionResponse.setCreatedAt(permission.getCreatedAt());
            permissionResponse.setUpdatedAt(permission.getUpdatedAt());
            permissionResponse.setPermissionTypeId(permission.getPermissionTypeId());
            return permissionResponse;
        }).collect(Collectors.toList());

    }
    public List<PermissionResponse> searchWithNotIn(String search,List<Long> ids){
        return permissionRepository.findPermissionNotIN(search,search,ids);
    }

}
