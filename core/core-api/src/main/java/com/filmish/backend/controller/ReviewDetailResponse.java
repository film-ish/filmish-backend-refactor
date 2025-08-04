package com.filmish.backend.controller;

import java.time.Instant;
import java.util.List;

public record ReviewDetailResponse(
    Long id,
    Long indieId,
    String title,
    String content,
    String writerName,
    String writerImage,
    Instant createdAt,
    Instant updatedAt,
    int views,
    List<ReviewImageResponse> images
) {}