package co.com.compraya.admin.marca;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No se encontró la marca")
public class MarcaNotFoundRestException extends Exception{

}
