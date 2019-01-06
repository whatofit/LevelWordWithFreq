package com.level.util;

//JsonSchema，JsonSchamaValidator，JsonPath
/*
{
    "$schema":"http://json-schema.org/draft-04/schema",
    "type":"object",
    "properties": {
        "name": {
            "type":"string"
        },
        
        "versions": {
            "type":"array",
            "items": {
                "type":"object",
                "properties": {
                    "id": {
                        "type":"string"
                    },
                    
                    "version": {
                        "type":"integer"
                    },
                    
                    "comment": {
                        "type":"string"
                    }
                },
                
                "required":["id", "version"],
                "minItems":1
            }
        }
    },
    
    "required":["name", "versions"]
}
*/
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.SchemaLoader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.level.Constant;

public class JsonSchemaValidator {

	// private static Log log = LogFactory.getLog(JsonSchemaValidator.class);

	/**
	 * validate instance and Schema,here including two functions. as follows: first：
	 * the Draft v4 will check the syntax both of schema and instance. second：
	 * instance validation.
	 * 
	 * @param mainSchema
	 * @param instance
	 * @return
	 * @throws IOException
	 * @throws ProcessingException
	 */
	public boolean validatorSchema(String instance, String mainSchema) throws IOException, ProcessingException {
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		JsonNode jonNodeInstance = JsonLoader.fromString(instance);
		JsonNode jsonNodeMain = JsonLoader.fromString(mainSchema);
		// JsonNode jsonNodeMain = JsonLoader.fromFile(mainFile);
		JsonSchema schema = factory.getJsonSchema(jsonNodeMain);
		// URL schemaPath = getClass().getResource("/swagger-schema.json");
		// JsonSchema schema = factory.getJsonSchema(schemaPath.toString());
		ProcessingReport processingReport = schema.validate(jonNodeInstance);
		// log.info(processingReport);
		System.out.println(processingReport);
		return processingReport.isSuccess();
	}

	public boolean isValid(String instance) {
		final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		// log.verboseOutput("Checking against schema. Available at
		// 'https://raw.githubusercontent.com/pivio/pivio-client/master/src/main/resources/pivio-schema.json'.",
		// configuration.isVerbose());
		try {
			String schemaContent = FileUtil.readFile(Constant.FILE_WORD_SCHEMA);
			System.out.println(schemaContent);
			JsonNode schemaNode = JsonLoader.fromString(schemaContent);
			final JsonSchema jsonSchema = factory.getJsonSchema(schemaNode);
			JsonNode jonNodeInstance = JsonLoader.fromString(instance);
			ProcessingReport processingReport = jsonSchema.validate(jonNodeInstance);
			// for (ProcessingMessage processingMessage : processingReport) {
			// //String pointer =
			// processingMessage.asJson().get("instance").get("pointer").asText();
			// String pointer = processingMessage.asJson().asText();
			// // log.output(pointer + " : " + processingMessage.getMessage());
			// System.out.println(pointer + " : " + processingMessage.getMessage());
			// }

			Iterator<ProcessingMessage> it = processingReport.iterator();
			while (it.hasNext()) {
				System.out.println("next="+ it.next());
			}
			return processingReport.isSuccess();
		} catch (ProcessingException | IOException e) {
			// log.output("Error processing Json. "+e.getMessage());
			System.out.println("Error processing Json. " + e.getMessage());
			return false;
		}
	}

	public boolean isValid(final JsonNode jsonInput, final JsonNode jsonSchema) {
		try {
			final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			final JsonValidator validator = factory.getValidator();
			ProcessingReport report = validator.validate(jsonSchema, jsonInput);
			// assertTrue(report.toString(), report.isSuccess());
			// System.out.println(report.isSuccess());
			return report.isSuccess();
		} catch (ProcessingException e) {
			System.out.println("Error processing Json. " + e.getMessage());
			return false;
		}
	}

//	public void testJsonSchema3() {
//		InputStream inputStream = getClass().getResourceAsStream("/Schema.json");
//		JSONObject Schema = new JSONObject(new JSONTokener(inputStream));
//		JSONObject data = new JSONObject("{\"foo\" : 1234}");
//		Schema schema = SchemaLoader.load(Schema);
//		try {
//			schema.validate(data);
//		} catch (ValidationException e) {
//			System.out.println(e.getMessage());
//		}
//	}

	public boolean testJsonSchema1() {
		JsonNode schema = readJsonFile("src/main/resources/Schema.json");
		JsonNode data = readJsonFile("src/main/resources/failure.json");
		ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
		// Assert.assertTrue(report.isSuccess());
		return report.isSuccess();
	}

	public JsonNode readJsonFile(String filePath) {
		JsonNode instance = null;
		try {
			instance = new JsonNodeReader().fromReader(new FileReader(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static void main(String[] args) throws IOException, ProcessingException {

		JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
		// String mainSchema = "{\"type\": \"object\", \"properties\" : {\"foo\" :
		// {\"type\" : \"string\"}}}";
//		 String instance = "{\"no\":1234}";
		// boolean isValidOK = jsonSchemaValidator.validatorSchema(mainSchema,
		// instance);

		String instance = "{\"id\":1234,\"spelling\":\"Dependencies\",\"detail\":\"details\"}";
		System.out.println(instance);
		boolean isValidOK = jsonSchemaValidator.isValid(instance);
		System.out.println(isValidOK);
	}
}
