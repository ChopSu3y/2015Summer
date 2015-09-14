package com.xerox.ajaxstuff;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class AjaxController extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public AjaxController(){
	     singletons.add(new AjaxResource());
	}
	@Override
	public Set<Class<?>> getClasses() {
	     return empty;
	}
	@Override
	public Set<Object> getSingletons() {
	     return singletons;
	}
	
}
