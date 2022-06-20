package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.entidades.TipoDeCocina;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "restaurante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurante.findAll", query = "SELECT r FROM Restaurante r"),
    @NamedQuery(name = "Restaurante.findById", query = "SELECT r FROM Restaurante r WHERE r.id = :id"),
    @NamedQuery(name = "Restaurante.findByNombre", query = "SELECT r FROM Restaurante r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Restaurante.findByUrlSitioWeb", query = "SELECT r FROM Restaurante r WHERE r.urlSitioWeb = :urlSitioWeb"),
    @NamedQuery(name = "Restaurante.findByNumeroTelefonico", query = "SELECT r FROM Restaurante r WHERE r.numeroTelefonico = :numeroTelefonico"),
    @NamedQuery(name = "Restaurante.findByTipoCocina", query = "SELECT r FROM Restaurante r WHERE r.tipoCocina = :tipoCocina")})
public class Restaurante implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Restaurante";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 127)
    @Column(name = "nombre")
    private String nombre;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url_sitio_web")
    private String urlSitioWeb;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "numero_telefonico")
    private String numeroTelefonico;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "tipo_cocina")
    private String tipoCocina;
    
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRestaurante")
    private Collection<Producto> productoCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurante")
    private Collection<Review> reviewCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurante")
    private Collection<Pedido> pedidoCollection;

    public Restaurante()
    {
    }

    public Restaurante(Short id)
    {
        this.id = id;
    }

    public Restaurante(Short id, String nombre, String urlSitioWeb, String numeroTelefonico, String tipoCocina)
    {
        this.id = id;
        this.nombre = nombre;
        this.urlSitioWeb = urlSitioWeb;
        this.numeroTelefonico = numeroTelefonico;
        this.tipoCocina = tipoCocina;
    }
    
        /**
     * Crea un nuevo Restaurante a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Restaurante correspondiente.
     */
    public static Restaurante desdeParametros(Map<String, String[]> parametros)
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
        final Restaurante restaurante = new Restaurante();
        
        String idRestauranteStr = campos.get(RestauranteDAO.COLUMNA_ID);
        
        if (idRestauranteStr != null)
        {
            restaurante.setId(Short.parseShort(idRestauranteStr));
        }
        
        String nombre = campos.get(RestauranteDAO.COLUMNA_NOMBRE);
        restaurante.setNombre(nombre);
        
        // Obtener los valores a partir de los campos.
        String urlSitioweb = campos.get(RestauranteDAO.COLUMNA_SITIO_WEB);
        restaurante.setUrlSitioWeb(urlSitioweb);
        
        TipoDeCocina tipoDeCocina = TipoDeCocina.valueOf(campos.get(RestauranteDAO.COLUMNA_TIPO_COCINA));
        restaurante.setTipoCocina(tipoDeCocina.toString());
        
        String numTelefono = campos.get(RestauranteDAO.COLUMNA_NUM_TELEFONICO);
        restaurante.setNumeroTelefonico(numTelefono);
        
        String idUbicacionStr = campos.get(RestauranteDAO.COLUMNA_ID_UBICACION);
        
        if (idUbicacionStr != null)
        {
            int idUbicacion = Integer.parseInt(idUbicacionStr);
            restaurante.setUbicacion(new Ubicacion(idUbicacion));
        }
    
        // Retornar nueva instancia de la entidad.
        return restaurante;
    }

    @Override
    public String toString()
    {
        return "Restaurante{" + "id=" + id + ", nombre=" + nombre + ", urlSitioWeb=" + urlSitioWeb + ", numeroTelefonico=" + numeroTelefonico + ", tipoCocina=" + tipoCocina + ", ubicacion=" + ubicacion + ", productoCollection=" + productoCollection + ", reviewCollection=" + reviewCollection + ", pedidoCollection=" + pedidoCollection + '}';
    }

    public Short getId()
    {
        return id;
    }

    public void setId(Short id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getUrlSitioWeb()
    {
        return urlSitioWeb;
    }

    public void setUrlSitioWeb(String urlSitioWeb)
    {
        this.urlSitioWeb = urlSitioWeb;
    }

    public String getNumeroTelefonico()
    {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico)
    {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getTipoCocina()
    {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina)
    {
        this.tipoCocina = tipoCocina;
    }

    public Ubicacion getUbicacion()
    {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion)
    {
        this.ubicacion = ubicacion;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection()
    {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection)
    {
        this.productoCollection = productoCollection;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Restaurante)) 
        {
            return false;
        }
        
        Restaurante other = (Restaurante) object;
        
        boolean idEsNulo = this.id == null && other.id == null;
        boolean idsCoinciden = this.id != null && this.id.equals(other.id);
        
        return (idEsNulo || idsCoinciden);
    }
}
