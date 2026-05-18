<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String usuario = (String) session.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");

    Integer paginaActual = (Integer) request.getAttribute("paginaActual");
    Integer totalPaginas = (Integer) request.getAttribute("totalPaginas");
    String buscar = (String) request.getAttribute("buscar");

    if (paginaActual == null) {
        paginaActual = 1;
    }

    if (totalPaginas == null) {
        totalPaginas = 1;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos - Sistema Inventario</title>
    <link rel="stylesheet" href="css/estilos.css?v=11">
</head>
<body>

<div class="dashboard-container">

    <h1>Lista de Productos</h1>

    <!-- BUSCADOR -->
    <div class="card">
        <form action="ProductoServlet" method="get" class="busqueda-form">
            <input type="hidden" name="accion" value="listar">

            <div class="busqueda-contenedor">
                <input type="text"
                       name="buscar"
                       value="<%= (buscar != null) ? buscar : "" %>"
                       placeholder="Buscar por nombre o categoría..."
                       class="input-busqueda">

                <button type="submit" class="btn-buscar">
                    Buscar
                </button>

                <a href="ProductoServlet?accion=listar" class="btn-mostrar-todos-inline">
                    Mostrar Todos
                </a>
            </div>
        </form>
    </div>

    <!-- NAVBAR -->
    <nav>
        <ul>
            <li><a href="dashboard.jsp">Inicio</a></li>
            <li><a href="ProductoServlet">Registrar Producto</a></li>
            <li><a href="LogoutServlet">Cerrar sesión</a></li>
        </ul>
    </nav>

    <!-- TABLA -->
    <div class="card">

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>

            <tbody>
                <% if (listaProductos != null && !listaProductos.isEmpty()) {
                    for (Producto p : listaProductos) { %>

                        <tr>
                            <td><%= p.getIdProducto() %></td>
                            <td><%= p.getNombre() %></td>
                            <td><%= p.getCategoria() %></td>
                            <td>S/ <%= p.getPrecio() %></td>
                            <td><%= p.getStock() %></td>
                            <td>
                                <div class="acciones">

                                    <a class="btn-editar"
                                       href="ProductoServlet?accion=editar&id=<%= p.getIdProducto() %>">
                                        Editar
                                    </a>

                                    <a class="btn-eliminar"
                                       href="ProductoServlet?accion=eliminar&id=<%= p.getIdProducto() %>"
                                       onclick="return confirm('¿Eliminar este producto?')">
                                        Eliminar
                                    </a>

                                </div>
                            </td>
                        </tr>

                <%  }
                   } else { %>

                        <tr>
                            <td colspan="6">No hay productos registrados.</td>
                        </tr>

                <% } %>
            </tbody>
        </table>

        <!-- PAGINACIÓN -->
        <div class="paginacion">

            <% if (paginaActual > 1) { %>
                <a href="ProductoServlet?accion=listar&pagina=<%= paginaActual - 1 %><%= (buscar != null && !buscar.trim().isEmpty()) ? "&buscar=" + buscar : "" %>"
                   class="btn-pagina">
                    Anterior
                </a>
            <% } %>

            <%
                int inicioPagina = Math.max(1, paginaActual - 2);
                int finPagina = Math.min(totalPaginas, paginaActual + 2);

                for (int i = inicioPagina; i <= finPagina; i++) {
            %>

                <a href="ProductoServlet?accion=listar&pagina=<%= i %><%= (buscar != null && !buscar.trim().isEmpty()) ? "&buscar=" + buscar : "" %>"
                   class="btn-pagina <%= (i == paginaActual) ? "pagina-activa" : "" %>">
                    <%= i %>
                </a>

            <%
                }
            %>

            <% if (paginaActual < totalPaginas) { %>
                <a href="ProductoServlet?accion=listar&pagina=<%= paginaActual + 1 %><%= (buscar != null && !buscar.trim().isEmpty()) ? "&buscar=" + buscar : "" %>"
                   class="btn-pagina">
                    Siguiente
                </a>
            <% } %>

        </div>

    </div>

</div>

</body>
</html>
