package co.com.semillero.exception;

import lombok.Getter;

public class ErrorResponse extends Throwable {

    @Getter
    private String message;
    private String stackTrace;

    public ErrorResponse(Exception e) {
        if (e != null){
            this.message = e.getMessage();
            this.stackTrace = convertStackTraceToString(e);
        } else {
            this.message = null;
            this.stackTrace = ""; // Manejo para excepciones nulas
        }
    }

    private String convertStackTraceToString(Throwable throwable) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTraceAsString() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
