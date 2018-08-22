SC2Player{
	var <>units, <>pos, <>selection, <>race, <>pid, <>unitsDied, <>apm;

	*new{|apm, x, y, pid|
		^super.new.init(apm, x, y, pid);
	}

	init{|apm, x, y, pid|
		this.units = Dictionary.new;
		this.pos = Point(x, y);
		this.apm = apm;
		this.pid = pid;
		this.race = nil;
		this.selection = List.newClear();
		this.unitsDied = 0;
	}

	unitBorn{|id, type_name, x, y, control|
		this.addUnit(id, type_name, x, y, this.pid, true);
		this.units.at(id).unitBorn();
	}

	unitInit{|id, type_name, control x, y|
		this.addUnit(id, type_name, x, y, this.pid, false);
		this.units.at(id).unitInit();

	}

	unitDone{|id|
		this.units.at(id).is_done = true;
		this.units.at(id).unitDone();
	}

	unitDied{|id, x, y|
		this.units.at(id).unitDied();
		this.units.removeAt(id);
		this.unitsDied = this.unitsDied + 1;
	}

	addUnit{|id, type_name, x, y, control, isdone|
		this.units.add(id -> SC2Unit.new(id, type_name, x, y, control, isdone ));
		if(this.race.isNil){
			if(type_name == "Drone"){ this.race = \Zerg};
			if(type_name == "Probe"){ this.race = \Protoss};
			if(type_name == "SCV"){ this.race = \Terran};
		};
	}
}