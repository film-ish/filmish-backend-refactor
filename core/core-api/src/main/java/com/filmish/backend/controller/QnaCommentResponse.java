package com.filmish.backend.controller;

import java.time.Instant;
import java.util.List;

public record QnaCommentResponse(Long id, String writerName, String writerImage, String content, Instant createdAt, Instant updatedAt, List<QnaCommentResponse> replies) {
}
