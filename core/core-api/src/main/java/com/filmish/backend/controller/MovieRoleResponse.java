package com.filmish.backend.controller;

import com.filmish.backend.domain.Type;

public record MovieRoleResponse(Long id, String name, Type type, String thumbnailImage) {
}