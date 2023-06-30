package csaf.upload.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

import org.springframework.validation.annotation.Validated;

/**
 * ImportRequest
 */
@Validated


public class ImportRequest   {
  @JsonProperty("advisory")
  private String advisory = null;

  @JsonProperty("signature")
  private String signature = null;

  @JsonProperty("public_key")
  private String publicKey = null;

  public ImportRequest advisory(String advisory) {
    this.advisory = advisory;
    return this;
  }

  /**
   * Get advisory
   * @return advisory
   **/
  @Schema(requiredMode = RequiredMode.REQUIRED, description = "")
  public String getAdvisory() {
    return advisory;
  }

  public void setAdvisory(String advisory) {
    this.advisory = advisory;
  }

  public ImportRequest signature(String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * Get signature
   * @return signature
   **/
  @Schema(description = "")
  
    public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public ImportRequest publicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  /**
   * Get publicKey
   * @return publicKey
   **/
  @Schema(description = "")
  
    public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImportRequest importRequest = (ImportRequest) o;
    return Objects.equals(this.advisory, importRequest.advisory) &&
        Objects.equals(this.signature, importRequest.signature) &&
        Objects.equals(this.publicKey, importRequest.publicKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(advisory, signature, publicKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImportRequest {\n");
    
    sb.append("    advisory: ").append(toIndentedString(advisory)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    publicKey: ").append(toIndentedString(publicKey)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
