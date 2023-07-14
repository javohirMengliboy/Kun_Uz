package com.example.service;

import com.example.dto.FilterResultDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileCustomRepository profileCustomRepository;
    /** 1 */
    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.isVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getPhotoId());
        return dto;
    }

    /** 2 */
    public Boolean updateStatusById(ProfileDTO dto, Integer id) {
        int effectedRows = profileRepository.updateStatusById(dto.getStatus(),id);
        return effectedRows > 0;
    }

    /** 4 */
    public List<ProfileDTO> getAll() {
        List<ProfileDTO> dtoList = profileRepository.getAll();
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        return dtoList;
    }

    /** 5 */
    public Boolean deleteProfileById(Integer id) {
        int effectedRows = profileRepository.deleteProfileById(id);
        return effectedRows > 0;
    }

    /** 7 */
    public PageImpl<ProfileDTO> filter(ProfileFilterDTO dto,Integer page, Integer size) {
        FilterResultDTO result = profileCustomRepository.filter(dto, page,  size);
        List<ProfileDTO> dtoList = result.getList();
        return new PageImpl<>(dtoList, PageRequest.of(page,size), result.totalCount);
    }
}
