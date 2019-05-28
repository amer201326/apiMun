package municipality.utility;

import io.swagger.annotations.ApiModelProperty;

public class HttpReturnData {

	String returnContent;
	int statusCode;

	public String getReturnContent() {
		return returnContent;
	}

	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}

	@ApiModelProperty(notes = "Status Code", name = "status", required = true, value = "Test Status Code")
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
