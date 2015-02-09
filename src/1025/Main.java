import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


/** 
 * poj1025 : <b>Department</b> <br/>
 * 时间模拟
 * @author Ervin.zhang
 */
public class Main {
    public static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    public static Map<String, Room> roomMap = new HashMap<String, Room>();
    public static Map<String, Elevator> elevatorMap = new HashMap<String, Elevator>();

    public static void main(String[] args) throws ParseException {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        List<Agent> agents = new ArrayList<Agent>();
        while (!".".equals(line.trim())) {
            Agent agent = new Agent();
            StringTokenizer st = new StringTokenizer(line);
            agent.code = st.nextToken().charAt(0);
            agent.startTime = sdf.parse(st.nextToken());
            line = scan.nextLine();
            while (!"0".equals(line.trim())) {
                st = new StringTokenizer(line);
                agent.rooms.put(st.nextToken(),
                        Integer.parseInt(st.nextToken()));
                line = scan.nextLine();
            }
            agents.add(agent);
            line = scan.nextLine();
        }
        Object[] as = agents.toArray();
        Arrays.sort(as);

        List<Agent> ags = new ArrayList<Agent>();
        for (Object a : as) {
            ags.add((Agent) a);
        }
        go(ags);
        scan.close();
    }

    public static Room getRoom(String id) {
        if (!roomMap.containsKey(id)) {
            Room room = new Room();
            room.floor = id.substring(0, 2);
            room.num = id.substring(2);
            roomMap.put(id, room);
            return room;
        } else {
            return roomMap.get(id);
        }
    }

    public static Elevator getElevator(String floor) {
        if (!elevatorMap.containsKey(floor)) {
            Elevator elevator = new Elevator();
            elevator.floor = floor;
            elevatorMap.put(floor, elevator);
            return elevator;
        } else {
            return elevatorMap.get(floor);
        }
    }

