import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import javafx.scene.input.KeyCode;



public class Main {
    
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws FileNotFoundException {
        
        int i;
        int j;
        int enToplamKelime = 0;
        int toplamDoğruKelime = 0;
        int düzeltilenToplamKelime = 0;
        double mühendislikOranToplam = 0;
        int mühendisSayısı = 0;
        double tıpOranToplam = 0;
        int tıpOperatörSayısı = 0;
        double tümAlgoBaşarıToplamı = 0;
        int girdiSayisi = 1;
        ArrayList<Text> textler = new ArrayList<>();
        DataEntryOperator dataEntryOperator = new DataEntryOperator();
        double[][] ikiBoyutluDizi = new double[5][6] ;
      
        Scanner girdiOku = new Scanner(new FileReader("metadata.txt"));
        String girdiAdi = girdiOku.nextLine();
        Text text = new Text(dataEntryOperator , girdiAdi);
        textler.add(text);
        while (girdiOku.hasNextLine()) {
            girdiAdi = girdiOku.nextLine();
            girdiSayisi++;
            text = new Text(dataEntryOperator , girdiAdi);
            textler.add(text);
        }
        for (i=0 ; i<textler.size() ; i++) {
            double toplamKelime = textler.get(i).girdiListe.size();
            double doğruKelime = textler.get(i).doğruKelimeSayısı;
            double düzeltilenKelime = textler.get(i).düzeltilenKelimeSayısı;
            double hatalıDüzeltilmeyen = (toplamKelime-textler.get(i).doğruKelimeSayısı) - textler.get(i).düzeltilenKelimeSayısı;
            double operatorDoğruOranı = doğruKelime / toplamKelime;
            double başarıOranı = düzeltilenKelime / (düzeltilenKelime + hatalıDüzeltilmeyen);
            
            ikiBoyutluDizi[i][0] = toplamKelime;
            ikiBoyutluDizi[i][1] = doğruKelime;
            ikiBoyutluDizi[i][2] = düzeltilenKelime;
            ikiBoyutluDizi[i][3] = hatalıDüzeltilmeyen;
            ikiBoyutluDizi[i][4] = operatorDoğruOranı;
            ikiBoyutluDizi[i][5] = başarıOranı;
            
            enToplamKelime += toplamKelime;
            toplamDoğruKelime += doğruKelime;
            düzeltilenToplamKelime += düzeltilenKelime;
            
            tümAlgoBaşarıToplamı += başarıOranı;
            
            if (textler.get(i).yazar.getDepartman().equals("MuhendislikFakultesi")) {
                mühendislikOranToplam += operatorDoğruOranı;
                mühendisSayısı++;
            }
            else if (textler.get(i).yazar.getDepartman().equals("TipFakultesi")) {
                tıpOranToplam += 1-operatorDoğruOranı;
                tıpOperatörSayısı++;
            }
            
        }
        System.out.println("------------------------------------------"
                     + "-----------------------------------------------------"
                     + "-----------------------------------------");
        for (j=0 ; j<textler.size() ; j++) {
             textler.get(j).yazdırma();
             System.out.println();
             System.out.println("------------------------------------------"
                     + "-----------------------------------------------------"
                     + "-----------------------------------------");
        } 
        System.out.println("Okunan tüm metinlerdeki toplam kelime sayısı : " +
                enToplamKelime);
        System.out.println("Okunan tüm metinlerdeki doğru yazılmış toplam kelime sayısı : " +
                toplamDoğruKelime);
        System.out.println("Okunan tüm metinlerdeki düzeltilen toplam kelime sayısı : " +
                düzeltilenToplamKelime);
        System.out.println("Mühendislik fakültesinde çalışan veri giriş operatörlerinin "
                + "doğru yazım oranı ortalaması : " + mühendislikOranToplam/mühendisSayısı * 100);
        System.out.println("Tıp fakültesinde çalışan veri giriş operatörlerinin "
                + "hatalı yazım oranı ortalaması : %" + tıpOranToplam/tıpOperatörSayısı * 100);
        System.out.println("Okunan tüm metinler için algoritmanın başarı oranı ortalaması : %" + 
                tümAlgoBaşarıToplamı/girdiSayisi * 100);
                     
    }
}
