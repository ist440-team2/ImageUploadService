package edu.psu.ist440.team2.imageuploadservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import edu.psu.ist440.team2.imageuploadservice.ResponseObject.UploadedImageInfo;

public class LambdaFunctionHandler implements RequestHandler<RequestObject, ResponseObject> {

	private static final String BUCKET = "ist440grp2-images";
	private static final String WORKFLOW_START_URL = "https://l08fl95wj9.execute-api.us-east-1.amazonaws.com/test/jobs/start";

	private String key;
	private String jobId;

	@Override
	public ResponseObject handleRequest(RequestObject input, Context context) {

		ResponseObject responseObject = new ResponseObject();
		UploadedImageInfo uii = new UploadedImageInfo();
		ZonedDateTime createdDate = ZonedDateTime.now(ZoneId.of("UTC"));
		String fileType = (input.getType().isEmpty()) ? "png" : input.getType();
		

		jobId = UUID.randomUUID().toString();
		key = String.format("%s_%s.%s", input.getUser(), jobId, fileType);
		saveImage(input.getBase64image());

		startWorkflow(input.getUser(), jobId, createdDate.toString(), "QUEUED", key);

		responseObject.setCreatedDate(createdDate);
		responseObject.setJobId(jobId);
		responseObject.setUserId(input.getUser());
		uii.setBucket(BUCKET);
		uii.setKey(key);
		responseObject.setUploadedImageInfo(uii);
		return responseObject;
	}

	/**
	 * Saves the image to S3
	 * 
	 * @param base64
	 *            the base64-encoded string of the image
	 * @param userId
	 *            the userId associated with the request
	 */
	private void saveImage(String base64) {
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

		byte[] imageData = Base64.getDecoder().decode(base64);
		InputStream in = new ByteArrayInputStream(imageData);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/png");
		metadata.setContentLength(imageData.length);

		PutObjectRequest objectRequest = new PutObjectRequest(BUCKET, key, in, metadata);
		s3client.putObject(objectRequest);
	}

	/**
	 * Calls the start workflow API
	 */
	private void startWorkflow(String userId, String jobId, String createdDate, String status, String key) {
		CloseableHttpClient client;
		HttpPost post;
		StringEntity body;

		JsonObject uploadedImageInfoObj = (JsonObject) Json.createObjectBuilder().add("bucket", BUCKET).add("key", key)
				.build();

		JsonObject outputData = (JsonObject) Json.createObjectBuilder().add("userId", userId).add("jobId", jobId)
				.add("createdDate", createdDate).add("status", status).add("uploadedImageInfo", uploadedImageInfoObj)
				.build();

		try {
			client = HttpClients.createDefault();
			post = new HttpPost(WORKFLOW_START_URL);
			body = new StringEntity(outputData.toString());
			
			post.setEntity(body);
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			
			HttpResponse response = client.execute(post);
			client.close();
			
			System.out.println(response.toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
