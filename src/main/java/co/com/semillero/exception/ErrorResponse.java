package co.com.semillero.exception;

public class ErrorResponse extends Throwable {

    private String message;
    private String stackTrace;

    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
        this.stackTrace = convertStackTraceToString(e); // Lógica local
    }

    private String convertStackTraceToString(Throwable throwable) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    public String getMessage() {
        return message;
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
