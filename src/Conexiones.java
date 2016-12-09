
import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jorge
 */
public class Conexiones {

    Connection conn1;
    ResultSet rs;
    DefaultTableModel model;

    public void AbrirConexion() {

        try {
            String url1 = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "SYSTEM";
            String password = "ROOT";
            conn1 = (Connection) DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Conectado a dentista…");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: dirección o usuario/clave no válida");
            ex.printStackTrace();
        }
    }

    public int añadirDentista(String _DNI, String _Nombre, String _Apellidos, String _Nacimiento, int _SeguridadSocial, int _numMovil,String _numTipo, String _Email) {
        try {

            Statement sta = conn1.createStatement();

            sta.executeUpdate("INSERT INTO dentista VALUES ('"+_DNI+"', '"+_Nombre+"', '"+_Apellidos+"', '"+_Email+"', tabla_telefonos (telefono('"+_numTipo+"', '"+_numMovil+"')),'"+_SeguridadSocial+"', TO_DATE('"+_Nacimiento+"'))");
            return 0;
        } catch (SQLException ex) {
            System.out.println("ERROR:al hacer un Insert");

            ex.printStackTrace();
            return 1;
        }
    }

    void loadcombo(JComboBox combo, String consulta) {
        try {
            // Your database connections 
            Statement selcod = conn1.createStatement();
            ResultSet rs = selcod.executeQuery(consulta);

            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }

    public int añadirPaciente(String _DNI, String _Nombre, String _Apellidos, String _Nacimiento, String _Email, String _movilTipo, int _Movil) {
        try {

            Statement sta = conn1.createStatement();

            sta.executeUpdate("INSERT INTO paciente VALUES ('"+_DNI+"', '"+_Nombre+"', '"+_Apellidos+"', tabla_telefonos (telefono('"+_movilTipo+"', '"+_Movil+"')) ,'"+_movilTipo+"')");
            return 0;
        } catch (SQLException ex) {
            System.out.println("ERROR:al hacer un Insert");

            ex.printStackTrace();
            return 1;
        }
    }

    public int añadirCitas(String _Dentista, String _Paciente, int _Precio, String _Fecha, String _Observaciones) {
        try {
            
            System.out.println("INSERT INTO citas VALUES(\n"
                    + " (SELECT REF(a) FROM dentista a where a.dni ='"+_Dentista+"'), \n"
                    + " (SELECT REF(b) FROM paciente b where b.dni = '"+_Paciente+"'),\n"
                    + "  TO_DATE('"+_Fecha+"'),\n"
                    + "   "+_Precio+", '"+_Observaciones+"')");

            Statement sta = conn1.createStatement();

            sta.executeUpdate("INSERT INTO citas VALUES(\n"
                    + " (SELECT REF(a) FROM dentista a where a.dni ='"+_Dentista+"'), \n"
                    + " (SELECT REF(b) FROM paciente b where b.dni = '"+_Paciente+"'),\n"
                    + "  TO_DATE('"+_Fecha+"'),\n"
                    + "   "+_Precio+", '"+_Observaciones+"')");

            return 0;
        } catch (SQLException ex) {
            System.out.println("ERROR:al hacer un Insert");

            ex.printStackTrace();
            return 1;
        }
    }

    public void borrarFilas(JTable tabla, String tablaElegida) {

        int[] selectedRow = tabla.getSelectedRows();
        int del = 1;

        if (selectedRow.length > 1) {

            try {
                Statement sta = conn1.createStatement();
                conn1.setAutoCommit(false);
                for (int j = 0; j < selectedRow.length; j++) {

                    sta.executeUpdate("DELETE from " + tablaElegida + " where DNI = '" + tabla.getModel().getValueAt(selectedRow[j], 0) + "'");

                    // if para evitar errores a la hora de eliminar las filas de la tabla por el cambio de posiciones
                    // al eliminar index anteriores 
                    if (j < selectedRow.length - 1) {
                        selectedRow[j + 1] = selectedRow[j + 1] - del;
                        del = del + 1;
                    }
                }
                conn1.commit();
                sta.close();
                conn1.setAutoCommit(true);
            } catch (Exception e) {
                try {
                    if (conn1 != null) {
                        conn1.rollback();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
            //Si solo borro una fila
        } else {

            try {
                Statement sta = conn1.createStatement();
                sta.executeUpdate("DELETE from " + tablaElegida + " where DNI = '" + tabla.getModel().getValueAt(selectedRow[0], 0) + "'");

                sta.close();
            } catch (Exception e) {

            }

        }
    }

    public void borrarFilasCitas(JTable tabla, String tablaElegida) {

        int[] selectedRow = tabla.getSelectedRows();
        int del = 1;

        if (selectedRow.length > 1) {

            try {
                Statement sta = conn1.createStatement();
                conn1.setAutoCommit(false);
                for (int j = 0; j < selectedRow.length; j++) {

                    sta.executeUpdate("DELETE from " + tablaElegida + " where DNI = '" + tabla.getModel().getValueAt(selectedRow[j], 0) + "'");

                    // if para evitar errores a la hora de eliminar las filas de la tabla por el cambio de posiciones
                    // al eliminar index anteriores 
                    if (j < selectedRow.length - 1) {
                        selectedRow[j + 1] = selectedRow[j + 1] - del;
                        del = del + 1;
                    }
                }
                conn1.commit();
                sta.close();
                conn1.setAutoCommit(true);
            } catch (Exception e) {
                try {
                    if (conn1 != null) {
                        conn1.rollback();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
            //Si solo borro una fila
        } else {

            try {
                Statement sta = conn1.createStatement();
                sta.executeUpdate("DELETE from " + tablaElegida + " where DNI = '" + tabla.getModel().getValueAt(selectedRow[0], 0) + "';");

                sta.close();
            } catch (Exception e) {

            }

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

    public boolean modificarNestedNum(String id, String newV, String TIPO, String tabla) {
          boolean res = false;
        String q = null;
        System.out.println("-------------------------------------");
        System.out.println(id);
        System.out.println(newV);
        System.out.println(TIPO);
        System.out.println("UPDATE TABLE (SELECT TELEFONO FROM DENTISTA where DNI = '"+id+"') SET Num_telefono = "+newV+" where Tipo = '"+TIPO+"'");

//        q = "UPDATE TABLE ( SELECT telefono FROM " + tabla + " WHERE DNI = " + id + ") telf SET VALUE(telf) = telefono('" + newV + "', 7) WHERE VALUE(telf) = telefono('" + oldV + "', 666555444)";
//      
        
        q = "UPDATE TABLE (SELECT TELEFONO FROM "+tabla+" where DNI = '"+id+"') SET Num_telefono = "+newV+" where Tipo = '"+TIPO+"'";
        try {
            Statement pstm = conn1.createStatement();
            pstm.executeUpdate(q);
            pstm.close();
            res = true;

        } catch (SQLException e) {
            System.out.println(e);
            res = false;
        }
        return res;
    }
    
    
    public boolean modificarNestedTipo(String id, String newV, String numTelf, String tabla) {
          boolean res = false;
        String q = null;
   
       
      
        
        q = "UPDATE TABLE (SELECT TELEFONO FROM "+tabla+" where DNI = '"+id+"') SET  Tipo = '"+newV+"' where Num_telefono = "+numTelf+"";
        try {
            Statement pstm = conn1.createStatement();
            pstm.executeUpdate(q);
            pstm.close();
            res = true;

        } catch (SQLException e) {
            System.out.println(e);
            res = false;
        }
        return res;
    }

    public boolean modificarFila(String valores, String id, String id2, String id3, String tabla) {
        String q = null;
        boolean res = false;

        if (tabla.equals("Dentista") || tabla.equals("Paciente")) {

            q = " UPDATE " + tabla + " SET " + valores + " WHERE DNI= '" + id + "'";
        } else {
            q = " UPDATE " + tabla + " SET " + valores + " WHERE Dentista= '" + id + "' && Paciente ='" + id2 + "' && Fecha = '" + id3 + "' ";
        }
        try {
            PreparedStatement pstm = conn1.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res = true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }

    public void cargarTabla(JTable tabla, String consulta) {

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
                    tabla.setDefaultRenderer(tabla.getColumnClass(i), tableCellRenderer);
                }
                modelo.addRow(fila);
            }
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-YYYY");

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof Date) {
                value = f.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }

    };

}
