package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.model.CatalogItem;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.Rating;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);


       return ratings.getUserRating().stream().map(rating -> {
           Movie movie =restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
           return new CatalogItem(movie.getName(), "Test", rating.getRating());

       }).collect(Collectors.toList());
    }
}



//           Movie movie = webClientBuilder.build()
//                   .get()
//                   .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                   .retrieve()
//                   .bodyToMono(Movie.class)
//                   .block();