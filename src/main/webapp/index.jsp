<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Librería Digital</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            line-height: 1.6;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .menu {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }
        .menu a {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            min-width: 150px;
            text-align: center;
        }
        .menu a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Librería Digital</h1>
        <div class="menu">
            <a href="clientes">Gestión de Clientes</a>
            <a href="productos">Gestión de Productos</a>
            <a href="categorias">Gestión de Categorías</a>
            <a href="ventas">Gestión de Ventas</a>
            <a href="reportes">Reportes y Estadísticas</a>
            <a href="descuentos">Gestión de Descuentos</a>
            <a href="acerca-de">Acerca de</a>
        </div>
    </div>
</body>
</html>
