package edu.psu.ist440.team2.imageuploadservice;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * POJO class for response
 *
 */
public class ResponseObject {

	private String userId;
	private String jobId;
	private String createdDate;
	private String bucket;
	private String key;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDateTime) {
		this.createdDate = createdDateTime.toString();
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
