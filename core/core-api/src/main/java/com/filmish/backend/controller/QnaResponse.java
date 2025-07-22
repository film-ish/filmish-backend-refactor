package com.filmish.backend.controller;

import java.time.Instant;
import java.util.List;

public record QnaResponse(Long id, String title, String writerName, Instant createdAt, Instant updatedAt, String content, List<QnaCommentResponse> comments) {
}
