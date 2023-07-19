package com.example.service;

import com.example.dto.FilterResultDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileCustomRepository profileCustomRepository;
    /** 1 */
    public ProfileDTO create(ProfileDTO dto, Integer ptrId) {
        check(dto);
        Optional<ProfileEntity> emailEntity = profileRepository.findByEmail(dto.getEmail());
        if (emailEntity.isPresent()){
            throw new AppBadRequestException("This email already exists");
        }
        Optional<ProfileEntity> phoneEntity = profileRepository.findByPhone(dto.getPhone());
        if (phoneEntity.isPresent()){
            throw new AppBadRequestException("This phone already exists");
        }


        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setPrtId(ptrId);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.isVisible() ? 1 : 0);
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }





    /** 2 */
    public Boolean updateForAdmin(ProfileDTO dto, Integer id) {
        profileRepository.save(checkingForUpdate(dto,profileRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Profile not found"))));
        return true;
    }

    public Boolean updateForUser(ProfileDTO dto, Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Profile not found"));
        profileRepository.save(checkingForUpdate(dto,entity));
        return true;
    }

    private ProfileEntity checkingForUpdate(ProfileDTO dto, ProfileEntity entity) {
        if (dto.getName() != null){
            entity.setName(dto.getName());
        }
        if (dto.getSurname() != null){
            entity.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null){
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null){
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPassword() != null){
            entity.setPassword(MD5Util.encode(dto.getPassword()));
        }
        if (dto.getPhotoId() != null){
            entity.setPhotoId(dto.getPhotoId());
        }
        if (dto.getStatus() != null){
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null){
            entity.setRole(dto.getRole());
        }
        if (dto.getVisible() != null){
            entity.setVisible(dto.getVisible() != 0);
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
                entity.getRole(), entity.isVisible(), entity.getCreatedDate(), entity.getPhotoId());
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


    private void check(ProfileDTO dto) {
        checkingName(dto.getName());
        checkingSurname(dto.getSurname());
        if (!checkingPhone(dto.getPhone())){
            throw new AppBadRequestException("Number is invalid");
        }
        checkingPassword(dto.getPassword());
        checkingEmail(dto.getEmail());
    }

    private void checkingEmail(String email) {
        if (!email.contains("@") || !email.contains("mail")){
            throw new AppBadRequestException("Email is invalid");
        }
    }

    private void checkingPassword(String password) {
        if (password.isBlank() && password.length() < 4) {
            throw new AppBadRequestException("There are not enough characters");
        }
        char[] arr = password.toCharArray();
        int upper = 0;
        int lower = 0;
        int number = 0;
        for (char c : arr) {
            if (c == 32){
                throw new AppBadRequestException("There can be no spaces");
            }else if (c > 64 && c < 91) {
                upper++;
            }else if (c > 96 && c < 123) {
                lower++;
            }else if (c > 47 && c < 58) {
                number++;
            }
        }
        if (upper == 0 || lower == 0 || number == 0){
            throw new AppBadRequestException("Password must contain at least one uppercase letter, one lowercase letter, one number and one symbol");
        }
    }

    public void checkingName(String value){
        if (value.length() < 3 || value.isBlank()){
            throw new AppBadRequestException("There are not enough characters");
        }
        if (value.charAt(0) < 64 || value.charAt(0) > 91) {
            throw new AppBadRequestException("Note the capital letter");
        }
        char[] arr = value.toCharArray();
        for (char c : arr) {
            if (c > 32 && c < 58) {
                throw new AppBadRequestException("There is an excess character");
            }
        }
    }

    public void checkingSurname(String value){
        if (value.length() < 2 || value.isBlank()){
            throw new AppBadRequestException("There are not enough characters");
        }
        if (value.charAt(0) < 64 || value.charAt(0) > 91) {
            throw new AppBadRequestException("Note the capital letter");
        }
        char[] arr = value.toCharArray();
        for (char c : arr) {
            if (c > 32 && c < 58) {
                throw new AppBadRequestException("There is an excess character");
            }
        }
    }

    public static boolean checkingPhone(String phone){
        boolean bool = true;
        char[] arr = phone.toCharArray();

        if (phone.length() != 13 || !phone.startsWith("+998")){
            bool = false;
        }

        for (int i = 1; i< arr.length; i++) {
            if (arr[i] < 48 || arr[i] > 57) {
                bool = false;
                break;
            }
        }
        return bool;
    }


}
