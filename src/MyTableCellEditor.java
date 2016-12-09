
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**

 */

public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor{

    private Conexiones db;
    public String tabla  = "";
    private String OldValue=""; //Valor antiguo de la celda
    private String NewValue=""; //valor nuevo de la celda
    private String NameColum="";//nombre de la columna
    private String ID="";// Llave del registro
      private String ID2="";// Llave del registro
        private String TIPO="";// Llave del registro
        private String numTelf = "";
    private JComponent component = new JTextField();

    public MyTableCellEditor(Conexiones db, String NameColumn, String tabla)
    {
            this.db = db;
            this.NameColum = NameColumn;
            this.tabla = tabla;
    }

    public Object getCellEditorValue() {
        return ((JTextField)component).getText();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        OldValue = value.toString();//Toma valor de celda antes de cualquier modificación
        ID = table.getValueAt(row,0).toString();//obtiene el ID unico del registro
         ID2 = table.getValueAt(row,1).toString();//obtiene el ID unico del registro
         TIPO = table.getValueAt(row,4).toString();//obtiene el ID unico del registro
           numTelf = table.getValueAt(row,5).toString();//obtiene el ID unico del registro
        ((JTextField)component).setText(value.toString());//coloca valor de la celda al JTextField
        return component;
    }

    
    public boolean stopCellEditing() {
        NewValue = (String)getCellEditorValue();//Captura nuevo valor de la celda
        //Compara valores, si no son iguales, debe actualizar registro
        if( !NewValue.equals(OldValue))
        {
            //Realiza la actualizacion
            if(NameColum.equals("Num_telefono")){
                System.out.println(ID);
             if( !db.modificarNestedNum(ID, NewValue, TIPO, tabla) )
            {   //Si existe algun error al actualizar, escribe viejo valor en la celda
                JOptionPane.showMessageDialog(null,"Error: No se puede actualizar");
                ((JTextField)component).setText(OldValue);
            }
            
            } else if(NameColum.equals("Tipo")){
            if( !db.modificarNestedTipo(ID, NewValue, numTelf, tabla) )
            {   //Si existe algun error al actualizar, escribe viejo valor en la celda
                JOptionPane.showMessageDialog(null,"Error: No se puede actualizar");
                ((JTextField)component).setText(OldValue);
            }
            
            }else {
                    
            
            if( !db.modificarFila( NameColum+"='"+NewValue+"' ", ID, ID2, TIPO , tabla) )
            {   //Si existe algun error al actualizar, escribe viejo valor en la celda
                JOptionPane.showMessageDialog(null,"Error: No se puede actualizar");
                ((JTextField)component).setText(OldValue);
            }
            }
        }
        
        return super.stopCellEditing();
    }
}
