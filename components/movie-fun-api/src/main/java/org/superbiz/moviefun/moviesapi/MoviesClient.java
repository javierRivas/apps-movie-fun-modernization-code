package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MoviesClient {
    private RestOperations restOperations;
    private String moviesUrl;
    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };
    private static final Logger LOG = LoggerFactory.getLogger(MoviesClient.class.getName());

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public MovieInfo find(Long id) {
        return restOperations.getForObject(moviesUrl + "/{id}", MovieInfo.class, id);
    }

    public void addMovie(MovieInfo movie) {
        restOperations.postForObject(this.moviesUrl , movie, MovieInfo.class);
    }

    public void updateMovie(MovieInfo movie) {
        restOperations.put(moviesUrl + "/{id}", movie, movie.getId());

    }

    public void deleteMovieId(long id) {
        restOperations.delete(moviesUrl+"/{id}", id);
    }


    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, HttpMethod.GET,null, movieListType).getBody();
    }

    public List<MovieInfo> findAll(int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl);
        builder.queryParam("start", start)
                .queryParam("pageSize", pageSize);
        LOG.info("findAllUrl={}", builder.toUriString());

        List movies = restOperations.exchange(builder.toUriString(), HttpMethod.GET,null, movieListType).getBody();
        LOG.info("Movies: {}", movies );
        return movies;
    }

    public int countAll() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl+"/count");
        LOG.info("countAllUrl={}", builder.toUriString());
        return restOperations.getForObject(builder.toUriString() , Integer.class);
    }

    public int count(String field, String key) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl+"/count");
        builder  .queryParam("field", field)
                .queryParam("key", key);
        LOG.info("url={}", builder.toUriString());
        return restOperations.getForObject(builder.toUriString() , Integer.class);
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl);
        builder.queryParam("field", field)
                .queryParam("key", searchTerm)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);
        LOG.info("url={}", builder.toUriString());
        List movies = restOperations.exchange(builder.toUriString(), HttpMethod.GET,null, movieListType).getBody();
        return movies;
    }

}