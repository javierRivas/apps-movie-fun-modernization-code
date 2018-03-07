package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final AlbumsRepository albumsRepository;


    public AlbumsController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @PostMapping
    public Album addAlbum(@RequestBody Album album) {
        albumsRepository.addAlbum(album);
        return album;
    }

    @GetMapping
    public List<Album> findAll() {
        return albumsRepository.getAlbums();
    }

    @GetMapping("/{id}")
    public Album findById(@PathVariable long id) {
        return albumsRepository.find(id);
    }
}
