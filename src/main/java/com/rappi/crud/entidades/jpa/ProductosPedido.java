package com.rappi.crud.entidades.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "productos_pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductosPedido.findAll", query = "SELECT p FROM ProductosPedido p"),
    @NamedQuery(name = "ProductosPedido.findByIdProducto", query = "SELECT p FROM ProductosPedido p WHERE p.productosPedidoPK.idProducto = :idProducto"),
    @NamedQuery(name = "ProductosPedido.findByIdPedido", query = "SELECT p FROM ProductosPedido p WHERE p.productosPedidoPK.idPedido = :idPedido"),
    @NamedQuery(name = "ProductosPedido.findByCantidad", query = "SELECT p FROM ProductosPedido p WHERE p.cantidad = :cantidad")})
public class ProductosPedido implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Productos del pedido";
    
    @EmbeddedId
    protected ProductosPedidoPK productosPedidoPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private short cantidad;
    
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;
    
    @JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ProductosPedido()
    {
    }

    public ProductosPedido(ProductosPedidoPK productosPedidoPK)
    {
        this.productosPedidoPK = productosPedidoPK;
    }

    public ProductosPedido(ProductosPedidoPK productosPedidoPK, short cantidad)
    {
        this.productosPedidoPK = productosPedidoPK;
        this.cantidad = cantidad;
    }

    public ProductosPedido(short idProducto, int idPedido)
    {
        this.productosPedidoPK = new ProductosPedidoPK(idProducto, idPedido);
    }

    public ProductosPedidoPK getProductosPedidoPK()
    {
        return productosPedidoPK;
    }

    public void setProductosPedidoPK(ProductosPedidoPK productosPedidoPK)
    {
        this.productosPedidoPK = productosPedidoPK;
    }

    public short getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(short cantidad)
    {
        this.cantidad = cantidad;
    }

    public Pedido getPedido()
    {
        return pedido;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public void setProducto(Producto producto)
    {
        this.producto = producto;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (productosPedidoPK != null ? productosPedidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductosPedido)) {
            return false;
        }
        ProductosPedido other = (ProductosPedido) object;
        if ((this.productosPedidoPK == null && other.productosPedidoPK != null) || (this.productosPedidoPK != null && !this.productosPedidoPK.equals(other.productosPedidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.ProductosPedido[ productosPedidoPK=" + productosPedidoPK + " ]";
    }
    
}
