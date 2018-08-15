package com.cien.articulos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cien.articulos.model.Articulo;
import com.cien.articulos.model.Conexion;

public class ArticuloDAO {

	private Conexion con;
	private Connection connection;

	/**
	 * En este constructor estamos obligando que cuando se cree un articuloDAO , nos
	 * veamos en la obligación de introducir los datos de la base de datos. y a la
	 * vez estamos incializando el objeto Conexion con los datos de la base de datos
	 * 
	 * @param jdbcURL
	 * @param jdbcUsername
	 * @param jdbcPassword
	 */
	public ArticuloDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		System.out.println(jdbcURL);
		con = new Conexion(jdbcURL, jdbcUsername, jdbcPassword);
	}

	/**
	 * Insertar en base de datos pasamos por parametro un objeto de tipo Articulo Se
	 * genera la Query de insert en base de datos y la dejamos preparada para
	 * PreparedStatement Conectamos a la base de datos con el objeto de la clase
	 * modelo Conexion. Utilizamos la interfaz Connection y almacenamos en esa
	 * variable la conexion generada anteriormente Generamos un objeto de la
	 * interfaz PreparedStatement y almacenamos en ella la Query genrada
	 * anteriormente, queda precompilada para ser usada cuando sea oportuno y tenga
	 * los datos necesarios. posteriormente se recogen todos los datos del objeto en
	 * cuestion y se ejecuta el executeUpdate cerramos recursos y devolvemos true si
	 * la operación se llevo acabo y false si ocurrio algún erro
	 * 
	 * @param articulo
	 * @return
	 * @throws SQLException
	 */
	public boolean insertar(Articulo articulo) throws SQLException {
		String sql = "INSERT INTO articulos(id, codigo, nombre, descripcion, existencia, precio) VALUES (?, ?, ?, ?, ?, ?)";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, null);
		statement.setString(2, articulo.getCodigo());
		statement.setString(3, articulo.getNombre());
		statement.setString(4, articulo.getDescripcion());
		statement.setDouble(5, articulo.getExistencia());
		statement.setDouble(6, articulo.getPrecio());

		boolean rowInserted = statement.executeUpdate() > 0;
		System.out.println("Articulo insertado es: "+ articulo.toString());
		statement.close();
		con.desconectar();

		return rowInserted;
	}

	/**
	 * Metodo que devuelve una lista de todos los articulos encontrados en la base
	 * da datos. Generamos la query conectamos Recuperamos la conexion y la
	 * almacenamos en un objeto de tipo Connection. Creamos un objeto de Statement y
	 * almacenamos en el la creación del mismo. Ejecutamos la query SQL y la
	 * almacenamos en un ResulSet. Recorremos el Resulset recuperando todos los
	 * datos de cada columna. Creamos un objeto de articulo con todos los datos que
	 * acabamos de recuperar. Lo almacenamos en la lista de articulos
	 * 
	 * @return lista de articulos generada.
	 * @throws SQLException
	 */

	public List<Articulo> listarArticulos() throws SQLException {

		List<Articulo> listaArticulos = new ArrayList<>();
		String sql = "SELECT id, codigo, nombre, descripcion, existencia, precio FROM articulos";

		con.conectar();
		connection = con.getJdbcConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String codigo = resultSet.getString("codigo");
			String nombre = resultSet.getString("nombre");
			String descripcion = resultSet.getString("descripcion");
			double existencia = resultSet.getDouble("existencia");
			double precio = resultSet.getDouble("precio");
			Articulo articulo = new Articulo(id, codigo, nombre, descripcion, existencia, precio);
			listaArticulos.add(articulo);
		}
		resultSet.close();
		connection.close();
		return listaArticulos;
	}
	/**
	 * Este metodo nos permite recuperar un articulo introduciendo su id.
	 * Creamos un objeto del tipo Articulo
	 * Creamos la query para hacer un select
	 * conectamos con la base de datos con el objeto Conexion.
	 * Recuperamos los datos de la conexion con el metodo GetConnection y lo almacenamos en un objeto de tipo Connection.
	 * Creamos el Statement y lo almacenamos en un objeto del tipo Statement.
	 * Ejecutamos la Query y lo almacenamos en un objeto de tipo ResulSet.
	 * Recorremos  la lista de resultados con un condicional if y mientras exista otro resultado , iremos recuperando y almacenando en un objeto tipo Articulo
	 * cerramos recursos
	 * Devolvemos el objeto Articulo
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Articulo obtenerPorId(int id) throws SQLException {
		Articulo articulo = null;

		String sql = "SELECT id, codigo, nombre, descripcion, existencia, precio FROM articulos";
		con.conectar();
		connection = con.getJdbcConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			articulo = new Articulo(resultSet.getInt("id"), resultSet.getString("codigo"),
					resultSet.getString("nombre"), resultSet.getString("descripcion"),
					resultSet.getDouble("existencia"), resultSet.getDouble("precio"));
		}
		resultSet.close();
		con.desconectar();
		return articulo;
	}
	/**
	 * Metodo que nos va a permitir actualizar usuarios de la base de datos
	 * Creamos la Query UPDATE.
	 * Conectamos y recuperamos la conexion
	 * Preparamos el Statement pasandole la Query SQL  através del objeto Connection para almacenarlo en un objeto de tipo PrepareStatemen.
	 * Seteamos los valores nuevos y recuperamos los valores actualizados.
	 * Ejecutamos la query update siempre que el index sea superior a 0.
	 * cerramos recursos 
	 * devolvemos el valor true si se ejecuto la query correctamente
	 * 
	 */
	public boolean actualizar(Articulo articulo) throws SQLException {
		boolean rowActualizar = false;
		String sql = "UPDATE articulos SET codigo=?,nombre=?,descripcion=?,existencia=?,precio=? WHERE id=?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, articulo.getCodigo());
		statement.setString(2, articulo.getNombre());
		statement.setString(3, articulo.getDescripcion());
		statement.setDouble(4, articulo.getExistencia());
		statement.setDouble(5, articulo.getPrecio());
		statement.setInt(6, articulo.getId());
		rowActualizar = statement.executeUpdate()>0;
		System.out.println("El Precio actualizado es: "+ articulo.toString());
		statement.close();
		con.desconectar();
		return rowActualizar;
	}
	/**
	 * Metodo que vamos a utilizar para borrar una entrada de base de datos
	 * Declaramos las variables boolean para confirmación de borrado y String  para declarar la Query SQL
	 * conectamos con la base de dato.
	 * Recuperamos la conexion y la almacenamos en un objeto de tipo Connection.
	 * Preparamos la SQL y la almacenamos en un objeto de PreparedStatement para ser utilizado una vez sea necesario.
	 * Le decimos la id que vamos a borrar insertando su posicion en la columba y recuperamos la id borrada
	 * cerramos recursos
	 * devolvemos la variable boolean de confirmación.
	 * @param articulo
	 * @return
	 * @throws SQLException
	 */
	
	public boolean eliminar(Articulo articulo) throws SQLException {
		boolean rowEliminar = false;
		String sql = "DELETE FROM articulos WHERE ID= ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, articulo.getId());
		rowEliminar = statement.executeUpdate()>0;
		System.out.println("El Articulo borrado es: "+articulo.toString());
		statement.close();
		con.desconectar();
		return rowEliminar;
		
	}
	
	

}
