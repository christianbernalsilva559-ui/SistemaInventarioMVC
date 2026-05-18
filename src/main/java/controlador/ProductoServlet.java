package controlador;

import dao.ProductoDAO;
import modelo.Producto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // SI NO HAY ACCIÓN -> FORMULARIO PRODUCTOS
        if (accion == null || accion.trim().isEmpty()) {
            cargarFormularioProductos(request, response);
            return;
        }

        switch (accion) {

            case "listar":
                cargarListaProductos(request, response);
                break;

            case "eliminar":
                try {
                    int idEliminar = Integer.parseInt(request.getParameter("id"));
                    productoDAO.eliminarProducto(idEliminar);
                } catch (Exception e) {
                    System.out.println("Error al eliminar: " + e.getMessage());
                }

                response.sendRedirect("ProductoServlet?accion=listar");
                break;

            case "editar":
                try {
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    Producto producto = productoDAO.buscarPorId(idEditar);

                    request.setAttribute("productoEditar", producto);

                } catch (Exception e) {
                    System.out.println("Error al editar: " + e.getMessage());
                }

                request.getRequestDispatcher("productos.jsp").forward(request, response);
                break;

            default:
                cargarFormularioProductos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {

            if (accion == null || accion.equals("guardar")) {

                Producto producto = new Producto();

                producto.setNombre(request.getParameter("nombre"));
                producto.setCategoria(request.getParameter("categoria"));
                producto.setPrecio(new BigDecimal(request.getParameter("precio")));
                producto.setStock(Integer.parseInt(request.getParameter("stock")));

                productoDAO.agregarProducto(producto);

            } else if (accion.equals("actualizar")) {

                Producto producto = new Producto();

                producto.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
                producto.setNombre(request.getParameter("nombre"));
                producto.setCategoria(request.getParameter("categoria"));
                producto.setPrecio(new BigDecimal(request.getParameter("precio")));
                producto.setStock(Integer.parseInt(request.getParameter("stock")));

                productoDAO.actualizarProducto(producto);
            }

        } catch (Exception e) {
            System.out.println("Error en operación POST: " + e.getMessage());
        }

        response.sendRedirect("ProductoServlet?accion=listar");
    }

    // FORMULARIO REGISTRAR / EDITAR
    private void cargarFormularioProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    // LISTA DE PRODUCTOS CON PAGINACIÓN
    private void cargarListaProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pagina = 1;
        int registrosPorPagina = 20;

        try {
            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        int inicio = (pagina - 1) * registrosPorPagina;

        String buscar = request.getParameter("buscar");

        List<Producto> listaProductos;
        int totalProductos;

        if (buscar != null && !buscar.trim().isEmpty()) {

            listaProductos = productoDAO.buscarProductos(buscar);
            totalProductos = listaProductos.size();

        } else {

            listaProductos = productoDAO.listarProductosPaginados(inicio, registrosPorPagina);
            totalProductos = productoDAO.contarProductos();
        }

        int totalPaginas = (int) Math.ceil((double) totalProductos / registrosPorPagina);

        request.setAttribute("listaProductos", listaProductos);
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("buscar", buscar);

        request.getRequestDispatcher("listaProductos.jsp").forward(request, response);
    }
}
