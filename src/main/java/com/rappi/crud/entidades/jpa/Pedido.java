package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.PedidoDAO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findById", query = "SELECT p FROM Pedido p WHERE p.id = :id"),
    @NamedQuery(name = "Pedido.findByFecha", query = "SELECT p FROM Pedido p WHERE p.fecha = :fecha")})
public class Pedido implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Pedido";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @JoinColumn(name = "id_cliente", referencedColumnName = "nombre_usuario")
    @ManyToOne(optional = false)
    private Usuario cliente;
    
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Restaurante restaurante;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Collection<ProductosPedido> productos;

    public Pedido()
    {
    }

    public Pedido(Integer id)
    {
        this.id = id;
    }

    public Pedido(Integer id, Date fecha)
    {
        this.id = id;
        this.fecha = fecha;
    }
    
        /**
     * Crea un nuevo Producto a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Producto correspondiente.
     */
    public static Pedido desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            System.out.println(entrada.getKey() + ": " + entrada.getValue()[0]);
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        // Crear nueva instancia de Producto.
        final Pedido pedido = new Pedido();
        
        // Obtener los valores a partir de los campos.
        String idPedidoString = campos.get(PedidoDAO.COLUMNA_ID);
        
        if (idPedidoString != null)
        {
            pedido.setId(Integer.parseInt(idPedidoString));
        }
        
        // Las fechas necesitan cambios, porque el HTML las produce con una "T" 
        // entre la fecha y hora, además de que no incluyen segundos.
        String strFecha = campos.get(PedidoDAO.COLUMNA_FECHA);
        
        if (strFecha != null)
        {
            pedido.setFecha(Timestamp.valueOf(strFecha.replace("T", " ").concat(":00")));
        }
        
        String idClienteStr = campos.get(PedidoDAO.COLUMNA_ID_CLIENTE);
//        pedido.setCliente(idClienteStr);
        
        String idRestauranteStr = campos.get(PedidoDAO.COLUMNA_ID_RESTAURANTE);
        
        if (idRestauranteStr != null)
        {
            int idRestaurante = Integer.parseInt(idRestauranteStr);
//            pedido.setRestaurante(idRestaurante);
        }
    
        // Retornar nueva instancia de la entidad.
        return pedido;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Usuario getCliente()
    {
        return cliente;
    }

    public void setCliente(Usuario cliente)
    {
        this.cliente = cliente;
    }

    public Restaurante getRestaurante()
    {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante)
    {
        this.restaurante = restaurante;
    }

    @XmlTransient
    public Collection<ProductosPedido> getProductos()
    {
        return productos;
    }

    public void setProductos(Collection<ProductosPedido> productos)
    {
        this.productos = productos;
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
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.Pedido[ id=" + id + " ]";
    }
    
}
