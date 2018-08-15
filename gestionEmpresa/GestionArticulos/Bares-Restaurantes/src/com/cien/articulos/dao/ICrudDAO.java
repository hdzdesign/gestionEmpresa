package com.cien.articulos.dao;

import java.sql.SQLException;
import java.util.List;

import com.cien.articulos.model.Articulo;

public interface ICrudDAO {
	
	public boolean insertar(Articulo articulo) throws SQLException;
	
	public List<Articulo> listarArticulos() throws SQLException;
	
	public Articulo obtenerPorId(int id) throws SQLException;
	
	public boolean actualizar(Articulo articulo) throws SQLException;
	
	public boolean eliminar(Articulo articulo) throws SQLException;
	
	

}

