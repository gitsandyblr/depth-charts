-- insert data into sports table
insert into sports(sports_id, sports_name) values(1, 'NFL');
insert into sports(sports_id, sports_name) values(2, 'AFL');

-- insert data into team table
insert into team(team_id, team_name, team_sports_id) values(213, 'Arizona Cardinals', 1);
insert into team(team_id, team_name, team_sports_id) values(432, 'Chicago Bears', 1);
insert into team(team_id, team_name, team_sports_id) values(543, 'Green Bay Packers', 1);
insert into team(team_id, team_name, team_sports_id) values(765, 'New York Giants', 1);
insert into team(team_id, team_name, team_sports_id) values(345, 'Detroit Lions', 1);
insert into team(team_id, team_name, team_sports_id) values(764, 'Washington Commanders', 1);
insert into team(team_id, team_name, team_sports_id) values(346, 'Philadelphia Eagles', 1);
insert into team(team_id, team_name, team_sports_id) values(768, 'Tampa Bay Buccaneers', 1);

-- insert data into player_position table
insert into player_position(player_position_name, player_sports_id) values('LWR', 1);
insert into player_position(player_position_name, player_sports_id) values('RWR', 1);
insert into player_position(player_position_name, player_sports_id) values('SWR', 1);
insert into player_position(player_position_name, player_sports_id) values('LT', 1);
insert into player_position(player_position_name, player_sports_id) values('LG', 1);
insert into player_position(player_position_name, player_sports_id) values('C', 1);
insert into player_position(player_position_name, player_sports_id) values('RG', 1);
insert into player_position(player_position_name, player_sports_id) values('RT', 1);
insert into player_position(player_position_name, player_sports_id) values('TE', 1);
insert into player_position(player_position_name, player_sports_id) values('QB', 1);
insert into player_position(player_position_name, player_sports_id) values('RB', 1);
insert into player_position(player_position_name, player_sports_id) values('DE', 1);
insert into player_position(player_position_name, player_sports_id) values('NT', 1);
insert into player_position(player_position_name, player_sports_id) values('OLB', 1);
insert into player_position(player_position_name, player_sports_id) values('ILB', 1);
insert into player_position(player_position_name, player_sports_id) values('LCB', 1);
insert into player_position(player_position_name, player_sports_id) values('SS', 1);
insert into player_position(player_position_name, player_sports_id) values('FS', 1);
insert into player_position(player_position_name, player_sports_id) values('RCB', 1);
insert into player_position(player_position_name, player_sports_id) values('NB', 1);
insert into player_position(player_position_name, player_sports_id) values('PT', 1);
insert into player_position(player_position_name, player_sports_id) values('PK', 1);
insert into player_position(player_position_name, player_sports_id) values('LS', 1);
insert into player_position(player_position_name, player_sports_id) values('H', 1);
insert into player_position(player_position_name, player_sports_id) values('KO', 1);
insert into player_position(player_position_name, player_sports_id) values('PR', 1);
insert into player_position(player_position_name, player_sports_id) values('KR', 1);

insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(12, 'Tom Brady', 'QB', 0, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(11, 'Blaine Gabbert', 'QB', 1, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(2, 'Kyle Trask', 'QB', 2, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(13, 'Mike Evans', 'LWR', 0, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(1, 'Jaelon Darden', 'LWR', 1, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(10, 'Scott Miller', 'LWR', 2, 1, 768);
insert into player(player_id, player_name, player_position, player_position_depth, player_sports_id, player_team_id)
values(2, 'Kyle Trask', 'LWR', 3, 1, 768);

