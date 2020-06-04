import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class Text
{
    
    DataEntryOperator yazar;
    String content;
    
    List girdiListe=new ArrayList();
    List wordList = new ArrayList();
    
    int doğruKelimeSayısı;
    //int hatalıKelimeSayısı;
    int düzeltilenKelimeSayısı;
    
    
        
    public Text() 
    {
        this(null, null);
    }
    
    public Text(DataEntryOperator yazar,String content) 
    {
        setYazar(yazar);
        setContent(content);
        girdiListele(this.content);
        wordsListele();
        yerDegistirme();
        ikiKereBasma();
        sagSolHatası();
    }
    
    public DataEntryOperator getYazar() 
    {
        return yazar;
    }

    public String getContent() 
    {
        return content;
    }

    public void setYazar(DataEntryOperator yazar)
    {
        this.yazar = yazar;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }
    
    
    //Bu metotta girdi.txt deki metinlerimizi listeye aktarma işlemi yapılmaktadır.
    private void girdiListele(String girdiAdi)
    {
        
    String girdiKelime;
    try
    {
            Scanner girdiOku = new Scanner(new FileReader(girdiAdi));
            girdiKelime = girdiOku.nextLine();
            String[] ilkSatır = girdiKelime.split(",");
            int ID = Integer.parseInt(ilkSatır[0]);
            String adSoyad = ilkSatır[1];
            String departman = ilkSatır[2];
            yazar = new DataEntryOperator(ID,adSoyad,departman);
            while (girdiOku.hasNextLine())
            {
                girdiKelime = girdiOku.nextLine();
                String[] array = girdiKelime.split(" ");
                int i;
                for (i = 0 ; i < array.length ; i++)
                {
                    girdiListe.add(array[i]);   
                }   
            }  
        }
        catch (IOException iox)
        {
            System.out.println("Dosya okunamıyor.");
        }
   
    }
    //Bu metotta words.txt deki kelimeleri listeye aktarma işlemi yapılmaktadır.
    private void wordsListele()
    {
        
        String word = null;
        BufferedReader sözlükOku;
        try 
        {
            sözlükOku = new BufferedReader(new FileReader("words.txt"));
            word = sözlükOku.readLine();
            wordList.add(word);
            
            while (word != null)
            {
                word = sözlükOku.readLine();
                wordList.add(word);     
            }
            sözlükOku.close();  
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println("fnf");
        } 
        catch (IOException ex)
        {
            System.out.println("ioex");
        }
            
    }
    //Bu metotta iki harfin yer değiştirmesinden kaynaklı bir hatanın bulunup düzeltilmesi gerçekleşmektedir.
    private void yerDegistirme()
    {
        int i;
        int j;
        String yeniKelime = "";
        BufferedReader sözlükOku;
        String word = "boş" ;
        
        try 
        {
                           
            for (i = 0 ; i <= girdiListe.size()-1 ; i++)
            {
                
                Boolean virgül = false;
                Boolean nokta = false;
                Boolean büyükHarf = false;
                sözlükOku = new BufferedReader(new FileReader("words.txt"));
                String girdiKelimeOku = (String) girdiListe.get(i);
                String girdiKelime = girdiKelimeOku.toLowerCase(Locale.ENGLISH);
                String büyükHarfListe = null;
                String ilkHarf = girdiKelime.substring(0,1);
                String[] alfabe = {"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
                for (String element:alfabe) {
                    if (element.equals(ilkHarf)) {
                        büyükHarf = true;
                        büyükHarfListe = element;
                    }
                }
                if (girdiKelime.endsWith(",")) 
                {
                    girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                    virgül = true;
                }
                if (girdiKelime.endsWith(".")) 
                {
                    girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                    nokta = true;
                }
                if (wordList.contains(girdiKelime) || girdiKelime.length()==1 || girdiKelime.length()==2)  {
                    doğruKelimeSayısı++;
                }
                else 
                {
                    word = sözlükOku.readLine();
                    while (word != null)
                    {
                        if (girdiKelime.length() == word.length())
                        {
                            if (girdiKelime.equals(word)) 
                            {
                            }
                            else 
                            {
                                for (j = 0 ; j <= (girdiKelime.length()-2) ; j++) 
                                {
                                    yeniKelime = girdiKelime.substring(0,j) +
                                        girdiKelime.charAt(j+1) + girdiKelime.charAt(j);
                                    if (j < (girdiKelime.length()-2)) 
                                    {
                                        yeniKelime += girdiKelime.substring((j + 2),(girdiKelime.length()));
                                    }                                
                                    if (yeniKelime.equals(word))
                                    {
                                        if (virgül == true) {
                                            yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                        if (büyükHarf == true) {
                                            yeniKelime = büyükHarfListe + yeniKelime.substring(1, yeniKelime.length());
                                        }
                                        girdiListe.set(i, yeniKelime);
                                        düzeltilenKelimeSayısı++;

                                    }
                                }

                            }

                        }
                        word = sözlükOku.readLine();
                    }
                }
            }

        } 
        catch (FileNotFoundException ex)
        {
            System.out.println("Words.txt bulunamadı..");
        } 
        catch (IOException ex) 
        {
            System.out.println("IOException");
        }

    } 
    //Bu metotta bir tuşa yanlışlıkla iki kere basılmış olması nedeni ile hatalı bir kelime yazılmışsa onu bulup düzeltme işlemi yapmaktadır.
    private void ikiKereBasma() 
    {
        int i;
        int j;
        BufferedReader sözlükOku;
        String word = "boş";
        String yeniKelime = "";
            try
            {
                for (i = 0 ; i <= girdiListe.size()-1 ; i++)
                {
                    Boolean virgül = false;
                    Boolean nokta = false;
                    sözlükOku = new BufferedReader(new FileReader("words.txt"));
                    String girdiKelimeOku = (String) girdiListe.get(i);
                    String girdiKelime = girdiKelimeOku.toLowerCase(Locale.ENGLISH);
                    if (girdiKelime.endsWith(",")) 
                    {
                        girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                        virgül = true;
                    }
                    if (girdiKelime.endsWith(".")) 
                    {
                        girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                        nokta = true;
                    }
                    if (wordList.contains(girdiKelime) || girdiKelime.length()==1 || girdiKelime.length()==2) {
                    
                    }
                    else
                    {
                        word = sözlükOku.readLine();
                        while (word != null) 
                        {
                            if (girdiKelime.length()-1 == word.length())
                            {
                                for (j = 0 ; j <= (girdiKelime.length()-2) ; j++) 
                                {
                                    if (girdiKelime.charAt(j) == girdiKelime.charAt(j+1)) 
                                    {
                                        yeniKelime = girdiKelime.substring(0, j+1);
                                        if (j < (girdiKelime.length()-2)) 
                                        {
                                            yeniKelime += girdiKelime.substring(j+2,girdiKelime.length());
                                        }  
                                        if (yeniKelime.equals(word))
                                        {
                                            if (virgül == true) {
                                            yeniKelime = yeniKelime + ",";
                                            }
                                            else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                            }
                                            girdiListe.set(i, yeniKelime);
                                            düzeltilenKelimeSayısı++;
                                        }
                                    }

                                }
                            }
                        word = sözlükOku.readLine();
                        }
                    }
                }
            }
            catch (FileNotFoundException ex) 
            {
                System.out.println("Words.txt bulunamadı..");
            } 
            catch (IOException ex) 
            {
                System.out.println("IOException");
            }
    }
    //Bu metotta  klavyeden yanlışlıkla bir sağ veya bir soldaki tuşa basılması nedeniyle oluşan hataları bulup düzeltme işlemi yapmaktadır.
    private void sagSolHatası() 
    {
        int i;
        int j;
        BufferedReader sözlükOku;
        String word = "boş";
        String yeniKelime = ""; 
        String klavyeSatır1 = "qwertyuiop";
        String klavyeSatır2 = "asdfghjkl";
        String klavyeSatır3 = "zxcvbnm";
        int klavyeSatırIndex = 0;
        try 
        {
            for (i = 0 ; i <= girdiListe.size()-1 ; i++) {
                Boolean virgül = false;
                Boolean nokta = false;
                sözlükOku = new BufferedReader(new FileReader("words.txt"));
                String girdiKelimeOku = (String) girdiListe.get(i);
                String girdiKelime = girdiKelimeOku.toLowerCase(Locale.ENGLISH);
                if (girdiKelime.endsWith(",")) 
                {
                    girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                    virgül = true;
                }
                if (girdiKelime.endsWith(".")) 
                {
                    girdiKelime = girdiKelime.substring(0, girdiKelime.length()-1);
                    nokta = true;
                }
                if (wordList.contains(girdiKelime) || girdiKelime.length()==1 || girdiKelime.length()==2) {
                    
                }
                else
                {
                    word = sözlükOku.readLine();
                    while (word != null) 
                    {
                        if (girdiKelime.equalsIgnoreCase(word)) {
                            break;
                        }
                        for (j = 0 ; j <= (girdiKelime.length()-1) ; j++)
                        {
                            String harf = girdiKelime.substring(j,j+1);
                            if (klavyeSatır1.contains(harf))
                            {
                               klavyeSatırIndex = klavyeSatır1.indexOf(harf);

                               if (klavyeSatır1.indexOf(harf) == 0) {
                                   String klavyeHarfSağ = klavyeSatır1.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);

                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                        yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                        yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else if (klavyeSatır1.indexOf(harf) == klavyeSatır1.length()-1) {
                                   String klavyeHarfSol = klavyeSatır1.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                   yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else {
                                   String klavyeHarfSağ = klavyeSatır1.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                            yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                                   else
                                   {
                                       String klavyeHarfSol = klavyeSatır1.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                       yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                       if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                           girdiListe.set(i, yeniKelime);
                                           düzeltilenKelimeSayısı++;
                                       }
                                   }
                               }

                            } 
                            else if (klavyeSatır2.contains(harf))
                            {
                               klavyeSatırIndex = klavyeSatır2.indexOf(harf);

                               if (klavyeSatır2.indexOf(harf) == 0) {
                                   String klavyeHarfSağ = klavyeSatır2.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);

                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else if (klavyeSatır2.indexOf(harf) == klavyeSatır2.length()-1) {
                                   String klavyeHarfSol = klavyeSatır2.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                   yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else {
                                   String klavyeHarfSağ = klavyeSatır2.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                                   else
                                   {
                                       String klavyeHarfSol = klavyeSatır2.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                       yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                       if(yeniKelime.equals(word)) {

                                            if (virgül == true) {
                                               yeniKelime = yeniKelime + ",";
                                            }
                                            else if (nokta == true) {
                                                yeniKelime = yeniKelime + ".";
                                            }
                                           girdiListe.set(i, yeniKelime);
                                           düzeltilenKelimeSayısı++;
                                       }
                                   }
                               }

                            } 
                            else if (klavyeSatır3.contains(harf))
                            {
                               klavyeSatırIndex = klavyeSatır3.indexOf(harf);

                               if (klavyeSatır3.indexOf(harf) == 0) {
                                   String klavyeHarfSağ = klavyeSatır3.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);

                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else if (klavyeSatır3.indexOf(harf) == klavyeSatır3.length()-1) {
                                   String klavyeHarfSol = klavyeSatır3.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                   yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                               }
                               else {
                                   String klavyeHarfSağ = klavyeSatır3.substring(klavyeSatırIndex+1, klavyeSatırIndex+2);
                                   yeniKelime = girdiKelime.replace(harf,klavyeHarfSağ);
                                   if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                       girdiListe.set(i, yeniKelime);
                                       düzeltilenKelimeSayısı++;
                                   }
                                   else
                                   {
                                       String klavyeHarfSol = klavyeSatır3.substring(klavyeSatırIndex-1, klavyeSatırIndex);
                                       yeniKelime = girdiKelime.replace(harf, klavyeHarfSol);
                                       if(yeniKelime.equals(word)) {
                                        if (virgül == true) {
                                           yeniKelime = yeniKelime + ",";
                                        }
                                        else if (nokta == true) {
                                            yeniKelime = yeniKelime + ".";
                                        }
                                           girdiListe.set(i, yeniKelime);
                                           düzeltilenKelimeSayısı++;
                                       }
                                   }
                               }

                            } 

                        }
                    word = sözlükOku.readLine();
                    }
                }
                    
            }
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println("Words.txt bulunamadı..");
        } 
        catch (IOException ex) 
        {
            System.out.println("IOException");
        }
        
      
    }
    //Yazdırma metodunda düzeltilen kelimeleri tekrardan metin şeklinde yazdırmak için kullanıyoruz.
    public void yazdırma() {
        int i;
        for(i=0;i<girdiListe.size();i++) {
            System.out.print(girdiListe.get(i) + " ");
            String satırKontrol = (String) girdiListe.get(i);
            if (satırKontrol.contains(".")) {
                System.out.println();
            }
        }
    }
}