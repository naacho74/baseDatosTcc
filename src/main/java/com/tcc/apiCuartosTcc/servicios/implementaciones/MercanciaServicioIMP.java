package com.tcc.apiCuartosTcc.servicios.implementaciones;


import com.tcc.apiCuartosTcc.entidades.Mercancia;
import com.tcc.apiCuartosTcc.entidades.Zona;
import com.tcc.apiCuartosTcc.repocitorios.MercanciaRepocitorio;
import com.tcc.apiCuartosTcc.repocitorios.ZonaRepositorio;
import com.tcc.apiCuartosTcc.servicios.ServicioGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MercanciaServicioIMP implements ServicioGenerico<Mercancia>  {

    @Autowired
    MercanciaRepocitorio mercanciaRepocitorio;

    @Autowired
    ZonaRepositorio zonaRepositorio;

    @Override
    public List<Mercancia> buscarTodos() throws Exception {
        try{

            List<Mercancia> mercancias=mercanciaRepocitorio.findAll();
            return mercancias;

        } catch (Exception error){

            throw  new Exception(error.getMessage());
        }

    }





    @Override
    public Mercancia buscarPoRId(Integer id) throws Exception {
        try{
            Optional<Mercancia> mercancia=mercanciaRepocitorio.findById(id);
            return  mercancia.get();

        }catch(Exception error){
            throw  new Exception(error.getMessage());
        }



    }

    @Override
    public Mercancia registrar(Mercancia tabla) throws Exception {

        try{

           Optional<Zona> zonaAsociadaAMercancia=zonaRepositorio.findById(tabla.getZona().getId());
          double capacidadDisponibleZona=zonaAsociadaAMercancia.get().getDisponible();
          double capacidadOcupadaMercancia = tabla.getVolumen();
          double capacidadRestante=capacidadDisponibleZona-capacidadOcupadaMercancia;

          if (capacidadRestante>=0){
             zonaAsociadaAMercancia.get().setDisponible(capacidadRestante);
             zonaRepositorio.save(zonaAsociadaAMercancia.get());
              tabla=mercanciaRepocitorio.save(tabla);
              return tabla;
          } else{

           throw new Exception("no tenemos capacidad para esta mercancia");
          }

        }catch(Exception error){
            throw  new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia actualizar(Integer id, Mercancia tabla) throws Exception {
        try{
            Optional<Mercancia> mercanciaBuscar=mercanciaRepocitorio.findById(id);
            Mercancia mercancia=mercanciaBuscar.get();

            mercancia=mercanciaRepocitorio.save(tabla);

            return  mercancia;

        }catch(Exception error){
            throw  new Exception(error.getMessage());
        }
    }

    @Override
    public Boolean borrar(Integer id) throws Exception {
        try{

            if(mercanciaRepocitorio.existsById(id))
            {
            Optional<Mercancia> mercanciaARetirar=mercanciaRepocitorio.findById(id);
            Optional<Zona> zonaAsociadaAMercancia=zonaRepositorio.findById(mercanciaARetirar.get().getZona().getId());

            Double capacidadOcupadaPorMercancia=mercanciaARetirar.get().getVolumen();
            Double capacidadDisponible=zonaAsociadaAMercancia.get().getDisponible();
            Double capacidadLiberada=capacidadDisponible+capacidadOcupadaPorMercancia;
            zonaAsociadaAMercancia.get().setDisponible(capacidadLiberada);
            zonaRepositorio.save(zonaAsociadaAMercancia.get());

            mercanciaRepocitorio.deleteById(id);
               return true;
            } else{

                throw  new Exception();
            }

        }catch(Exception error){
            throw  new Exception(error.getMessage());
        }
    }
}
