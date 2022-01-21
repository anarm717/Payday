# Payday

Proyekt modullar şəklindədir. Hər modulda aşağıdakı microservislər vardır.
1. ldap-service
2. account-service
3. notification-service
4. portfolio-service
5. stock-service

İlkin mərhələdə ldap-service ilə fayl-dan user məlumatlarını oxuyaraq ldap security təmin olunmuşdur.
Vaxt azlığından ldap security bütün proyektə tətbiq edilməmişdir.

Account servisi ilə yeni accountun yaradılması, id-yə və username görə axtarışı,
accountun balansının artırılması və azaldılması yerinı yetirilir. Burada exception üçün nümunələr vardır.
Məsələn eyni username təkrar vurula bilməməsi, axtararkən yoxdursa tapılmadı vəs kimi Customize olunmuş exceptionlar vardır.
Burada əlavə olaraq metodların request parametrlərinə validasiyalarda yazıla bilər.

Notification-service email notification-ları əvəz etmək üçün yazılmışdır. 
Hal-hazırda email servisi xidməti olmadığı üçün email göndərməyi bazaya vuraraq userlərə gedəcək emailləri bazaya vurulur.
Bu servis digər servislərdə lazım olan yerlərdə çağrılır.

Stock-service  3 fərqli market üçün stockların qiymətini, ən son dəyişimini, dəyişim faizini almaq olar.
Burada  real stocklar üçün yahoo kimi servislərdə problem olduğu üçün  olmadığı üçün,  bazadan stocklar götürülür və arxada bir timer işləyir və daim sanki qiymətlər artıb azalır.

Portfolio-service ilə account sahibləri stockları alıb sata bilirlər. Burada Alma və satma işi yerinə yetirilərkən accountun balansı yoxlanılır. 
Lazımi artım və azalmalar orada da nəzərə alınır. ALınmamış stockun satılması və ya aldığından artıq satmaq mümkün deyildir.
burada user adına görə hal hazırda əlində tutduğu stocklara baxmaq mümkündür. Bu siyahıda stockun hazırdakı qiyməti də nəzərə alınmışdır.
Eyni zamanda son 2 ayın alma və satma əməliyyatlarının  hesabatına da baxmaq olur.

Nümunə üçün account-service dockerize edilib. Bütün servislərə eynisini tətbiq edə bilərik. Dockerize bir neçə variantla edə bilərdim. Build olunmuş jar versiyanı containerə atıb elə də edə bilərdim (nümunə üçün bu variantı da Dockerfile2 əlavə etdim) ancaq mən fərqli variantla etdim ki, jar build etməmiş də dockerize olacaq.
Həmçinin sadə .gitlab-ci.yml faylında CI/CD əlavə etdim proyekti git accounta deploy etdikdən sonra servis avtomatik docker containerlə qalxacaq 

Postman ilə yoxlamaq üçün payday.postman_collection.json export olunmuş faylından istifadə edə bilərsiniz.
