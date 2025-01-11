import java.util.Scanner;

public class FutbolLigFiksturVePuanDurumu {

    public static void main(String[] args) {
        Scanner oku = new Scanner(System.in);
        int menu = -1;
        int takimSayisi = 0;
        String[] takimlar = null; // Takımların isimlerini tutacak dizi
        String[][] fikstur = null; // Fikstür haftalarını tutacak dizi
        boolean fiksturOlusturuldu = false; // Fikstür oluşturulup oluşturulmadığını kontrol etmek için
        boolean ciftDevre = false;
        int[][] skorlar = null; // Skorları tutacak dizi
        int[] puanlar = null; // Puanları tutacak dizi
        int[] atilanGoller = null;  // Atılan goller
        int[] yenilenGoller = null;  // Yenilen goller       
        
        /* 
        do-while yapısı ile kullanıcan menü üzerinde işlem yapmasını sağlıyoruz
        switch case yapaısı ile de menü üzerinde belirttiğimiz işlemlerin yapılmasını sağlayan işlemri yapıyoruz
        */
        do {
            // Menü Seçeneklerini Göster
            System.out.println();
            System.out.println("=== ANA MENÜ ===");
            System.out.println("1. TAKIM SAYISI BELİRLE");
            System.out.println("2. TAKIM İSİMLERİNİ GİR");
            System.out.println("3. FİKSTÜR OLUŞTUR");
            System.out.println("4. SKOR GİR");
            System.out.println("5. PUAN DURUMU GÖR");
            System.out.println("6. ÇIKIŞ");
            System.out.println();
            System.out.print("Seçiminizi yapınız: ");

            // Kullanıcının Seçimini Al
            menu = oku.nextInt();

            // Switch ile Seçime Göre İşlem Yap
            switch (menu) {
                case 1: /*  case 1 de kullanıcan takım sayısını girmesini istiyoruz */
                    takimSayisi = takimSayisiBelirle(oku); // Takım sayısını belirle
                    takimlar = new String[takimSayisi + (takimSayisi % 2)];
                    fiksturOlusturuldu = false; // Yeni takım sayısı ile fikstür sıfırlanır
                    break;

                case 2:/*  case 2 de kullanıcandan takım isimlerini girmesini isityoruz
                        if-else yapısı kullanıca ilk önce takım sayısının girilmesini isityor takım sayısı girilmeden kaç tane takım ismi alıcağımızı bilemeyiz*/
                    if (takimSayisi > 0) {
                        takimlar = takimIsimleriGir(oku, takimSayisi); // Takım isimlerini al
                        System.out.println();
                        System.out.println("Takımlar başarıyla girildi.");
                    } else {
                        System.out.println("Öncelikle takım sayısını belirleyin.");
                    }
                    break;

                case 3:
                    /*  case 3 te kullanıcan alınan takımlar ile oluşturduğum fiksturOlustur methodu ile fikstur oluşturyoruz kullanın isteğine göre lig tek ve ya çift devreleri olmasını sağlıyoruz.
                    if -else yaspısı ile kullanıcı eğer takım sayısı ve takım isimlerini berillemdi ise kullanıcı takım sayısı ve takım adlarını berillemisini istiyoruz  */
                    if (takimlar != null && takimlar.length > 0) {
                        System.out.println();
                        System.out.print("Çift devreli lig olsun mu? (Evet: 1 / Hayır: 0): ");
                        ciftDevre = oku.nextInt() == 1;
                        
                        // Fikstür oluşturuluyor
                        fikstur = fiksturOlustur(takimlar, ciftDevre); // Takımlar ve çift devre parametresi ile fikstür oluştur
                        fiksturOlusturuldu = true; // Fikstür oluşturuldu
                        
                        // Skor ve puan dizilerini takım sayına göre değerler almasını sağlıyouz 
                        int toplamMacSayisi = ciftDevre ? (takimlar.length - 1) * 2 : takimlar.length - 1;
                        skorlar = new int[toplamMacSayisi][takimlar.length / 2];
                        puanlar = new int[takimlar.length];
                        atilanGoller = new int[takimlar.length];
                        yenilenGoller = new int[takimlar.length];
                        
                        System.out.println("Fikstür başarıyla oluşturuldu.");
                        System.out.println();
                        
                        // Fikstürü yazdır
                        yazdirFikstur(fikstur, ciftDevre);
                    } else {
                        System.out.println("Öncelikle takım sayısını ve takım isimlerini girin.");
                    }
                    break;

                case 4:/* case 4 te oluşturduğumuz fikstürün skorlarını girilmesinini skorGir methodu sağlıyouruz eğer kullanıcı. 
                    if else yapısı ile eğer kullanıcı fisktur olulturmadı ise ilk önce fisktur oluşturmasını istiyoruz.*/
                    if (fiksturOlusturuldu && skorlar != null) {
                        skorGir(oku, skorlar, fikstur, takimlar, puanlar, atilanGoller, yenilenGoller);
                    } else {
                        System.out.println("Öncelikle fikstür oluşturun.");
                    }
                    break;

                case 5:/* case 5 te ise kullanıcının girilen skorlar a göre puanlarının, averajların vb parametlerinin  berillenmesi sağlayığ kullanıcıya bir puan tablosu gösteriyoruz 
                    if-else yapısı ile kullanıcan ilk önce skorları girmesini istiyoruz skorlar girilmeden puan tablosu hesabı yapılamaz.*/
                    if (fiksturOlusturuldu && puanlar != null) {
                        puanDurumuGor(puanlar, atilanGoller, yenilenGoller, takimlar);
                    } else {
                        System.out.println("Öncelikle skorları girin.");
                    }
                    break;

                case 6:/* case 6 da ise kullanıcının uygulamadan çıkmasını sağlıyoruz */
                    System.out.println("ÇIKIŞ YAPILIYOR...");
                    break;

                default:
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
            }

        } while (menu != 6); // Menü 6 (Çıkış) olana kadar döngü devam eder

        oku.close(); // Scanner'ı kapat
    }
    // Takım Sayısı Belirleme Metodu
    // Kullanıcanda 2 ile 20 araında nir takım ismi girmesini istiyoruz 
    //if-else yapısı ise kullanıcı 2 ile 20 arasında bir sayı girmez ise hata mesajı göstericek.
    public static int takimSayisiBelirle(Scanner oku) {
        int takimSayisi;
        do {
            System.out.println();
            System.out.print("Lütfen 2 ile 20 arasında bir takım sayısı girin: ");
            
            takimSayisi = oku.nextInt();

            if (takimSayisi < 2 || takimSayisi > 20) {
                System.out.println("Geçersiz giriş! Takım sayısı 2 ile 20 arasında olmalıdır.");
            }
        } while (takimSayisi < 2 || takimSayisi > 20);
        
        System.out.println();
        System.out.println("Belirlenen takım sayısı: " + takimSayisi);
        return takimSayisi;
    }

