/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.controler;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.udea.modelo.Mensaje;
import com.udea.servicio.ServicioMensaje;

/**
 *
 * @author itmanager
 */
@Named(value = "controladorMensaje")
@RequestScoped
public class ControladorMensaje {
    private Mensaje mensaje = new Mensaje();
    @Inject ServicioMensaje servicio;
    
    public Mensaje getMensaje(){ return mensaje;}
    public void setMensaje(Mensaje mensaje){ this.mensaje = mensaje;}
    
    public List<Mensaje> getMensajes(){
        return servicio.getMensajes();
    }
    
    public String guardar(){
        servicio.crear(mensaje);
        return "/index";
    }

    /**
     * Creates a new instance of ControladorMensaje
     */
    public ControladorMensaje() {
    }
    
}
