package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    private MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping("/{id}")
    public Movie findMovie(@PathVariable Long id) {
        return moviesRepository.find(id);
    }


    @PostMapping()
    public Movie createMovie(@RequestBody Movie movie) {
        moviesRepository.addMovie(movie);
        return movie;
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie existing = moviesRepository.find(id);
        if (existing != null) {
            moviesRepository.updateMovie(existing);
            return existing;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void updateMovie(@PathVariable Long id) {
        moviesRepository.deleteMovieId(id);
    }

    @GetMapping()
    public List<Movie> findMovies(@RequestParam(required = false) String field, @RequestParam(required = false) String key, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer pageSize) {
        if (field != null && key != null && start != null && pageSize != null) {
            return moviesRepository.findRange(field, key, start, pageSize);
        } else if (start != null && pageSize != null) {
            return moviesRepository.findAll(start, pageSize);
        }
        return moviesRepository.getMovies();
    }

    @GetMapping("/count")
    public int findMoviesCount(@RequestParam(required = false) String field, @RequestParam(required = false) String key) {
        if (field != null && key != null) {
            return moviesRepository.count(field, key);
        }
        return moviesRepository.countAll();

    }


}
