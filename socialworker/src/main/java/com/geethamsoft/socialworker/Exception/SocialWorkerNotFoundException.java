package com.geethamsoft.socialworker.Exception;

public class SocialWorkerNotFoundException extends RuntimeException{
    public SocialWorkerNotFoundException(String message){
        super(message);
    }
    public SocialWorkerNotFoundException(String message, Throwable cause) {
       super(message, cause);
     }

}









