package com.swan.picturerepository.service;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.property.ConstantProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class PageNavicationService {
	private int MAX_IMAGE_CNT;//페이지 당 있을 수 있는 MAX 이미지 수
	private int MAX_NAV_LINK_CNT;//하단 네비게이션 링크의  MAX 수
	private GenericXmlApplicationContext ctx;
	public PageNavicationService(){
		 this.ctx = new GenericXmlApplicationContext();
		 ctx.load("file:/usr/local/tomcat8.5/webapps/ROOT/resources/props/constant.xml");
		 ctx.refresh();
		 ConstantProperty constantProperty = (ConstantProperty)ctx.getBean("constantProperty");
		 this.MAX_IMAGE_CNT =constantProperty.getMax_image_cnt();
		 this.MAX_NAV_LINK_CNT =constantProperty.getMax_nav_link_cnt();
	}
	public int getStartPage(int page) {
		int startPage = page;
		while (startPage%MAX_NAV_LINK_CNT != 1) {
			startPage -= 1;
		}
		return startPage;
	}
	public int getEndPage(int page, int maxPage) {
		int endPage = page;
		while (endPage%MAX_NAV_LINK_CNT != 0) {
			endPage += 1;
		}
		return endPage > maxPage ? maxPage : endPage;
	}
	//조회한 데이터의 마지막 페이지  = (검색 이미지수 / 페이지 당 있을 수 있는 MAX 이미지 수)를 올림처리
	public int getMaxPage(int searchCnt) {
		return (int) Math.ceil((double) searchCnt / MAX_IMAGE_CNT);
	}
	public boolean getIsPreviousPage(int startPage) {
		return startPage==1 ? false : true;
	}
	public boolean getIsNextPage(int maxPage, int endPage) {
		return maxPage==endPage ? false : true;
	}
}
