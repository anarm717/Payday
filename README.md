# Payday

Proyekt modullar şəklindədir. Hər modulda aşağıdakı microservislər vardır.
1. ldap-service           ---
2. account-service        ---8080 portunda işləyir
3. notification-service   ---8082
4. portfolio-service      ---8081
5. stock-service          ---8083

Default olaraq h2 database istifadə olunur. Configurasiyaları dəyişib istənilən database istifadə edilə bilər.

İlkin mərhələdə ldap-service ilə fayl-dan user məlumatlarını oxuyaraq ldap security təmin olunmuşdur.
Vaxt azlığından ldap security bütün proyektə tətbiq edilməmişdir.

-----account-service-də olan metodlar:-----
1./account  Post metoddur.
{
   "address":"",
   "password":"",
    "userName":"",
	"email":"",
    "creditcard":"",
	"fullname":"",
	"creationdate":"",
    "balance":
}
2./account/{id} get metoddur
3./account/?name=   get metoddur
4./accounts/{userName}/decreaseBalance/{amount}  get metoddur
5./accounts/{userName}/increaseBalance/{amount}   get metoddur
Account servisi ilə yeni accountun yaradılması, id-yə və username görə axtarışı,
accountun balansının artırılması və azaldılması yerinı yetirilir. Burada exception üçün nümunələr vardır.
Məsələn eyni username təkrar vurula bilməməsi, axtararkən yoxdursa tapılmadı vəs kimi Customize olunmuş exceptionlar vardır.
Burada əlavə olaraq metodların request parametrlərinə validasiyalarda yazıla bilər.



-----notification-service olan metodlar:-----
1./notification post metoddur
{
     "message":"",
     "userName":"",
     "email":"",
     "orderId":      
 }
Notification-service  transaction-lari bazaya vuraraq userlərə gedəcək emailləri bazada saxlanilir. Eyni zamanda bu servisdə 5 saniyədən bir işləyən scheduled ilə payday.bank.email@gmail.com maili ilə bazaya düşmüş transactionlara uyğun maillərə göndərilir və göndərilmiş maillərin bazada sendStatus-u dəyişdirilir.
Bu servis digər servislərdə lazım olan yerlərdə çağrılır.


-----stock-service olan metodlar:-----
1./stocks/?query= get metoddur
1./stocks/all get metoddur
Stock-service  3 fərqli market üçün stockların qiymətini, ən son dəyişimini, dəyişim faizini almaq olar. All metodu ile hal-hazırda ala bildiyiniz stockları görmək mümkündür.
Burada  real stocklar üçün yahoo kimi servislərdə problem olduğu üçün ,  bazadan stocklar götürülür və arxada bir timer işləyir və daim sanki qiymətlər artıb azalır.


-----portfolio-service olan metodlar:-----
1./portfolio/{userName}   get metoddur
2./portfolio/{userName}   post metoddur
{
    "orderId":,
	"userName":"",
    "symbol":"",
    "orderFee":,
    "orderType":"",
    "price":,
    "quantity":
}
3. /report/{userName} get metod
Portfolio-service ilə account sahibləri stockları alıb sata bilirlər. Burada Alma və satma işi yerinə yetirilərkən accountun balansı yoxlanılır. 
Lazımi artım və azalmalar orada da nəzərə alınır. ALınmamış stockun satılması və ya aldığından artıq satmaq mümkün deyildir.
burada user adına görə hal hazırda əlində tutduğu stocklara baxmaq mümkündür. Bu siyahıda stockun hazırdakı qiyməti də nəzərə alınmışdır.
Eyni zamanda userName görə son 2 ayın alma və satma əməliyyatlarının  hesabatına da baxmaq olur. Burada satış və alış üçün volatility-ni də hesablanır.



Prosesləri test etmək üçün aşağıdakı ardıcıllığı izləyə bilərsiniz.
1. localhost:8080/account    post metodu
2. localhost:8081/portfolio/{userName}  post metodu
3. localhost:8081/portfolio/{userName}  get metod
4. localhost:8081/report/{userName}   get netod

Nümunə üçün account-service dockerize edilib və swagger əlavə edilib. http://localhost:8080/swagger-ui.html#/ swagger url-dir. Bütün servislərə eynisini tətbiq edə bilərik. Dockerize bir neçə variantla edə bilərdim. Build olunmuş jar versiyanı containerə atıb elə də edə bilərdim (nümunə üçün bu variantı da Dockerfile2 əlavə etdim) ancaq mən fərqli variantla etdim ki, jar build etməmiş də dockerize olacaq.
Həmçinin sadə .gitlab-ci.yml faylında CI/CD əlavə etdim proyekti git accounta deploy etdikdən sonra servis avtomatik docker containerlə qalxacaq 

Postman ilə yoxlamaq üçün payday.postman_collection.json export olunmuş faylından istifadə edə bilərsiniz.


