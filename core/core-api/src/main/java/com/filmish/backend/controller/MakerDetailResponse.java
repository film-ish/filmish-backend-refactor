package com.filmish.backend.controller;

import java.util.List;

public record MakerDetailResponse(Long id, Long userId, String name, String image, int qnaCnt, List<FilmographyResponse> filmography) {
}
