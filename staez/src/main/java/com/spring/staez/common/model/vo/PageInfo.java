package com.spring.staez.common.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageInfo {
	private int listCount; // 현재 총 게시글 수
	private int currentPage; //현재 페이지(사용자가 요청한 페이지 = 사용자가 몇페이지인지 준다(프론트에서 보냄))
	private int pageLimit; // 페이지 하단의 페이징 바 갯수
	private int boardLimit; // 한 페이지 내에 보여질 게시글 최대 갯수
	// 위 4개의 값을 기준으로 아래 3개의 값을 구해야함
	
	private int maxPage; // 가장 마지막 페이지(총 페이지의 수)
	private int startPage; // 페이징바의 시작수
	private int endPage; // 페이징바의 마지막 끝 수
	
	
}