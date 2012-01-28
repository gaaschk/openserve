create table SecSystemUser(
	PrincipalID BIGINT PRIMARY KEY,
	Username VARCHAR(25),
	Password VARCHAR(150),
	LockedOut SMALLINT,
	LastSuccessfullLogin DATETIME
);

create table SecPrincipal(
	PrincipalID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50),
	Active SMALLINT
);

create table SecSystemRole(
	PrincipalID BIGINT PRIMARY KEY,
	Description VARCHAR(50)
);

create table SecPermission(
	PermissionID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50)
);

create table SecAssignedPermission(
	AssignedPermissionID BIGINT PRIMARY KEY AUTO_INCREMENT,
	PermissionID BIGINT,
	PrincipalID BIGINT
);

create table SecAssignedRole(
	RolePrincipalID BIGINT,
	UserPrincipalID BIGINT
);

alter table SecAssignedRole
add foreign key AssignedRole_SystemRole_FK(RolePrincipalID) references SecSystemRole(PrincipalID);

alter table SecAssignedRole
add foreign key AssignedRole_SystemUser_FK(UserPrincipalID) references SecSystemUser(PrincipalID);

alter table SecAssignedPermission
add foreign key AssignedPermission_Permission_FK(PermissionID) references SecPermission(PermissionID);

alter table SecAssignedPermission
add foreign key AssignedPermission_Principal_FK(PrincipalID) references SecPrincipal(PrincipalID);

alter table SecSystemUser
add foreign key SystemUser_Principal_FK(PrincipalID) references SecPrincipal(PrincipalID);

alter table SecSystemRole
add foreign key SystemRole_Principal_FK(PrincipalID) references SecPrincipal(PrincipalID);

insert into SecPrincipal(Name, Active) values ('Admin User', 1);

select (@userid:=PrincipalID) from SecPrincipal where Name = 'Admin User';

insert into SecSystemUser (PrincipalID, Username, Password, LockedOut, LastSuccessfullLogin) values (@userid, 'admin', '1b771698e9d4723bfd35818165db49b7', 0, curdate());

insert into SecPrincipal(Name, Active) values ('Admin Role', 1);

select (@roleid:=PrincipalID) from SecPrincipal where Name = 'Admin Role';

insert into SecSystemRole (PrincipalID, Description) values (@roleid, 'Admin Role');

insert into SecPermission (Name) values ('AddLoan');
insert into SecPermission (Name) values ('AddPayment');
insert into SecPermission (Name) values ('CreateAmortizationSchedule');
insert into SecPermission (Name) values ('ViewAccountSummary');

insert into SecAssignedPermission (PermissionID, PrincipalID) select PermissionID, @roleid from SecPermission;

insert into SecAssignedRole (RolePrincipalID, UserPrincipalID) values (@roleid, @userid);