package com.filmish.backend.controller;

import com.filmish.backend.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class MovieControllerTest extends RestDocsTest {

    MovieController controller;

    @BeforeEach
    public void setup() {
        controller = new MovieController();
        mockMvc = mockController(controller);
    }

    @Test
    void likeIndie() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("indieId", 1L)
            .post("/movies/likes")
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "like-indie-movie",
                queryParameters(
                    parameterWithName("indieId").description("'보고싶어요' 등록할 독립 영화의 아이디")
                ),
                responseFields(
                    fieldWithPath("result")
                        .type(JsonFieldType.STRING)
                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)")
                )
            ));
    }

    @Test
    void unlikeIndie() {
        given()
            .contentType(ContentType.JSON)
            .delete("/movies/likes/{indieId}", 1L)
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "unlike-indie-movie",
                pathParameters(
                    parameterWithName("indieId").description("'보고싶어요' 취소할 독립 영화의 아이디")
                ),
                responseFields(
                    fieldWithPath("result")
                        .type(JsonFieldType.STRING)
                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)")
                )
            ));
    }

    @Test
    void movieInfo() {
        given()
            .contentType(ContentType.JSON)
            .get("/movies/{movieId}", 1L)
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "find-movie-info",
                pathParameters(
                    parameterWithName("movieId").description("조회할 영화의 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                    fieldWithPath("data.movieId").type(JsonFieldType.NUMBER).description("영화 아이디"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("영화 제목"),
                    fieldWithPath("data.plot").type(JsonFieldType.STRING).description("영화 줄거리"),
                    fieldWithPath("data.releaseDate").type(JsonFieldType.STRING).description("개봉일"),
                    fieldWithPath("data.runningTime").type(JsonFieldType.NUMBER).description("상영 시간 (분)"),
                    fieldWithPath("data.avgRating").type(JsonFieldType.NUMBER).description("평균 별점"),
                    fieldWithPath("data.movieType").type(JsonFieldType.STRING).description("영화 타입 (예: INDEPENDENT)"),
                    fieldWithPath("data.liked").type(JsonFieldType.BOOLEAN).description("현재 사용자의 '보고싶어요' 여부"),
                    fieldWithPath("data.posters").type(JsonFieldType.ARRAY).description("포스터 이미지 URL 목록"),
                    fieldWithPath("data.stills").type(JsonFieldType.ARRAY).description("스틸컷 이미지 URL 목록"),
                    fieldWithPath("data.makers").type(JsonFieldType.ARRAY).description("참여 영화인 목록"),
                    fieldWithPath("data.makers[].makerId").type(JsonFieldType.NUMBER).description("영화인 아이디"),
                    fieldWithPath("data.makers[].name").type(JsonFieldType.STRING).description("영화인 이름"),
                    fieldWithPath("data.makers[].role").type(JsonFieldType.STRING).description("역할 (예: DIRECTOR, ACTOR)"),
                    fieldWithPath("data.makers[].thumbnail").type(JsonFieldType.STRING).description("영화인 썸네일 이미지 URL")
                )
            ));
    }

    @Test
    void reviewList() {
        given()
            .contentType(ContentType.JSON)
            .pathParam("movieId", 1L)
            .queryParam("page", 1)
            .queryParam("size", 5)
            .get("/movies/{movieId}/reviews")
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "find-movie-reviews",
                pathParameters(
                    parameterWithName("movieId").description("리뷰를 조회할 영화의 아이디")
                ),
                queryParameters(
                    parameterWithName("page").description("페이지 번호 (1부터 시작)"),
                    parameterWithName("size").description("페이지 당 리뷰 개수")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                    fieldWithPath("data.[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
                    fieldWithPath("data.[].movieId").type(JsonFieldType.NUMBER).description("영화 아이디"),
                    fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("리뷰 제목"),
                    fieldWithPath("data.[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                    fieldWithPath("data.[].writerNickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
                    fieldWithPath("data.[].writerProfileImage").type(JsonFieldType.STRING).description("작성자 프로필 이미지 URL"),
                    fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                    fieldWithPath("data.[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                    fieldWithPath("data.[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                    fieldWithPath("data.[].images").type(JsonFieldType.ARRAY).description("리뷰 이미지 목록"),
                    fieldWithPath("data.[].images[].imageId").type(JsonFieldType.NUMBER).description("이미지 아이디"),
                    fieldWithPath("data.[].images[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL")
                )
            ));
    }

    @Test
    void listCommercial() {
        given()
            .contentType(ContentType.JSON)
            .get("/movies/like-commercial")
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "list-commercial-movies",
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                    fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("상업 영화 아이디"),
                    fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("영화 제목"),
                    fieldWithPath("data.[].poster").type(JsonFieldType.STRING).description("포스터 이미지 URL"),
                    fieldWithPath("data.[].releaseDate").type(JsonFieldType.STRING).description("개봉일"),
                    fieldWithPath("data.[].genres").type(JsonFieldType.ARRAY).description("장르 목록")
                )
            ));
    }

    @Test
    void likeCommercial() {
        given()
            // @RequestParam으로 List를 받을 때는 form 파라미터로 전송합니다.
            .contentType(ContentType.URLENC)
            .formParam("commercialId", 101L, 102L)
            .post("/movies/like-commercial")
            .then()
            .status(HttpStatus.OK)
            .apply(document(
                "like-commercial-movies",
                formParameters(
                    parameterWithName("commercialId").description("'좋아요'할 상업 영화 아이디 목록")
                ),
                responseFields(
                    fieldWithPath("result")
                        .type(JsonFieldType.STRING)
                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)")
                )
            ));
    }
}