package com.prosnav.ivms.util;

import javax.servlet.http.HttpServletRequest;

public class PageView {
	
	private static final int default_size = 10;
	private static final int default_page = 1;
	private int size;
	private int page;
	private long count;

	public PageView() {
	}

	public PageView(HttpServletRequest request) {
		String sizeStr = request.getParameter("size");
		String pageStr = request.getParameter("page");
		if (sizeStr == null || Integer.parseInt(sizeStr) <= 0) {
			this.size = default_size;
		} else {
			this.size = Integer.parseInt(request.getParameter("size"));
		}
		if (pageStr == null || Integer.parseInt(pageStr) <= 0) {
			this.page = default_page;
		} else {
			this.page = Integer.parseInt(request.getParameter("page"));
		}

	}

	public int getBegin() {
		return (this.page - 1) * this.size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
		if ((page - 1) * size >= count) {
			page = Math.round(count / size);
		}
	}

}
