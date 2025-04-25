package co.com.semillero.repository;

import co.com.semillero.constants.ParameterKey;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.util.ParameterStoreUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
public class ParameterStoreRepository {
    public ParameterStoreDTO getParameter(){
        
        ParameterStoreDTO parameter=new ParameterStoreDTO();

        Map<String, String> parameterSemillero = ParameterStoreUtil.getParameters(ParameterKey.BASE_PATH);

        if (parameterSemillero == null) {
            return new ParameterStoreDTO(); // Devuelve un objeto con valores nulos
        }
        log.info("Parametro desde el parameterStoreRepository {} :",parameterSemillero);

        parameter.setTable(parameterSemillero.get(ParameterKey.NOMBRE_TABLA.getFullKey()));
        parameter.setRegion(parameterSemillero.get(ParameterKey.REGION.getFullKey()));
        parameter.setArnSecre(parameterSemillero.get(ParameterKey.ARN_SECRET.getFullKey()));
        parameter.setUrlDynamo(parameterSemillero.get(ParameterKey.URL_DYNAMO.getFullKey()));

        log.info("Parametro desde el parameterStoreRepository {} :",parameter);
        return parameter;
    }
}
