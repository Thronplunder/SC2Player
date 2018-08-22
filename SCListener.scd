NetAddr.localAddr
Server.default.boot()
n = NetAddr.new("127.0.0.1", 57120);    // create the NetAddr
~game = SC2Game.new

n.sendMsg("/Setup", 2, 1, 1);
~game.players
~game.mapheight
n.sendMsg("/UnitBorn", 2, "Nexus", 1, 100, 100);
n.sendMsg("/UnitBorn", 2, "Hatchery", 1, 100, 100);
n.sendMsg("/UnitDied", 1, 12, 13)
n.sendMsg("/UnitDied", 2, 12, 13)
n.sendMsg("/UnitInit", 1, "Pylon", 1, 12, 12);
n.sendMsg("/UnitDone", 1)
~game.allunits.values.do({|value|
	value.name.postln;
})
~game.allunits.size

SC2Game.unitsDict

Server.default.scope
Server.default.makeGui
~game.players.at(1).units