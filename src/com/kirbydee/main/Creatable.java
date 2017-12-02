package com.kirbydee.main;

public interface Creatable {
	default public void create() throws Exception {}
	default public void destroy() throws Exception {}
}
