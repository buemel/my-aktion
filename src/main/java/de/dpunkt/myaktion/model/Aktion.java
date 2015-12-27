package de.dpunkt.myaktion.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({	@NamedQuery(name=Aktion.findByOrganisator, query="SELECT a FROM Aktion a WHERE a.organisator = :organisator ORDER BY a.name"),
	            @NamedQuery(name=Aktion.findAll, query="SELECT a from Aktion a ORDER BY a.name"),
				@NamedQuery(name=Aktion.getBisherGespendet, query="SELECT SUM(s.betrag) FROM Spende s WHERE s.aktion = :action")})
@Entity
public class Aktion {
	public static final String findByOrganisator = "Aktion.findByOrganisator";
	public static final String findAll = "Aktion.findAll";
	public static final String getBisherGespendet = "Aktion.getBisherGespendet";
	
	@NotNull
	@Size(min=4, max=30, message="Der Name einer Aktion muss min. 4 und darf max. 30 Zeichen lang sein.")
	private String name;
	
	@NotNull(message="Bitte ein Spendenziel angeben.")
	@DecimalMin(value="10.00", message="Das Spendenziel für die  Aktion muss min. 10 Euro sein.")
	private Double spendenZiel;
	
	@NotNull(message="Bitte einen Spendenbetrag angeben.")
	@DecimalMin(value="1.00", message="Der Spendenbetrag muss min. 1 Euro sein.")
	private Double spendenBetrag;
	
	@Transient
	private Double bisherGespendet;
	
	@AttributeOverrides({@AttributeOverride(name = "name", column = @Column(name = "kontoName"))})
	@Embedded
	private Konto konto;
	
	@GeneratedValue
	@Id
	private Long id;
	
	@OneToMany(mappedBy = "aktion", cascade = CascadeType.REMOVE)
	private List<Spende> spenden;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Organisator organisator;

	public Aktion() {
		konto = new Konto();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSpendenZiel() {
		return spendenZiel;
	}

	public void setSpendenZiel(Double spendenZiel) {
		this.spendenZiel = spendenZiel;
	}

	public Double getSpendenBetrag() {
		return spendenBetrag;
	}

	public void setSpendenBetrag(Double spendenBetrag) {
		this.spendenBetrag = spendenBetrag;
	}

	public Double getBisherGespendet() {
		return bisherGespendet;
	}

	public void setBisherGespendet(Double bisherGespendet) {
		this.bisherGespendet = bisherGespendet;
	}

	public Konto getKonto() {
		return konto;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<Spende> getSpenden() {
		return spenden;
	}

	public void setSpenden(List<Spende> spenden) {
		this.spenden = spenden;
	}
	
	public Organisator getOrganisator() {
		return organisator;
	}
	
	public void setOrganisator(Organisator organisator) {
		this.organisator = organisator;
	}
}