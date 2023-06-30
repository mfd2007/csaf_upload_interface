package csaf.upload.rest;

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
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) 
    			@RequestParam(value="advisory", required=true)  
    		MultipartFile advisory, 
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
    			@RequestParam(value="signature", required=false)  
    		MultipartFile signature, 
    		@Parameter(in = ParameterIn.DEFAULT, description = "", required=false,schema=@Schema()) 
    			@RequestParam(value="public_key", required=false)  
    		MultipartFile publicKey,
			@Parameter(in = ParameterIn.HEADER, description = "", required=false,schema=@Schema()) 
				@RequestHeader(value="api_key", required=false)  
				String apikey){
    	

			if(advisory.getOriginalFilename().equals("1.json")){
				return new ResponseEntity<Response>(new SucessResponse(), HttpStatusCode.valueOf(201));
			}
    	return new ResponseEntity<Response>(new SucessResponse(), HttpStatusCode.valueOf(200));
    }
}
