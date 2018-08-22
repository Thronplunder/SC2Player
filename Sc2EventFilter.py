import sc2reader
import pythonosc
import time
from sc2reader.events import *

class EventFilter:
    mRelevantEvents = list()
    
    def __init__(self, replaypath):
        self.replay = sc2reader.load_replay( replaypath, load_map=1, load_level=4)
        print("init")
        
    def getMapInfo(self):
        self.mapinfo = self.replay.map.map_info
    
    def filterProperUnitInitEvents(self): #läuft :), nimmt events von RelevantEvents
        index = []
        for init in range(len(self.mRelevantEvents)):
            bool = 0
            if isinstance(self.mRelevantEvents[init], UnitInitEvent):
                for done in range(len(self.mRelevantEvents)):
                    if isinstance(self.mRelevantEvents[done], UnitDoneEvent):
                        if self.mRelevantEvents[init].unit_id == self.mRelevantEvents[done].unit_id: 
                            bool = 1
                if bool == 0:
                    index.append(init)
        index.reverse()
        for init in index:
            del self.mRelevantEvents[init]
                        
    
    def getRelevantEvents(self):
        print("GetRelevantEvents")
        for event in self.replay.events:
            if isinstance(event, CameraEvent) or \
            isinstance(event, UnitBornEvent) or isinstance(event, UnitDiedEvent) or \
            isinstance(event, UnitDoneEvent) or isinstance(event, UnitInitEvent) or  isinstance(event, PlayerStatsEvent) or isinstance(event, UnitOwnerChangeEvent):
                self.mRelevantEvents.append(event)
                
    def countInitAndDone(self):#nimmt von mRelevantEvents, prüft anzahl der unitinit und unit done events
        init = 0
        done = 0
        for event in self.mRelevantEvents:
            if isinstance(event, UnitInitEvent):
                init = init + 1
            if isinstance(event, UnitDoneEvent):
                done = done + 1
        print( "Init: " + str(init) + " Done: "+ str(done))
        
    def writeRelevantEvents(self):
        print("WriteRelevantEvents")
        RelevantEvents = open("Relevant_Events.txt", "w")
        i = 0
        for event in self.mRelevantEvents:
            if event.name == "SelectionEvent":
                RelevantEvents.write(str(event.frame) +" "+ str(event.name) +" "+ str(event.pid) + " "+ str(event.new_units) + "\n")
            elif event.name == "CameraEvent":
                RelevantEvents.write(str(event.frame) + " "+ str(event.name) + " " + str(event.pid) + " " + str(event.x) + " " + str(event.y) + "\n")
            elif event.name == "UnitBornEvent":
                if event.unit_upkeeper != None:
                    RelevantEvents.write(str(event.frame) + " " + str(event.name) + " " + str(event.upkeep_pid - 1) + " " + str(event.unit) + "\n" )
                
'''replay = EventFilter("replaytest.SC2Replay")
replay.getRelevantEvents()
replay.countInitAndDone()
replay.filterProperUnitInitEvents()
replay.countInitAndDone()'''