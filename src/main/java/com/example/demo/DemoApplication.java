package com.example.demo;

import com.example.demo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        final String url = "http://91.241.64.178:7081/api/users";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntityGet = restTemplate.getForEntity(
                url, String.class);

        System.out.println(responseEntityGet.getBody());

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        List<String> cookies = forEntity.getHeaders().get("Cookie");
        if (cookies == null) {
            cookies = forEntity.getHeaders().get("Set-Cookie");
        }
        assert cookies != null;
        String cookie = cookies.get(cookies.size() - 1);
        System.out.println(cookie);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);

        ResponseEntity<String> responseEntityPost = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(new User(3L,"James", "Brown", (byte)20), httpHeaders),
                String.class);
        System.out.println(responseEntityPost.getBody());

        ResponseEntity<String> responseEntityPut= restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(new User(3L,"Thomas", "Shelby",(byte)20), httpHeaders),
                String.class);

        System.out.println(responseEntityPut.getBody());
        ResponseEntity<String> responseEntityDelete = restTemplate.exchange(url + "/3", HttpMethod.DELETE,
                new HttpEntity<>(httpHeaders),
                String.class);
        System.out.println(responseEntityDelete.getBody());

    }

}
