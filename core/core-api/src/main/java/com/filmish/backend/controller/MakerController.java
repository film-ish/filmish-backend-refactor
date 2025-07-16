package com.filmish.backend.controller;

import com.filmish.backend.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
}
