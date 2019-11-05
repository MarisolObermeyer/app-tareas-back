/**
 * 
 */
package com.todo.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Marisol
 *
 */

@Entity
@Table (name="Todo")
public class Todo {
	/**
	 * Identificador
	 */
	private long id;
	
	/**
	 * Descripci�n de la tarea
	 */
	private String descripcion;
	
	/**
	 * Estado de la tarea (Pendiente = false o Finalizada=true)
	 */
	private boolean estado;
	
	/**
	 * Archivo adjunto a la descripcion
	 */
	private byte[] adjunto;
	
	/**
	 * Constructor
	 */
	public Todo() {
		
	}
	
	/**
	 * Constructor con par�metros
	 * @param descripcion
	 * @param estado
	 * @param adjunto
	 */
	public Todo(String descripcion, boolean estado, byte[] adjunto) {
        this.setDescripcion(descripcion);
        this.setEstado(estado);
        this.setAdjunto(adjunto);
   }


	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estado", nullable = false)
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Lob
	@Column(name = "adjunto", nullable=true, columnDefinition="BLOB")
	public byte[] getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(byte[] adjunto) {
		this.adjunto = adjunto;
	}

}
