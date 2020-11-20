

drop database Cinema;

drop table ticketPrice;
drop table ticket;
drop table filmInfo;
drop table userData;
drop table genreOfFilm;
DROP table hallInfo;
drop table hallName;
drop table rating;
drop table filmDate;
drop table filmTime;
drop table session;


create database Cinema;
use Cinema


create table cinema_info(
id uniqueidentifier primary key NOT NULL,
name nvarchar(50) NOT NULL,
adress nvarchar(MAX) NOT NULL)

create table roles(
id int primary key identity(1,1),
role int NOT NULL
)

create table user_data(
id uniqueidentifier primary key NOT NULL,
login nvarchar(50) NOT NULL,
email nvarchar(50) NOT NULL,
password nvarchar(50) NOT NULL,
role_id int foreign key references roles(id)
)


create table hall_info(
id uniqueidentifier primary key NOT NULL,
cinema_id uniqueidentifier NOT NULL,
name nvarchar(50) NOT NULL ,
places int NOT NULL
)

create table film_info(
id uniqueidentifier primary key NOT NULL,
poster nvarchar(max) NOT NULL,
year int NOT NULL,
country nvarchar(MAX) NOT NULL,
duration int NOT NULL,
genre nvarchar(MAX) NOT NULL,
name nvarchar(MAX) NOT NULL,
description text 
)

create table rating(
id uniqueidentifier primary key,
film_id uniqueidentifier NOT NULL,
user_id uniqueidentifier NOT NULL,
rating float NOT NULL,
review nvarchar(max)
)

create table seance(
id uniqueidentifier primary key NOT NULL,
start_time smalldatetime NOT NULL,
end_time smalldatetime NOT NULL,
hall_id uniqueidentifier NOT NULL  ,
film_id uniqueidentifier NOT NULL ,
ticket_price float NOT NULL
)
create table tickets(
id uniqueidentifier primary key NOT NULL,
user_id uniqueidentifier NOT NULL,
seance_id uniqueidentifier NOT NULL,
place int NOT NULL)

alter table hall_info
add constraint Fk_HallInfo_Cascade
foreign key (cinema_id) references cinema_info(id) on delete cascade

alter table tickets
add constraint Fk_Ticket_id_user_Cascade
foreign key (user_id) references user_data(id) on delete cascade

alter table tickets
add constraint Fk_Ticket_Seance_id_Cascade
foreign key (seance_id) references seance(id) on delete cascade

alter table seance
add constraint Fk_Seance_Hall_id___Cascade
foreign key (hall_id) references hall_info(id) on delete cascade

alter table seance
add constraint Fk_Seance_Film_id__Cascade
foreign key (film_id) references film_info(id) on delete cascade

alter table rating
add constraint Fk_Rating_Film_id__Cascade
foreign key (film_id) references film_info(id) on delete cascade

alter table rating
add constraint Fk_Rating_User_id__Cascade
foreign key (user_id) references user_data(id) on delete cascade


insert into roles(role) values (1)
insert into roles(role) values (2)
insert into roles(role) values (3)

insert into user_data(id,login,email,password,role_id)
values('5096f422-e4d4-47c8-934c-9ac5fced23c7','Guest1234','Guest1234@gmail.com','Guest1234',3)


insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'32aca567-2fe2-480e-9a4d-340d0443833e',
'https://wallpapers.moviemania.io/phone/movie/454626/c6cd4c/sonic-the-hedgehog-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США, Япония, Канада',
99,
'фантастика, комедия, приключения, семейный',
'Соник',
'Мир ждал героя, и появился ёжик. Способный бегать со сверхзвуковой скоростью, Соник должен спасти Землю и не дать злому доктору Роботнику завладеть мировым господством.'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'31cfe46e-73f4-442f-8aa2-a52e3c8b2d53',
'https://wallpapers.moviemania.io/phone/movie/464052/aa0696/wonder-woman-1984-phone-wallpaper.jpg?w=1080&h=1920',
2019,
'Великобритания, Мексика, Испания, Канада, США',
141,
'фэнтези, боевик, приключения',
'Чудо женщина 1984',
'Влиятельный и успешный бизнесмен Лорд мечтает стать богом среди смертных. Для этого он не жалеет средств и собирает со всех уголков света разнообразные магические артефакты в попытке найти тот, который сможет подарить ему безграничную силу и могущество настоящего божества. В поисках ему помогает доктор Барбара Энн Минерва, специалист по древней истории. Так в руки Минервы попадает некий артефакт, который превращает её в дикую, кровожадную и неуправляемую женщину-кошку — Гепарду. Вне себя от ярости, она начинает охоту за Лордом, по чьей вине и превратилась в монстра.'
)



insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'6fc3bf03-576e-4531-be70-70ff943f9cf0',
'https://wallpapers.moviemania.io/phone/movie/475430/c5d6a6/artemis-fowl-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США',
120,
'фантастика, фэнтези, приключения, семейный',
'Артемиус Фаул',
'Укрытый от посторонних глаз, глубоко под поверхностью земной коры таится целый мир, населенный гномами, эльфами, пикси, лепреконами, брауни и другими волшебными существами. Они спрятались от людей и создали свою собственную весьма развитую цивилизацию. Их тайна так и оставалась бы нераскрытой, если бы не юный Артемис Фаул, прирожденный мошенник и единственный наследник древнего рода криминальных гениев. Он сумел не только найти доказательства существования подземного мира, но и придумал дерзкий план – ограбить его обитателей.'
)



insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'2a2bd15a-4ba4-4c38-93ba-433b77648c1a',
'https://wallpapers.moviemania.io/phone/movie/337401/da9cd1/mulan-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США',
84,
'мультфильм, мюзикл, фэнтези, приключения, семейный, военный, музыка',
'Мулан',
'Для великого народа наступили тяжелые времена: на страну напало воинственное племя гуннов. Переодевшись в мужскую одежду, Мулан присоединяется к другим воинам и отправляется в опасный поход к подножию заснеженных гор. Ее сопровождает забавный дракончик Мушу, который больше похож на маленькую собачку, чем на мифическое чудовище. Пытаясь скрыть тайну, они попадают в забавные ситуации и не подозревают, что их секрет вот - вот раскроется!'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'3540089d-1d8a-4b7b-b009-b9b9a58c6439',
'https://wallpapers.moviemania.io/phone/movie/508439/283cb6/onward-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США',
102,
'мультфильм, фэнтези, комедия, приключения, семейный',
'Вперед',
'Когда-то давно в сказочном мире царила магия, но после открытия электричества маги стали не нужны, и развитие пошло по техногенному пути. Теперь эльфы, тролли, гоблины, мантикоры, феи, кентавры и единороги летают на самолетах, пользуются автомобилями и мобильниками и ведут в целом прозаичную жизнь. Братья-эльфы Иэн и Барли Лайтфуты воспитывались одной мамой, потому что их отец умер, когда они были ещё совсем маленькими. На 16-летие Иэну достаётся посох отца, и магическое заклинание, которое должно вернуть того к жизни на один день, но волшебный кристалл исчезает, воскресив только нижнюю половину папы. Барли, большой любитель квестов, невероятно рад - прихватив полупапу, братья отправляются в приключение на поиски другого кристалла.'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'9fbae319-b793-43c5-9a4c-d3dac45260cb',
'https://wallpapers.moviemania.io/phone/movie/343668/a28993/kingsman-the-golden-circle-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США, Великобритания',
111,
'боевик, приключения',
'King’s Man: Начало',
'События фильма происходят во времена Первой мировой войны. Молодой Конрад в исполнении Харриса Дикинсона пытается помочь своей стране под наставничеством агента, которого играет Рэйф Файнс'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'e9b8bcdb-43b9-426b-9a39-3b388ffcc571',
'https://wallpapers.moviemania.io/phone/movie/443791/9c8705/underwater-phone-wallpaper.jpg?w=1080&h=1920',
2020,
'США',
95,
'ужасы, боевик',
'Под водой',
'Группа сотрудников большого подводного бурильного комплекса, располагающегося на дне Марианской впадины на глубине 11 км, переживает внезапное разрушение станции и сталкивается с неведомым океанским ужасом.'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'f4b23bb5-925b-46c3-8ccb-7d35ac90441b',
'https://wallpapers.moviemania.io/phone/movie/361743/306ec7/top-gun-maverick-phone-wallpaper.jpg?w=720&h=1280',
2020,
'США',
110,
'драма, боевик',
'Лучший стрелок',
'Искусный пилот истребителя Ф-14, курсант элитного училища ВМС США, летающий как бог и руководимый как в жизни, так и в небе инстинктами, а не разумом, влюбляется в инструктора училища, астрофизика, которая долгое время отказывает ему во взаимности. Уступит ли она его белозубой улыбке?'
)

insert into film_info(id,poster,year,country,duration,genre,name,description) values(
'b47a39c0-9ed2-4884-aea5-fda89ea3ad77',
'https://wallpapers.moviemania.io/phone/movie/551808/011932/black-christmas-phone-wallpaper.jpg?w=1080&h=1920',
2019,
'США, Новая Зеландия',
92,
'ужасы, триллер, детектив',
'Черное рождество',
'Незнакомец преследует группу студенток во время рождественских каникул.'
)





