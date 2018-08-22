import Sc2EventFilter
import time
from pythonosc import osc_message_builder
from pythonosc import udp_client


#initialisierung aller wichtigen dinge
replay = Sc2EventFilter.EventFilter("jaedong_jyp.SC2Replay")
replay.getRelevantEvents()
replay.getMapInfo()
replay.filterProperUnitInitEvents()
client = udp_client.UDPClient("127.0.0.1", 57120)
msgs = list()
frame = 0    #aktueller frame
setup = osc_message_builder.OscMessageBuilder(address = "/Setup")
setup.add_arg(len(replay.replay.players))
setup.add_arg(replay.mapinfo.width)
setup.add_arg(replay.mapinfo.height)
setup = setup.build()
client.send(setup)
time.sleep(1)

#wiedergabe der events in (hoffentlich) echtzeit
for event in replay.mRelevantEvents:
    while event.frame > frame:  #warten falls kein event ansteht, bis event ansteht
        if msgs:
            for message in msgs:
                message = message.build()
                client.send(message)
                print(str(frame) + " " + str(event))
            time.sleep(1/17)
        frame = frame + 1
        #print(frame)
        time.sleep(1/16)
        msgs = list()
    if event.name == "UnitDiedEvent":
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/UnitDied"))
        msgs[-1].add_arg(event.unit_id)
        msgs[-1].add_arg(event.x)
        msgs[-1].add_arg(event.y)
    if event.name == "CameraEvent":
            if(event.player.pid < len(replay.replay.players)):
                msgs.append(osc_message_builder.OscMessageBuilder(address = "/Camera"))
                msgs[-1].add_arg(event.pid)
                msgs[-1].add_arg(event.x)
                msgs[-1].add_arg(event.y)
    if event.name == "SelectionEvent": 
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/Selection"))
        msgs[-1].add_arg(event.pid)
        msgs[-1].add_arg(event.new_unit_ids)
    if event.name == "UnitBornEvent":
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/UnitBorn"))
        msgs[-1].add_arg(event.unit_id)
        msgs[-1].add_arg(event.unit_type_name)
        msgs[-1].add_arg(event.control_pid)
        msgs[-1].add_arg(event.x)
        msgs[-1].add_arg(event.y)
    if event.name == "UnitInitEvent":
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/UnitInit"))
        msgs[-1].add_arg(event.unit_id)
        msgs[-1].add_arg(event.unit_type_name)
        msgs[-1].add_arg(event.control_pid)
        msgs[-1].add_arg(event.x)
        msgs[-1].add_arg(event.y)
    if event.name == "UnitDoneEvent":
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/UnitDone"))
        msgs[-1].add_arg(event.unit_id)
    if event.name == "PlayerStatsEvent":
        msgs.append(osc_message_builder.OscMessageBuilder(address = "/PlayerStat"))
        msgs[-1].add_arg(event.pid)
        msgs[-1].add_arg(event.minerals_current)
        msgs[-1].add_arg(event.vespene_current)
        msgs[-1].add_arg(event.minerals_collection_rate)
        msgs[-1].add_arg(event.vespene_collection_rate)
        msgs[-1].add_arg(event.food_used)    
    if event.name == "UnitOwnerChangeEvent":
        msg.append(osc_message_builder.OscMessageBuilder(address = "/OwnerChange"))
        msgs[-1].add_arg(event.unit_id)
        msgs[-1].add_arg(event.upkeep_pid)
       

 
