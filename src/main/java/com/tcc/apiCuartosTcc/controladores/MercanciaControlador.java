package com.tcc.apiCuartosTcc.controladores;


import com.tcc.apiCuartosTcc.entidades.Mercancia;
import com.tcc.apiCuartosTcc.entidades.Zona;
import com.tcc.apiCuartosTcc.servicios.implementaciones.MercanciaServicioIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tcc/mercancias")
public class MercanciaControlador {

    @Autowired
    MercanciaServicioIMP mercanciaServicio;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Mercancia mercancia) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.registrar(mercancia));

        } catch (Exception error) {
            String mensaje="{\"error\":\"Error revise: "+error+"\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }

    }


    @GetMapping
    public ResponseEntity<?> buscarTodos() {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.buscarTodos());

        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Datos no encontrados.}");
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.buscarPoRId(id));

        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Datos no encontrados.}");
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Mercancia mercancia) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.actualizar(id, mercancia));

        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:No se pudo actualizar}");
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Integer id) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.borrar(id));

        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:No se pudo borrar}");
        }


    }
}
