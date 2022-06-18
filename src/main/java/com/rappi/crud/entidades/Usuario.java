package com.rappi.crud.entidades;

import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.servlets.Accion;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Usuario
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Usuario";
    
    // Columnas de la tabla en la BD.
    private String mNombreUsuario;
    
    private String mEmail;
    private TipoDeUsuario mTipoDeUsuario;
    private String mPassword;
    private String mNumTelefono;
    
    private int mIdUbicacion;
    private Ubicacion mUbicacion;
    
    public Usuario()
    {
    }

    public Usuario(String nombreUsuario, String email, TipoDeUsuario tipoDeUsuario, String password, String numTelefono, int idUbicacion)
    {
        this.mNombreUsuario = nombreUsuario;
        this.mEmail = email;
        this.mTipoDeUsuario = tipoDeUsuario;
        this.mPassword = password;
        this.mNumTelefono = numTelefono;
        this.mIdUbicacion = idUbicacion;
    }
    
    /**
     * Crea un nuevo Usuario a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Usuario correspondiente.
     */
    public static Usuario desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            System.out.println("Campo (" + entrada.getKey() + ": " + entrada.getValue()[0] + ")");
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        // Crear nueva instancia de Usuario.
        final Usuario usuario = new Usuario();
        
        String nombreUsuario = campos.get(UsuarioDAO.COLUMNA_ID);
        usuario.setNombreUsuario(nombreUsuario);
        
        // Obtener los valores a partir de los campos.
        String email = campos.get(UsuarioDAO.COLUMNA_EMAIL);
        usuario.setEmail(email);
        
        TipoDeUsuario tipoDeUsuario = TipoDeUsuario.valueOf(campos.get(UsuarioDAO.COLUMNA_TIPO));
        usuario.setTipoDeUsuario(tipoDeUsuario);
        
        String password = campos.get(UsuarioDAO.COLUMNA_PASSWORD);
        usuario.setPassword(password);
        
        String numTelefono = campos.get(UsuarioDAO.COLUMNA_NUM_TELEFONICO);
        usuario.setNumTelefono(numTelefono);
        
        String idUbicacionStr = campos.get(UsuarioDAO.COLUMNA_ID_UBICACION);
        
        if (idUbicacionStr != null)
        {
            int idUbicacion = Integer.parseInt(idUbicacionStr);
            usuario.setIdUbicacion(idUbicacion);
        }
    
        // Retornar nueva instancia de la entidad.
        return usuario;
    }
    
    /**
     * Determina el encabezado adecuado para la vista de detalles de la entidad,
     * según la acción de la vista.
     * 
     * @param accionVista La acción de la vista.
     * @return El texto para el encabezado de la vista.
     */
    public static String tituloVistaConAccion(Accion accionVista)
    {
        switch (accionVista) 
        {
            case LEER: return "Detalles del " + NOMBRE_ENTIDAD;
            case CREAR: return "Agrega un Nuevo " +NOMBRE_ENTIDAD;
            case ACTUALIZAR: return "Edita el " + NOMBRE_ENTIDAD;
            case ELIMINAR: return "¿Deseas eliminar este " + NOMBRE_ENTIDAD + "?";
        }
        
        return "Vista de Detalles de " + NOMBRE_ENTIDAD;
    }

    @Override
    public String toString()
    {
        return "Usuario{" + "mNombreUsuario=" + mNombreUsuario + ", mEmail=" + mEmail + ", mTipoDeUsuario=" + mTipoDeUsuario + ", mPassword=" + mPassword + ", mNumTelefono=" + mNumTelefono + ", mIdUbicacion=" + mIdUbicacion + ", mUbicacion=" + mUbicacion + '}';
    }

    public String getNombreUsuario()
    {
        return mNombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario)
    {
        this.mNombreUsuario = nombreUsuario;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public void setEmail(String email)
    {
        this.mEmail = email;
    }

    public TipoDeUsuario getTipoDeUsuario()
    {
        return mTipoDeUsuario;
    }

    public void setTipoDeUsuario(TipoDeUsuario tipoDeUsuario)
    {
        this.mTipoDeUsuario = tipoDeUsuario;
    }

    public String getPassword()
    {
        return mPassword;
    }
    
    public String getPasswordOfuscado() 
    {
        int longitud = mPassword.length();
        
        return Stream.generate(() -> String.valueOf('*'))
                    .limit(longitud)
                    .collect(Collectors.joining());
    }

    public void setPassword(String password)
    {
        this.mPassword = password;
    }

    public String getNumTelefono()
    {
        return mNumTelefono;
    }

    public void setNumTelefono(String numTelefono)
    {
        this.mNumTelefono = numTelefono;
    }

    public int getIdUbicacion()
    {
        return mIdUbicacion;
    }

    public void setIdUbicacion(int idUbicacion)
    {
        this.mIdUbicacion = idUbicacion;
    }

    public Ubicacion getUbicacion()
    {
        return mUbicacion;
    }

    public void setUbicacion(Ubicacion ubicacion)
    {
        this.mUbicacion = ubicacion;
    }
    
    
}
