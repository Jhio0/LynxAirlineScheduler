import React, { useState, useEffect } from 'react';
import { SafeAreaView, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { ExpandableCalendar, CalendarProvider, AgendaList } from 'react-native-calendars';
import axios from 'axios';



// Define type for each marked date entry
type MarkedDate = {
  marked?: boolean;
  dotColor?: string;
  selected?: boolean;
  selectedColor?: string;
};

interface FlightEvent {
  flightID: number;
  pilotID: number;
  aircraftID: number;
  date: string;
  pairingActivity: string;
  dutyReportTime: string;
  departureStationID: string;
  arrivalStationID: string;
  dutyDebriefEnd: string;
  flyingHours: string;
  dutyHours: string;
  hotel: string;
  updatedBy: string;
  file: string;
}

const getTodayDate = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const fetchEvents = async (): Promise<{ title: string, data: { name: string, [key: string]: any }[] }[]> => {
  try {
    const response = await axios.get<FlightEvent[]>('http://192.168.1.109:3001/api/flights');
    const data = response.data;

    // Map the API data to the calendar event structure
    return data.map((flight: FlightEvent) => ({
      title: flight.date,
      data: [{
        name: `${flight.pairingActivity} at ${flight.dutyReportTime}`,
        pilotID: flight.pilotID,
        aircraftID: flight.aircraftID,
        flightID: flight.flightID,
        departureStation: flight.departureStationID,
        arrivalStation: flight.arrivalStationID,
        hotel: flight.hotel,
      }],
    }));
  } catch (error) {
    console.error('Error fetching events:', error);
    return []; // Return an empty array in case of error
  }
};

export default function CalendarTab() {
  const [selectedDate, setSelectedDate] = useState(getTodayDate());
  const [events, setEvents] = useState<{ title: string, data: { name: string, [key: string]: any }[] }[]>([]); // State to hold the events data

  useEffect(() => {
    // Fetch events on component mount
    const loadEvents = async () => {
      const fetchedEvents = await fetchEvents();
      setEvents(fetchedEvents);
      console.log(events)
    };

    loadEvents();
  }, []);

  // Filter events for the selected date
  const filteredEvents = events.filter(event => event.title === selectedDate) || [{ title: '', data: [] }];

  const markedDates: { [key: string]: MarkedDate } = {};
  events.forEach(event => {
    markedDates[event.title] = {
      marked: true,
      dotColor: 'orange',  // You can change this color
    };
  });
  markedDates[selectedDate] = {
    ...markedDates[selectedDate],
    selected: true,
    selectedColor: 'grey',  // Color for selected date
  };


  return (
    <SafeAreaView style={styles.container}>
      <CalendarProvider
        date={selectedDate}
        onDateChanged={(date) => setSelectedDate(date)}    
      >
        <ExpandableCalendar 
          initialPosition={ExpandableCalendar.positions.OPEN} 
          markedDates={markedDates}
          closeOnDayPress={true}
          pagingEnabled={true}
        />
        <AgendaList
          sections={filteredEvents.length ? filteredEvents : [{ title: 'No Events', data: [{ name: 'No events for this day' }] }]}
          renderItem={({ item }) => (
            <TouchableOpacity style={styles.item}>
              <Text>{item.name}</Text>
            </TouchableOpacity>
          )}
        />
      </CalendarProvider>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    display: 'flex',
    flex: 1,
    marginVertical: 10
  },
  item: {
    backgroundColor: 'white',
    padding: 10,
    marginVertical: 5,
    marginHorizontal: 10,
    borderRadius: 5,
  },
  sectionHeader: {
    backgroundColor: '#f2f2f2',
    paddingVertical: 5,
    paddingHorizontal: 10,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
  },
});
