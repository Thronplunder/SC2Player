SC2Game{
	classvar <>unitsDict, <>campos;
	var <>players, <>campos, <>mapwidth, <>mapheight, <>allunits, <>ambibus;

	*initClass {
		this.unitsDict = (SC2Game.filenameSymbol.asString.dirname ++ "\\units.scd").load
	}

	*new{
		^super.new.init();
	}

	init{
		Server.default.waitForBoot({
			this.ambibus = Bus.audio(Server.default, 2);
			this.loadSynthDefs(this.ambibus);
			Server.default.sync();
			this.loadOSCDefs();
			this.loadPdefs();
			this.playDefaultSynths();
		});

	}
	setpos{|pid, x, y|
		this.players.atFail(pid, {this.players.add(pid -> SC2Player.new(0, 0, 0, pid))});
		this.campos = Point(x, y);
		this.players.at(pid).pos = Point(x, y);
	}

	setup{ arg players, mapwidth, maplength;
		"Setup".postln;
		this.players = Dictionary.new(players);
		(players).do({|i|
			this.players.add((i + 1) -> SC2Player.new(0, 0, 0, i + 1));
		});
		this.campos = Point(0,0);
		this.mapwidth = mapwidth;
		this.mapheight = maplength;
		this.allunits = Dictionary.new;
	}

	unitDied{|id, x, y|
		this.players.atFail(this.allunits.at(id).control, {this.players = this.players.add(this.allunits.at(id).control -> SC2Player.new(0, 0, 0, this.allunits.at(id).control))});
		this.players.at(this.allunits.at(id).control).unitDied(id, x, y);
		allunits.removeAt(id);
	}

	unitBorn{|id, type_name, control, x, y|
		this.players.atFail(control, {this.players = this.players.add(control -> SC2Player.new(0, 0, 0, control))});
		this.allunits.add(id -> SC2Unit.new(id, type_name, x, y, control, true));
		this.players.at(control).unitBorn(id, type_name, x, y, control);
	}

	unitInit{|id, type_name, control, x, y|
		this.players.atFail(control, {this.players.add(control -> SC2Player.new(0, 0, 0, control))});
		this.allunits.add(id -> SC2Unit.new(id, type_name, x, y, control, false));
		this.players.at(control).unitInit(id, type_name, x, y, control);
	}

	unitDone{|id|
		this.allunits.at(id).is_done = true;
		this.players.at(this.allunits.at(id).control).unitDone(id);
	}

	unitOwnerChange{|id, pid|
		this.players.at(pid).addUnit(id,this.allunits.at(id).name, this.allunits.at(id).pos.x, this.allunits.at(id).pos.y, pid, true);
		this.players.at(this.allunits.at(id).control).units.at(id).remove;
		this.allunits.at(id).control = pid;
	}

}
