package com.spring.staez.community.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.staez.admin.model.vo.Category;
import com.spring.staez.common.model.vo.PageInfo;
import com.spring.staez.community.model.dto.AjaxBoardDto;
import com.spring.staez.community.model.dto.AjaxReplyDto;
import com.spring.staez.community.model.dto.CategoryDto;
import com.spring.staez.community.model.dto.CommunityDto;
import com.spring.staez.community.model.vo.Board;
import com.spring.staez.community.model.vo.BoardLike;
import com.spring.staez.community.model.vo.Reply;
import com.spring.staez.community.model.vo.ReplyLike;
import com.spring.staez.community.model.vo.Tag;
import com.spring.staez.concert.model.vo.Concert;

@Repository
public class CommunityDao {

	public ArrayList<Category> selectCategory(SqlSessionTemplate sqlSession, Category c) {
		return (ArrayList)sqlSession.selectList("communityMapper.selectCategory", c);
	}

	public ArrayList<Concert> selectConcertList(SqlSessionTemplate sqlSession, String keyword) {
		return (ArrayList)sqlSession.selectList("communityMapper.selectConcertList", keyword);
	}

	public int insertBoard(SqlSessionTemplate sqlSession, CommunityDto communityDto) {
		return sqlSession.insert("communityMapper.insertBoard", communityDto);
	}

	public int insertCategory(SqlSessionTemplate sqlSession, List<Integer> categoryNoList) {
		int result = 1;
		for(int categoryNo : categoryNoList) {
			result *= sqlSession.insert("communityMapper.insertCategory", categoryNo);
		}
		return result;
	}

	public int insertTag(SqlSessionTemplate sqlSession, int concertNo) {
		return sqlSession.insert("communityMapper.insertTag", concertNo);
	}

	public ArrayList<Board> selectBoard(SqlSessionTemplate sqlSession, CategoryDto categoryDto, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1)* pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("communityMapper.selectBoard", categoryDto, rowBounds);
	}

	public ArrayList<Board> selectBoard(SqlSessionTemplate sqlSession, String keyword, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1)* pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("communityMapper.selectBoardAll", keyword, rowBounds);
	}

	public ArrayList<Category> selectCategory(SqlSessionTemplate sqlSession, int boardNo) {
		return (ArrayList)sqlSession.selectList("communityMapper.selectBoardCategory", boardNo);
	}

	public Board boardDetail(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("communityMapper.boardDetail", boardNo);
	}

	public String selectProfile(SqlSessionTemplate sqlSession, int userNo) {
		return sqlSession.selectOne("communityMapper.selectProfile", userNo);
	}

	public int selectUserBoardLike(SqlSessionTemplate sqlSession, AjaxBoardDto dto) {
		return sqlSession.selectOne("communityMapper.selectUserBoardLike", dto);
	}

	public int selectBoardLikeCnt(SqlSessionTemplate sqlSession, AjaxBoardDto dto) {
		return sqlSession.selectOne("communityMapper.selectBoardLikeCnt", dto);
	}

	public int selectReplyCnt(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("communityMapper.selectReplyCnt", boardNo);
	}

	public Concert selectConcert(SqlSessionTemplate sqlSession, int concertNo) {
		return sqlSession.selectOne("communityMapper.selectConcert", concertNo);
	}
	
	public Tag selectTag(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("communityMapper.selectTag", boardNo);
	}

	public int updateBoard(SqlSessionTemplate sqlSession, CommunityDto communityDto) {
		return sqlSession.update("communityMapper.updateBoard", communityDto);
	}

	public int updateTag(SqlSessionTemplate sqlSession, CommunityDto communityDto) {
		return sqlSession.update("communityMapper.updateTag", communityDto);
	}

	public int deleteBoard(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("communityMapper.deleteBoard", boardNo);
	}

	public int insertCategory(SqlSessionTemplate sqlSession, CommunityDto communityDto) {
		int result = 1;
		Map<String, Integer> map = new HashMap<>();
		map.put("boardNo", communityDto.getBoardNo());
		for(int categoryNo : communityDto.getCategoryNo()) {
			map.put("categoryNo", categoryNo);
			result *= sqlSession.insert("communityMapper.updateCategory", map);
		}
		return result;
	}

	public int deleteCategory(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.delete("communityMapper.deleteCategory", boardNo);
	}

	public int selectUserBoardLikeAll(SqlSessionTemplate sqlSession, AjaxBoardDto dto) {
		return sqlSession.selectOne("communityMapper.selectUserBoardLikeAll", dto);
	}

	public int updateBoardLike(SqlSessionTemplate sqlSession, BoardLike boardLike) {
		return sqlSession.update("communityMapper.updateBoardLike", boardLike);
	}

	public int insertBoardLike(SqlSessionTemplate sqlSession, BoardLike boardLike) {
		return sqlSession.insert("communityMapper.insertBoardLike", boardLike);
	}

	public ArrayList<Reply> selectReplyAll(SqlSessionTemplate sqlSession, int boardNo) {
		return (ArrayList)sqlSession.selectList("communityMapper.selectReplyAll", boardNo);
	}

	public int selectBoardCnt(SqlSessionTemplate sqlSession, CategoryDto categoryDto) {
		return sqlSession.selectOne("communityMapper.selectBoardCnt", categoryDto);
	}

	public int selectBoardCnt(SqlSessionTemplate sqlSession, String keyword) {
		return sqlSession.selectOne("communityMapper.selectBoardCntAll", keyword);
	}

	public int insertReport(SqlSessionTemplate sqlSession, Board report) {
		return sqlSession.insert("communityMapper.insertReport", report);
	}

	public int updateBoardCnt(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("communityMapper.updateBoardCnt", boardNo);
	}

	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("communityMapper.insertReply", r);
	}

	public int updateReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("communityMapper.updateReply", r);
	}

	public int deleteReply(SqlSessionTemplate sqlSession, int replyNo) {
		return sqlSession.update("communityMapper.deleteReply", replyNo);
	}

	public int selectUserReplyLike(SqlSessionTemplate sqlSession, AjaxReplyDto dto) {
		return sqlSession.selectOne("communityMapper.selectUserReplyLike", dto);
	}

	public int selectUserReplyLikeAll(SqlSessionTemplate sqlSession, AjaxReplyDto dto) {
		return sqlSession.selectOne("communityMapper.selectUserReplyLikeAll", dto);
	}

	public int selectReplyLikeCnt(SqlSessionTemplate sqlSession, AjaxReplyDto dto) {
		return sqlSession.selectOne("communityMapper.selectReplyLikeCnt", dto);
	}

	public int selectReplyLike(SqlSessionTemplate sqlSession, int replyNo) {
		return sqlSession.selectOne("communityMapper.selectReplyLike", replyNo);
	}

	public int updateReplyLike(SqlSessionTemplate sqlSession, ReplyLike replyLike) {
		return sqlSession.update("communityMapper.updateReplyLike", replyLike);
	}

	public int insertReplyLike(SqlSessionTemplate sqlSession, ReplyLike replyLike) {
		return sqlSession.insert("communityMapper.insertReplyLike", replyLike);
	}
	
	public String selectConcertId(SqlSessionTemplate sqlSession, int concertNo) {
		return sqlSession.selectOne("communityMapper.selectConcertId", concertNo);
	}
}
