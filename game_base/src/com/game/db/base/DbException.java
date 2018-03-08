package com.game.db.base;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DbException(Throwable tx) {
		super(tx);
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(String message, Throwable e) {
		super(message, e);
	}
}
