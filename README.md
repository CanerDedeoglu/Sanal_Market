# Ürün Tanıtım ve Sipariş Uygulaması

Bu proje, bir firmanın ürünlerini kullanıcılara gösteren ve kullanıcıların bu ürünleri sipariş edebilmesini sağlayan bir Android uygulamasıdır. Uygulama, aynı zamanda Push Notification ile bildirim alabilme yeteneklerine sahiptir.

## Teslim Detayları

Bu proje, Turkcell Geleceği Yazanlar Android Final Sınavı için hazırlanmıştır. Projenin tüm ekran görüntüleri, `ekranlar` klasörü altında bulunmalıdır.

## Teknik Özellikler

- **Jetpack Compose:** Login Sayfası için kullanılmıştır.
- **Room Database:** Beğendiğim Ürünler için kullanılmıştır.
- **Push Notification:** Kullanıcılara bildirim göndermek için kullanılmıştır.
- **Activity ve Fragment:** Sayfa navigasyonu ve detay gösterimi için kullanılmıştır.
- **Drawer Navigation:** Uygulamanın navigasyonu için kullanılmıştır.
- **Remote Config:** Uygulama arkaplan rengini değiştirmek için kullanılmıştır.
- **GitHub:** Projenin versiyon kontrolü için kullanılmıştır.

## Drawer Navigation Menüsü

Navigasyon menüsünde şu bölümler bulunmaktadır:
- **Anasayfa:** İlk 30 ürünün listelendiği sayfa
- **Kategoriler:** Ürün kategorilerinin listelendiği sayfa
- **Ürün Arama:** Ürün arama sayfası
- **Beğendiğim Ürünler:** Kullanıcının beğendiği ürünlerin listelendiği sayfa
- **Siparişlerim:** Kullanıcının siparişlerinin listelendiği sayfa
- **Profil:** Kullanıcı profil sayfası
- **Çıkış Yap:** Uygulamadan çıkış yapma

## Kullanılan Kütüphaneler ve Teknolojiler

Projede herhangi bir kütüphane kullanımı serbesttir. Proje, kullanıcı login olduktan sonra Drawer Navigation kullanarak yönlendirme yapmaktadır. Her sayfa bir fragment olarak tasarlanmıştır (Login sayfası hariç). Detay bölümü için Activity kullanılmıştır. Tasarım konusunda özgürlük sağlanmıştır.

## API Dokümantasyonu
Proje, DummyJSON servis dokümantasyonunu kullanmaktadır. Daha fazla bilgi için: [DummyJSON API Dokümantasyonu](https://dummyjson.com/docs/)

# Ekran Görüntüleri

| Anasayfa Ekranı | Açılış Ekranı | Beğenilen Ürünler Ekranı |
| --------------- | ------------- | ----------------------- |
| ![Anasayfa Ekranı](Ekran%20Görüntüleri/Anasayfa%20Ekranı.png) | ![Açılış Ekranı](Ekran%20Görüntüleri/Açılış_ekranı.png) | ![Beğenilen Ürünler Ekranı](Ekran%20Görüntüleri/Beğenilen_Ürünler_Ekranı.png) |

| Kategoriler | Kategoriler Ürün Listesi | Login Ekranı |
| ----------- | ----------------------- | ------------ |
| ![Kategoriler](Ekran%20Görüntüleri/Kategoriler.png) | ![Kategoriler Ürün Listesi](Ekran%20Görüntüleri/Kategoriler_Ürün_Listesi.png) | ![Login Ekranı](Ekran%20Görüntüleri/Login%20Ekranı.png) |

| Navigation Drawer | Profil Ekranı 2 | Profil Ekranı |
| ----------------- | --------------- | ------------- |
| ![Navigation Drawer](Ekran%20Görüntüleri/Navigation_drawer.png) | ![Profil Ekranı 2](Ekran%20Görüntüleri/ProfiL_Ekranı_2.png) | ![Profil Ekranı](Ekran%20Görüntüleri/Profil_Ekranı.png) |

| Push Notification | Remote Config | Sipariş Ekranı |
| ----------------- | ------------- | -------------- |
| ![Push Notification](Ekran%20Görüntüleri/Push_Notification.png) | ![Remote Config](Ekran%20Görüntüleri/Remote_config.png) | ![Sipariş Ekranı](Ekran%20Görüntüleri/Sipariş_Ekranı.png) |

| Ürün Arama | Ürün Arama 2 | Ürün Detay Ekranı |
| ---------- | ------------ | ---------------- |
| ![Ürün Arama](Ekran%20Görüntüleri/Ürün_Arama.png) | ![Ürün Arama 2](Ekran%20Görüntüleri/Ürün_Arama_2.png) | ![Ürün Detay Ekranı](Ekran%20Görüntüleri/Ürün_Detay_Ekranı.png) |
