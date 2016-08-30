package kr.ac.sungkyul.mysite.exception;

public class UserInfoUpdateException extends RuntimeException {
	public UserInfoUpdateException(){
		super("Exception for Updating User Info");
	}
	
	public UserInfoUpdateException(String message){
		super(message);
	}
}
