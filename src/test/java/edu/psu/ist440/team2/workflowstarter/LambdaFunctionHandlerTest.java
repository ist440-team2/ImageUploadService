package edu.psu.ist440.team2.workflowstarter;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {
	
	private static final String MESSAGE_FORMAT = "Operation: %sUser: %s%nRequest Id: %s%n%n %s";
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
        
        input.setOperation("operation");
        input.setUser("jen");
        input.setBase64image(IMAGE_BASE64);
        input.setRequestId("asdfasf");

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals(String.format(MESSAGE_FORMAT, 
        		input.getOperation(), input.getUser(), input.getRequestId(), input.getBase64image()), output);
    }
}
