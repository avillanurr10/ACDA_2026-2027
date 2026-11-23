package com.centro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {

    public int insertar(Moto moto) throws SQLException {
        String sql = "INSERT INTO motos (marca, modelo, cilindrada, precio) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, moto.getMarca());
            ps.setString(2, moto.getModelo());
            ps.setInt(3, moto.getCilindrada());
            ps.setDouble(4, moto.getPrecio());

            return ps.executeUpdate();
        }
    }

    public List<Moto> listarTodas() throws SQLException {
        String sql = "SELECT id, marca, modelo, cilindrada, precio FROM motos ORDER BY id";
        List<Moto> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Moto moto = new Moto(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("cilindrada"),
                        rs.getDouble("precio")
                );
                lista.add(moto);
            }
        }
        return lista;
    }

    public int actualizarPrecio(int id, double nuevoPrecio) throws SQLException {
        String sql = "UPDATE motos SET precio = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setInt(2, id);

            return ps.executeUpdate();
        }
    }

    public int eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM motos WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate();
        }
    }
}