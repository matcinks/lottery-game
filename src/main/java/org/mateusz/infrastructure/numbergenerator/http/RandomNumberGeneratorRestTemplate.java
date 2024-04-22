package org.mateusz.infrastructure.numbergenerator.http;

import lombok.AllArgsConstructor;
import org.mateusz.domain.numbergenerator.RandomNumberGenerable;
import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RandomNumberGeneratorRestTemplate implements RandomNumberGenerable {

    private final RestTemplate restTemplate;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl("http://www.randomnumberapi.com:80/api/v1.0/random")
                .queryParam("min", 1)
                .queryParam("max", 99)
                .queryParam("count", 6)
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        List<Integer> numbers = response.getBody();
        System.out.println(numbers);
        return SixRandomNumbersDto.builder().numbers(numbers.stream().collect(Collectors.toSet())).build();
    }
}
