package csaf.upload.rest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;

import csaf.upload.model.ErrorResponse;
import csaf.upload.model.Response;
import csaf.upload.model.SucessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @Operation(summary = "Import a CSAF document", description = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", 
        		     description = "successful import", 
        		     content = @Content(
        		    		 mediaType = "application/json", 
        		    		 schema = @Schema(implementation = SucessResponse.class))),
        @ApiResponse(responseCode = "400", 
        	description = "Bad Request", 
        	content = @Content(
        			mediaType = "application/json", 
        			schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", 
        	description = "Unauthorized", 
        	content = @Content(
        			mediaType = "application/json", 
        			schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", 
        	description = "Unprocessable content", 
        	content = @Content(
        			mediaType = "application/json", 
        			schema = @Schema(implementation = ErrorResponse.class))) })
    @PostMapping(value = "/import",
        produces = { "application/json" }, 
        consumes = { "multipart/form-data" })
    ResponseEntity<Response> callImport(
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
    			@RequestParam(value="advisory", required=false)  
    		MultipartFile advisory, 
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
    			@RequestParam(value="validation_status", required=false)  
    		String validationStatus, 
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
    			@RequestParam(value="hash-256", required=false)  
    		MultipartFile hash256,
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
			@RequestParam(value="hash-512", required=false)  
    		MultipartFile hash512,
			@Parameter(in = ParameterIn.HEADER, description = "", required=false,schema=@Schema()) 
				@RequestHeader(value="api_key", required=false)  
				String apiKey) throws IOException, NoSuchAlgorithmException{

    	if(apiKey != null){
    		if(!apiKey.equals("secret-api-key"))
    			return new ResponseEntity<Response>(new ErrorResponse("Unauthorized", "401", "Unauthorized, use api_key: secret-api-key"), HttpStatusCode.valueOf(401));
    	}
    	
    	if(advisory == null){
    		return new ResponseEntity<Response>(new ErrorResponse("Missing parameter", "400", "Missing parameter: advisory"), HttpStatusCode.valueOf(400));
    	}
    	if(validationStatus == null){
    		return new ResponseEntity<Response>(new ErrorResponse("Missing parameter", "400", "Missing parameter: validation_status"), HttpStatusCode.valueOf(400));
    	}
    	
    	if(! validationStatus.matches("valid|invalid|not_validated")) {
    		return new ResponseEntity<Response>(new ErrorResponse("Invalid validation_status", "422", "validation_status must be 'valid|invalid|not_validated'"), HttpStatusCode.valueOf(422));
    	}
    	
		// JSON Schema validation
		Set<ValidationMessage> errors = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V4);
		    JsonSchema jsonSchema = factory.getSchema(MainController.class.getResourceAsStream("/csaf_json_schema.json"));
		    JsonNode jsonNode = mapper.readTree(advisory.getInputStream());
		    errors = jsonSchema.validate(jsonNode);
		}catch(Exception e) {
			return new ResponseEntity<Response>(new ErrorResponse(e.getMessage(), "422", e.getStackTrace().toString()), HttpStatusCode.valueOf(422));
		}
		if(errors.size() > 0) {
			return new ResponseEntity<Response>(new ErrorResponse("Schema error", "422", errors.toString()), HttpStatusCode.valueOf(422));
		}
		
		if(hash256 != null) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(advisory.getBytes());
			String providedHash = new String(hash256.getBytes()).split(" ")[0];
			if(! (providedHash.equals(bytesToHex(encodedHash)))) {
				return new ResponseEntity<Response>(new ErrorResponse("Hash validation failed", "400", errors.toString()), HttpStatusCode.valueOf(400));
			}
		}
		
		if(hash512 != null) {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] encodedHash = digest.digest(advisory.getBytes());
			String providedHash = new String(hash512.getBytes()).split(" ")[0];
			if(! (providedHash.equals(bytesToHex(encodedHash)))) {
				return new ResponseEntity<Response>(new ErrorResponse("Hash validation failed", "400", errors.toString()), HttpStatusCode.valueOf(400));
			}
		}
    		
    	return new ResponseEntity<Response>(new SucessResponse(), HttpStatusCode.valueOf(201));
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
