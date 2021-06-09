use company_db;

insert into wallet values (1, 23300);
insert into wallet values (2, 11200);
insert into wallet values (3, 52000);

insert into user_role values(1, 'CEO');
insert into user_role values(2, 'Customer');

insert into user_account values(1, 'Kirill', 'dc41a642e730da71b359b67a99989fb0', 'Kirill@gmail.com', true, 1);
insert into user_account values(2, 'Vladimir', '1a09554a01c24fd6c63e0ccaf3892dcf', 'vladimir@gmail.com', true, 2);
insert into user_account values(3, 'Kate', 'bca32062a89dba8eb1651a4f0ce1b7f0', 'kate@gmail.com', true, 3);

insert into account_role values(1, 1, 1);
insert into account_role values(2, 2, 2);
insert into account_role values(3, 3, 2);

insert into customer values(1, 'Siemens');
insert into customer values(2, 'Daimler');

insert into material values(1, '34Cr4', 0.62, 1);
insert into material values(2, '41Cr4', 0.66, 1);
insert into material values(3, '20Mn5', 0.50, 1);
insert into material values(4, 'Cr45E', 0.6, 0.5);
insert into material values(5, '25CrMo4', 0.72, 0.5);
insert into material values(6, 'X12CrMo5', 0.86, 0.5);
insert into material values(7, '80W2', 1.52, 0.6);

insert into technological_process (technological_process_name) values ('CRHG');
insert into technological_process (technological_process_name) values ('CSMHG');

insert into product values(1, 'R2314_shaft', 5.4, 3, 1);
insert into product values(2, 'R231423_shaft', 3.8, 3, 2);

insert into operation values(1, 'CUTTING', 0.16, 0.02);
insert into operation values(2, 'ROLLING', 0.05, 0.001);
insert into operation values(3, 'STAMPING', 0.08, 0.002);
insert into operation values(4, 'MILLING', 0.3, 0.04);
insert into operation values(5, 'GRINDING', 0.5, 0.07);
insert into operation values(6, 'TURNING', 0.3, 0.03);
insert into operation values(7, 'HARDENING', 0.4, 0.14);

insert into progress values(1, 0, 'not payed');
insert into progress values(2, 0, 'not payed');
insert into progress values(3, 0, 'not payed');

insert into operations_order (technological_process_id, operation_id) values (1, 1);
insert into operations_order (technological_process_id, operation_id) values (1, 2);
insert into operations_order (technological_process_id, operation_id) values (1, 5);
insert into operations_order (technological_process_id, operation_id) values (1, 7);
insert into operations_order (technological_process_id, operation_id) values (2, 1);
insert into operations_order (technological_process_id, operation_id) values (2, 3);
insert into operations_order (technological_process_id, operation_id) values (2, 4);
insert into operations_order (technological_process_id, operation_id) values (2, 7);
insert into operations_order (technological_process_id, operation_id) values (2, 5);