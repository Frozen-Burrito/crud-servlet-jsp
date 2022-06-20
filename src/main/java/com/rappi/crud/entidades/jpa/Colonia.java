package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.ColoniaDAO;
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
@Table(name = "colonia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colonia.findAll", query = "SELECT c FROM Colonia c"),
    @NamedQuery(name = "Colonia.findById", query = "SELECT c FROM Colonia c WHERE c.id = :id"),
    @NamedQuery(name = "Colonia.findByNombre", query = "SELECT c FROM Colonia c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Colonia.findByCodigoPostal", query = "SELECT c FROM Colonia c WHERE c.codigoPostal = :codigoPostal")})
public class Colonia implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Colonia";
    
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
    @Column(name = "codigo_postal")
    private int codigoPostal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colonia")
    private Collection<Ubicacion> ubicacionCollection;
    
    @JoinColumn(name = "id_municipio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Municipio municipio;

    public Colonia()
    {
    }

    public Colonia(Short id)
    {
        this.id = id;
    }

    public Colonia(Short id, String nombre, int codigoPostal)
    {
        this.id = id;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
    }
    
    /**
     * Crea una nueva Colonia a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Colonia correspondiente.
     */
    public static Colonia desdeParametros(Map<String, String[]> parametros)
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
        final Colonia colonia = new Colonia();
        
        if (campos.get(ColoniaDAO.COLUMNA_ID) != null)
        {
            short id = Short.parseShort(campos.get(ColoniaDAO.COLUMNA_ID));
            colonia.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(ColoniaDAO.COLUMNA_NOMBRE);
        colonia.setNombre(nombre);
        
        String strCodigoPostal = campos.get(ColoniaDAO.COLUMNA_CODIGO_POSTAL);
        
        if (strCodigoPostal != null)
        {
            int codigoPostal = Integer.parseInt(strCodigoPostal);
            colonia.setCodigoPostal(codigoPostal);
        }
        
        String idMunicipioStr = campos.get(ColoniaDAO.COLUMNA_ID_MUNICIPIO);
        
        System.out.println("ID municipio: " );
        
        if (idMunicipioStr != null)
        {
            short idMunicipio = Short.parseShort(idMunicipioStr);
            colonia.setMunicipio(new Municipio(idMunicipio));
        }
    
        // Retornar nueva instancia de la entidad.
        return colonia;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s %s", nombre, municipio);
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

    public int getCodigoPostal()
    {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal)
    {
        this.codigoPostal = codigoPostal;
    }

    @XmlTransient
    public Collection<Ubicacion> getUbicacionCollection()
    {
        return ubicacionCollection;
    }

    public void setUbicacionCollection(Collection<Ubicacion> ubicacionCollection)
    {
        this.ubicacionCollection = ubicacionCollection;
    }

    public Municipio getMunicipio()
    {
        return municipio;
    }

    public void setMunicipio(Municipio municipio)
    {
        this.municipio = municipio;
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
        if (!(object instanceof Colonia)) 
        {
            return false;
        }
        
        Colonia other = (Colonia) object;
        
        boolean idEsNulo = this.id == null && other.id == null;
        boolean idsCoinciden = this.id != null && this.id.equals(other.id);
        
        return (idEsNulo || idsCoinciden);
    }
}
