package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.UbicacionDAO;
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
@Table(name = "ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u"),
    @NamedQuery(name = "Ubicacion.findById", query = "SELECT u FROM Ubicacion u WHERE u.id = :id"),
    @NamedQuery(name = "Ubicacion.findByCalle", query = "SELECT u FROM Ubicacion u WHERE u.calle = :calle"),
    @NamedQuery(name = "Ubicacion.findByNumeroExterior", query = "SELECT u FROM Ubicacion u WHERE u.numeroExterior = :numeroExterior"),
    @NamedQuery(name = "Ubicacion.findByNumeroInterior", query = "SELECT u FROM Ubicacion u WHERE u.numeroInterior = :numeroInterior")})
public class Ubicacion implements Serializable
{
    private static final long serialVersionUID = 1L;
 
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Ubicación";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 511)
    @Column(name = "calle")
    private String calle;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_exterior")
    private short numeroExterior;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_interior")
    private short numeroInterior;
    
    @JoinColumn(name = "id_colonia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Colonia colonia;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion")
    private Collection<Restaurante> restaurantes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion")
    private Collection<Usuario> usuarios;

    public Ubicacion()
    {
    }

    public Ubicacion(Integer id)
    {
        this.id = id;
    }

    public Ubicacion(Integer id, String calle, short numeroExterior, short numeroInterior)
    {
        this.id = id;
        this.calle = calle;
        this.numeroExterior = numeroExterior;
        this.numeroInterior = numeroInterior;
    }
    
    /**
     * Crea una nueva Ubicacion a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Ubicacion correspondiente.
     */
    public static Ubicacion desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Colonia.
        final Ubicacion ubicacion = new Ubicacion();
        
        if (campos.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            int id = Integer.parseInt(campos.get(UbicacionDAO.COLUMNA_ID));
            ubicacion.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String calle = campos.get(UbicacionDAO.COLUMNA_CALLE);
        ubicacion.setCalle(calle);
        
        String strNumExt = campos.get(UbicacionDAO.COLUMNA_NUM_EXTERIOR);
        String strNumInt = campos.get(UbicacionDAO.COLUMNA_NUM_INTERIOR);
        
        if (strNumExt != null && strNumInt != null)
        {
            short numExterior = Short.parseShort(strNumExt);
            ubicacion.setNumeroExterior(numExterior);
            
            short numInterior = Short.parseShort(strNumInt);
            ubicacion.setNumeroInterior(numInterior);
        }
        
        String idColoniaStr = campos.get(UbicacionDAO.COLUMNA_ID_COLONIA);
        
        if (idColoniaStr != null)
        {
            short idColonia = Short.parseShort(idColoniaStr);
            ubicacion.setColonia(new Colonia(idColonia));
        }
    
        // Retornar nueva instancia de la entidad.
        return ubicacion;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s, #%s-%s, %s", calle, numeroExterior, numeroInterior, colonia);
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCalle()
    {
        return calle;
    }

    public void setCalle(String calle)
    {
        this.calle = calle;
    }

    public short getNumeroExterior()
    {
        return numeroExterior;
    }

    public void setNumeroExterior(short numeroExterior)
    {
        this.numeroExterior = numeroExterior;
    }

    public short getNumeroInterior()
    {
        return numeroInterior;
    }

    public void setNumeroInterior(short numeroInterior)
    {
        this.numeroInterior = numeroInterior;
    }

    public Colonia getColonia()
    {
        return colonia;
    }

    public void setColonia(Colonia idColonia)
    {
        this.colonia = idColonia;
    }

    @XmlTransient
    public Collection<Restaurante> getRestauranteCollection()
    {
        return restaurantes;
    }

    public void setRestauranteCollection(Collection<Restaurante> restauranteCollection)
    {
        this.restaurantes = restauranteCollection;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection()
    {
        return usuarios;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection)
    {
        this.usuarios = usuarioCollection;
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
        if (!(object instanceof Ubicacion)) 
        {
            return false;
        }
        
        Ubicacion other = (Ubicacion) object;
         
        boolean idEsNulo = this.id == null && other.id == null;
        boolean idsCoinciden = this.id != null && this.id.equals(other.id);
        
        return (idEsNulo || idsCoinciden);
    }
}
