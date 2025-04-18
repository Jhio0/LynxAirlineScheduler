import React, { useState, useEffect } from 'react';
import { SafeAreaView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { ExpandableCalendar, CalendarProvider, AgendaList } from 'react-native-calendars';
import { ProgressBar, MD3Colors } from 'react-native-paper';
import axios from 'axios';

import Icon from 'react-native-vector-icons/MaterialIcons';

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
  departureStation: string;
  arrivalStation: string;
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

const fetchEvents = async (): Promise<{ title: string; data: FlightEvent[] }[]> => {
  try {
    const response = await axios.get<FlightEvent[]>('http://192.168.1.109:3001/api/flights');
    const data = response.data;

    // Map the API data to match the calendar structure
    return data.map((flight: FlightEvent) => ({
      title: flight.date,
      data: [flight], // Directly pass the full FlightEvent object
    }));
  } catch (error) {
    console.error('Error fetching events:', error);
    return []; // Return an empty array in case of error
  }
};

export default function CalendarTab() {
  const [selectedDate, setSelectedDate] = useState(getTodayDate());
  const [events, setEvents] = useState<{ title: string, data: {[key: string]: any }[] }[]>([]); // State to hold the events data

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

  const formatTime = (time: string) => {
    if (!time) return ''; // Return an empty string if time is null or undefined
  
    const [hours, minutes] = time.split(':');
  
    // Check if hours or minutes are invalid (e.g., undefined or empty)
    if (!hours || !minutes) return time; // Return the original time if the format is invalid
  
    const formattedHours = parseInt(hours, 10).toString(); // Convert hours to a number to remove leading zero
    return `${formattedHours}:${minutes}`;
  };

  const calculateMaxDutyPeriod = (time: string) => { 
    if (!time) return 0;
  
    // Split the time into hours and minutes
    const [hours, minutes] = time.split(':');
    
    // Calculate decimal time
    const decimalTime = parseInt(hours, 10) + parseInt(minutes, 10) / 60;
  
    // Round the result to 2 decimal places
    return parseFloat(decimalTime.toFixed(1));
  };
  
  
  return (
    <SafeAreaView style={styles.container}>
      <CalendarProvider
        date={selectedDate}
        onDateChanged={(date) => setSelectedDate(date)}    
      >
        <ExpandableCalendar p-5
          initialPosition={ExpandableCalendar.positions.OPEN} 
          markedDates={markedDates}
          closeOnDayPress={true}
          pagingEnabled={true}
        />
        <AgendaList
          sections={filteredEvents.length > 0 ? filteredEvents : []}
          renderItem={({ item }) => (
          
            
             
            <TouchableOpacity style={styles.item}>
              <Text className='p-5 text-xl text-blue-700'>My Duty Period</Text>
              <Text className='border-t-2 border-gray-300'></Text>
              <View className='flex flex-row pl-5 justify-between'>
                  <Icon name="airplanemode-active" size={40} color="#4F8EF7" />
                  <View className='flex items-center justify-center'>
                    <Text className='text-lg'>{item.departureStation}</Text>
                  </View>
                  <View className='flex items-center justify-center'>
                    <Icon name="arrow-forward" size={20} color="#000000" />
                  </View>
                  <View className='flex items-center justify-center pr-5'>
                    <Text className='text-lg'>{item.arrivalStation}</Text>
                  </View>
              </View>
              <View className='flex flex-row pl-5 pt-5 justify-between'>
                <Text>Max Duty Period</Text>
                <Text>12:00</Text>
              </View>
              <View className='flex flex-row pl-5 pt-2 justify-between'>
                <Text>Duty Hours</Text>
                <Text>{formatTime(item.dutyHours)}</Text>
              </View>
              <View className='pl-5 pt-3'>
                <ProgressBar progress={calculateMaxDutyPeriod(item.dutyHours) / 24} theme={{ colors: { primary: 'blue' } }} style={progressStyle.progressBar}/>
              </View>
              <View className='flex flex-row pl-5 pt-3 justify-between'>
                <Text>Duty Period Ends</Text>
                <View className='flex flex-row'>
                  <Text>
                  {formatTime(item.dutyReportTime)} - {formatTime(item.dutyDebriefEnd)}
                  </Text>
                </View>
              </View>
              <View className='flex flex-row pl-5 pt-2 justify-between'>
                <Text>Pairing Activity</Text>
                <Text>{item.pairingActivity}</Text>
              </View>
              <View className='flex flex-row pl-5 pt-2 justify-between'>
                <Text>Flying Hours</Text>
                <Text>{formatTime(item.flyingHours)}</Text>
              </View>
            </TouchableOpacity>
          )}
          ListEmptyComponent={() => (
            <View style={{ padding: 10 }}>
              <Text>No events for the selected date.</Text>
            </View>
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

const progressStyle = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 16,
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
  progressBar: {
    width: '100%', // Adjust width
    height: 9,   // Adjust height
    borderRadius: 5, // Add rounded corners
  },
});