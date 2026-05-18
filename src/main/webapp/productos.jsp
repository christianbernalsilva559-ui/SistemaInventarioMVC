<%@page import="modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String usuario = (String) session.getAttribute("usuario");

    // VALIDAR SESIÓN
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Producto productoEditar = (Producto) request.getAttribute("productoEditar");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Productos - Sistema Inventario</title>
    <link rel="stylesheet" href="css/estilos.css?v=5">
</head>
<body>

<div class="dashboard-container">

    <h1>Gestión de Productos</h1>

    <nav>
        <ul>
            <li><a href="dashboard.jsp">Inicio</a></li>
            <li><a href="ProductoServlet">Productos</a></li>
            <li><a href="ProductoServlet?accion=listar">Lista de Productos</a></li>
            <li><a href="LogoutServlet">Cerrar sesión</a></li>
        </ul>
    </nav>

    <div class="card">
        <h2><%= (productoEditar != null) ? "Editar Producto" : "Registrar Nuevo Producto" %></h2>

        <form action="ProductoServlet" method="post">

            <% if (productoEditar != null) { %>
                <input type="hidden" name="accion" value="actualizar">
                <input type="hidden" name="idProducto" value="<%= productoEditar.getIdProducto() %>">
            <% } else { %>
                <input type="hidden" name="accion" value="guardar">
            <% } %>

            <label>Nombre:</label>
            <input type="text"
                   name="nombre"
                   value="<%= (productoEditar != null) ? productoEditar.getNombre() : "" %>"
                   required>

            <label>Categoría:</label>
            <input type="text"
                   name="categoria"
                   value="<%= (productoEditar != null) ? productoEditar.getCategoria() : "" %>"
                   required>

            <label>Precio:</label>
            <input type="number"
                   step="0.01"
                   name="precio"
                   value="<%= (productoEditar != null) ? productoEditar.getPrecio() : "" %>"
                   required>

            <label>Stock:</label>
            <input type="number"
                   name="stock"
                   value="<%= (productoEditar != null) ? productoEditar.getStock() : "" %>"
                   required>

            <button type="submit">
                <%= (productoEditar != null) ? "Actualizar Producto" : "Guardar Producto" %>
            </button>

        </form>

    </div>

</div>

</body>
</html>