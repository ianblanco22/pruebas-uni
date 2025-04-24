package co.com.semillero.constants;

public enum ParameterKey {
    NOMBRE_TABLA("nombreTabla"),
    REGION("region"),
    ARN_SECRET("arnSecret"),
    URL_DYNAMO("dynamoEndpoint");

    public static final String BASE_PATH = "/Semillero/capacitacion-semillero/";
    private final String key;

    ParameterKey(String key) {
        this.key = key;
    }

    public String getFullKey() {
        return BASE_PATH + key;
    }
}
