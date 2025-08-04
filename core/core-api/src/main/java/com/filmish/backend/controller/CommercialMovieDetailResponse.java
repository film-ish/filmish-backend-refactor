package com.filmish.backend.controller;

import java.sql.Date;
import java.util.List;

public record CommercialMovieDetailResponse(Long id,
                                             String title,
                                             String poster,
                                             Date pubDate,
                                             List<String> categories
) {

}