    // Takım İsimlerini Girme Metodu
    //Kullanıcanda ntakim sayısı kadar isim alıyoruz eğer takım sayısı tek ise en son bay isimli bir takım ekliyoruz takimlar dizisine 
    public static String[] takimIsimleriGir(Scanner oku, int takimSayisi) {
        // Eğer takım sayısı tekse, 'BAY' eklemek için +1 yer ayır
        String[] takimlar = new String[takimSayisi + (takimSayisi % 2)];

        oku.nextLine(); 
        
        for (int i = 0; i < takimSayisi; i++) {
            System.out.println();
            System.out.print((i + 1) + ". takım ismini girin: ");
            
            takimlar[i] = oku.nextLine();
        }

        // Takım sayısı tekse, "BAY" adlı takımı ekle
        if (takimSayisi % 2 != 0) {
            takimlar[takimSayisi] = "BAY";
            System.out.println();
            System.out.println("Takım sayısı tek olduğu için 'BAY' adlı bir takım eklendi.");
        }
        return takimlar;
    }
    // Fikstür Oluşturma Metodu
    //Kullanıcan alınana takım sayısı ve takımlar ile fikstğr oluşturuyor hafata sayısı takımsayısı eksi bir olur eğer çift devreli bir lig ise takımsa sayısı eksi bir sonuncunun iki katı olur 
    public static String[][] fiksturOlustur(String[] takimlar, boolean ciftDevre) {
        int takimSayisi = takimlar.length;

        int toplamTur = takimSayisi - 1;
        String[][] fikstur = new String[toplamTur][takimSayisi / 2];

        // Takımların eşleşmeleri
        for (int tur = 0; tur < toplamTur; tur++) {
            for (int i = 0; i < takimSayisi / 2; i++) {
                int evSahibi = (tur + i) % (takimSayisi - 1);
                int deplasman = (takimSayisi - 1 - i + tur) % (takimSayisi - 1);

                if (i == 0) {
                    deplasman = takimSayisi - 1; // Son takımı BAY olarak belirliyoruz
                }

                fikstur[tur][i] = takimlar[evSahibi] + " vs " + takimlar[deplasman];
                System.out.println();
            }
        }
        // Çift devreli fikstür kontrolü
        if (ciftDevre) {
            String[][] ciftDevreFikstur = new String[toplamTur * 2][takimSayisi / 2];
             

            // İlk devreyi kopyala
            for (int tur = 0; tur < toplamTur; tur++) {
                for (int i = 0; i < takimSayisi / 2; i++) {
                    // İlk devreyi her bir maç için kopyalayalım
                    ciftDevreFikstur[tur][i] = fikstur[tur][i]; // Aynı maçı kopyala
                }
                

                for (int i = 0; i < takimSayisi / 2; i++) {
                    String[] takimlarMac = fikstur[tur][i].split(" vs ");
                    
                    // Çift devreli fikstürün ikinci yarısındaki eşleşmeleri oluşturuyoruz
                    ciftDevreFikstur[tur + toplamTur][i] = takimlarMac[1] + " vs " + takimlarMac[0];
                    
                    System.out.println();
                }
            }

            return ciftDevreFikstur; // Çift devreli fikstür
        }

        return fikstur; // Tek devreli fikstür
    }
    // Fikstürü Yazdırma Metodu
    //fikstür dizidinde oluşturulan maçları tek tek yazdırıyoruz hafta hafta ve maç maç 
    public static void yazdirFikstur(String[][] fikstur, boolean ciftDevre) {
        int toplamTur = fikstur.length;

        for (int tur = 0; tur < toplamTur; tur++) {
            
            System.out.println("Hafta " + (tur + 1) + ":");
            System.out.println();
            

            for (String mac : fikstur[tur]) {
                if (mac != null) {
                    System.out.println(mac);
                    System.out.println();
                }
            }

            
        }
    }
    // Skor Girme Metodu
    //Kullanıcıdan ilk önce skor girmke isteği haftayı girmesini istiyoruz eğer aralık dışı bir hafta girerse hata mesajı çıkar 
    //Bay takım ile eşleşen takım maçının 0-0 olarka kabul ediyoruz sontasında o haftaki maçları skorları sıraylısayla kullanıcan alıyoruz
    //Şeçilen hafta tamamlandığında kullanıcıya başka hafta için işlem yapmak isterse 1 menüye dönmek için ise 0 a basar
   
