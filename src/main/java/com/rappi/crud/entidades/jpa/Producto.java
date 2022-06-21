package com.rappi.crud.entidades.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByDisponible", query = "SELECT p FROM Producto p WHERE p.disponible = :disponible"),
    @NamedQuery(name = "Producto.findByKcalPorcion", query = "SELECT p FROM Producto p WHERE p.kcalPorcion = :kcalPorcion"),
    @NamedQuery(name = "Producto.findByTama\u00f1oPorcion", query = "SELECT p FROM Producto p WHERE p.tama\u00f1oPorcion = :tama\u00f1oPorcion"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByMonedaIso4217", query = "SELECT p FROM Producto p WHERE p.monedaIso4217 = :monedaIso4217"),
    @NamedQuery(name = "Producto.findByTipo", query = "SELECT p FROM Producto p WHERE p.tipo = :tipo")})
public class Producto implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Producto";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponible")
    private boolean disponible;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "kcal_porcion")
    private short kcalPorcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tama\u00f1o_porcion")
    private short tamañoPorcion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private float precio;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "moneda_iso_4217")
    private String monedaIso4217;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "tipo")
    private String tipo;
    
    @ManyToMany(mappedBy = "productoCollection")
    private Collection<Ingrediente> ingredienteCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Oferta> ofertaCollection;
    
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Restaurante idRestaurante;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductosPedido> productosPedidoCollection;

    public Producto()
    {
    }

    public Producto(Short id)
    {
        this.id = id;
    }

    public Producto(Short id, String nombre, boolean disponible, short kcalPorcion, short tamañoPorcion, String descripcion, float precio, String monedaIso4217, String tipo)
    {
        this.id = id;
        this.nombre = nombre;
        this.disponible = disponible;
        this.kcalPorcion = kcalPorcion;
        this.tamañoPorcion = tamañoPorcion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.monedaIso4217 = monedaIso4217;
        this.tipo = tipo;
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

    public boolean getDisponible()
    {
        return disponible;
    }

    public void setDisponible(boolean disponible)
    {
        this.disponible = disponible;
    }

    public short getKcalPorcion()
    {
        return kcalPorcion;
    }

    public void setKcalPorcion(short kcalPorcion)
    {
        this.kcalPorcion = kcalPorcion;
    }

    public short getTamañoPorcion()
    {
        return tamañoPorcion;
    }

    public void setTamañoPorcion(short tamañoPorcion)
    {
        this.tamañoPorcion = tamañoPorcion;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public float getPrecio()
    {
        return precio;
    }

    public void setPrecio(float precio)
    {
        this.precio = precio;
    }

    public String getMonedaIso4217()
    {
        return monedaIso4217;
    }

    public void setMonedaIso4217(String monedaIso4217)
    {
        this.monedaIso4217 = monedaIso4217;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Ingrediente> getIngredienteCollection()
    {
        return ingredienteCollection;
    }

    public void setIngredienteCollection(Collection<Ingrediente> ingredienteCollection)
    {
        this.ingredienteCollection = ingredienteCollection;
    }

    @XmlTransient
    public Collection<Oferta> getOfertaCollection()
    {
        return ofertaCollection;
    }

    public void setOfertaCollection(Collection<Oferta> ofertaCollection)
    {
        this.ofertaCollection = ofertaCollection;
    }

    public Restaurante getIdRestaurante()
    {
        return idRestaurante;
    }

    public void setIdRestaurante(Restaurante idRestaurante)
    {
        this.idRestaurante = idRestaurante;
    }

    @XmlTransient
    public Collection<ProductosPedido> getProductosPedidoCollection()
    {
        return productosPedidoCollection;
    }

    public void setProductosPedidoCollection(Collection<ProductosPedido> productosPedidoCollection)
    {
        this.productosPedidoCollection = productosPedidoCollection;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.Producto[ id=" + id + " ]";
    }
    
}
