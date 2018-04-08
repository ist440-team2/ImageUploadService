package edu.psu.ist440.team2.imageuploadservice;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

/**
 * POJO class for response
 *
 */
public class ResponseObject {

	private String userId;
	private String jobId;
	private String createdDate;
	private String status;
	private UploadedImageInfo uploadedImageInfo;
	
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
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UploadedImageInfo getUploadedImageInfo() {
		return uploadedImageInfo;
	}

	public void setUploadedImageInfo(UploadedImageInfo uploadedImageInfo) {
		this.uploadedImageInfo = uploadedImageInfo;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}



	public static class UploadedImageInfo {
		private String bucket = "";
		private String key = "";
		
		public String getBucket() {
			return bucket;
		}

		public void setBucket(String imageBucket) {
			this.bucket = imageBucket;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String imageKey) {
			this.key = imageKey;
		}
	}
}