    public static void skorGir(Scanner oku, int[][] skorlar, String[][] fikstur, String[] takimlar, int[] puanlar, int[] atilanGoller, int[] yenilenGoller) {
        int toplamTur = fikstur.length;

        boolean devamEt = true;

        while (devamEt) {
            // Kullanıcıdan hafta seçimi al
            System.out.println();
            System.out.print("Hangi haftanın skorlarını girmek istiyorsunuz? (1-" + toplamTur + " / Ana menüye dönmek için 0): ");
            int hafta = oku.nextInt() - 1; // Haftayı 0 indeksli yapmak için 1 çıkartıyoruz

            if (hafta == -1) {
                devamEt = false; // Ana menüye geri dön
                continue;
            }

            if (hafta < 0 || hafta >= toplamTur) {
                System.out.println("Geçersiz hafta seçimi! Lütfen geçerli bir hafta seçin.");
                continue;
            }
            System.out.println();

            // Girilen skorları kontrol et
            boolean skorlarGirildi = false;
            for (int i = 0; i < fikstur[hafta].length; i++) {
                if (skorlar[hafta][i] != 0) {
                    skorlarGirildi = true;
                    break;
                }
            }

            if (skorlarGirildi) {
                System.out.println("Seçtiğiniz hafta için skorlar zaten girilmiş.");
                System.out.println();
                System.out.print("Bu hafta için skorları sıfırlamak istiyor musunuz? "
                                  + "(Evet: 1 / Hayır: 0): ");
                int karar = oku.nextInt();
                if (karar == 1) {
                    // Eski skorları sıfırlama
                    for (int i = 0; i < fikstur[hafta].length; i++) {
                        skorlar[hafta][i] = 0;
                    }
                    System.out.println();
                    System.out.println("Skorlar sıfırlanmıştır.");
                    continue; // Tekrar hafta seçimi için menüye geri dön
                } else if (karar == 0) {
                    continue; // Tekrar hafta seçimi için menüye geri dön
                }
            }

            System.out.println("Hafta " + (hafta + 1) + ":");
            for (int i = 0; i < fikstur[hafta].length; i++) {
                String[] takimlarMac = fikstur[hafta][i].split(" vs ");
                String evSahibiTakim = takimlarMac[0].trim();
                String deplasmanTakim = takimlarMac[1].trim();

                // Eğer bir takım "BAY" ise, skoru otomatik olarak 0 kabul et
                if (evSahibiTakim.equalsIgnoreCase("BAY") || deplasmanTakim.equalsIgnoreCase("BAY")) {
                    System.out.println();
                    System.out.println(evSahibiTakim + " vs " + deplasmanTakim + " maçında bay olduğu için 0-0 kabul edilecektir.");
                    skorlar[hafta][i] = 0; // Bay maçları için 0
                    continue;
                }

                // Skorları kullanıcıdan al
                System.out.println();
                System.out.println(evSahibiTakim + " vs " + deplasmanTakim);
                System.out.println();
                System.out.print(evSahibiTakim + " skorunu girin: ");
                int evSahibiSkoru = oku.nextInt();
                System.out.println();
                System.out.print(deplasmanTakim + " skorunu girin: ");
                int deplasmanSkoru = oku.nextInt();

                // Sonuçları kaydet (1: ev sahibi galip, -1: deplasman galip, 0: beraberlik)
                if (evSahibiSkoru > deplasmanSkoru) {
                    System.out.println();
                    System.out.println(evSahibiTakim + " MAÇI KAZANMIŞTIR!");
                    skorlar[hafta][i] = 1;
                    puanlar[takimIndex(takimlar, evSahibiTakim)] += 3;
                } else if (evSahibiSkoru < deplasmanSkoru) {
                    System.out.println();
                    System.out.println(deplasmanTakim + " MAÇI KAZANMIŞTIR!");
                    skorlar[hafta][i] = -1;
                    puanlar[takimIndex(takimlar, deplasmanTakim)] += 3;
                } else {
                    System.out.println();
                    System.out.println("MAÇ BERABERE BİTMİŞTİR!");
                    skorlar[hafta][i] = 0;
                    puanlar[takimIndex(takimlar, evSahibiTakim)] += 1;
                    puanlar[takimIndex(takimlar, deplasmanTakim)] += 1;
                }

                atilanGoller[takimIndex(takimlar, evSahibiTakim)] += evSahibiSkoru;
                yenilenGoller[takimIndex(takimlar, evSahibiTakim)] += deplasmanSkoru;
                atilanGoller[takimIndex(takimlar, deplasmanTakim)] += deplasmanSkoru;
                yenilenGoller[takimIndex(takimlar, deplasmanTakim)] += evSahibiSkoru;
            }

            System.out.println();
            System.out.println("Skorlar başarıyla girildi!"); // Skor girme işlemi tamamlandıktan sonra mesaj göster
            System.out.println();
            System.out.print("Başka bir hafta için skor girmek ister misiniz? "
                          + "(Evet: 1 / Hayır: 0 / Ana menüye dönmek için 2): ");
            int secim = oku.nextInt();
            if (secim == 0) {
                devamEt = false;
            } else if (secim == 2) {
                continue;
            }
        }
    }

