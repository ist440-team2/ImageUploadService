package edu.psu.ist440.team2.workflowstarter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class LambdaFunctionHandler implements RequestHandler<RequestObject, String> {

	private static final String MESSAGE_FORMAT = "Operation: %sUser: %s%nRequest Id: %s%n%n %s";
	private static final String BUCKET = "ist440grp2-images";

	@Override
	public String handleRequest(RequestObject input, Context context) {

		saveImage(input.getBase64image(), input.getUser());
		return String.format(MESSAGE_FORMAT, input.getOperation(), input.getUser(), input.getRequestId(),
				input.getBase64image());
	}

	/**
	 * Saves the image to S3
	 * 
	 * @param base64
	 *            the base64-encoded string of the image
	 * @param userId
	 *            the userId associated with the request
	 */
	private void saveImage(String base64, String userId) {
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		String key = String.format("%s_%s.%s", userId, UUID.randomUUID(), "png");

		byte[] imageData = Base64.getDecoder().decode(base64);
		InputStream in = new ByteArrayInputStream(imageData);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/png");
		metadata.setContentLength(imageData.length);

		PutObjectRequest objectRequest = new PutObjectRequest(BUCKET, key, in, metadata);
		s3client.putObject(objectRequest);
	}

}
