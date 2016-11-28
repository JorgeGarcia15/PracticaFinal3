
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Héctor
 */
public class Conexiones {
    
     Connection conn1;
    ResultSet rs;
    DefaultTableModel model;
    
    public void AbrirConexion() {

        try {
            String url1 = "jdbc:mysql://localhost:3306/videojuegos";
            String user = "root";
            String password = "";
            conn1 = (Connection) DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Conectado a videojuegos…");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: dirección o usuario/clave no válida");
            ex.printStackTrace();
        }
    }

    public void cerrar_conexion() {
        try {
            conn1.close();
        } catch (SQLException ex) {
            System.out.println("ERROR:al cerrar la conexión");
            ex.printStackTrace();
        }
    }
    
    
    public void cargarTabla(JTable tabla, String consulta){
        
        try {
            //Para establecer el modelo al JTable
            DefaultTableModel modelo = new DefaultTableModel();
            tabla.setModel(modelo);
            //Para conectarnos a nuestra base de datos

            //Para ejecutar la consulta
            Statement s = conn1.createStatement();
            //Ejecutamos la consulta que escribimos en la caja de texto
            //y los datos lo almacenamos en un ResultSet
            ResultSet rs = s.executeQuery(consulta);
            //Obteniendo la informacion de las columnas que estan siendo consultadas
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                modelo.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
     public void removeSelectedRows(JTable table){
   DefaultTableModel model = (DefaultTableModel) table.getModel();
   int[] rows = table.getSelectedRows();
   for(int i=0;i<rows.length;i++){
     model.removeRow(rows[i]-i);
   }
     }
    
    public String borrarUnaFila(JTable tabla, String tablaelegida, int b) throws SQLException {

        String salida = "";
        System.out.println(tabla.getModel().getValueAt(b, 0));

        try {
            Statement sta = conn1.createStatement();
            sta.executeUpdate("DELETE FROM " + tablaelegida + " where Id = "+tabla.getModel().getValueAt(b, 0) +";");
            salida = "Columna borrada";
        } catch (Exception e) {
            salida = "Columna no existe";
        }
        return salida;
    } 
    public void borrarFilas(JTable tabla, String tablaelegida){
    
    for (int count = 0; count < tabla.getModel().getRowCount(); count++){
//        numdata.add(tabla.getModel().getValueAt(count, 0).toString()); 
       try {
            Statement sta = conn1.createStatement();
            sta.executeUpdate("DELETE FROM " + tablaelegida + " where Id = "+tabla.getModel().getValueAt(count, 0)+";");
            sta.close();
            
        } catch (Exception e) {
            
        }
    }
    
    
    
        
}
    
    }
    

