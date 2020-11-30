
insert into head_quarter (country_code)
values 
('DK'),
('DE'),
('SE'),
('FI');

insert into hotel (id,name, address, city, distance_to_center, raiting, head_quarter_id)
values 
('000007','Scandic Eremitage', 'Klampenborgvej 230', 'Lyngby', 3.0, 0.7, 1),
('000016','Scandic Berlin Kurfürstendamm', 'Augsburger Str. 5','Berlin', 4.0, 0.9, 2),
('000151','Scandic Continental', 'Augsburger Str. 5','Stockholm', 4.0, 2.5, 3),
('012301','Scandic Helsinki Airport', 'Lentäjäntie 1','Helsinki', 5.0, 3.7, 4);



insert into room (max_capacity, price, type, hotel_id)
values
(2,1178,'S','000007'),
(3,1278,'D','000007'),
(5,1378,'T','000007'),
(8,1800,'F','000007'),
(100,9000,'M','000007'),
(2,1478,'S','000016'),
(3,1578,'D','000016'),
(5,1678,'T','000016'),
(8,2000,'F','000016'),
(100,15000,'M','000016'),
(2,1878,'S','000151'),
(3,1978,'D','000151'),
(5,2178,'T','000151'),
(8,2300,'F','000151'),
(100,16500,'M','000151'),
(2,2478,'S','012301'),
(3,2578,'D','012301'),
(5,2678,'T','012301'),
(8,2900,'F','012301'),
(100,20000,'M','012301');

insert into guest (passport_number)
values
('DK_khgig865845874598'),
('SE_gfekyyug897986987'),
('FI_lkjlk97607607687'),
('DE_pohhlgdlk87987435984');

insert into booking(arrival_is_late, guest_passport_number)
values
(true, 'DK_khgig865845874598'),
(false, 'SE_gfekyyug897986987'),
(false, 'FI_lkjlk97607607687'),
(false, 'DE_pohhlgdlk87987435984');

insert into room_booking(date_of_arrival, date_of_departure, room_id, booking_id)
values
('2008-11-11', '2008-11-12', 22, 1),
('2008-11-13', '2008-11-14', 28, 2),
('2008-08-01', '2008-08-12', 33, 2),
('2008-04-18', '2008-04-22', 37, 3),
('2009-12-24', '2009-12-27', 31, 4),
('2009-12-28', '2010-01-01', 23, 4);

