package com.filmish.backend.controller;

import com.filmish.backend.domain.Type;
import com.filmish.backend.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    // "보고싶어요" 등록
    @PostMapping("/likes")
    public ApiResponse<?> likeIndie(@RequestParam(required = false) Long indieId) {
        return ApiResponse.success();
    }

    // "보고싶어요" 삭제
    @DeleteMapping("/likes/{indieId}")
    public ApiResponse<?> unlikeIndie(@PathVariable Long indieId){
        return ApiResponse.success();
    }

    // 영화 상세 정보 조회
    @GetMapping("/{movieId}")
    public ApiResponse<?> movieInfo(@PathVariable Long movieId, HttpServletRequest request) {
        // 1. 영화인(Maker) 데이터 생성
        List<MovieRoleResponse> makers = List.of(
            new MovieRoleResponse(1L, "김감독", Type.DIRECTOR, "director_thumb.jpg"),
            new MovieRoleResponse(2L, "이배우", Type.ACTOR, "actor1_thumb.jpg"),
            new MovieRoleResponse(3L, "박배우", Type.ACTOR, "actor2_thumb.jpg")
        );

        // 2. 영화 상세 정보 데이터 생성 (MovieDetailResponse 레코드 사용)
        MovieDetailResponse response = new MovieDetailResponse(
            movieId,
            "어느 멋진 날",
            "평범한 일상이 특별해지는 마법 같은 이야기.",
            Date.valueOf("2025-08-05"),
            125,
            4.8f,
            "INDEPENDENT",
            true,
            List.of("poster_main.jpg", "poster_special.jpg"),
            List.of("still_01.jpg", "still_02.jpg", "still_03.jpg"),
            makers
        );

        return ApiResponse.success(response);
    }

    // 영화 리뷰 목록 조회
    @GetMapping("/{movieId}/reviews")
    public ApiResponse<List<ReviewDetailResponse>> reviewList(@PathVariable Long movieId,
        @RequestParam(name = "page") int pageNum,
        @RequestParam(name = "size") int pageSize) {

        List<ReviewDetailResponse> reviews = new ArrayList<>();
        List<ReviewImageResponse> sampleImages = List.of(
            new ReviewImageResponse(1L, "review_image1.jpg"),
            new ReviewImageResponse(2L, "review_image2.jpg")
        );

        // pageSize만큼 가짜 리뷰 데이터 생성
        for (int i = 1; i <= pageSize; i++) {
            long reviewId = (long) (pageNum - 1) * pageSize + i; // 페이지와 사이즈를 이용해 ID 생성
            ReviewDetailResponse review = new ReviewDetailResponse(
                reviewId,
                movieId, // URL로 받은 영화 ID
                "정말 감동적인 영화입니다 #" + reviewId,
                "이 영화는 단순한 재미를 넘어 깊은 울림을 줍니다. 배우들의 연기가 특히 인상 깊었어요.",
                "영화광" + reviewId,
                "writer_profile" + i + ".jpg",
                Instant.now().minus(i, ChronoUnit.DAYS), // 현재부터 i일 전
                Instant.now().minus(i, ChronoUnit.HOURS), // 현재부터 i시간 전
                100 + i, // 조회수
                sampleImages // 이미지 목록
            );
            reviews.add(review);
        }

        return ApiResponse.success(reviews);
    }

    @GetMapping("/like-commercial")
    public ApiResponse<List<CommercialMovieDetailResponse>> listCommercial() {
        List<CommercialMovieDetailResponse> response = List.of(
            new CommercialMovieDetailResponse(
                101L,
                "서울의 봄",
                "poster_seoul.jpg",
                Date.valueOf("2023-11-22"),
                List.of("드라마", "역사")
            ),
            new CommercialMovieDetailResponse(
                102L,
                "범죄도시4",
                "poster_crime4.jpg",
                Date.valueOf("2024-04-24"),
                List.of("액션", "범죄")
            ),
            new CommercialMovieDetailResponse(
                103L,
                "파묘",
                "poster_exhuma.jpg",
                Date.valueOf("2024-02-22"),
                List.of("미스터리", "공포")
            )
        );

        return ApiResponse.success(response);
    }

    @PostMapping("/like-commercial")
    public ApiResponse<?> likeCommercial(@RequestParam(name = "commercialId") List<Long> commercialIdList) {

        return ApiResponse.success();
    }

    // 장르별 전체 영화 목록 조회
//    @GetMapping("/genre/{genreId}")
//    public ApiResponse<?> genreMovies(@PathVariable Long genreId,
//        @RequestParam(name = "page") int pageNum,
//        @RequestParam(name = "size") int pageSize) {
//        // userDetails 파라미터가 제거되었습니다.
//        return movieService.genreMovies(genreId, pageNum, pageSize);
//    }

    // 상업 영화 좋아요 입력 여부 확인
//    @GetMapping("/like-commercials")
//    public ApiResponse<?> checkLikeCommercial(){
//        // userDetails 파라미터가 제거되었습니다.
//        return movieService.checkLikeCommercial();
//    }
//
    // 영화 평점 목록 조회
//    @GetMapping("/{movieId}/ratings")
//    public ApiResponse<?> rateList(@PathVariable Long movieId,
//        @RequestParam(name = "page") int pageNum,
//        @RequestParam(name = "size") int pageSize){
//        return rateService.rateList(movieId, pageNum, pageSize);
//    }
}