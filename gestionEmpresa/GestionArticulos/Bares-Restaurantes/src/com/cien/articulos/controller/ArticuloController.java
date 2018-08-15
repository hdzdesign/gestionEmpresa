package com.cien.articulos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cien.articulos.dao.ArticuloDAO;
import com.cien.articulos.model.Articulo;

@WebServlet("/articuloController")
public class ArticuloController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ArticuloDAO articuloDAO;

	@Override
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		try {
			articuloDAO = new ArticuloDAO(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArticuloController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hola Servlet DoGet");
		String action = request.getParameter("action");
		System.out.println(action);
		try {
		switch (action) {
		case "index":
			index(request, response);
			break;
		case "nuevo":
			nuevo(request, response);
			break;
		case "register":
			registrar(request, response);
			break;
		case "mostrar":
			mostrar(request, response);
			break;			
		case "showedit":
			showEdit(request, response);
			break;
		case "editar":
			editar(request, response);
			break;
		case "eliminar":
			eliminar(request, response);
			break;
		default:
			break;
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hola Servlet DoPost");
		doGet(request, response);
		
	}
	private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	private void registrar(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException, SQLException {
		Articulo articulo = new Articulo(
				0,
				request.getParameter("codigo"),
				request.getParameter("nombre"),
				request.getParameter("descripcion"),
				Double.parseDouble(request.getParameter("existencia")),
				Double.parseDouble(request.getParameter("precio")));
		articuloDAO.insertar(articulo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	private void nuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/register.jsp");
		dispatcher.forward(request, response);
		
	}
	private void mostrar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/mostrar.jsp");
		List<Articulo> listaArticulos = articuloDAO.listarArticulos();
		request.setAttribute("lista", listaArticulos);
		dispatcher.forward(request, response);
	}
	private void showEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
		Articulo articulo = articuloDAO.obtenerPorId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("articulo", articulo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/editar.jsp");
		dispatcher.forward(request, response);
	}
	private void editar(HttpServletRequest request, HttpServletResponse response) throws  SQLException, ServletException, IOException {
		Articulo articulo = new Articulo(
				Integer.parseInt(request.getParameter("id")),
				request.getParameter("codigo"),
				request.getParameter("nombre"),
				request.getParameter("descripcion"),
				Double.parseDouble(request.getParameter("existencia")),
				Double.parseDouble(request.getParameter("precio")));
		articuloDAO.actualizar(articulo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		Articulo articulo = articuloDAO.obtenerPorId(Integer.parseInt(request.getParameter("id")));
		articuloDAO.eliminar(articulo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		
	}
	
	
	
	

}
