package org.superbiz.moviefun.albumsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.superbiz.moviefun.moviesapi.MovieInfo;

import java.util.List;

public class AlbumsClient {
    private RestOperations restOperations;
    private String albumsUrl;
    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    private static final Logger LOG = LoggerFactory.getLogger(AlbumsClient.class.getName());

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, HttpMethod.GET, null, albumListType).getBody();
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForObject(albumsUrl, album, AlbumInfo.class);
    }

    public AlbumInfo find(long albumId) {
        return restOperations.getForObject(albumsUrl.concat("/").concat(String.valueOf(albumId)), AlbumInfo.class);
    }
}
