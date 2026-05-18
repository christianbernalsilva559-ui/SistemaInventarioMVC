<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = (String) session.getAttribute("usuario");

    if(usuario == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Sistema Inventario</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>

    <div class="dashboard-container">
        <h1>Bienvenido, <%= usuario %></h1>

        <nav>
            <ul>
                <li><a href="dashboard.jsp">Inicio</a></li>
                <li><a href="ProductoServlet">Productos</a></li>
                <li><a href="LogoutServlet">Cerrar sesión</a></li>
            </ul>
        </nav>

        <section>
            <h2>Panel Principal</h2>
            <p>Gestiona inventario, productos y ventas.</p>
        </section>
    </div>

</body>
</html>