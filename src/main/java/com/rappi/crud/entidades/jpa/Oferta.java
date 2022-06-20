package com.rappi.crud.entidades.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o"),
    @NamedQuery(name = "Oferta.findById", query = "SELECT o FROM Oferta o WHERE o.id = :id"),
    @NamedQuery(name = "Oferta.findByPorcentajeDescuento", query = "SELECT o FROM Oferta o WHERE o.porcentajeDescuento = :porcentajeDescuento"),
    @NamedQuery(name = "Oferta.findByFechaTermino", query = "SELECT o FROM Oferta o WHERE o.fechaTermino = :fechaTermino")})
public class Oferta implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Oferta";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_descuento")
    private short porcentajeDescuento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTermino;
    
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Oferta()
    {
    }

    public Oferta(Integer id)
    {
        this.id = id;
    }

    public Oferta(Integer id, short porcentajeDescuento, Date fechaTermino)
    {
        this.id = id;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaTermino = fechaTermino;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public short getPorcentajeDescuento()
    {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(short porcentajeDescuento)
    {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Date getFechaTermino()
    {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino)
    {
        this.fechaTermino = fechaTermino;
    }

    public Producto getIdProducto()
    {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto)
    {
        this.idProducto = idProducto;
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
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.Oferta[ id=" + id + " ]";
    }
    
}
