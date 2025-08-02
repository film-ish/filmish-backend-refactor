package com.filmish.backend.controller;

public record AppendCommentRequest(String content, Long parentId) {
}
