package com.usuarios.Demo.dto;

public class APIResponse<T> {
    private String status;
    private String mensaje;
    private T data;

    public APIResponse(String status, String mensaje, T data) {
        this.status = status;
        this.mensaje = mensaje;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public T getData() {
        return data;
    }
}

/*T --> tipo de dato genérico, que indica que data puede ser un userModel, un adminModel, una lista o incluso null, es decir, 
 puede ser cualquier tipo de dato, puede ser lo que quiera ser, como una barbie girl. Y podemos reutilziarlo cuantas veces queramos. */

/*DTO es Data transfer object, Es un objeto usado para transferir datos entre distintas capas de tu aplicación (backend y front o
 * entre servicio y contorlador). Basicameente es una estructura para entregar la info como una respuesta JSON limpiecita. */

