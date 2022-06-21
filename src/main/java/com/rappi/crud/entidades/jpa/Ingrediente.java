package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.IngredienteDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i"),
    @NamedQuery(name = "Ingrediente.findById", query = "SELECT i FROM Ingrediente i WHERE i.id = :id"),
    @NamedQuery(name = "Ingrediente.findByNombre", query = "SELECT i FROM Ingrediente i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Ingrediente.findByEsVegano", query = "SELECT i FROM Ingrediente i WHERE i.esVegano = :esVegano"),
    @NamedQuery(name = "Ingrediente.findByEsAlergenico", query = "SELECT i FROM Ingrediente i WHERE i.esAlergenico = :esAlergenico"),
    @NamedQuery(name = "Ingrediente.findByTieneGluten", query = "SELECT i FROM Ingrediente i WHERE i.tieneGluten = :tieneGluten")})
public class Ingrediente implements Serializable
{
    private static final long serialVersionUID = 1L;
        
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Ingrediente";
    
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
    @Column(name = "esVegano")
    private boolean esVegano;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "esAlergenico")
    private boolean esAlergenico;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tieneGluten")
    private boolean tieneGluten;
    
    @JoinTable(name = "ingredientes_producto", joinColumns = {
        @JoinColumn(name = "id_ingrediente", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_producto", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Producto> productoCollection;

    public Ingrediente()
    {
    }

    public Ingrediente(Short id)
    {
        this.id = id;
    }

    public Ingrediente(Short id, String nombre, boolean esVegano, boolean esAlergenico, boolean tieneGluten)
    {
        this.id = id;
        this.nombre = nombre;
        this.esVegano = esVegano;
        this.esAlergenico = esAlergenico;
        this.tieneGluten = tieneGluten;
    }
    
   /**
     * Crea un nuevo Ingrediente a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Ingrediente correspondiente.
     */
    public static Ingrediente desdeParametros(Map<String, String[]> parametros)
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
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(IngredienteDAO.COLUMNA_NOMBRE);

        String idIngredienteStr = campos.get(IngredienteDAO.COLUMNA_ID);
        short idIngrediente = -1;

        if (campos.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngrediente = Short.parseShort(idIngredienteStr);
        }

        String esVeganoStr = campos.get(IngredienteDAO.COLUMNA_ESVEGANO);
        boolean esVegano = esVeganoStr != null && esVeganoStr.equals("on");
        System.out.println("Es vegano: " + esVegano);
        
        String esAlergicoStr = campos.get(IngredienteDAO.COLUMNA_ESALERGICO);
        boolean esAlergico = esAlergicoStr != null && esAlergicoStr.equals("on");
        System.out.println("Es Alergenico: " + esAlergico);

        String tieneGlutenStr = campos.get(IngredienteDAO.COLUMNA_TIENEGLUTEN);
        boolean tieneGluten = tieneGlutenStr != null && tieneGlutenStr.equals("on");
        System.out.println("Tiene gluten: " + tieneGluten);

        // Retornar nueva instancia de la entidad.
        return new Ingrediente(idIngrediente, nombre, esVegano, esAlergico, tieneGluten);
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

    public boolean getEsVegano()
    {
        return esVegano;
    }

    public void setEsVegano(boolean esVegano)
    {
        this.esVegano = esVegano;
    }

    public boolean getEsAlergenico()
    {
        return esAlergenico;
    }

    public void setEsAlergenico(boolean esAlergenico)
    {
        this.esAlergenico = esAlergenico;
    }

    public boolean getTieneGluten()
    {
        return tieneGluten;
    }

    public void setTieneGluten(boolean tieneGluten)
    {
        this.tieneGluten = tieneGluten;
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
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.rappi.crud.entidades.jpa.Ingrediente[ id=" + id + " ]";
    }
    
}
