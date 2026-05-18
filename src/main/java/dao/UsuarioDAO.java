package dao;

import conexion.Conexion;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario validarUsuario(String correo, String password) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE correo = ? AND password = ? AND estado = TRUE";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getBoolean("estado"));
            }

        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return usuario;
    }
}
