package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.TipoDeUsuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByCredenciales", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.password = :password"),
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByNumeroTelefonico", query = "SELECT u FROM Usuario u WHERE u.numeroTelefonico = :numeroTelefonico")})
public class Usuario implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Usuario";
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "tipo")
    private String tipo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "numero_telefonico")
    private String numeroTelefonico;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
    private Collection<Review> reviewCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<Pedido> pedidoCollection;
    
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;

    public Usuario()
    {
    }

    public Usuario(String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String nombreUsuario, String email, String tipo, String password, String numeroTelefonico)
    {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.tipo = tipo;
        this.password = password;
        this.numeroTelefonico = numeroTelefonico;
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
        usuario.setTipo(tipoDeUsuario.toString());
        
        String password = campos.get(UsuarioDAO.COLUMNA_PASSWORD);
        usuario.setPassword(password);
        
        String numTelefono = campos.get(UsuarioDAO.COLUMNA_NUM_TELEFONICO);
        usuario.setNumeroTelefonico(numTelefono);
        
        String idUbicacionStr = campos.get(UsuarioDAO.COLUMNA_ID_UBICACION);
        
        if (idUbicacionStr != null)
        {
            int idUbicacion = Integer.parseInt(idUbicacionStr);
            usuario.setUbicacion(new Ubicacion(idUbicacion));
        }
    
        // Retornar nueva instancia de la entidad.
        return usuario;
    }

    @Override
    public String toString()
    {
        return "Usuario{" + "nombreUsuario=" + nombreUsuario + ", email=" + email + ", tipo=" + tipo + ", password=" + password + ", numeroTelefonico=" + numeroTelefonico + ", reviewCollection=" + reviewCollection + ", pedidoCollection=" + pedidoCollection + ", ubicacion=" + ubicacion + '}';
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getPassword()
    {
        return password;
    }
    
    public String getPasswordOfuscado() 
    {
        int longitud = password.length();
        
        return Stream.generate(() -> String.valueOf('*'))
                    .limit(longitud)
                    .collect(Collectors.joining());
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNumeroTelefonico()
    {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico)
    {
        this.numeroTelefonico = numeroTelefonico;
    }

    @XmlTransient
    public Collection<Review> getReviewCollection()
    {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Review> reviewCollection)
    {
        this.reviewCollection = reviewCollection;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection()
    {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection)
    {
        this.pedidoCollection = pedidoCollection;
    }

    public Ubicacion getUbicacion()
    {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion)
    {
        this.ubicacion = ubicacion;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Usuario)) 
        {
            return false;
        }
        
        Usuario other = (Usuario) object;
        
        boolean idsCoinciden = this.nombreUsuario.equals(other.nombreUsuario);
        
        return idsCoinciden;
    }
}
