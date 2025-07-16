package com.filmish.backend.controller;

import com.filmish.backend.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MakerController {
    @GetMapping("/makers")
    public ApiResponse<List<MakerResponse>> findMakers(@RequestParam(required = false) Long cursorId, @RequestParam Integer pageSize, @RequestParam(name="name", required = false) String name) {
        List<MakerResponse> response = List.of(
                new MakerResponse(1L, "Maker1", "maker1@aaaa.com", "ACTOR", "maker-image1.jpg", 1, 1, "maker1's film"),
                new MakerResponse(2L, "Maker2", "maker2@aaaa.com", "ACTOR", "maker-image2.jpg", 2, 2, "maker2's film"),
                new MakerResponse(3L, "Maker3", "maker3@aaaa.com", "ACTOR", "maker-image3.jpg", 3, 3, "maker3's film")
        );
        return ApiResponse.success(response);
    }

    @GetMapping("/makers/{makerId}")
    public ApiResponse<MakerDetailResponse> findMaker(@PathVariable Long makerId){
        List<FilmographyResponse> filmography = List.of(
                new FilmographyResponse(1L, "film1", "poster1.jpg", "stillCut.jpg", "2000-00-00"),
                new FilmographyResponse(2L, "film2", "poster1.jpg", "stillCut1.jpg", "2000-00-00")
        );
        MakerDetailResponse response = new MakerDetailResponse(1L, 1L, "Maker1", "maker-image1.jpg", 1, filmography);
        return ApiResponse.success(response);
    }

}
