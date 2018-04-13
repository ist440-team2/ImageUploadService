package edu.psu.ist440.team2.imageuploadservice;

/**
 * POJO class for the request (input) object
 *
 */
public class RequestObject {
	private String type;
	private String base64image;
	private String user;

	public RequestObject() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String operation) {
		this.type = operation;
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

}
