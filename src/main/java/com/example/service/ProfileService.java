package com.example.service;

import com.example.dto.FilterResultDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import com.example.util.ProfileUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;
    @Autowired
    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private ProfileCustomRepository profileCustomRepository;
    @Autowired
    public void setProfileCustomRepository(ProfileCustomRepository profileCustomRepository) {
        this.profileCustomRepository = profileCustomRepository;
    }

    private AttachService attachService;
    @Autowired
    public void setAttachService(AttachService attachService) {
        this.attachService = attachService;
    }

    public ProfileDTO create(ProfileDTO dto) {
        ProfileUtil.check(dto);
        Optional<ProfileEntity> emailEntity = profileRepository.findByEmail(dto.getEmail());
        if (emailEntity.isPresent()){
            throw new AppBadRequestException("This email already exists");
        }
        Optional<ProfileEntity> phoneEntity = profileRepository.findByPhone(dto.getPhone());
        if (phoneEntity.isPresent()){
            throw new AppBadRequestException("This phone e already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setPrtId(SpringSecurityUtil.getCurrentUserId());
        if (dto.getImageId() != null){
            entity.setImage(attachService.get(dto.getImageId()));
        }
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(ProfileDTO dto, Integer id) {
        profileRepository.save(checkingForUpdate(dto,get(id)));
        return true;
    }

    public Boolean updateDetail(ProfileDTO dto) {
        profileRepository.save(checkingForUpdate(dto,get(SpringSecurityUtil.getCurrentUserId())));
        return true;
    }

    private ProfileEntity checkingForUpdate(ProfileDTO dto, ProfileEntity entity) {
        if (dto == null){
            throw new AppBadRequestException("No data found to change");
        }
        if (dto.getName() != null){
            ProfileUtil.checkingName(dto.getName());
            entity.setName(dto.getName());
        }
        if (dto.getSurname() != null){
            ProfileUtil.checkingSurname(dto.getSurname());
            entity.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null){
            ProfileUtil.checkingEmail(dto.getEmail());
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null){
            ProfileUtil.checkingPhone(dto.getPhone());
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPassword() != null){
            ProfileUtil.checkingPassword(dto.getPassword());
            entity.setPassword(MD5Util.encode(dto.getPassword()));
        }
        if (dto.getImageId() != null){
            entity.setImage(attachService.get(dto.getImageId()));
        }
        if (dto.getStatus() != null){
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null){
            entity.setRole(dto.getRole());
        }
        return entity;
    }

    /** 4 */
    public List<ProfileDTO> getAll() {
        List<ProfileEntity> entityList = profileRepository.findAllByVisibleTrue();
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        List<ProfileDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    private ProfileDTO toDto(ProfileEntity entity) {
        return new ProfileDTO(entity.getId(), entity.getName(), entity.getSurname(),
                entity.getEmail(), entity.getPhone(), entity.getPassword(), entity.getStatus(),
                entity.getRole(), entity.getVisible(), entity.getCreatedDate(), entity.getImage().getId());
    }

    /** 5 */
    public Boolean deleteProfileById(Integer id) {
        int effectedRows = profileRepository.deleteProfileById(id);
        return effectedRows > 0;
    }

    /** 7 */
    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filterDTO,Integer page, Integer size) {
        FilterResultDTO result = profileCustomRepository.filter(filterDTO, page-1,  size);
        List<ProfileDTO> dtoList = result.getList();
        return new PageImpl<>(dtoList, PageRequest.of(page,size), result.totalCount);
    }

    public ProfileEntity get(Integer id){
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Profile not found"));
    }


    public String updateImage(ProfileDTO dto) {
        ProfileEntity entity = get(SpringSecurityUtil.getCurrentUserId());
        entity.setImage(attachService.get(dto.getImageId()));
        profileRepository.save(entity);
        return "Image setted";
    }
}