    public static Room getNextRoom(Agent agent) {
        Iterator<String> keyIt = agent.rooms.keySet().iterator();
        String nextRoomId = agent.nextRoom == null ? null
                : agent.nextRoom.floor + agent.nextRoom.num;
        while (keyIt.hasNext()) {
            String next = keyIt.next();
            if (nextRoomId == null) {
                return getRoom(next);
            }
            if (nextRoomId.equals(next)) {
                if (keyIt.hasNext()) {
                    return getRoom(keyIt.next());
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static void go(List<Agent> agents) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(findMinStartTime(agents));
        int finishCount = agents.size();
        while (finishCount > 0) {
            for (int i = 0; i < agents.size(); i++) {
                Agent agent = agents.get(i);
                if (agent.isFinish) {
                    continue;
                }
                if (agent.startTime.equals(currentTime.getTime())) {
                    Event e = new Event();
                    e.startTime = new Date(currentTime.getTimeInMillis());
                    e.endTime = new Date(currentTime.getTimeInMillis() + 30000);
                    agent.nextRoom = getNextRoom(agent);
                    if (agent.nextRoom.floor.equals("01")) {
                        e.type = 1; // to room
                        e.to = agent.nextRoom;
                    } else {
                        e.type = 2; // to elevator
                        e.to = getElevator(agent.nextRoom.floor);
                    }
                    agent.currentEvent = e;
                }
                if (agent.currentEvent != null
                        && agent.currentEvent.endTime != null
                        && agent.currentEvent.endTime.equals(currentTime
                                .getTime())) {
                    Event e = getNextEvent(agent);
                    if (e == null) {
                        finishCount--;
                    }
                    agent.events.add(agent.currentEvent);
                    agent.currentEvent = e;
                }
            }
            if (currentTime.get(Calendar.SECOND) % 5 == 0) {
                for (String key : elevatorMap.keySet()) {
                    Elevator elevator = elevatorMap.get(key);
                    if (elevator.waitAgents.size() > 0) {
                        Agent frontAgent = elevator.waitAgents.get(0);
                        frontAgent.currentEvent.endTime = currentTime.getTime();
                        frontAgent.events.add(frontAgent.currentEvent);
                        elevator.waitAgents.remove(0);
                        Event frontEvent = new Event();
                        frontEvent.startTime = frontAgent.currentEvent.endTime;
                        int floors = 0;
                        if (frontAgent.nextRoom == null) {
                            floors = Integer.parseInt(frontAgent.currentFloor) - 1;
                        } else {
                            floors = Integer
                                    .parseInt(frontAgent.nextRoom.floor)
                                    - Integer.parseInt(frontAgent.currentFloor);
                        }
                        frontEvent.endTime = addSeconds(frontEvent.startTime,
                                30 * floors);
                        frontEvent.type = 6;
                        frontEvent.from = frontAgent.currentEvent.to;
                        frontEvent.to = frontEvent.from;
                        frontAgent.currentEvent = frontEvent;
                    }
                }
            }
            currentTime.add(Calendar.SECOND, 1);
        }
        for (int i = 0; i < agents.size(); i++) {
            Agent agent = agents.get(i);
            System.out.println(agent.code);
            for (int j = 0; j < agent.events.size(); j++) {
                Event e = agent.events.get(j);
                if (e.startTime.equals(e.endTime)) {
                    continue;
                }
                System.out.print(sdf.format(e.startTime) + " "
                        + sdf.format(e.endTime) + " ");
                if ((e.type == 1 || e.type == 2) && e.from == null) {
                    System.out.println("Entry");
                } else if (e.type == 9) {
                    System.out.println("Exit");
                } else if (e.type == 1 || e.type == 2) {
                    System.out.print("Transfer from ");
                    if (e.from instanceof Room) {
                        System.out.print("room " + ((Room) (e.from)).floor
                                + ((Room) (e.from)).num + " ");
                    } else {
                        System.out.print("elevator ");
                    }
                    System.out.print("to ");
                    if (e.to instanceof Room) {
                        System.out.println("room " + ((Room) (e.to)).floor
                                + ((Room) (e.to)).num);
                    } else {
                        System.out.println("elevator");
                    }
                } else if (e.type == 3) {
                    System.out.println("Waiting in front of room "
                            + ((Room) (e.to)).floor + ((Room) (e.to)).num);
                } else if (e.type == 4) {
                    System.out.println("Waiting in elevator queue");
                } else if (e.type == 5) {
                    System.out.println("Stay in room " + ((Room) (e.to)).floor
                            + ((Room) (e.to)).num);
                } else if (e.type == 6) {
                    System.out.println("Stay in elevator");
                }
            }
            System.out.println();
        }
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static Event getNextEvent(Agent agent) {
        if (agent.currentEvent.type == 9) {
            agent.isFinish = true;
            return null;
        }
        Event e = new Event();
        e.from = agent.currentEvent.to;
        e.startTime = agent.currentEvent.endTime;
        if (agent.currentEvent.type == 1) { // to room
            if (agent.nextRoom.isUsed) {
                agent.nextRoom.insert(agent);
                e.type = 3; // wait room
            } else {
                e.endTime = addSeconds(
                        e.startTime,
                        agent.rooms.get(agent.nextRoom.floor
                                + agent.nextRoom.num));
                e.type = 5; // stay room
                agent.nextRoom.isUsed = true;
            }
            e.to = e.from;
        }
        if (agent.currentEvent.type == 2) { // to elevator
            Elevator elevator = getElevator(agent.currentFloor);
            elevator.insert(agent);
            e.to = e.from;
            e.type = 4; // wait elevator
        }
        if (agent.currentEvent.type == 5) { // stay room
            Room nextRoom = getNextRoom(agent);
            e.endTime = addSeconds(e.startTime, 10);
            if (nextRoom == null) {
                if (!agent.nextRoom.floor.equals("01")) {
                    e.type = 2;
                    e.to = getElevator(agent.nextRoom.floor);
                } else {
                    e.type = 9; // finish
                    e.endTime = addSeconds(e.startTime, 30);
                }
            } else {
                if (nextRoom.floor.equals(agent.nextRoom.floor)) {
                    e.type = 1;
                    e.to = nextRoom;
                } else {
                    e.type = 2;
                    e.to = getElevator(agent.nextRoom.floor);
                }
            }
            if (agent.nextRoom.waitAgents.size() > 0) {
                Agent frontAgent = agent.nextRoom.waitAgents.get(0);
                frontAgent.currentEvent.endTime = agent.currentEvent.endTime;
                frontAgent.events.add(frontAgent.currentEvent);
                agent.nextRoom.waitAgents.remove(0);
                Event frontEvent = new Event();
                frontEvent.startTime = frontAgent.currentEvent.endTime;
                frontEvent.endTime = addSeconds(
                        frontEvent.startTime,
                        frontAgent.rooms.get(frontAgent.nextRoom.floor
                                + frontAgent.nextRoom.num));
                frontEvent.type = 5;
                frontEvent.from = frontAgent.currentEvent.to;
                frontEvent.to = frontEvent.from;
                frontAgent.currentEvent = frontEvent;
            } else {
                agent.nextRoom.isUsed = false;
            }
            agent.nextRoom = nextRoom;
        }
        if (agent.currentEvent.type == 6) { // stay elevator
            e.endTime = addSeconds(e.startTime, 10);
            e.type = 1;
            e.to = agent.nextRoom;
            agent.currentFloor = agent.nextRoom != null ? agent.nextRoom.floor
                    : agent.currentFloor;
            if (e.to == null) {
                e.type = 9; // finish
                e.endTime = addSeconds(e.startTime, 30);
            }
        }
        return e;
    }

    public static Date findMinStartTime(List<Agent> agents) {
        Date min = agents.get(0).startTime;
        for (int i = 1; i < agents.size(); i++) {
            if (agents.get(i).startTime.compareTo(min) < 0) {
                min = agents.get(i).startTime;
            }
        }
        return min;
    }
}

class Agent implements Comparable<Agent> {
    public char code;
    public Date startTime;
    public Map<String, Integer> rooms = new LinkedHashMap<String, Integer>();
    public String currentFloor = "01";
    public Room nextRoom;
    public Event currentEvent;
    public Event nextEvent;
    public List<Event> events = new ArrayList<Event>();
    public boolean isFinish;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(code + " " + Main.sdf.format(startTime) + "\n");
        for (String key : rooms.keySet()) {
            sb.append(key + " " + rooms.get(key) + "\n");
        }
        return sb.toString();
    }

    public int compareTo(Agent o) {
        return code - o.code;
    }

}

class Event {
    Device from;
    Device to;
    int type;
    Date startTime;
    Date endTime;
}

class Device {
    public boolean isUsed;
    public List<Agent> waitAgents = new ArrayList<Agent>();

    public void insert(Agent agent) {
        int index = 0;
        for (index = 0; index < waitAgents.size(); index++) {
            if (agent.code < waitAgents.get(index).code) {
                break;
            }
        }
        waitAgents.add(index, agent);
    }
}

class Room extends Device {
    public String floor;
    public String num;
    public Date endTime;
}

class Elevator extends Device {
    public String floor;
}

}

class Agent implements Comparable<Agent> {
    public char code;
    public Date startTime;
    public Map<String, Integer> rooms = new LinkedHashMap<String, Integer>();
    public String currentFloor = "01";
    public Room nextRoom;
    public Event currentEvent;
    public Event nextEvent;
    public List<Event> events = new ArrayList<Event>();
    public boolean isFinish;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(code + " " + Main.sdf.format(startTime) + "\n");
        for (String key : rooms.keySet()) {
            sb.append(key + " " + rooms.get(key) + "\n");
        }
        return sb.toString();
    }

    public int compareTo(Agent o) {
        return code - o.code;
    }

}

class Event {
    Device from;
    Device to;
    int type;
    Date startTime;
    Date endTime;
}

class Device {
    public boolean isUsed;
    public List<Agent> waitAgents = new ArrayList<Agent>();

    public void insert(Agent agent) {
        int index = 0;
        for (index = 0; index < waitAgents.size(); index++) {
            if (agent.code < waitAgents.get(index).code) {
                break;
            }
        }
        waitAgents.add(index, agent);
    }
}

class Room extends Device {
    public String floor;
    public String num;
    public Date endTime;
}

class Elevator extends Device {
    public String floor;
}

