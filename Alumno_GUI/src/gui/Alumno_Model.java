/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import persona.Alumno;

/**
 *
 * @author Fede
 */
public class Alumno_Model extends AbstractTableModel {

    List<Alumno> alumnos = new ArrayList<>();

    private static final String[] ENCABEZADOS = {"DNI", "Nombre", "Apellido", "Estado"};

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
        refrescarModelo();
    }

    @Override
    public int getRowCount() {
        if (alumnos != null) {
            return alumnos.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return ENCABEZADOS.length;
    }

    @Override
    public String getColumnName(int col) {
        return ENCABEZADOS[col];
    }
    
        
    @Override
    public Object getValueAt(int row, int col) {
        Alumno alu = alumnos.get(row);

        switch (col) {
            case 0: //DNI
                return alu.getDni();
            case 1: //Nombre
                return alu.getNombre();
            case 2: //Apellido
                return alu.getApellido();
//            case 3: //Fecha Nacimiento
//                return alu.getFechaNac();
//            case 4: //Fecha de Ingreso
//                return alu.getFechaIng();
//            case 5: //Cantidad de Materias Aprobadas
//                return alu.getCantMatAprob();
//            case 6: //Promedio
//                return alu.getPromedio();
//            case 7: //Sexo
//                return alu.getSexo();
            case 3: //Activo
                return alu.isActivo() ? "Alta" : "Baja";
        }
        return null;
    }

    public void refrescarModelo() {
        
        fireTableDataChanged();
    }
    
    public void limpiarTabla() throws Throwable{
        
        
    }
}
