package com.example.demo.rpcsimple.common;

import com.example.demo.rpcsimple.model.RequestInfo;

import java.io.Serializable;

public class MessageOutput implements Serializable {


	private static final long serialVersionUID = 9016321997797178830L;
	private String requestId;
	private String type;
	private RequestInfo payload;

	public MessageOutput() {
	}

	public MessageOutput(String requestId, String type, RequestInfo payload) {
		this.requestId = requestId;
		this.type = type;
		this.payload = payload;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RequestInfo getPayload() {
		return payload;
	}

	public void setPayload(RequestInfo payload) {
		this.payload = payload;
	}
}
