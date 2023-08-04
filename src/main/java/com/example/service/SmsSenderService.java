package com.example.service;

import com.example.dto.SmsDTO;
import com.example.util.RandomUtil;
import jakarta.servlet.ServletRequest;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SmsSenderService {

    private static String token;
    public void send(String phone){
        String code = RandomUtil.getRandomSmsCode();
        String text = "Kun.uz ro'yxatdan o'tish uchun kod";
        if (token == null){
            getToken();
            send(phone);
            System.out.println("in -> "+ token);
        }
        System.out.println("out -> "+token);

        SmsDTO smsDTO = new SmsDTO(phone, code);

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:1001/api/sms/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        HttpEntity<SmsDTO> request = new HttpEntity<>(smsDTO,headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(fooResourceUrl,HttpMethod.POST, request, Boolean.class);
    }
    public void getToken(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:1001/api/auth/login";
        Map<String, String> map= new HashMap<>();
        map.put("login", "kunnuqtauz");
        map.put("password", "parol");
        ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl, map, String.class);
        token = response.getBody();
    }

}

