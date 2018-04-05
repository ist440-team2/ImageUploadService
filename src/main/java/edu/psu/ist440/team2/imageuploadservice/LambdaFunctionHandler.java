package edu.psu.ist440.team2.imageuploadservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class LambdaFunctionHandler implements RequestHandler<RequestObject, ResponseObject> {

	private static final String BUCKET = "ist440grp2-images";
	
	private String key;
	private String jobId;

	@Override
	public ResponseObject handleRequest(RequestObject input, Context context) {

		ResponseObject resultObject = new ResponseObject();
		
		jobId = UUID.randomUUID().toString();
		key = String.format("%s_%s.%s", input.getUser(), jobId, "png");
		saveImage(input.getBase64image());
		
		resultObject.setBucket(BUCKET);
		resultObject.setKey(key);
		resultObject.setCreatedDate(ZonedDateTime.now(ZoneId.of("UTC")));
		resultObject.setJobId(jobId);
		resultObject.setUserId(input.getUser());
		
		return resultObject;
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
	private void startWorkflow() {
		
	}
}
