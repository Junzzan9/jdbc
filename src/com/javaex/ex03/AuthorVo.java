package com.javaex.ex03;

public class AuthorVo {
	
	//field
	private int authorId;
	private String authorName;
	private String authorDesc;
	//constructor
	
	public AuthorVo(int authorId, String authorName, String authorDesc) {
		this.authorId = authorId;
		this.authorName = authorName;
		this.authorDesc = authorDesc;
	}
	
	public AuthorVo(String authorName, String authorDesc) {
		this.authorName = authorName;
		this.authorDesc = authorDesc;
	}

	//method g/s
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorDesc() {
		return authorDesc;
	}
	public void setAuthorDesc(String authorDesc) {
		this.authorDesc = authorDesc;
	}
	//method

	@Override
	public String toString() {
		return "AuthorVo [authorId=" + authorId + ", authorName=" + authorName + ", authorDesc=" + authorDesc + "]";
	}
	
	
	
}
