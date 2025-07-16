package com.filmish.backend.controller;

public record MakerResponse(Long id, String name, String email, String role, String image, int qnaCnt, int indieCnt, String movieTitle) {
}
