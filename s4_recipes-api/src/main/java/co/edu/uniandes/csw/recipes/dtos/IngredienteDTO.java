/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredienteEntity;

/**
 *
 * @author estudiante
 */
public class IngredienteDTO {

    private String name;
    private Long calorias;
    private Long id;
    
    public IngredienteDTO() {
        
    }

    public IngredienteDTO(IngredienteEntity ing) {
        this.name = ing.getName();
        this.calorias = ing.getCalorias();
        this.id = ing.getId();
    }
    
    public IngredienteEntity toEntity() {
        IngredienteEntity entity = new IngredienteEntity();
        entity.setId(this.id);
        entity.setName(this.name);        
        entity.setCalorias(this.calorias);
        return entity;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the calorias
     */
    public Long getCalorias() {
        return calorias;
    }

    /**
     * @param calorias the calorias to set
     */
    public void setCalorias(Long calorias) {
        this.calorias = calorias;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
}
