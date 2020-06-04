/*class DataEntryOperator :
	Variables
		operatör sicil no int ID
		ad soya String adSoyad
		operatör birim String departman
	methods
		parametresiz constructor
		tüm değişkenleri kullanan const.
		toString
		get set
*/

public class DataEntryOperator {
    private int ID;
    private String adSoyad;
    private String departman;
    
    public DataEntryOperator() {
        this(00000,"Ad Soyad","Departman");
    }
    
    public DataEntryOperator(int ID,String adSoyad,String departman) {
        
        setID(ID);
        setAdSoyad(adSoyad);
        setDepartman(departman);
    }

    @Override
    public String toString() {
        //editlenecek
        return "DataEntryOperator{" + "ID=" + ID + ", adSoyad=" + adSoyad 
                + ", departman=" + departman + '}';
    }

    public int getID() {
        return ID;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public String getDepartman() {
        return departman;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }   
}
