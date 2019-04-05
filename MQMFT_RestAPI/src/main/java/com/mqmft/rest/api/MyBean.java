package com.mqmft.rest.api;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
	 private long id;
	    private String content;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getContent(String user) {
			content=setContent(user);
			return content;
		}
		public String setContent(String content) {
			return this.content = content;
		}
@Override
public String toString()
{ return "syam[id"+content+"]";
	}

}
