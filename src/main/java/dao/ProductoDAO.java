package dao;

import conexion.Conexion;
import java.math.BigDecimal;
import modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // LISTAR PRODUCTOS ACTIVOS
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE estado = TRUE";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setEstado(rs.getBoolean("estado"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    // AGREGAR PRODUCTO
    public boolean agregarProducto(Producto p) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setBigDecimal(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setBoolean(5, true);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }

        return false;
    }

    // ELIMINAR PRODUCTO (ELIMINACIÓN LÓGICA)
    public boolean eliminarProducto(int idProducto) {
        String sql = "UPDATE productos SET estado = FALSE WHERE id_producto = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }

        return false;
    }

    // BUSCAR PRODUCTO POR ID
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Producto p = new Producto();

                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setStock(rs.getInt("stock"));
                    p.setEstado(rs.getBoolean("estado"));

                    return p;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null;
    }

    // ACTUALIZAR PRODUCTO
    public boolean actualizarProducto(Producto p) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id_producto = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setBigDecimal(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }

        return false;
    }
    // BUSCAR PRODUCTOS

    public List<Producto> buscarProductos(String buscar) {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE estado = TRUE AND (nombre LIKE ? OR categoria LIKE ?)";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + buscar + "%");
            ps.setString(2, "%" + buscar + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setEstado(rs.getBoolean("estado"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar productos: " + e.getMessage());
        }

        return lista;
    }
    // LISTAR PRODUCTOS PAGINADOS

    public List<Producto> listarProductosPaginados(int inicio, int registrosPorPagina) {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE estado = TRUE LIMIT ?, ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, inicio);
            ps.setInt(2, registrosPorPagina);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setEstado(rs.getBoolean("estado"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar productos paginados: " + e.getMessage());
        }

        return lista;
    }
// CONTAR TOTAL PRODUCTOS

    public int contarProductos() {
        int total = 0;

        String sql = "SELECT COUNT(*) FROM productos WHERE estado = TRUE";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al contar productos: " + e.getMessage());
        }

        return total;
    }
    // TOTAL PRODUCTOS ACTIVOS

    public int obtenerTotalProductos() {
        int total = 0;

        String sql = "SELECT COUNT(*) FROM productos WHERE estado = TRUE";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error total productos: " + e.getMessage());
        }

        return total;
    }

// PRODUCTOS CON STOCK BAJO (<10)
    public int obtenerProductosBajoStock() {
        int total = 0;

        String sql = "SELECT COUNT(*) FROM productos WHERE estado = TRUE AND stock < 10";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error productos bajo stock: " + e.getMessage());
        }

        return total;
    }

// VALOR TOTAL INVENTARIO
    public BigDecimal obtenerValorInventario() {
        BigDecimal total = BigDecimal.ZERO;

        String sql = "SELECT SUM(precio * stock) FROM productos WHERE estado = TRUE";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next() && rs.getBigDecimal(1) != null) {
                total = rs.getBigDecimal(1);
            }

        } catch (Exception e) {
            System.out.println("Error valor inventario: " + e.getMessage());
        }

        return total;
    }
}
