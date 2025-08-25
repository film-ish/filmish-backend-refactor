package com.filmish.backend.controller;

import java.util.Date;
import java.util.List;

public record MovieDetailResponse(
    Long id,
    String title,
    String plot,
    Date pubDate,
    int runningTime,
    float averageRating,
    String type,
    boolean like,
    List<String> posters,
    List<String> stillcuts,
    List<MovieRoleResponse> makers
) {
}
