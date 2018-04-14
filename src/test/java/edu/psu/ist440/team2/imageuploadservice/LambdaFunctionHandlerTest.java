package edu.psu.ist440.team2.imageuploadservice;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import edu.psu.ist440.team2.imageuploadservice.LambdaFunctionHandler;
import edu.psu.ist440.team2.imageuploadservice.RequestObject;
import edu.psu.ist440.team2.imageuploadservice.ResponseObject;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

	private static final String EXPECTED_BUCKET = "ist440grp2-images";
	private static final String EXPECTED_USER = "testUser";
	private static final String IMAGE_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAQAAAD8x0bcAAAAvUlEQVR4AZ2ShW3FQBAFp4FAA8EC/jcV5A7CSXtbiFEQpg6S1Z5k5nlmz/GxjVgzwRMBIBqIuGeAYwq+CUwK+SHlsK/AuWmFXTNO3ddmQ4UqTvvT5JwBF5TcURHwXWmvvFTKJ3sY0kRjdbaVGEGsoVeEX43wao2KJu5KL6OSEdqIzjrNeTSI+HGDto6/ViP9wqfintSUCzcF1UhzrmlwCFzaoHPNp9OG5tzNi2g8vshN6XHHvuq4z/X2rTLOP8RxXOAVkvAYAAAAAElFTkSuQmCC";

	private static RequestObject input;

	@BeforeClass
	public static void createInput() throws IOException {
		// TODO: set up your sample input object here.
		input = new RequestObject();
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// TODO: customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testLambdaFunctionHandler() {
		LambdaFunctionHandler handler = new LambdaFunctionHandler();
		Context ctx = createContext();

		input.setType("png");
		input.setUser(EXPECTED_USER);
		input.setBase64image(IMAGE_BASE64);

		ResponseObject result = handler.handleRequest(input, ctx);
		long timeDiff = ChronoUnit.SECONDS.between(ZonedDateTime.now(ZoneId.of("UTC")),
				ZonedDateTime.parse(result.getCreatedDate()));

		assertNotNull(result);
		assertTrue(String.format("Time difference was %d", timeDiff), timeDiff <= 5); // created date should be less
																						// than 5s before now
		assertEquals(EXPECTED_BUCKET, result.getUploadedImageInfo().getBucket());
		assertEquals(EXPECTED_USER, result.getUserId());
		assertTrue(result.getUploadedImageInfo().getKey().startsWith(EXPECTED_USER));
	}
	
	@Test
	public void testLambdaFunctionHandlerNullType() {
		LambdaFunctionHandler handler = new LambdaFunctionHandler();
		Context ctx = createContext();

		input.setUser(EXPECTED_USER);
		input.setBase64image(IMAGE_BASE64);

		ResponseObject result = handler.handleRequest(input, ctx);
		long timeDiff = ChronoUnit.SECONDS.between(ZonedDateTime.now(ZoneId.of("UTC")),
				ZonedDateTime.parse(result.getCreatedDate()));

		assertNotNull(result);
		assertTrue(String.format("Time difference was %d", timeDiff), timeDiff <= 5); // created date should be less
																						// than 5s before now
		assertEquals(EXPECTED_BUCKET, result.getUploadedImageInfo().getBucket());
		assertEquals(EXPECTED_USER, result.getUserId());
		assertTrue(result.getUploadedImageInfo().getKey().startsWith(EXPECTED_USER));
	}
}
