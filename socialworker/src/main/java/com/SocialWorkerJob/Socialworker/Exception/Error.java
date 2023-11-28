package com.SocialWorkerJob.Socialworker.Exception;

public class Error {
        private String message;
        private String field;

        public Error(String message ,String field ) {
            this.message = message;
            this.field=field;
        }



        public String getMessage() {
            return message;
        }

        public String getField() {
            return field;
        }
    }

