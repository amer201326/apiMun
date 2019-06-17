package Data;


public class ServerResponse {

   
    boolean success;
    
    public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	String message;
    public String getMessage() {
        return message;
    }
    public boolean getSuccess() {
        return success;
    }

}