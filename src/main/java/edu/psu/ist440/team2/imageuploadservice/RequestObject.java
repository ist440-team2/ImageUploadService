package edu.psu.ist440.team2.imageuploadservice;

/**
 * POJO class for the request (input) object
 *
 */
public class RequestObject {
	private String operation;
	private String base64image;
	private String user;
	private String requestId;

	public RequestObject() {
		super();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getBase64image() {
		return base64image;
	}

	public void setBase64image(String base64image) {
		this.base64image = base64image;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
