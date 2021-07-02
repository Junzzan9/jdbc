package com.javaex.ex04;

import java.util.List;



public class AuthorApp {

	public static void main(String[] args) {
		List<AuthorVo> authorList;
		AuthorDao authorDao = new AuthorDao();
		//리스트출력
		authorList = authorDao.getAuthorList();
		//리스트 for문으로 출력
		printList(authorList);
			
			
			/*
			AuthorVo authorVo=authorList.get(i);
			
			int authorId=authorVo.getAuthorId();
			String authorName=authorVo.getAuthorName();
			String authorDesc=authorVo.getAuthorDesc();
			
			System.out.println(authorId+"\t"+authorName+"\t"+authorDesc);
			*/
			
			/*
			int authorId=authorList.get(i).getAuthorId();
			String authorName=authorList.get(i).getAuthorName();
			String authorDesc=authorList.get(i).getAuthorDesc();
			
			System.out.println(authorId+"\t"+authorName+"\t"+authorDesc);
		}
			*/
		
		
		
		//작가등록
		AuthorVo authorVo2 = new AuthorVo("황일영","하이미디어");
		int count=authorDao.authorInsert(authorVo2);
		if(count>0) {
			System.out.println("[등록되었습니다.]");
		}else {
			System.out.println("[관리자에게 문의하세요.]");
		}
		//리스트출력
		authorList = authorDao.getAuthorList();
		printList(authorList);
		
		
		//작가수정
		AuthorVo authorVo = new AuthorVo(3,"김일영","하이미디어");
		int uCount=authorDao.authorUpdate(authorVo);
		if(uCount>0) {
			System.out.println("[수정띠되었습니다.]");
		}else {
			System.out.println("[관리자에게 문희는 포도가 머꼬찌뿐데 하세요.]");
		}
		//리스트출력
		authorList = authorDao.getAuthorList();
		//리스트 for문으로 출력
		printList(authorList);
		
		
		/*
		//작가삭제	
		authorDao.authorDelete(3);
		//리스트출력
		List<> authorList = authorDao.getAuthorList();
		//리스트 for문으로 출력
		printList(authorList);
		//작가 1명의 정보
		??? = authorDao.getAuthorOne();
		*/
		
		
	}
	
	public static void printList(List<AuthorVo> authorList) {
		for(int i=0;i<authorList.size();i++) {
			AuthorVo authorVo=authorList.get(i);
			System.out.println(authorVo.getAuthorId()+"\t"+authorVo.getAuthorName()+"\t\t"+authorVo.getAuthorDesc()
			);
		}
	}

}
