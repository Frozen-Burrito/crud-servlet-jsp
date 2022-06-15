package com.rappi.crud.entidades;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Datos
{
    private int mId;
    
    private String mTitulo;
    
    private Timestamp mFecha;

    private String mDescripcion;
    
    private int mStatus;
    
    private Timestamp mFechaCreacion;

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
    
        mapa.put("id", String.valueOf(mId));
        mapa.put("titulo", mTitulo);
        mapa.put("fecha", mFecha.toString());
        mapa.put("descripcion", mDescripcion);
        mapa.put("status", String.valueOf(mStatus));
        mapa.put("fechaCreacion", mFechaCreacion.toString());
        
        return mapa;
    }
    
    public static Datos desdeMapa(Map<String, String> mapa)
    {
        int id = Integer.parseInt(mapa.get("id"));
        String titulo = mapa.get("titulo");
        Timestamp fecha = Timestamp.valueOf(mapa.get("fecha"));
        String descripcion = mapa.get("descripcion");
        int status = Integer.parseInt(mapa.get("status"));
        Timestamp fechaCreacion = Timestamp.valueOf(mapa.get("fechaCreacion"));
    
        return new Datos(id, titulo, fecha, descripcion, status, fechaCreacion);
    }

    @Override
    public String toString()
    {
        return "Datos{" + "mId=" + mId + ", mTitulo=" + mTitulo + ", mFecha=" + mFecha + ", mDescripcion=" + mDescripcion + ", mStatus=" + mStatus + ", mFechaCreacion=" + mFechaCreacion + '}';
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
