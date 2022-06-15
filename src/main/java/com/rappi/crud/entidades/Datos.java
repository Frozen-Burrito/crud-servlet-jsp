package com.rappi.crud.entidades;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Datos
{
    private final int mId;
    
    private String mTitulo;
    
    private Timestamp mFecha;

    private String mDescripcion;
    
    private int mStatus;
    
    private Timestamp mFechaCreacion;
    
    public static final String COLUMNA_ID = "id";
    
    public Datos(String mTitulo, Timestamp mFecha, String mDescripcion, int mStatus)
    {
        this.mId = -1;
        this.mTitulo = mTitulo;
        this.mFecha = mFecha;
        this.mDescripcion = mDescripcion;
        this.mStatus = mStatus;
        this.mFechaCreacion = null;
    }

    public Datos(int mId, String mTitulo, Timestamp mFecha, String mDescripcion, int mStatus, Timestamp mFechaCreacion)
    {
        this.mId = mId;
        this.mTitulo = mTitulo;
        this.mFecha = mFecha;
        this.mDescripcion = mDescripcion;
        this.mStatus = mStatus;
        this.mFechaCreacion = mFechaCreacion;
    }
    
    public Map<String, String> comoMapa()
    {
        Map<String, String> mapa = new HashMap<>();
    
        mapa.put(COLUMNA_ID, String.valueOf(mId));
        mapa.put("titulo", mTitulo);
        mapa.put("fecha", mFecha.toString());
        mapa.put("descripcion", mDescripcion);
        mapa.put("status", String.valueOf(mStatus));
        mapa.put("fechaCreacion", mFechaCreacion.toString());
        
        return mapa;
    }
    
    public static Datos desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        parametros.entrySet().forEach(entrada -> {
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        // Si no hay datos, indicar que el objeto de Datos estaría vacío usando
        // un NullPointerException.
        if (campos.size() < 1) 
        {
            throw new NullPointerException();
        }
        
        // Obtener los valores a partir de los campos.
        String titulo = campos.get("titulo");
        String descripcion = campos.get("descripcion");
        int status = Integer.parseInt(campos.get("status"));
        
        int id = -1;
        
        if (campos.get(COLUMNA_ID) != null)
        {
            id = Integer.parseInt(campos.get(COLUMNA_ID));
        }
        
        // Las fechas necesitan cambios, porque el HTML las produce con una "T" 
        // entre la fecha y hora, además de que no incluyen segundos.
        String strFechaCreacion = campos.get("fechaCreacion");
        Timestamp fechaCreacion = null; 
        
        if (strFechaCreacion != null)
        {
            fechaCreacion = Timestamp.valueOf(strFechaCreacion.replace("T", " ").concat(":00"));
        }
        
        String strFecha = campos.get("fecha");
        Timestamp fecha = null;
        
        if (strFecha != null)
        {
            fecha = Timestamp.valueOf(strFecha.replace("T", " ").concat(":00"));
        }
    
        // Retornar nueva instancia del Modelo.
        return new Datos(id, titulo, fecha, descripcion, status, fechaCreacion);
    }

    @Override
    public String toString()
    {
        return "Datos{" + "id =" + mId + ", titulo =" + mTitulo + ", fecha =" + mFecha + ", descripcion =" + mDescripcion + ", status =" + mStatus + ", fechaCreacion =" + mFechaCreacion + '}';
    }

    public int getId()
    {
        return mId;
    }

    public String getTitulo()
    {
        return mTitulo;
    }

    public void setTitulo(String titulo)
    {
        this.mTitulo = titulo;
    }

    public Timestamp getFecha()
    {
        return mFecha;
    }

    public void setFecha(Timestamp fecha)
    {
        this.mFecha = fecha;
    }

    public String getDescripcion()
    {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.mDescripcion = descripcion;
    }

    public int getStatus()
    {
        return mStatus;
    }

    public void setStatus(int status)
    {
        this.mStatus = status;
    }

    public Timestamp getFechaCreacion()
    {
        return mFechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion)
    {
        this.mFechaCreacion = fechaCreacion;
    }
}
