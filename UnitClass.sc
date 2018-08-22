SC2Unit{
	var <>unit_id, <>name, <>pos, <>control, <>is_done, <>sound, <>type;
	*new{| unit_id, name, x, y, control, is_done|
		^super.new.init(unit_id, name, x, y, control, is_done)
	}

	init{|unit_id, name, x, y, control, is_done|
		this.type = SC2Game.unitsDict.at(name);
		this.unit_id = unit_id;
		this.name = name;
		this.pos = Point(x, y);
		this.control = control;
		this.is_done = is_done;
	}

	unitInit{
		this.sound = Synth(\Init, [\freq, this.pos.x.linlin(0,1, 100, 1000) + this.pos.y.linlin(0,1, 100, 1000), \x, this.pos.x, \y, this.pos.y]);
	}

	unitDone{
		this.is_done = true;
		if(this.type == (this.type == \BuildingZer) || (this.type == \BuildingTer) ||( this.type == \BuildingPro),
			{this.sound = Synth(this.type, [\freq, rrand(100, 2000), \amp, 0.04, \x, this.pos.x, \y, this.pos.y]);
			"BuildingDone".postln;},
			{this.sound = Pdef(this.type).play}
				);
	}
	unitBorn{
		if((this.type == \BuildingZer) || (this.type == \BuildingTer) ||( this.type == \BuildingPro),
			{this.sound = Synth(this.type, [\freq, rrand(100, 2000), \amp, 0.04, \x, this.pos.x, \y, this.pos.y]);
			"BuildingBorn".postln;},
			{this.sound = Pdef(this.type).play}
				);
	}

	unitDied{
		if((this.type == \BuildingZer) || (this.type == \BuildingTer) ||( this.type == \BuildingPro),
			{this.sound.free;
			"BuildingDied".postln;},
			{this.sound.stop}
		);
	}
}

