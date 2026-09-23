package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {


        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);// Se inicializa el servidor en el puerto 8080.


        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String respuesta = "Servidor funcionando correctamente";

/*
String html = """
<html>
<head>
    <title>Mi Servidor</title>
</head>
<body>
    <h1>Servidor funcionando</h1>
</body>
</html>
""";

*/

                exchange.sendResponseHeaders(200, respuesta.getBytes().length);// exchange para definir el código de estado 200 OK, 401,404,500


                OutputStream os = exchange.getResponseBody(); // para generar la respuesta
                os.write(respuesta.getBytes());
                os.close();// Si no pones el os.close(), el inspector de las DevTools del navegador mostrará que la petición se queda en estado "Pending" (Pendiente) de forma indefinida y la página parecerá que nunca termina de cargar (el icono de carga seguirá girando).
            }
        });


        server.start();

        System.out.println("Servidor iniciado en http://localhost:8080");

        /*
        write(): Llena el carrito de la compra (guarda los datos en el búfer de memoria).
        flush(): Empuja el carrito por el pasillo (fuerza a que todo lo que hay en el búfer salga inmediatamente hacia el navegador), pero la conexión sigue abierta por si quieres meter más cosas después.
        close(): Paga en la caja y cierra la tienda (hace un flush() automático de lo que quede y cierra la conexión definitivamente).
         */
    }
}
