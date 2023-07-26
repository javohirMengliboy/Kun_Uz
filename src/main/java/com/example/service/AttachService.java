package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class AttachService {
    private final String folderName = "attach";
    @Autowired
    private AttachRepository attachRepository;

    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public AttachDTO save(MultipartFile file) {
        String pathFolder = getYmDString(); // 2022/04/23
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // jpg
        key = key+"."+extension;
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setPath(pathFolder); // 2022/04/23
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getId());
            attachDTO.setOriginalName(entity.getOriginalName());
            // any think you want mazgi.
            attachDTO.setUrl("");

            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] loadImage(String fileName) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("attaches/" + fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] loadImageById(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            BufferedImage originalImage = ImageIO.read(new File(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            File file = new File(url);

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Attach not found"));
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public List<AttachDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        List<AttachEntity> entityList = attachRepository.findAllAttach(pageable);
        List<AttachDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;

    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setOriginalName(entity.getOriginalName());
        dto.setPath(entity.getPath());
        dto.setExtension(entity.getExtension());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(String id) {
        AttachEntity entity = get(id);
        String url = folderName+"/"+entity.getPath()+"/"+entity.getId()+"."+entity.getExtension();
        System.out.println(url);

        File file = new File(url);

        if (file.delete()) {
            System.out.println("File deleted successfully.");
            attachRepository.delete(entity);
            return true;
        } else {
            System.out.println("Failed to delete the file.");
            return false;
        }
    }
}
