/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredienteEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CesarF
 */
public class RecipeDetailDTO extends RecipeDTO {
    
    private List<IngredienteDTO> ingredientes;
    public RecipeDetailDTO(){
    
    }
    
    public RecipeDetailDTO(RecipeEntity entity){
        super(entity);
         
        if (entity != null) {
            if (entity.getIngredientes()!= null) {
                ingredientes = new ArrayList<>();
                for (IngredienteEntity entityIngrediente : entity.getIngredientes()) {
                    ingredientes.add(new IngredienteDTO(entityIngrediente));
                }
            }
        }
    }
    public RecipeEntity toEntity(){
        RecipeEntity editorialEntity = super.toEntity();
        if (ingredientes != null) {
            List<IngredienteEntity> booksEntity = new ArrayList<>();
            for (IngredienteDTO dtoBook : ingredientes) {
                booksEntity.add(dtoBook.toEntity());
            }
            editorialEntity.setIngredientes(booksEntity);
        }
        return editorialEntity;
    }
    

    /**
     * @return the ingredientes
     */
    public List<IngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(List<IngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
}
