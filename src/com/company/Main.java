package com.company;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static class Building {
        String streetName;
        Integer numberOfBuilding;
        Integer area;
        List<Floor> floors = new ArrayList<Floor>();

        public Building(String streetName, int numberOfBuilding)
        {
            this.streetName = streetName;
            this.numberOfBuilding = numberOfBuilding;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public Integer getNumberOfBuilding() {
            return numberOfBuilding;
        }

        public void setNumberOfBuilding(Integer numberOfBuilding) {
            this.numberOfBuilding = numberOfBuilding;
        }

        public Integer getArea() {
            int summ = 0;
            for (Floor floor : floors) {
                summ = summ + floor.calcFullArea();
            }
            return summ;
        }

        public void addFloor(Floor floor) {
            floors.add(floor);
        }

        public void removeFloor(int index) {
            floors.remove(index);
        }
    }


    public static class Room {
        Integer area;
        String type;

        public Room(String type, int area)
        {
            this.type = type;
            this.area = area;
        }

        public Integer getArea() {
            return area;
        }

        public void setArea(Integer area) {
            this.area = area;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


    public static class Apartment extends Room
    {
        public Apartment(Integer area)
        {
            super("Apartment", area);
        }
    }


    public static class Office extends Room
    {
        public Office(Integer area)
        {
            super("Office", area);
        }
    }


    public static abstract class Floor
    {
        Iterator iterator;
        public void setIterator(Iterator iterator) {
            this.iterator = iterator;
        }
        public abstract Integer calcFullArea();
    }


    public static class FirstFloor extends Floor {
        Room[] rooms;

        public FirstFloor(Integer numberOfRooms) {
            rooms = new Room[numberOfRooms];
        }

        @Override
        public Integer calcFullArea() {
            int summ = 0;
            while (!iterator.isDone()) {
                summ = summ + iterator.getNext().getArea();
            }
            return summ;
        }

        public void addRooms(Room[] rooms) {
            this.rooms = rooms;
        }

        public Room[] getRooms() {
            return rooms;
        }
    }

    public static class SecondFloor extends Floor
    {
        List<Room> rooms = new ArrayList<Room>();
        @Override
        public Integer calcFullArea()
        {
            int summ = 0;
            while(!iterator.isDone())
            {
                summ = summ + iterator.getNext().getArea();
            }
            return summ;
        }

        public void addRoom(Room room){
            this.rooms.add(room);
        }

        public List<Room> getRooms() {
            return rooms;
        }
    }


    public static abstract class Iterator
    {
        List<Room> rooms;
        String type;
        public Iterator(String type, List<Room> rooms)
        {
            this.type = type;
            this.rooms = rooms;
        }
        public abstract Room getNext();
        public abstract Boolean isDone();

        public Integer getArea()
        {
            int summ = 0;
            for (Room room : rooms) summ = summ + room.getArea();
            return summ;
        }
    }

    public static class FirstFloorIterator extends Iterator
    {
        Integer numberOfRooms;
        Integer counter;
        public FirstFloorIterator(String type, Room[] array)
        {
            super(type,processArray(array,type));
            counter = 0;
            numberOfRooms = this.rooms.size();
        }

        public static List<Room> processArray(Room[] array, String type)
        {
            List<Room> tempRooms = new ArrayList<Room>();
            if (type == null)
                tempRooms.addAll(Arrays.asList(array));
            else
                for (Room room : array)
                    if (room.type.equals(type))
                        tempRooms.add(room);
            return tempRooms;
        }

        @Override
        public Boolean isDone() {
            if (counter.equals(numberOfRooms))
            {
                counter = 0;
                return true;
            }
            else
                return false;
        }

        @Override
        public Room getNext()
        {
            Room temp = rooms.get(counter);
            counter++;
            return temp;
        }
    }

    public static class SecondFloorIterator extends Iterator
    {
        Integer numberOfRooms;
        Integer counter = 0;
        public SecondFloorIterator(String type, List<Room> array)
        {
            super(type, processArray(array, type));
            counter = 0;
            this.numberOfRooms = array.size();
        }

        public static List<Room> processArray(List<Room> array, String type)
        {
            List<Room> tempRooms = new ArrayList<Room>();
            if (type == null)
                tempRooms = array;
            else
                for (Room room : array)
                    if (room.type.equals(type))
                        tempRooms.add(room);
            return tempRooms;
        }

        @Override
        public Boolean isDone() {
            if (counter.equals(numberOfRooms))
            {
                counter = 0;
                return true;
            }
            else
                return false;
        }

        @Override
        public Room getNext()
        {
            Room temp = rooms.get(counter);
            counter++;
            return temp;
        }

    }


    public static void main(String[] args) {
        Building building = new Building("Lenina", 8);
        FirstFloor firstFloor = new FirstFloor(3);
        Room room11 = new Apartment(25);
        Room room12 = new Apartment(38);
        Room room13 = new Office(453);
        firstFloor.addRooms(new Room[]{room11,room12,room13});
        Iterator firstFloorIter = new FirstFloorIterator(null, firstFloor.getRooms());
        firstFloor.setIterator(firstFloorIter);
        SecondFloor secondFloor = new SecondFloor();
        Room room21 = new Apartment(30);
        Room room22 = new Office(100);
        Room room23 = new Apartment(110);
        Room room24 = new Office(50);
        secondFloor.addRoom(room21);
        secondFloor.addRoom(room22);
        secondFloor.addRoom(room23);
        secondFloor.addRoom(room24);
        Iterator secondFloorIter = new SecondFloorIterator(null, secondFloor.getRooms());
        secondFloor.setIterator(secondFloorIter);
        SecondFloor thirdsFloor = new SecondFloor();
        Room room31 = new Apartment(100);
        Room room32 = new Office(50);
        Room room33 = new Apartment(100);
        Room room34 = new Office(50);
        thirdsFloor.addRoom(room31);
        thirdsFloor.addRoom(room32);
        thirdsFloor.addRoom(room33);
        thirdsFloor.addRoom(room34);
        Iterator thirdsFloorIter = new SecondFloorIterator(null, thirdsFloor.getRooms());
        thirdsFloor.setIterator(thirdsFloorIter);
        building.addFloor(firstFloor);
        building.addFloor(secondFloor);
        building.addFloor(thirdsFloor);
        Iterator apartmentsIterator1 = new FirstFloorIterator("Apartment",firstFloor.getRooms());
        Iterator apartmentsIterator2 = new SecondFloorIterator("Apartment",secondFloor.getRooms());
        Iterator officeIterator2 = new SecondFloorIterator("Office",secondFloor.getRooms());

        System.out.printf("Area of all building = %d%n", building.getArea());
        System.out.printf("Area of all first floor = %d%n", firstFloor.calcFullArea());
        System.out.printf("Area of all second floor = %d%n", secondFloor.calcFullArea());
        System.out.printf("Area of apartments first floor = %d%n", apartmentsIterator1.getArea());
        System.out.printf("Area of apartments second floor = %d%n", apartmentsIterator2.getArea());
        System.out.printf("Area of offices second floor = %d%n", officeIterator2.getArea());

    }
}
