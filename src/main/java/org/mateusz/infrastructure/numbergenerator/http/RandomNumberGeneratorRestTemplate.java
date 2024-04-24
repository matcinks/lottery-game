package org.mateusz.infrastructure.numbergenerator.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mateusz.domain.numbergenerator.RandomNumberGenerable;
import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class RandomNumberGeneratorRestTemplate implements RandomNumberGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers(int lowerBand, int upperBand, int count) {
        log.info("Starting fetching winning numbers using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        try {
            final ResponseEntity<List<Integer>> response = makeGetRequest(lowerBand, upperBand, count, requestEntity);
            Set<Integer> sixDistinctNumbers = getSixDistinctRandomNumbers(response);
            if(sixDistinctNumbers.size() != 6) {
                log.error("Set is less than: {} Have to request again", count);
                return generateSixRandomNumbers(lowerBand, upperBand, count);
            }
            return SixRandomNumbersDto.builder()
                    .numbers(sixDistinctNumbers)
                    .build();
        } catch(ResourceAccessException e) {
            log.error("Error while fetching winning numbers using http client: " + e.getMessage());
            return SixRandomNumbersDto.builder()
                    .build();
        }
    }

    private ResponseEntity<List<Integer>> makeGetRequest(int lowerBand, int upperBand, int count, HttpEntity<HttpHeaders> requestEntity) {
        final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService("/api/v1.0/random"))
                .queryParam("min", lowerBand)
                .queryParam("max", upperBand)
                .queryParam("count", count)
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response;
    }

    private Set<Integer> getSixDistinctRandomNumbers(ResponseEntity<List<Integer>> response) {
        List<Integer> numbers = response.getBody();
        if (numbers == null) {
            log.error("Response body was null returning empty collection");
            return Collections.emptySet();
        }
        log.info("Success response body returned: " + response);
        Set<Integer> distinctNumbers = new HashSet<>(numbers);
        return distinctNumbers.stream()
                .limit(6)
                .collect(Collectors.toSet());
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
