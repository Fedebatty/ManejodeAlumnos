package persona;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MiCalendario extends GregorianCalendar {

    public MiCalendario() {
    }

    public MiCalendario(int dia, int mes, int anio) throws MiCalendarioException {
        super(anio, mes-1, dia);
        //apagar permisividad ( que no intente parsearlo ya que le pasamos los datos de forma estricta)
        setLenient(false);
        
        try {
            get(Calendar.YEAR);
        }catch (Exception ex) {
            throw new MiCalendarioException("Error en la fecha");
        }
    }

    public MiCalendario(Calendar cal) {
        setTimeInMillis(cal.getTimeInMillis());
    }
    
    public MiCalendario(Date date) {
        setTimeInMillis(date.getTime());
    }
    

    
    public int getAnio(){
        return get(Calendar.YEAR);
    }
        
    public int getMes(){
        return get(MONTH)+1;
    }
    
    public int getDia(){
        return get(DAY_OF_MONTH);
    }

    @Override
    public String toString() {
        return String.format("%2d/%2d/%4d", getDia(), getMes(), getAnio());
    }

    public Date toSQLDate() {
        return new Date(this.getTimeInMillis());
    }
    
}
