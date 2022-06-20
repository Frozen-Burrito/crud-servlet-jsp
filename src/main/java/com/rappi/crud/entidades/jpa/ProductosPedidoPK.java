package com.rappi.crud.entidades.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ProductosPedidoPK implements Serializable
{

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private short idProducto;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pedido")
    private int idPedido;

    public ProductosPedidoPK()
    {
    }

    public ProductosPedidoPK(short idProducto, int idPedido)
    {
        this.idProducto = idProducto;
        this.idPedido = idPedido;
    }

    public short getIdProducto()
    {
        return idProducto;
    }

    public void setIdProducto(short idProducto)
    {
        this.idProducto = idProducto;
    }

    public int getIdPedido()
    {
        return idPedido;
    }

    public void setIdPedido(int idPedido)
    {
        this.idPedido = idPedido;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) idProducto;
        hash += (int) idPedido;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductosPedidoPK)) {
            return false;
        }
        ProductosPedidoPK other = (ProductosPedidoPK) object;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idPedido != other.idPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.ProductosPedidoPK[ idProducto=" + idProducto + ", idPedido=" + idPedido + " ]";
    }
    
}
