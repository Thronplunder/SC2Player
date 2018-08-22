+ SC2Game{

	loadSynthDefs{ |bus|
		"Load SynthDefs".postln;
		SynthDef(\Init, {|freq = 200, amp = 0.1, x = 0.2, y = 0.5|
			var snd = WhiteNoise.ar(amp);
			var line = Line.kr(0.01, 1, 18, doneAction:2);
			snd = BPF.ar(snd,  freq, 0.1);
			snd = snd * SinOsc.kr(line.linlin(0.01,1,0.01,3), 0, 0.2, 0.5);
			snd = snd * line;
			snd = PanB2.ar(snd, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\BuildingZer, {|freq = 400, amp = 0.1, x = 1, y = 1, detune|
			var snd = SinOscFB.ar(freq + LFNoise1.kr(0.2).range(-10, 10),0.8) ;
			snd = PanB2.ar(snd * amp * 0.2, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\BuildingPro, {|freq = 400, amp = 0.1, x = 1, y = 1, detune|
			var snd = Impulse.ar(freq * 5 + Rand(-30, 30), 0.02);
			snd = PanB2.ar(snd * amp, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\BuildingTer, {|freq = 400, amp = 0.1, x = 1, y = 1, detune|
			var snd = SinOsc.ar( freq * SinOsc.kr( 1.73 * freq),0 , amp);
			snd = PanB2.ar(snd * amp, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\ArmyZer, {|freq, amp, x, y, detune|
			var snd = SinOscFB.ar(freq,0.9, mul: amp * 0.6);
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd * 0.8, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\ArmyTer, {|freq, amp, x, y, tick, detune|
			var snd = SinOsc.ar( freq * SinOsc.kr(freq* x * y).range(-10, 10));
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd * amp * 0.3, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\ArmyPro, {|freq = 400, amp = 0.5, x = 0.2, y = 0.5, detune |
			var snd = PinkNoise.ar(0.4) + Impulse.ar(freq);
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd * amp * 1.1, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\WorkerTer, {|freq = 400, amp = 0.5, x = 0.2, y = 0.5, detune |
			var snd = LFSaw.ar(freq) * EnvGen.ar(Env.perc(), doneAction:2);
			snd = BPF.ar(snd, freq, 0.3, 1.2);
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd * amp, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\WorkerZer, {|freq, amp, x, y, detune|
			var snd = SinOsc.ar(freq + Rand(-10, 10),0 ,amp) * EnvGen.ar(Env.perc(), doneAction:2);
			snd = BPF.ar(snd, freq, 0.1);
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd * 1.2, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;


		SynthDef(\WorkerPro, {|freq = 400, amp = 0.5, x = 0.2, y = 0.5, tick, detune |
			var snd = Impulse.ar(freq, 0, amp) * EnvGen.ar(Env.perc(), doneAction:2);
			snd = snd * EnvGen.ar(Env.perc(), doneAction:2);
			snd = PanB2.ar(snd, Point(x * -1,y).rotate(-pi/2).angle/pi);
			Out.ar(bus, snd);
		}).add;

		SynthDef(\play, {
			var snd = In.ar(bus, 4);
			Out.ar(0, DecodeB2.ar(2, snd[0], snd[1], snd[2]));
		}).add;
		}

	playDefaultSynths {
		Synth.tail(Server.default, \play);


	}

	loadPdefs{
		Pdef(\WorkerZer, Pbind(
			\instrument, \WorkerZer,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.augmented2
		));
		Pdef(\WorkerPro, Pbind(
			\instrument, \WorkerPro,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.harmonicMinor
		));
		Pdef(\WorkerTer, Pbind(
			\instrument, \WorkerTer,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.phrygian
		));
		Pdef(\ArmyZer, Pbind(
			\instrument, \ArmyZer,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.augmented2
		));
		Pdef(\ArmyPro, Pbind(
			\instrument, \ArmyPro,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.harmonicMinor
		));
		Pdef(\ArmyTer, Pbind(
			\instrument, \ArmyTer,
			\dur, Pseq([1, 1, 3], inf),
			\degree, Prand([1, 2, 3, 4, 5, 6, 7], inf),
			\scale, Scale.phrygian
	));
	}

	loadOSCDefs{
		OSCFunc({arg msg;
			var players, width, height;
			#players, width, height = msg[1..].postln;
			this.setup(players, width, height);
		}, "/Setup");

		OSCFunc({ arg msg, time, addr, recvPort;
			var id, x, y;
			#id, x, y = msg[1..];
			[id, "Died"].postln;
			this.unitDied(id, x.linlin(0, this.mapwidth, -1, 1), y.linlin(0, this.mapheight, -1, 1));
		},"/UnitDied");

		OSCFunc({ arg msg, time, addr, recvPort;
			var  pid, x, y;
			#pid, x, y = msg[1..];
			this.setpos(pid, x.linlin(0, this.mapwidth, -1, 1), y.linlin(0, this.mapheight, -1, 1))
		}, "/Camera");

		OSCFunc({ arg msg, time, addr, recvPort;
			var pid, unit_id;
			#pid, unit_id = msg[1..];
		}, "/Selection");

		OSCFunc({ arg msg, time, addr, recvPort;
			var unit_id, unit_type_name, control_pid, x, y;
			#unit_id, unit_type_name, control_pid, x, y = msg[1..];
			[unit_id, control_pid,unit_type_name, "Born"].postln;
			this.unitBorn(unit_id, unit_type_name, control_pid,x.linlin(0, this.mapwidth, -1, 1), y.linlin(0, this.mapheight, -1, 1));
		}, "/UnitBorn");

		OSCFunc({ arg msg;
			var unit_id, unit_type_name, control_pid, x, y;
			#unit_id, unit_type_name, control_pid, x, y = msg[1..];
			[unit_id, unit_type_name, "Init"].postln;
			this.unitInit(unit_id, unit_type_name, control_pid, x.linlin(0, this.mapwidth, -1, 1), y.linlin(0, this.mapheight, -1, 1));
		}, "/UnitInit");

		OSCFunc({ arg msg, time, addr, recvPort;
			var id;
			#id = msg[1..];
			[id, "Done"].postln;
			this.unitDone(id);
		}, "/UnitDone");

		OSCFunc({ arg msg, time, addr, recvPort;
			var id, pid;
			#id, pid = msg[1..];
			[id, pid, "Change"].postln;
			this.unitOwnerChange(id, pid);
		}, "/OwnerChange");

		OSCFunc({ arg msg, time, addr, recvPort;
			var pid, minerals_current, vespene_current, minerals_collection_rate, vespene_colletion_rate, food_used;
			#pid, minerals_current, vespene_current, minerals_collection_rate, vespene_colletion_rate, food_used = msg[1..];
		}, "/PlayerStat");
	}
}