    // Takımın dizideki indeksini bulma metodu
    private static int takimIndex(String[] takimlar, String takim) {
        for (int i = 0; i < takimlar.length; i++) {
            if (takimlar[i].equalsIgnoreCase(takim)) {
                return i;
            }
        }
        return -1; // Takım bulunamazsa -1 döner
    }



    
    // Puan Durumunu Görme Metodu
// Bu metod, takımların puan durumlarını büyükten küçüğe sıralayarak ekrana yazdırır. Eğer puanlar eşitse, averajlara göre sıralama yapılır.
public static void puanDurumuGor(int[] puanlar, int[] atilanGoller, int[] yenilenGoller, String[] takimlar) {
    System.out.printf("%-5s %-15s %-10s %-10s %-15s %-15s\n", "Sıra", "Takım", "Puan", "Averaj", "Atılan Gol", "Yenilen Gol");
    System.out.println();
    // Takımları sıralama
    for (int i = 0; i < takimlar.length - 1; i++) {
        for (int j = 0; j < takimlar.length - 1 - i; j++) {
            int averaj1 = atilanGoller[j] - yenilenGoller[j];
            int averaj2 = atilanGoller[j + 1] - yenilenGoller[j + 1];
            if (puanlar[j] < puanlar[j + 1] || (puanlar[j] == puanlar[j + 1] && averaj1 < averaj2)) {
                // Puan veya averaja göre sıralama yap
                swap(takimlar, j, j + 1);
                swap(puanlar, j, j + 1);
                swap(atilanGoller, j, j + 1);
                swap(yenilenGoller, j, j + 1);
            }
        }
    }

    // Sıralı takımları yazdırma
    int siralama = 1; // Sıralama numarası için yeni bir değişken
    for (int i = 0; i < takimlar.length; i++) {
        if (!takimlar[i].equalsIgnoreCase("BAY")) { // "BAY" takımını kontrol et ve yazdırma
            int averaj = atilanGoller[i] - yenilenGoller[i];
            System.out.printf("%-5d %-15s %-10d %-10d %-15d %-15d\n", siralama, takimlar[i], puanlar[i], averaj, atilanGoller[i], yenilenGoller[i]);
            siralama++; // Sıralama numarasını artır
        }
    }
}

// Swap metodu
// Bu metodlar, dizilerdeki iki elemanın yerini değiştirmek için kullanılır. Hem `String` dizileri hem de `int` dizileri için ayrı ayrı tanımlanmışlardır.
private static void swap(String[] array, int i, int j) {
    String geçici = array[i];
    array[i] = array[j];
    array[j] = geçici;
}

private static void swap(int[] array, int i, int j) {
    int geçici = array[i];
    array[i] = array[j];
    array[j] = geçici;
}
}