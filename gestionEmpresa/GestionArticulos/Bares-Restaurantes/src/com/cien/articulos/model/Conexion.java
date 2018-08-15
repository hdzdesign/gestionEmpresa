package com.cien.articulos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clase que nos va a permitir conectarnos con la base de datos. 
 * Los datos están vinculados en el web.xml para mayor seguridad.
 * @author carlos
 *
 */
public class Conexion {
	
	private Connection jdbcConnection; // Objeto connection
	private String jdbcURL; // URL de la conexion a base de datos 
	private String jdbcUsername; // Username de la base de datos root por defecto
	private String jdbcPassword; // Password a la base de datos no mapeado.
	/**
	 * Creamos un contructor con los campos necesarios para iniciar la conexion.
	 * @param jdbcURL
	 * @param jdbcUsername
	 * @param jdbcPassword
	 */
	public Conexion(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	/**
	 * Metodo para conectar con la base de datos.
	 * @throws SQLException
	 */

	public void conectar() throws SQLException {
		/*
		 * El condicional nos indica que si la conexion es null o esta cerradaç
		 * Cargamos el Driver de mysq jdbc.
		 * la inicializamos getConnection ( jdbcUrl, jdbcUsername, jdbcPassword)
		 * 
		 */
		if(jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			/**
			 * Inicializamos jdbcConnection y recuperamos la URL Username y Password.
			 */
			jdbcConnection = DriverManager.getConnection(
					jdbcURL, jdbcUsername, jdbcPassword);
		}
		
	}
	/**
	 * Metodo que utilizaremos para desconectar de la base de datos.
	 * Cada vez que terminemos las modificaciones.
	 * @throws SQLException
	 */
	public void desconectar() throws SQLException {
		if(jdbcConnection !=null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	/**
	 * Metodo para recuperar la conexion
	 * @return
	 */
	public Connection getJdbcConnection() {
		return jdbcConnection;
	}

}
